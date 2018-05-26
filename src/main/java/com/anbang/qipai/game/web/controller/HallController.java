package com.anbang.qipai.game.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.game.remote.service.QipaiMembersRomoteService;
import com.anbang.qipai.game.remote.vo.CommonRemoteVO;
import com.anbang.qipai.game.web.vo.CommonVO;

@RestController
@RequestMapping("/hall")
public class HallController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private QipaiMembersRomoteService qipaiMembersRomoteService;

	@RequestMapping(value = "/index")
	@ResponseBody
	public CommonVO index(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.auth_trytoken(token);
			if (rvo.isSuccess()) {
				Map data = (Map) rvo.getData();
				memberId = (String) data.get("memberId");
				memberAuthService.createSessionForMember(token, memberId);
			} else {
				vo.setSuccess(false);
				vo.setMsg("invalid token");
			}
		}
		return vo;
	}

}
