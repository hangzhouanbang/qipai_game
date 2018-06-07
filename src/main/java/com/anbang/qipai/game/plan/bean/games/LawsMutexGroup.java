package com.anbang.qipai.game.plan.bean.games;

/**
 * 玩法互斥组
 * 
 * @author Neo
 *
 */
public class LawsMutexGroup {

	private String id;
	private Game game;
	private String name;// 拼音缩写，用于游戏内唯一标示

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
