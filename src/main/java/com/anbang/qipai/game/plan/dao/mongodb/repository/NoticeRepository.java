package com.anbang.qipai.game.plan.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.game.plan.bean.Notices;

public interface NoticeRepository extends MongoRepository<Notices, String> {
	/**
	 * 查询启用状态的公告
	 * 
	 * @param state
	 *            状态
	 **/
	public Notices queryByState(Integer state);

	public Notices queryById(String id);
}
