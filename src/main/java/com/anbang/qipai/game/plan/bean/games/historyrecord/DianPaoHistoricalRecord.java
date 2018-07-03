package com.anbang.qipai.game.plan.bean.games.historyrecord;

import java.util.Comparator;

/**点炮麻将战绩
 * **/
public class DianPaoHistoricalRecord implements Comparator<DianPaoHistoricalRecord>{
	
	private String id;
	
	private String memberId;//用户编号
	
	private String headImgUrl;// 头像url
	
	private String nickName;//会员名称
	
	private Integer vipLevel;//vip等级
	
	private String roomId;//房间编号
	
	private int huCount;//胡几次
	
	private int zimoCount;//自摸次数
	
	private int mammonCount;//财神
	
	private int PointgunCount;//点炮count;
	
	private int totalScore;//总分
	
	private int reward;//奖励
	
	private int gameCount;//局数
	
	private long endTime;//结束时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getHuCount() {
		return huCount;
	}

	public void setHuCount(int huCount) {
		this.huCount = huCount;
	}

	public int getZimoCount() {
		return zimoCount;
	}

	public void setZimoCount(int zimoCount) {
		this.zimoCount = zimoCount;
	}

	public int getMammonCount() {
		return mammonCount;
	}

	public void setMammonCount(int mammonCount) {
		this.mammonCount = mammonCount;
	}

	public int getPointgunCount() {
		return PointgunCount;
	}

	public void setPointgunCount(int pointgunCount) {
		PointgunCount = pointgunCount;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getGameCount() {
		return gameCount;
	}

	public void setGameCount(int gameCount) {
		this.gameCount = gameCount;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Override
	public int compare(DianPaoHistoricalRecord o1, DianPaoHistoricalRecord o2) {
		if(o1.getTotalScore() > o2.getTotalScore()) {
			return 1;
		}else if(o1.getTotalScore() == o2.getTotalScore()){
			return 0;
		}else {
			return -1;
		}
	}
	
	
}
