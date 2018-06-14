package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.notice.Notices;

public interface NoticeDao {

	/**
	 * 添加系统公告
	 * @param notice 系统公告内容 有id修改 无ID添加
	 **/
	void addNotice(Notices notice);

	Notices queryByState(Integer state);

	Notices queryById(String id);
	

}
