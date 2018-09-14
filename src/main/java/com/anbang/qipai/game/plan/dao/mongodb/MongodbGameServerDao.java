package com.anbang.qipai.game.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameServer;
import com.anbang.qipai.game.plan.dao.GameServerDao;
import com.anbang.qipai.game.plan.dao.mongodb.repository.GameServerRepository;

@Component
public class MongodbGameServerDao implements GameServerDao {

	@Autowired
	private MongoTemplate mognoTempalte;

	@Autowired
	private GameServerRepository repository;

	@Override
	public void save(GameServer gameServer) {
		mognoTempalte.insert(gameServer);
	}

	@Override
	public void remove(String[] ids) {
		Object[] serverIds = ids;
		Query query = new Query(Criteria.where("id").in(serverIds));
		mognoTempalte.remove(query, GameServer.class);
	}

	@Override
	public List<GameServer> findAll() {
		return repository.findAll();
	}

	@Override
	public List<GameServer> findByGame(Game game) {
		Query query = new Query();
		query.addCriteria(Criteria.where("game").is(game));
		return mognoTempalte.find(query, GameServer.class);
	}

	@Override
	public void updateGameServerState(List<String> ids, int state) {
		Query query=new Query();
		query.addCriteria(Criteria.where("id").in(ids));
        Update update=new Update();
        update.set("state",state);
        this.mognoTempalte.updateMulti(query,update,GameServer.class);
	}

    @Override
    public List<GameServer> findServersByState(Game game,int state) {
	    // 初始状态没有state变量
	    Query query = new Query();
        query.addCriteria(Criteria.where("game").is(game)
                .orOperator(new Criteria("state").is(state),new Criteria("state").exists(false)));
        return this.mognoTempalte.find(query,GameServer.class);
    }

}
