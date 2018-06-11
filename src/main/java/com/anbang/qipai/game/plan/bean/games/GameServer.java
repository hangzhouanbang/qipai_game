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
	private String domainForHttp;
	private int portForHttp;
	private String wsUrl;

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

	public String getDomainForHttp() {
		return domainForHttp;
	}

	public void setDomainForHttp(String domainForHttp) {
		this.domainForHttp = domainForHttp;
	}

	public int getPortForHttp() {
		return portForHttp;
	}

	public void setPortForHttp(int portForHttp) {
		this.portForHttp = portForHttp;
	}

	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

}
