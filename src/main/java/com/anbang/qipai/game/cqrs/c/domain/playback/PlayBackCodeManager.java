package com.anbang.qipai.game.cqrs.c.domain.playback;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 回放码管理
 * 
 * @author lsc
 *
 */
public class PlayBackCodeManager {

	private Set<String> noSet = new HashSet<>();

	private static char[] charsForNo = new char[] { '0', '1', '2', '3', '5', '6', '7', '8', '9' };

	public String newNo(long seed) {
		Random random = new Random(seed);
		String newNo;
		while (true) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 6; i++) {
				int charIdx = random.nextInt(charsForNo.length);
				sb.append(charsForNo[charIdx]);
			}
			newNo = sb.toString();
			if (noSet.contains(newNo)) {
				continue;
			} else {
				break;
			}
		}

		noSet.add(newNo);
		return newNo;
	}

	public String removeNo(String no) {
		noSet.remove(no);
		return no;
	}

}
