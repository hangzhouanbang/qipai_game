package com.anbang.qipai.game.plan.bean.games;

/**
 * 游戏服务器
 * 
 * @author Neo
 *
 */
public class GameServer {

	private String id;
	private Game game;
	private String name;
	private String httpUrl;
	private String wsUrl;
	private long onlineTime;
	private int state;

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

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
