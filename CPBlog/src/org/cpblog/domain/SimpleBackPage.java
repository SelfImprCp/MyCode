package org.cpblog.domain;

import org.cpblog.ui.fragment.TweetFragment;
import org.cpblog.ui.fragment.TweetRecordFragment;

/**
 * 返回页的基本信息注册 (其实就是将原本会在Manifest中注册的Activity转成Fragment在这里注册)
 * 
 * @author ym
 * 
 */

public enum SimpleBackPage {

	OSC_TWEET_LIST(4, TweetFragment.class),
	OSC_TWEET_SEND(5,TweetRecordFragment.class);
	

	private Class<?> clazz;
	private int value;

	private SimpleBackPage(int value, Class<?> cls) {
		this.clazz = cls;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public static Class<?> getPageByValue(int value) {
		for (SimpleBackPage p : values()) {
			if (p.getValue() == value)
				return p.getClazz();
		}
		return null;
	}

}
