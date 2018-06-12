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
public interface QipaiMembersRomoteService {

	@RequestMapping(value = "/auth/trytoken")
	public CommonRemoteVO auth_trytoken(@RequestParam("token") String token);

	@RequestMapping(value = "/member/info")
	public MemberRemoteVO member_info(@RequestParam("memberId") String memberId);

	@RequestMapping(value = "/clubcard/showclubcard")
	public CommonRemoteVO clubcard_showclubcard();

	@RequestMapping(value = "/gold/withdraw")
	public CommonRemoteVO gold_withdraw(@RequestParam("memberId") String memberId, @RequestParam("amount") int amount,
			@RequestParam("textSummary") String textSummary);

}
