package com.anbang.qipai.game.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.game.cqrs.q.dbo.PlayBackDbo;

public interface PlayBackDboRepository extends MongoRepository<PlayBackDbo, String> {

}
