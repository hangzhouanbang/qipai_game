package com.anbang.qipai.game.plan.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.MemberGameRoom;

public interface MemberGameRoomRepository extends MongoRepository<MemberGameRoom, String> {

	void deleteByMemberIdAndGameRoomGameAndGameRoomServerGameGameId(String memberId, Game game, String serverGameId);

}
