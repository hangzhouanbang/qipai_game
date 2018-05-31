package com.anbang.qipai.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.game.plan.service.MemberAuthService;
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
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		return vo;
	}

}
