package com.anbang.qipai.game.plan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameLaw;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.IllegalGameLawsException;
import com.anbang.qipai.game.plan.bean.members.Member;
import com.anbang.qipai.game.plan.bean.members.MemberRights;
import com.anbang.qipai.game.plan.bean.members.NotVIPMemberException;
import com.anbang.qipai.game.plan.dao.GameLawDao;
import com.anbang.qipai.game.plan.dao.MemberDao;

@Component
public class GameService {

	@Autowired
	private GameLawDao gameLawDao;

	@Autowired
	private MemberDao memberDao;

	public GameLaw findGameLaw(Game game, String lawName) {
		return gameLawDao.findByGameAndName(game, lawName);
	}

	/**
	 * 创建瑞安麻将房间
	 */
	public GameRoom buildRamjGameRoom(String memberId, List<String> lawNames)
			throws IllegalGameLawsException, NotVIPMemberException {
		List<GameLaw> laws = new ArrayList<>();
		lawNames.forEach((name) -> laws.add(gameLawDao.findByGameAndName(Game.ruianMajiang, name)));
		GameRoom gameRoom = new GameRoom();
		gameRoom.setLaws(laws);
		if (!gameRoom.validateLaws()) {
			throw new IllegalGameLawsException();
		}
		gameRoom.calculateVip();
		Member member = memberDao.findById(memberId);
		if (gameRoom.isVip() && !member.isVip()) {
			throw new NotVIPMemberException();
		}
		MemberRights rights = member.getRights();
		gameRoom.setCurrentPanNum(0);
		gameRoom.setDeadlineTime(System.currentTimeMillis() + (rights.getRoomsAliveHours() * 60 * 60 * 1000));
		gameRoom.setGame(Game.ruianMajiang);
		if (lawNames.contains("sj")) {
			gameRoom.setPanCountPerJu(4);
		} else if (lawNames.contains("bj")) {
			gameRoom.setPanCountPerJu(8);
		} else if (lawNames.contains("slj")) {
			gameRoom.setPanCountPerJu(16);
		} else {
			gameRoom.setPanCountPerJu(4);
		}

		if (lawNames.contains("erren")) {
			gameRoom.setPlayersCount(2);
		} else if (lawNames.contains("sanren")) {
			gameRoom.setPlayersCount(3);
		} else if (lawNames.contains("siren")) {
			gameRoom.setPlayersCount(4);
		} else {
			gameRoom.setPlayersCount(4);
		}

		return gameRoom;

	}

}
