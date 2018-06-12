package com.anbang.qipai.game.plan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
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
public class GameService {

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

		List<GameServer> allServers = gameServerDao.findAll();
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

	public void offlineServer(String gameServerId) {
		gameServerDao.remove(gameServerId);
	}

	public void createGameRoom(GameRoom gameRoom) {
		gameRoomDao.save(gameRoom);
		String createMemberId = gameRoom.getCreateMemberId();
		MemberGameRoom mgr = new MemberGameRoom();
		mgr.setGameRoom(gameRoom);
		mgr.setMemberId(createMemberId);
		memberGameRoomDao.save(mgr);
	}

	public void createGameLaw(Game game, String name, String desc, String mutexGroupId, boolean vip) {
		GameLaw law = new GameLaw();
		law.setDesc(desc);
		law.setGame(game);
		law.setMutexGroupId(mutexGroupId);
		law.setName(name);
		law.setVip(vip);
		gameLawDao.save(law);
	}

	public void removeGameLaw(String lawId) {
		gameLawDao.remove(lawId);
	}

	public void addLawsMutexGroup(Game game, String name, String desc) {
		LawsMutexGroup lawsMutexGroup = new LawsMutexGroup();
		lawsMutexGroup.setDesc(desc);
		lawsMutexGroup.setGame(game);
		lawsMutexGroup.setName(name);
		lawsMutexGroupDao.save(lawsMutexGroup);
	}

	public void removeLawsMutexGroup(String groupId) {
		lawsMutexGroupDao.remove(groupId);
	}

}
