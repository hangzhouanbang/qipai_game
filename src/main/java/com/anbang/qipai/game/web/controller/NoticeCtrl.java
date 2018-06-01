package com.anbang.qipai.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anbang.qipai.game.plan.service.NoticeServiceImpl;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.anbang.qipai.game.websocket.HallWsNotifier;

/**
 * 系统通告controller
 **/
@Controller
@RequestMapping("/notice")
public class NoticeCtrl {

	@Autowired
	private NoticeServiceImpl noticeService;

	@Autowired
	private HallWsNotifier wsNotifier;

	/**
	 * 添加系统公告
	 * 
	 **/
	@RequestMapping("/addnotice")
	@ResponseBody
	public CommonVO addNotice(String notice) {
		noticeService.addNotice(notice);
		wsNotifier.publishSysNotice(notice);
		return new CommonVO();

	}
}
