package com.anbang.qipai.game.conf;

public enum SystemNoticePlace {
	游戏大厅, 游戏房间;
	public static SystemNoticePlace getSystemNoticePlaceByOrdinal(int ordinal) {
		SystemNoticePlace[] values = values();
		return values[ordinal];
	}
}
