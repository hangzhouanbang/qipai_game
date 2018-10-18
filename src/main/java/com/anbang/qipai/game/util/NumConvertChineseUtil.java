package com.anbang.qipai.game.util;

public class NumConvertChineseUtil {
	private static String[] number = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	private static String[] grade = { "十", "百", "千", "万" };

	public static String toChinese(String str) {
		String result = "";
		int n = str.length();
		for (int i = 0; i < n; i++) {
			int num = str.charAt(i) - '0';
			if (i != n - 1 && num != 0) {
				result += number[num] + grade[n - 2 - i];
			} else {
				result += number[num];
			}
		}
		return result;
	}

}
