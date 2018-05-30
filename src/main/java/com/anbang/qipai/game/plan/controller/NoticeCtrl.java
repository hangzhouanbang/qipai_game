package com.anbang.qipai.game.plan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anbang.qipai.game.plan.service.NoticeServiceImpl;
import com.anbang.qipai.game.web.vo.CommonVO;


 /**
  * 系统通告controller
 **/
@Controller
@RequestMapping("/NoticeCtrl")
public class NoticeCtrl {
	
	@Autowired
	private NoticeServiceImpl noticeService;
	/**添加系统公告
	 * 	
	 * **/
	@RequestMapping("/addNotice")
	@ResponseBody
	public CommonVO addNotice(String notice) {
		noticeService.addNotice(notice);
		return new CommonVO();

		
	}
}
