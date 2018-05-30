package com.anbang.qipai.game.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.Notices;
import com.anbang.qipai.game.plan.dao.NoticeDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.NoticeRepository;


@Component
public class MongdbNoticeDao implements NoticeDao{
	
	@Autowired
	private NoticeRepository noticeRepository;

	@Override
	public void addNotice(Notices notice) {
		noticeRepository.save(notice);
	}
/**
	@Override
	public Notices queryByState(Integer state) {
		return noticeRepository.queryByState(state);
	}

	@Override
	public Notices queryById(String id) {
		return noticeRepository.queryById(id);
	}
**/
	
}
