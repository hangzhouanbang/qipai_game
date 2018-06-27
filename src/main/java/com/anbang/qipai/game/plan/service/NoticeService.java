package com.anbang.qipai.game.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.notice.Notices;
import com.anbang.qipai.game.plan.dao.NoticeDao;

@Component
public class NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	public void addNotice(String notice,String place,String adminname) {
		Notices notices = new Notices();
		notices.setId("1");
		notices.setState(1);
		notices.setPlace(place);
		notices.setNotice(notice);
		notices.setAdminname(adminname);
		noticeDao.addNotice(notices);
	}
	
	public void updateNotice() {
		Notices notices = noticeDao.queryById("1");
		notices.setState(0);
		noticeDao.addNotice(notices);
	}

	public Notices findPublicNotice() {
		return noticeDao.queryByState(1);
	}
	
	public Notices queryById() {
		return noticeDao.queryById("1");
	}

}
