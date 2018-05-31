package com.anbang.qipai.game.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.Notices;
import com.anbang.qipai.game.plan.dao.NoticeDao;

@Component
public class NoticeServiceImpl {

	@Autowired
	private NoticeDao noticeDao;

	public void addNotice(String notice) {
		Notices notices = new Notices();
		notices.setId("1");
		notices.setState(1);
		notices.setNotice(notice);
		noticeDao.addNotice(notices);
	}

	public Notices findPublicNotice() {
		return noticeDao.queryByState(1);
	}

}
