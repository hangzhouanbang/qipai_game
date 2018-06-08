package com.anbang.qipai.game.plan.bean.games;

/**
 * 游戏规则（玩法）
 * 
 * @author Neo
 *
 */
public class GameLaw {

	private String id;
	private Game game;
	private String name;// 拼音缩写，用于游戏内唯一标示
	private String desc;
	private String mutexGroupId;
	private boolean vip;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMutexGroupId() {
		return mutexGroupId;
	}

	public void setMutexGroupId(String mutexGroupId) {
		this.mutexGroupId = mutexGroupId;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

}
