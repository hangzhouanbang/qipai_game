package com.anbang.qipai.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.msg.service.MemberRightsConfigurationMsgService;
import com.anbang.qipai.game.plan.bean.members.MemberRightsConfiguration;
import com.anbang.qipai.game.plan.service.MemberService;
import com.anbang.qipai.game.web.vo.CommonVO;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRightsConfigurationMsgService memberRightsConfigurationMsgService;

	@RequestMapping("/plan_rights_conf")
	@ResponseBody
	public CommonVO planrightsconf(int planMemberRoomsCount, int planMemberRoomsAliveHours, int planMemberMaxCreateRoomDaily,
			int planMemberCreateRoomDailyGoldPrice,int planMemberJoinRoomGoldPrice) {
		CommonVO vo = new CommonVO();
		memberService.setPlanMembersRights(planMemberRoomsCount, planMemberRoomsAliveHours, planMemberMaxCreateRoomDaily,
				planMemberCreateRoomDailyGoldPrice,planMemberJoinRoomGoldPrice);
		MemberRightsConfiguration memberRightsConfiguration = memberService.findMemberRightsById();
		memberRightsConfigurationMsgService.createMemberRights(memberRightsConfiguration);
		return vo;
	}

	@RequestMapping("/vip_rights_conf")
	@ResponseBody
	public CommonVO viprightsconf(int vipMemberRoomsCount, int vipMemberRoomsAliveHours) {
		CommonVO vo = new CommonVO();
		memberService.setVipMembersRights(vipMemberRoomsCount, vipMemberRoomsAliveHours);
		MemberRightsConfiguration memberRightsConfiguration = memberService.findMemberRightsById();
		memberRightsConfigurationMsgService.createMemberRights(memberRightsConfiguration);
		return vo;
	}

}
