package com.anbang.qipai.game.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anbang.qipai.game.conf.SystemNoticePlace;
import com.anbang.qipai.game.conf.SystemNoticeState;
import com.anbang.qipai.game.msg.service.NoticeMsgService;
import com.anbang.qipai.game.plan.bean.notice.SystemNotice;
import com.anbang.qipai.game.plan.service.SystemNoticeService;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.anbang.qipai.game.websocket.HallWsNotifier;

@Controller
@RequestMapping("/sysnotice")
public class SystemNoticeCtrl {

	@Autowired
	private SystemNoticeService systemNoticeService;

	@Autowired
	private HallWsNotifier wsNotifier;

	@Autowired
	private NoticeMsgService noticeMsgService;

	@RequestMapping("/addnotice")
	@ResponseBody
	public CommonVO addNotice(String content, @RequestBody SystemNoticePlace[] places, String adminName) {
		CommonVO vo = new CommonVO();
		List<SystemNotice> list = systemNoticeService.addSystemNotices(content, places, adminName);
		noticeMsgService.createNotice(list);
		list.forEach((SystemNotice) -> {
			wsNotifier.publishSysNotice(SystemNotice.getContent());
		});
		vo.setSuccess(true);
		return vo;
	}

	@RequestMapping("/startnotice")
	@ResponseBody
	public CommonVO startNotice(String id, String adminName) {
		CommonVO vo = new CommonVO();
		SystemNotice notice = systemNoticeService.updateSystemNoticeState(id, adminName, SystemNoticeState.START);
		noticeMsgService.startNotice(notice);
		vo.setSuccess(true);
		return vo;
	}

	@RequestMapping("/stopnotice")
	@ResponseBody
	public CommonVO stopNotice(String id, String adminName) {
		CommonVO vo = new CommonVO();
		SystemNotice notice = systemNoticeService.updateSystemNoticeState(id, adminName, SystemNoticeState.STOP);
		noticeMsgService.stopNotice(notice);
		vo.setSuccess(true);
		return vo;
	}

	@RequestMapping("/removenotice")
	@ResponseBody
	public CommonVO removeNotice(String id, String adminName) {
		CommonVO vo = new CommonVO();
		SystemNotice n = systemNoticeService.findById(id);
		if (!n.getState().equals(SystemNoticeState.STOP)) {
			vo.setSuccess(false);
			vo.setMsg("invalid id");
			return vo;
		}
		SystemNotice notice = systemNoticeService.updateSystemNoticeValid(id, adminName, false);
		noticeMsgService.removeNotice(notice);
		vo.setSuccess(true);
		return vo;
	}
}
