package com.anbang.qipai.game.remote.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anbang.qipai.game.remote.vo.CommonRemoteVO;
import com.anbang.qipai.game.remote.vo.MemberRemoteVO;

/**
 * 会员中心远程服务
 * 
 * @author neo
 *
 */
@FeignClient("qipai-members")
public interface QipaiMembersRemoteService {

	@RequestMapping(value = "/auth/trytoken")
	public CommonRemoteVO auth_trytoken(@RequestParam("token") String token);

	@RequestMapping(value = "/member/info")
	public MemberRemoteVO member_info(@RequestParam("memberId") String memberId);

	@RequestMapping(value = "/gold/withdraw")
	public CommonRemoteVO gold_withdraw(@RequestParam("memberId") String memberId, @RequestParam("amount") int amount,
			@RequestParam("textSummary") String textSummary);

	@RequestMapping(value = "/gold/givegoldtomember")
	public CommonRemoteVO gold_givegoldtomember(@RequestParam("memberId") String memberId,
			@RequestParam("amount") int amount, @RequestParam("textSummary") String textSummary);

	@RequestMapping(value = "/reward/mail_reward")
	public CommonRemoteVO game_mail_reward(@RequestParam("memberId") String memberId,
			@RequestParam("number") Integer number, @RequestParam("integral") Integer integral,
			@RequestParam("vipCardId") String vipCardId);
}
