package com.anbang.qipai.game.plan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.game.conf.SystemNoticePlace;
import com.anbang.qipai.game.conf.SystemNoticeState;
import com.anbang.qipai.game.plan.bean.notice.SystemNotice;
import com.anbang.qipai.game.plan.dao.SystemNoticeDao;

@Service
public class SystemNoticeService {

	@Autowired
	private SystemNoticeDao systemNoticeDao;

	public List<SystemNotice> addSystemNotices(String content, SystemNoticePlace[] places, String adminName) {
		List<SystemNotice> list = new ArrayList<>();
		long currentTime = System.currentTimeMillis();
		for (SystemNoticePlace place : places) {
			SystemNotice notice = new SystemNotice();
			notice.setAdminName(adminName);
			notice.setContent(content);
			notice.setPlace(place);
			notice.setState(SystemNoticeState.START);
			notice.setCreateTime(currentTime);
			notice.setValid(true);
			list.add(notice);
		}
		systemNoticeDao.addSystemNotices(list);
		return list;
	}

	public SystemNotice findById(String noticeId) {
		return systemNoticeDao.findById(noticeId);
	}

	public SystemNotice updateSystemNoticeState(String noticeId, String adminName, String state) {
		systemNoticeDao.updateSystemNoticeState(noticeId, adminName, state);
		return systemNoticeDao.findById(noticeId);
	}

	public SystemNotice updateSystemNoticeValid(String noticeId, String adminName, boolean valid) {
		systemNoticeDao.updateSystemNoticeValid(noticeId, adminName, valid);
		return systemNoticeDao.findById(noticeId);
	}

	public List<SystemNotice> findValidNotice() {
		return systemNoticeDao.findByValid(true);
	}

}
