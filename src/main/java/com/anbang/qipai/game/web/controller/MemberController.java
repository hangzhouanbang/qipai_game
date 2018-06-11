package com.anbang.qipai.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.plan.service.MemberService;
import com.anbang.qipai.game.web.vo.CommonVO;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/plan_rights_conf")
	@ResponseBody
	public CommonVO planrightsconf(int memberRoomsCount, int memberRoomsAliveHours) {
		CommonVO vo = new CommonVO();
		memberService.setPlanMembersRights(memberRoomsCount, memberRoomsAliveHours);
		return vo;
	}

	@RequestMapping("/vip_rights_conf")
	@ResponseBody
	public CommonVO viprightsconf(int memberRoomsCount, int memberRoomsAliveHours) {
		CommonVO vo = new CommonVO();
		memberService.setVipMembersRights(memberRoomsCount, memberRoomsAliveHours);
		return vo;
	}

}
