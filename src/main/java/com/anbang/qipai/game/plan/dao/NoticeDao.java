package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.Notices;

public interface NoticeDao {
	
	/**添加系统公告
	 * @param notice  系统公告内容 有id修改 无ID添加
	 * 
	Notices queryByState(Integer state);
	
	Notices queryById(String id);
	**/
	void addNotice(Notices notice);
}
