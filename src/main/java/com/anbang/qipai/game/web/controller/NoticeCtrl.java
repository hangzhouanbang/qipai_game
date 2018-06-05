package com.anbang.qipai.game.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anbang.qipai.game.msg.service.NoticeMsgService;
import com.anbang.qipai.game.plan.bean.Notices;
import com.anbang.qipai.game.plan.service.NoticeServiceImpl;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.anbang.qipai.game.websocket.HallWsNotifier;

/**
 * 系统通告controller
 * @author 程佳 2018.6.1
 **/
@Controller
@RequestMapping("/notice")
public class NoticeCtrl {

	@Autowired
	private NoticeServiceImpl noticeService;

	@Autowired
	private HallWsNotifier wsNotifier;
	
	@Autowired 
	private NoticeMsgService noticeMsgService;

	/**
	 * 添加系统公告
	 * 
	 **/
	@RequestMapping("/addnotice")
	@ResponseBody
	public CommonVO addNotice(String notice) {
		noticeService.addNotice(notice);
		//发送消息
		Notices notices = noticeService.findPublicNotice();
		Notices notices1 = new Notices();
		notices1.setNotice(notices.getNotice());
		notices1.setState(notices.getState());
		noticeMsgService.createNotice(notices1);
		wsNotifier.publishSysNotice(notice);
		return new CommonVO();

	}
}
