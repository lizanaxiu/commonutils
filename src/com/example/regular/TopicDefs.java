package com.example.regular;

import java.util.regex.Pattern;

import android.content.Intent;

/**
 * 自动匹配参数类 liz 匹配的原则：(.*)这个为通配符 (?=exp) 匹配exp前面的位置 (?<=exp) 匹配exp后面的位置 
 * （.*）匹配任意字符 public static final Pattern TRENDS_URL =
 * Pattern.compile("(\\w+?):");//匹配冒号前面的字符，不能匹配空格和括号 (.*):\\匹配冒号前面的所有字符，包含特殊字符
 */
public final class TopicDefs {

	public static final String MENTIONS_SCHEMA = "devdiv://commomtext";// 要跳转的类的host地址
	public static final String TRENDS_SCHEMA = "devdiv://yinc_profile1";// 要跳转的类的host地址
	public static final String PARAM_UID = "uid";// 匹配后跳转时的参数名称
	public static final String PARAM_UID3 = "uid2";
	public static final String PARAM_UID2 = "stockcode";// 匹配后跳转时的参数名称
	public static final int TRENDS_TYPE = Intent.FLAG_ACTIVITY_NEW_TASK;
	/** @好友匹配 **/
	public static final Pattern EMOTION_URL = Pattern
			.compile("(0?)_+[0-9]+x+[0-9]+_");
//	String content="http://192.168.10.61/load/mfimg01/M00/d:CTNk439_jpg0_480x960_c.png";
//	正则匹配 匹配url里面的0_480x960_ 只是0这个前缀有的有 有的图片地址没有，两种情况都要兼容
	
	 /**@好友匹配**/
//		public static final Pattern EMOTION_URL2 = Pattern.compile("@(\\w+?)(?=\\W|$)(.)");
	public static final Pattern EMOTION_URL2 = Pattern.compile("@(\\w+?)");
	
	public static final Pattern PIC_URL = Pattern
	.compile("(0?)_+[0-9]+x+[0-9]+_");
	/** web跳转匹配 **/
	public static final Pattern WEB_URL = Pattern
			.compile("http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]");
	/** 股票跳转 **/
	public static final Pattern STOCK_URL = Pattern.compile("\\$(\\w+?)\\$");
	// 股票的匹配
	public static final Pattern STOCK_URL2 = Pattern.compile("\\$(.*)\\$");
	public static final Pattern STOCK_URL3 = Pattern
			.compile("(?<=\\()(.*)(?=\\))");

	/** :开始和结束的文字跳转 **/
	public static final Pattern TRENDS_URL = Pattern.compile("(\\w+?):");
	public static final Pattern TRENDS_URL3 = Pattern.compile("(.*):");
	public static final Pattern TRENDS_URL2 = Pattern
			.compile("[a-zA-Z0-9_\u4e00-\u9fa5]:");

	public static final String MENTIONSSCHEME2 = String.format("%s/?%s=",
			TopicDefs.MENTIONS_SCHEMA, TopicDefs.PARAM_UID);
	public static final String MENTIONSSCHEME3 = String.format("%s/?%s=",
			TopicDefs.TRENDS_SCHEMA, TopicDefs.PARAM_UID2);
	public static final String MENTIONSSCHEME = String.format("%s/?%s=",
			TopicDefs.MENTIONS_SCHEMA, TopicDefs.PARAM_UID);
	public static final String MENTIONSSCHEME4 = String.format("%s/?%s=",
			TopicDefs.MENTIONS_SCHEMA, TopicDefs.PARAM_UID3);

}
