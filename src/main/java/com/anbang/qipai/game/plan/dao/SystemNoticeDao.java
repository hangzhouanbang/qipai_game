package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.notice.SystemNotice;

public interface SystemNoticeDao {

	void save(SystemNotice notice);

	void addSystemNotices(List<SystemNotice> notices);

	void updateSystemNoticeState(String noticeId, String adminName, String state);

	void updateSystemNoticeValid(String noticeId, String adminName, boolean valid);

	List<SystemNotice> findByValid(boolean valid);

	SystemNotice findById(String noticeId);
}
