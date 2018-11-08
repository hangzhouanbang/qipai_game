package com.anbang.qipai.game.cqrs.c.domain.playback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 回放码管理
 * 
 * @author lsc
 *
 */
public class PlayBackCodeManager {

	private Map<Integer, Integer> noCodeMap = new HashMap<>();

	private static int seed;

	public PlayBackCodeManager() {
		List<Integer> codeList = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			codeList.add(i);
		}
		Collections.shuffle(codeList, new Random(System.currentTimeMillis()));
		for (int i = 0; i < codeList.size(); i++) {
			noCodeMap.put(i, codeList.get(i));
		}
	}

	public int getPlayBackCode() {
		if (seed > 999999) {
			seed = 0;
		}
		int code = noCodeMap.get(seed);
		seed++;
		return code;
	}

}
