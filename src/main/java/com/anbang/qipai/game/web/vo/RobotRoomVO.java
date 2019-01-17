package com.anbang.qipai.game.web.vo;

import com.anbang.qipai.game.plan.bean.games.Game;

/**
 * @Author: 吴硕涵
 * @Date: 2019/1/14 3:51 PM
 * @Version 1.0
 */
public class RobotRoomVO {
    private String id;
    private String no;// 房间6位编号,可循环使用
    private Game game;
    private int playersCount;
    private int panCountPerJu;
    private int currentPanNum;
    private String createMemberId;
    private long createTime;
    private long deadlineTime;
    private boolean finished;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public int getPanCountPerJu() {
        return panCountPerJu;
    }

    public void setPanCountPerJu(int panCountPerJu) {
        this.panCountPerJu = panCountPerJu;
    }

    public int getCurrentPanNum() {
        return currentPanNum;
    }

    public void setCurrentPanNum(int currentPanNum) {
        this.currentPanNum = currentPanNum;
    }

    public String getCreateMemberId() {
        return createMemberId;
    }

    public void setCreateMemberId(String createMemberId) {
        this.createMemberId = createMemberId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(long deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
