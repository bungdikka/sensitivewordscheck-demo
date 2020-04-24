package com.moonlight.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SensitiveWordsChecker {
	private static volatile SensitiveWordsChecker singleton; // 1:volatile修饰

	private SensitiveWordsChecker() {
	}

	public static SensitiveWordsChecker getSingleton() {
		if (singleton == null) { // 2:减少不要同步，优化性能
			synchronized (SensitiveWordsChecker.class) { // 3：同步，线程安全
				if (singleton == null) {
					singleton = new SensitiveWordsChecker(); // 4：创建singleton 对象
				}
			}
		}
		return singleton;
	}

	private int minMatchTYpe = 1; // 最小匹配规则
	private int maxMatchType = 2; // 最大匹配规则

	@SuppressWarnings("rawtypes")
	public HashMap addSensitiveWordToHashMap(Set<String> keyWordSet) {
		HashMap sensitiveWordMap = new HashMap(keyWordSet.size()); // 初始化敏感词容器，减少扩容操作
		String key = null;
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		// 迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while (iterator.hasNext()) {
			key = iterator.next(); // 关键字
			nowMap = sensitiveWordMap;
			for (int i = 0; i < key.length(); i++) {
				char keyChar = key.charAt(i); // 转换成char型
				Object wordMap = nowMap.get(keyChar); // 获取

				if (wordMap != null) { // 如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				} else { // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String, String>();
					newWorMap.put("isEnd", "0"); // 不是最后一个
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}

				if (i == key.length() - 1) {
					nowMap.put("isEnd", "1"); // 最后一个
				}
			}
		}
		System.out.println(sensitiveWordMap);
		return sensitiveWordMap;
	}

	@SuppressWarnings({ "rawtypes" })
	public int CheckSensitiveWord(HashMap sensitiveWordsDFA, String txt, int beginIndex, int matchType) {
		boolean flag = false; // 敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0; // 匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordsDFA;
		for (int i = beginIndex; i < txt.length(); i++) {
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word); // 获取指定key
			if (nowMap != null) { // 存在，则判断是否为最后一个
				matchFlag++; // 找到相应key，匹配标识+1
				if ("1".equals(nowMap.get("isEnd"))) { // 如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true; // 结束标志位为true
					if (minMatchTYpe == matchType) { // 最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			} else { // 不存在，直接返回
				break;
			}
		}
		if (matchFlag < 2 || !flag) { // 长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
	}

	/**
	 * 判断文字是否包含敏感字符
	 * 
	 * @param txt       文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	public boolean isContaintSensitiveWord(HashMap sensitiveWordsDFA, String txt, int matchType) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {
			int matchFlag = CheckSensitiveWord(sensitiveWordsDFA, txt, i, matchType); // 判断是否包含敏感字符
			if (matchFlag > 0) { // 大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}
}
