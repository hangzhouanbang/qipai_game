package com.anbang.qipai.game.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.service.GameServerMsgService;
import com.anbang.qipai.game.plan.bean.games.CanNotJoinMoreRoomsException;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.IllegalGameLawsException;
import com.anbang.qipai.game.plan.bean.games.LawsMutexGroup;
import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;
import com.anbang.qipai.game.plan.bean.games.NoServerAvailableForGameException;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.NotVIPMemberException;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.plan.service.MemberService;
import com.anbang.qipai.game.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.game.remote.vo.CommonRemoteVO;
import com.anbang.qipai.game.web.fb.RamjLawsFB;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.google.gson.Gson;

/**
 * 去玩游戏相关的控制器
 * 
 * @author Neo
 *
 */
@RestController
@RequestMapping("/game")
public class GamePlayController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private GameService gameService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private GameRoomCmdService gameRoomCmdService;

	@Autowired
	private GameServerMsgService gameServerMsgService;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersRomoteService;

	@Autowired
	private HttpClient httpClient;

	private Gson gson = new Gson();

	/**
	 * 创建瑞安麻将房间
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/create_ramj_room")
	@ResponseBody
	public CommonVO createRamjRoom(String token, @RequestBody List<String> lawNames) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Member member = memberService.findMember(memberId);
		MemberRights rights = member.getRights();

		GameRoom gameRoom;
		try {
			gameRoom = gameService.buildRamjGameRoom(memberId, lawNames);
		} catch (IllegalGameLawsException e) {
			vo.setSuccess(false);
			vo.setMsg("IllegalGameLawsException");
			return vo;
		} catch (NotVIPMemberException e) {
			vo.setSuccess(false);
			vo.setMsg("NotVIPMemberException");
			return vo;
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		} catch (NoServerAvailableForGameException e) {
			vo.setSuccess(false);
			vo.setMsg("NoServerAvailableForGameException");
			return vo;
		}

		// 普通会员开vip房扣金币
		if (!member.isVip() && gameRoom.isVip()) {
			int gold = rights.getPlanMemberCreateRoomDailyGoldPrice();
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}

		GameServer gameServer = gameRoom.getServerGame().getServer();
		// 游戏服务器rpc，需要手动httpclientrpc
		RamjLawsFB fb = new RamjLawsFB(lawNames);
		Request req = httpClient.newRequest(
				"http://" + gameServer.getDomainForHttp() + ":" + gameServer.getPortForHttp() + "/game/newgame");
		req.param("playerId", memberId);
		req.param("difen", fb.getDifen());
		req.param("taishu", fb.getTaishu());
		req.param("panshu", fb.getPanshu());
		req.param("renshu", fb.getRenshu());
		req.param("dapao", fb.getDapao());
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			resData = (Map) resVo.getData();
			gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}

		String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
		gameRoom.setNo(roomNo);

		gameService.createGameRoom(gameRoom);

		Map data = new HashMap();
		data.put("httpDomain", gameRoom.getServerGame().getServer().getDomainForHttp());
		data.put("httpPort", gameRoom.getServerGame().getServer().getPortForHttp());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("gameId", gameRoom.getServerGame().getGameId());
		data.put("token", resData.get("token"));
		vo.setData(data);
		return vo;

	}

	/**
	 * 加入房间。如果加入的是自己暂时离开的房间，那么就变成返回房间
	 */
	@RequestMapping(value = "/join_room")
	@ResponseBody
	public CommonVO joinRoom(String token, String roomNo) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Member member = memberService.findMember(memberId);
		MemberRights rights = member.getRights();

		GameRoom gameRoom = gameService.findRoomOpen(roomNo);
		if (gameRoom == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid room number");
			return vo;
		}
		String serverGameId = gameRoom.getServerGame().getGameId();

		// 处理如果是自己暂时离开的房间
		MemberGameRoom memberGameRoom = gameService.findMemberGameRoom(memberId, gameRoom.getId());
		if (memberGameRoom != null) {
			// 游戏服务器rpc返回房间
			GameServer gameServer = gameRoom.getServerGame().getServer();
			Request req = httpClient.newRequest(
					"http://" + gameServer.getDomainForHttp() + ":" + gameServer.getPortForHttp() + "/game/backtogame");
			req.param("playerId", memberId);
			req.param("gameId", serverGameId);
			Map resData;
			try {
				ContentResponse res = req.send();
				String resJson = new String(res.getContent());
				CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
				if (resVo.isSuccess()) {
					resData = (Map) resVo.getData();
				} else {
					vo.setSuccess(false);
					vo.setMsg(resVo.getMsg());
					return vo;
				}
			} catch (Exception e) {
				vo.setSuccess(false);
				vo.setMsg("SysException");
				return vo;
			}

			Map data = new HashMap();
			data.put("httpDomain", gameRoom.getServerGame().getServer().getDomainForHttp());
			data.put("httpPort", gameRoom.getServerGame().getServer().getPortForHttp());
			data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
			data.put("roomNo", gameRoom.getNo());
			data.put("token", resData.get("token"));
			data.put("gameId", serverGameId);
			vo.setData(data);
			return vo;
		}

		try {
			gameService.tryHasMoreRoom(memberId);
		} catch (CanNotJoinMoreRoomsException e) {
			vo.setSuccess(false);
			vo.setMsg("CanNotJoinMoreRoomsException");
			return vo;
		}

		// 普通会员加入vip房扣金币
		if (gameRoom.isVip() && !member.isVip()) {
			int gold = rights.getPlanMemberJoinRoomGoldPrice();
			CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for join room");
			if (!rvo.isSuccess()) {
				vo.setSuccess(false);
				vo.setMsg(rvo.getMsg());
				return vo;
			}
		}

		// 游戏服务器rpc加入房间
		GameServer gameServer = gameRoom.getServerGame().getServer();
		Request req = httpClient.newRequest(
				"http://" + gameServer.getDomainForHttp() + ":" + gameServer.getPortForHttp() + "/game/joingame");
		req.param("playerId", memberId);
		req.param("gameId", serverGameId);
		Map resData;
		try {
			ContentResponse res = req.send();
			String resJson = new String(res.getContent());
			CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
			if (resVo.isSuccess()) {
				resData = (Map) resVo.getData();
			} else {
				vo.setSuccess(false);
				vo.setMsg(resVo.getMsg());
				return vo;
			}
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg("SysException");
			return vo;
		}
		gameService.joinGameRoom(gameRoom, memberId);

		Map data = new HashMap();
		data.put("httpDomain", gameRoom.getServerGame().getServer().getDomainForHttp());
		data.put("httpPort", gameRoom.getServerGame().getServer().getPortForHttp());
		data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
		data.put("roomNo", gameRoom.getNo());
		data.put("token", resData.get("token"));
		data.put("gameId", serverGameId);
		vo.setData(data);
		return vo;

	}

	/**
	 * 游戏服务器上线
	 * 
	 * @param gameServer
	 * @return
	 */
	@RequestMapping(value = "/game_server_online")
	@ResponseBody
	public CommonVO gameserveronline(@RequestBody GameServer gameServer) {
		CommonVO vo = new CommonVO();
		gameService.onlineServer(gameServer);
		gameServerMsgService.gameServerOnline(gameServer);
		return vo;
	}

	/**
	 * 游戏服务器下线
	 * 
	 * @param gameServer
	 * @return
	 */
	@RequestMapping(value = "/game_server_offline")
	@ResponseBody
	public CommonVO gameserveroffline(@RequestBody String[] gameServerIds) {
		CommonVO vo = new CommonVO();
		gameService.offlineServer(gameServerIds);
		gameServerMsgService.gameServerOffline(gameServerIds);
		return vo;
	}

	/**
	 * 添加玩法
	 * 
	 * @param game
	 * @param name
	 * @param desc
	 * @param mutexGroupId
	 * @param vip
	 * @return
	 */
	@RequestMapping(value = "/add_law")
	@ResponseBody
	public CommonVO addlaw(@RequestBody GameLaw law) {
		CommonVO vo = new CommonVO();
		gameService.createGameLaw(law);
		gameServerMsgService.createGameLaw(law);
		return vo;
	}

	/**
	 * 删除玩法
	 * 
	 * @param lawId
	 * @return
	 */
	@RequestMapping(value = "/remove_law")
	@ResponseBody
	public CommonVO removelaw(String lawId) {
		CommonVO vo = new CommonVO();
		gameService.removeGameLaw(lawId);
		gameServerMsgService.removeGameLaw(lawId);
		return vo;
	}

	/**
	 * 添加玩法互斥组
	 * 
	 * @param game
	 * @param name
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/add_mutexgroup")
	@ResponseBody
	public CommonVO addmutexgroup(@RequestBody LawsMutexGroup lawsMutexGroup) {
		CommonVO vo = new CommonVO();
		gameService.addLawsMutexGroup(lawsMutexGroup);
		gameServerMsgService.addLawsMutexGroup(lawsMutexGroup);
		return vo;
	}

	/**
	 * 删除玩法互斥组
	 * 
	 * @param lawId
	 * @return
	 */
	@RequestMapping(value = "/remove_mutexgroup")
	@ResponseBody
	public CommonVO removemutexgroup(String groupId) {
		CommonVO vo = new CommonVO();
		gameService.removeLawsMutexGroup(groupId);
		gameServerMsgService.removeLawsMutexGroup(groupId);
		return vo;
	}

	/**
	 * 查询玩家当前正在游戏的房间
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/query_membergameroom")
	public CommonVO queryMemberGameRoom(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		List<MemberGameRoom> roomList = gameService.queryMemberGameRoomForMember(memberId);
		vo.setMsg("room list");
		vo.setData(roomList);
		return vo;
	}

	/**
	 * 房间到时定时器，每小时
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	private void removeGameRoom() {
		long deadlineTime = System.currentTimeMillis();
		List<GameRoom> roomList = gameService.findExpireGameRoom(deadlineTime);
		List<String> ids = new ArrayList<>();
		for (GameRoom room : roomList) {
			String id = room.getId();
			ids.add(id);
			String no = room.getNo();
			gameRoomCmdService.removeRoom(no);
			Game game = room.getGame();
			String serverGameId = room.getServerGame().getGameId();
			gameService.expireMemberGameRoom(game, serverGameId);
		}
		gameService.expireGameRoom(ids);
	}
}
