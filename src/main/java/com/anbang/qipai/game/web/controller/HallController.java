package com.anbang.qipai.game.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;
import com.anbang.qipai.game.plan.dao.GameRoomDao;
import com.anbang.qipai.game.plan.dao.MemberGameRoomDao;
import com.anbang.qipai.game.plan.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.conf.WebsocketConfig;
import com.anbang.qipai.game.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.plan.service.MemberLoginLimitRecordService;
import com.anbang.qipai.game.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.game.remote.vo.MemberRemoteVO;
import com.anbang.qipai.game.web.vo.CommonVO;

/**
 * 游戏大厅相关
 * 
 * @author neo
 *
 */
@RestController
@RequestMapping("/hall")
public class HallController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private WebsocketConfig wsConfig;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersRomoteService;

	@Autowired
	private MemberLoginLimitRecordService memberLoginLimitRecordService;

	@Autowired
	private GameService gameService;

	/**
	 * 大厅首页
	 * last
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/index")
	@ResponseBody
	public CommonVO index(String token) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		vo.setData(data);
		data.put("wsUrl", wsConfig.getUrl());
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		MemberLoginLimitRecord record = memberLoginLimitRecordService.findByMemberId(memberId, true);
		if (record != null) {
			vo.setSuccess(false);
			vo.setMsg("login limited");
			return vo;
		}
		MemberRemoteVO memberRemoteVO = qipaiMembersRomoteService.member_info(memberId);
		if (memberRemoteVO.isSuccess()) {
			Map mm = new HashMap();
			data.put("member", mm);
			mm.put("id", memberRemoteVO.getMemberId());
			mm.put("nickname", memberRemoteVO.getNickname());
			mm.put("headimgurl", memberRemoteVO.getHeadimgurl());
			mm.put("gold", memberRemoteVO.getGold());
			mm.put("score", memberRemoteVO.getScore());
			mm.put("verifyUser", memberRemoteVO.isVerifyUser());
			mm.put("bindAgent", memberRemoteVO.isBindAgent());
		}
		return vo;
	}

	@RequestMapping(value = "/findroom")
	@ResponseBody
	public CommonVO findRoom(){
		CommonVO vo = new CommonVO();
		vo.setData(gameService.robotTest());
		return vo;
	}

}
