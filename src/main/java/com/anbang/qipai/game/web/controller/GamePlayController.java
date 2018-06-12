package com.anbang.qipai.game.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.service.GameServerMsgService;
import com.anbang.qipai.game.plan.bean.games.CanNotJoinMoreRoomsException;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.IllegalGameLawsException;
import com.anbang.qipai.game.plan.bean.games.NoServerAvailableForGameException;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.NotVIPMemberException;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.plan.service.MemberService;
import com.anbang.qipai.game.remote.service.QipaiMembersRomoteService;
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
	private QipaiMembersRomoteService qipaiMembersRomoteService;

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

		try {
			GameRoom gameRoom = gameService.buildRamjGameRoom(memberId, lawNames);

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
			req.param("difen", fb.getDifen());
			req.param("taishu", fb.getTaishu());
			req.param("panshu", fb.getPanshu());
			req.param("renshu", fb.getRenshu());
			req.param("dapao", fb.getDapao());
			try {
				ContentResponse res = req.send();
				String resJson = new String(res.getContent());
				CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
				gameRoom.getServerGame().setGameId((String) resVo.getData());
			} catch (Exception e) {
				vo.setSuccess(false);
				vo.setMsg("SysException");
				return vo;
			}

			String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
			gameRoom.setNo(roomNo);

			gameService.createGameRoom(gameRoom);

			Map data = new HashMap();
			data.put("serverGame", gameRoom.getServerGame());
			vo.setData(data);
			return vo;

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
	public CommonVO gameserveroffline(String gameServerId) {
		CommonVO vo = new CommonVO();
		gameService.offlineServer(gameServerId);
		gameServerMsgService.gameServerOffline(gameServerId);
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
	public CommonVO addlaw(Game game, String name, String desc, String mutexGroupId, boolean vip) {
		CommonVO vo = new CommonVO();
		gameService.createGameLaw(game, name, desc, mutexGroupId, vip);
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
	public CommonVO addmutexgroup(Game game, String name, String desc) {
		CommonVO vo = new CommonVO();
		gameService.addLawsMutexGroup(game, name, desc);
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
		return vo;
	}

}
