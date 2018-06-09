package com.anbang.qipai.game.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.cqrs.c.domain.games.CanNotJoinMoreRoomsException;
import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.service.GameServerMsgService;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.IllegalGameLawsException;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.NotVIPMemberException;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.plan.service.MemberService;
import com.anbang.qipai.game.web.vo.CommonVO;

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

		try {
			GameRoom gameRoom = gameService.buildRamjGameRoom(memberId, lawNames);
			Member member = memberService.findMember(memberId);
			MemberRights rights = member.getRights();
			String roomNo = gameRoomCmdService.createRoom(memberId, rights.getRoomsCount(), System.currentTimeMillis());
			gameRoom.setNo(roomNo);
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
		}

		Map data = new HashMap();
		vo.setData(data);
		// TODO: 游戏服务器rpc开房

		// gameService.saveGameRoom(gameRoom);
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
	public CommonVO gameserveroffline(String gameServerId) {
		CommonVO vo = new CommonVO();
		gameService.offlineServer(gameServerId);
		gameServerMsgService.gameServerOffline(gameServerId);
		return vo;
	}

}
