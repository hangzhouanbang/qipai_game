package com.anbang.qipai.game.plan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.anbang.qipai.game.msg.GameServerMsgConstant;
import com.anbang.qipai.game.msg.channel.source.GameServerSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.CanNotJoinMoreRoomsException;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.IllegalGameLawsException;
import com.anbang.qipai.game.plan.bean.games.LawsMutexGroup;
import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;
import com.anbang.qipai.game.plan.bean.games.NoServerAvailableForGameException;
import com.anbang.qipai.game.plan.bean.games.ServerGame;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.NotVIPMemberException;
import com.anbang.qipai.game.plan.dao.GameLawDao;
import com.anbang.qipai.game.plan.dao.GameRoomDao;
import com.anbang.qipai.game.plan.dao.GameServerDao;
import com.anbang.qipai.game.plan.dao.LawsMutexGroupDao;
import com.anbang.qipai.game.plan.dao.MemberDao;
import com.anbang.qipai.game.plan.dao.MemberGameRoomDao;
import com.anbang.qipai.game.util.TimeUtil;

@Component
@EnableBinding(GameServerSource.class)
public class GameService {

	public static final int GAME_SERVER_STATE_RUNNINT = 0;
	public static final int GAME_SERVER_STATE_STOP = 1;

	@Autowired
	private GameLawDao gameLawDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private GameServerDao gameServerDao;

	@Autowired
	private GameRoomDao gameRoomDao;

	@Autowired
	private MemberGameRoomDao memberGameRoomDao;

	@Autowired
	private LawsMutexGroupDao lawsMutexGroupDao;

	@Autowired
	private GameServerSource gameServerSource;

	public GameLaw findGameLaw(Game game, String lawName) {
		return gameLawDao.findByGameAndName(game, lawName);
	}

	/**
	 * 创建瑞安麻将房间
	 */
	public GameRoom buildRamjGameRoom(String memberId, List<String> lawNames) throws IllegalGameLawsException,
			NotVIPMemberException, NoServerAvailableForGameException, CanNotJoinMoreRoomsException {
		Member member = memberDao.findById(memberId);
		MemberRights rights = member.getRights();

		int memberRoomsCount = memberGameRoomDao.count(memberId);
		if (rights.getRoomsCount() <= memberRoomsCount) {
			throw new CanNotJoinMoreRoomsException();
		}

		List<GameServer> allServers = gameServerDao.findServersByState(Game.ruianMajiang,GameService.GAME_SERVER_STATE_RUNNINT);
		if (allServers == null || allServers.isEmpty()) {
			throw new NoServerAvailableForGameException();
		}
		Random r = new Random();
		GameServer gameServer = allServers.get(r.nextInt(allServers.size()));
		ServerGame serverGame = new ServerGame();
		serverGame.setServer(gameServer);
		GameRoom gameRoom = new GameRoom();
		gameRoom.setServerGame(serverGame);

		List<GameLaw> laws = new ArrayList<>();
		lawNames.forEach((name) -> laws.add(gameLawDao.findByGameAndName(Game.ruianMajiang, name)));
		gameRoom.setLaws(laws);
		if (!gameRoom.validateLaws()) {
			throw new IllegalGameLawsException();
		}
		gameRoom.calculateVip();
		if (gameRoom.isVip() && !member.isVip()) {
			Date d = new Date();
			long startTime = TimeUtil.getDayStartTime(d);
			long endTime = TimeUtil.getDayEndTime(d);
			int todayCreateVipRoomsCount = gameRoomDao.count(startTime, endTime, memberId, true);
			if (rights.getPlanMemberMaxCreateRoomDaily() <= todayCreateVipRoomsCount) {
				throw new NotVIPMemberException();
			}
		}
		gameRoom.setCurrentPanNum(0);
		gameRoom.setDeadlineTime(System.currentTimeMillis() + (rights.getRoomsAliveHours() * 60 * 60 * 1000));
		gameRoom.setGame(Game.ruianMajiang);
		if (lawNames.contains("sj")) {
			gameRoom.setPanCountPerJu(4);
		} else if (lawNames.contains("bj")) {
			gameRoom.setPanCountPerJu(8);
		}else if (lawNames.contains("sej")){
			gameRoom.setPanCountPerJu(12);
		}else if (lawNames.contains("slj")) {
			gameRoom.setPanCountPerJu(16);
		} else {
			gameRoom.setPanCountPerJu(4);
		}

		if (lawNames.contains("er")) {
			gameRoom.setPlayersCount(2);
		} else if (lawNames.contains("sanr")) {
			gameRoom.setPlayersCount(3);
		} else if (lawNames.contains("sir")) {
			gameRoom.setPlayersCount(4);
		} else {
			gameRoom.setPlayersCount(4);
		}
		gameRoom.setCreateTime(System.currentTimeMillis());
		gameRoom.setCreateMemberId(memberId);
		return gameRoom;

	}

	/**
	 * 创建放炮麻将房间
	 */
	public GameRoom buildFpmjGameRoom(String memberId, List<String> lawNames) throws IllegalGameLawsException,
			NotVIPMemberException, NoServerAvailableForGameException, CanNotJoinMoreRoomsException {
		Member member = memberDao.findById(memberId);
		MemberRights rights = member.getRights();

		int memberRoomsCount = memberGameRoomDao.count(memberId);
		if (rights.getRoomsCount() <= memberRoomsCount) {
			throw new CanNotJoinMoreRoomsException();
		}

		List<GameServer> allServers = gameServerDao.findByGame(Game.fangpaoMajiang);
		if (allServers == null || allServers.isEmpty()) {
			throw new NoServerAvailableForGameException();
		}
		Random r = new Random();
		GameServer gameServer = allServers.get(r.nextInt(allServers.size()));
		ServerGame serverGame = new ServerGame();
		serverGame.setServer(gameServer);
		GameRoom gameRoom = new GameRoom();
		gameRoom.setServerGame(serverGame);

		List<GameLaw> laws = new ArrayList<>();
		lawNames.forEach((name) -> laws.add(gameLawDao.findByGameAndName(Game.fangpaoMajiang, name)));
		gameRoom.setLaws(laws);
		if (!gameRoom.validateLaws()) {
			throw new IllegalGameLawsException();
		}
		gameRoom.calculateVip();
		if (gameRoom.isVip() && !member.isVip()) {
			Date d = new Date();
			long startTime = TimeUtil.getDayStartTime(d);
			long endTime = TimeUtil.getDayEndTime(d);
			int todayCreateVipRoomsCount = gameRoomDao.count(startTime, endTime, memberId, true);
			if (rights.getPlanMemberMaxCreateRoomDaily() <= todayCreateVipRoomsCount) {
				throw new NotVIPMemberException();
			}
		}
		gameRoom.setCurrentPanNum(0);
		gameRoom.setDeadlineTime(System.currentTimeMillis() + (rights.getRoomsAliveHours() * 60 * 60 * 1000));
		gameRoom.setGame(Game.fangpaoMajiang);
		if (lawNames.contains("sj")) {
			gameRoom.setPanCountPerJu(4);
		} else if (lawNames.contains("bj")) {
			gameRoom.setPanCountPerJu(8);
		}else if (lawNames.contains("sej")){
			gameRoom.setPanCountPerJu(12);
		}else if (lawNames.contains("slj")) {
			gameRoom.setPanCountPerJu(16);
		} else {
			gameRoom.setPanCountPerJu(4);
		}

		if (lawNames.contains("er")) {
			gameRoom.setPlayersCount(2);
		} else if (lawNames.contains("sanr")) {
			gameRoom.setPlayersCount(3);
		} else if (lawNames.contains("sir")) {
			gameRoom.setPlayersCount(4);
		} else {
			gameRoom.setPlayersCount(4);
		}
		gameRoom.setCreateTime(System.currentTimeMillis());
		gameRoom.setCreateMemberId(memberId);
		return gameRoom;
	}

	/**
	 * 创建温州麻将房间
	 */
	public GameRoom buildWzmjGameRoom(String memberId, List<String> lawNames) throws IllegalGameLawsException,
			NotVIPMemberException, NoServerAvailableForGameException, CanNotJoinMoreRoomsException {
		Member member = memberDao.findById(memberId);
		MemberRights rights = member.getRights();

		int memberRoomsCount = memberGameRoomDao.count(memberId);
		if (rights.getRoomsCount() <= memberRoomsCount) {
			throw new CanNotJoinMoreRoomsException();
		}

		List<GameServer> allServers = gameServerDao.findByGame(Game.wenzhouMajiang);
		if (allServers == null || allServers.isEmpty()) {
			throw new NoServerAvailableForGameException();
		}
		Random r = new Random();
		GameServer gameServer = allServers.get(r.nextInt(allServers.size()));
		ServerGame serverGame = new ServerGame();
		serverGame.setServer(gameServer);
		GameRoom gameRoom = new GameRoom();
		gameRoom.setServerGame(serverGame);

		List<GameLaw> laws = new ArrayList<>();
		lawNames.forEach((name) -> laws.add(gameLawDao.findByGameAndName(Game.wenzhouMajiang, name)));
		gameRoom.setLaws(laws);
		if (!gameRoom.validateLaws()) {
			throw new IllegalGameLawsException();
		}
		gameRoom.calculateVip();
		if (gameRoom.isVip() && !member.isVip()) {
			Date d = new Date();
			long startTime = TimeUtil.getDayStartTime(d);
			long endTime = TimeUtil.getDayEndTime(d);
			int todayCreateVipRoomsCount = gameRoomDao.count(startTime, endTime, memberId, true);
			if (rights.getPlanMemberMaxCreateRoomDaily() <= todayCreateVipRoomsCount) {
				throw new NotVIPMemberException();
			}
		}
		gameRoom.setCurrentPanNum(0);
		gameRoom.setDeadlineTime(System.currentTimeMillis() + (rights.getRoomsAliveHours() * 60 * 60 * 1000));
		gameRoom.setGame(Game.wenzhouMajiang);
		if (lawNames.contains("sj")) {
			gameRoom.setPanCountPerJu(4);
		} else if (lawNames.contains("bj")) {
			gameRoom.setPanCountPerJu(8);
		} else if (lawNames.contains("sej")){
			gameRoom.setPanCountPerJu(12);
		} else if (lawNames.contains("slj")) {
			gameRoom.setPanCountPerJu(16);
		} else {
			gameRoom.setPanCountPerJu(4);
		}

		if (lawNames.contains("er")) {
			gameRoom.setPlayersCount(2);
		} else if (lawNames.contains("sanr")) {
			gameRoom.setPlayersCount(3);
		} else if (lawNames.contains("sir")) {
			gameRoom.setPlayersCount(4);
		} else {
			gameRoom.setPlayersCount(4);
		}
		gameRoom.setCreateTime(System.currentTimeMillis());
		gameRoom.setCreateMemberId(memberId);
		return gameRoom;
	}

	public void onlineServer(GameServer gameServer) {
		gameServer.setOnlineTime(System.currentTimeMillis());
		gameServerDao.save(gameServer);
	}

	public void offlineServer(String[] gameServerIds) {
		gameServerDao.remove(gameServerIds);
	}

	public void createGameRoom(GameRoom gameRoom) {
		gameRoomDao.save(gameRoom);
		String createMemberId = gameRoom.getCreateMemberId();
		MemberGameRoom mgr = new MemberGameRoom();
		mgr.setGameRoom(gameRoom);
		mgr.setMemberId(createMemberId);
		memberGameRoomDao.save(mgr);
	}

	public void joinGameRoom(GameRoom gameRoom, String memberId) {
		MemberGameRoom mgr = new MemberGameRoom();
		mgr.setGameRoom(gameRoom);
		mgr.setMemberId(memberId);
		memberGameRoomDao.save(mgr);
	}

	public void createGameLaw(GameLaw law) {
		gameLawDao.save(law);
	}

	public void removeGameLaw(String lawId) {
		gameLawDao.remove(lawId);
	}

	public void addLawsMutexGroup(LawsMutexGroup lawsMutexGroup) {
		lawsMutexGroupDao.save(lawsMutexGroup);
	}

	public void removeLawsMutexGroup(String groupId) {
		lawsMutexGroupDao.remove(groupId);
	}

	public GameRoom findRoomOpen(String roomNo) {
		return gameRoomDao.findRoomOpen(roomNo);
	}

	public void tryHasMoreRoom(String memberId) throws CanNotJoinMoreRoomsException {
		Member member = memberDao.findById(memberId);
		MemberRights rights = member.getRights();
		int memberRoomsCount = memberGameRoomDao.count(memberId);
		if (rights.getRoomsCount() <= memberRoomsCount) {
			throw new CanNotJoinMoreRoomsException();
		}
	}

	public MemberGameRoom findMemberGameRoom(String memberId, String GameRoomId) {
		return memberGameRoomDao.findByMemberIdAndGameRoomId(memberId, GameRoomId);
	}

	public void ruianMajiangPlayerQuitQame(String serverGameId, String playerId) {
		memberGameRoomDao.remove(Game.ruianMajiang, serverGameId, playerId);
	}

	public void fangpaoMajiangPlayerQuitQame(String serverGameId, String playerId) {
		memberGameRoomDao.remove(Game.fangpaoMajiang, serverGameId, playerId);
	}

	public void wenzhouMajiangPlayerQuitQame(String serverGameId, String playerId) {
		memberGameRoomDao.remove(Game.wenzhouMajiang, serverGameId, playerId);
	}

	public void expireMemberGameRoom(Game game, String serverGameId) {
		memberGameRoomDao.removeExpireRoom(game, serverGameId);
	}

	public List<MemberGameRoom> queryMemberGameRoomForMember(String memberId) {
		return memberGameRoomDao.findMemberGameRoomByMemberId(memberId);
	}

	public List<GameRoom> findExpireGameRoom(long deadlineTime) {
		return gameRoomDao.findExpireGameRoom(deadlineTime, false);
	}

	public void expireGameRoom(List<String> ids) {
		gameRoomDao.updateGameRoomFinished(ids, true);
	}

	public void gameRoomFinished(Game game, String serverGameId) {
		gameRoomDao.updateFinishGameRoom(game, serverGameId, true);
		memberGameRoomDao.removeExpireRoom(game, serverGameId);
	}

	public void panFinished(Game game, String serverGameId, int no, List<String> playerIds) {
		gameRoomDao.updateGameRoomCurrentPanNum(game, serverGameId, no);
		memberGameRoomDao.updateMemberGameRoomCurrentPanNum(game, serverGameId, playerIds, no);
	}

	public GameRoom findRoomByGameAndServerGameGameId(Game game, String serverGameId) {
		return gameRoomDao.findRoomByGameAndServerGameGameId(game, serverGameId);
	}

	public void stopGameServer(List<String> ids) {
        try {
            this.gameServerDao.updateGameServerState(ids,GameService.GAME_SERVER_STATE_STOP);
        } catch (Exception e) {
            CommonMO commonMO=new CommonMO();
            commonMO.setMsg(GameServerMsgConstant.STOP_GAME_SERVERS_FAILED);
            commonMO.setData(ids);
            this.gameServerSource.gameServer().send(MessageBuilder.withPayload(commonMO).build());
        }
    }

	public void recoverGameServer(List<String> ids){
        try {
            this.gameServerDao.updateGameServerState(ids,GAME_SERVER_STATE_RUNNINT);
        } catch (Throwable e) {
            CommonMO commonMO=new CommonMO();
            commonMO.setMsg(GameServerMsgConstant.RECOVER_GAME_SERVERS_FAILED);
            commonMO.setData(ids);
            this.gameServerSource.gameServer().send(MessageBuilder.withPayload(commonMO).build());
        }
    }

}
