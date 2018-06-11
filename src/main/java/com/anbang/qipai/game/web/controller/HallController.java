package com.anbang.qipai.game.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.conf.WebsocketConfig;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.remote.service.QipaiMembersRomoteService;
import com.anbang.qipai.game.remote.vo.CommonRemoteVO;
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
	private QipaiMembersRomoteService qipaiMembersRomoteService;

	/**
	 * 大厅首页
	 * 
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
		MemberRemoteVO memberRemoteVO = qipaiMembersRomoteService.member_info(memberId);
		if (memberRemoteVO.isSuccess()) {
			Map mm = new HashMap();
			data.put("member", mm);
			mm.put("id", memberRemoteVO.getMemberId());
			mm.put("nickname", memberRemoteVO.getNickname());
			mm.put("headimgurl", memberRemoteVO.getHeadimgurl());
			mm.put("gold", memberRemoteVO.getGold());
		}
		return vo;
	}

	@RequestMapping("/showclubcard")
	@ResponseBody
	public CommonVO showClubCard(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		CommonRemoteVO commonRemoteVO = qipaiMembersRomoteService.clubcard_showclubcard();
		if (commonRemoteVO.isSuccess()) {
			vo.setSuccess(commonRemoteVO.isSuccess());
			vo.setMsg(commonRemoteVO.getMsg());
			vo.setData(commonRemoteVO.getData());
		}
		return vo;
	}

}
