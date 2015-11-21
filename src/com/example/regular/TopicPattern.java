package com.example.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.widget.TextView;

/**
 * 文本自动匹配工具类
 * 
 * @author liz
 * 
 */
public class TopicPattern {
	public static int position=0;

	public static void extractMention2Link(TextView v) {
		v.setAutoLinkMask(0);
		// @好友的文字跳转
		Linkify.addLinks(v, TopicDefs.EMOTION_URL2, TopicDefs.MENTIONSSCHEME,
				new MatchFilter() {

					@Override
					public boolean acceptMatch(CharSequence s, int start,
							int end) {
						return s.charAt(end - 1) != '.';
					}

				}, new TransformFilter() {
					@Override
					public String transformUrl(Matcher match, String url) {
						return match.group(1);
					}
				});

//		Linkify.addLinks(v, TopicDefs.WEB_URL, TopicPatternParams.WEB_SCHEME);
//
//		Linkify.addLinks(v, TopicDefs.STOCK_URL2, TopicDefs.MENTIONSSCHEME2,
//				new MatchFilter() {
//
//					@Override
//					public boolean acceptMatch(CharSequence s, int start,
//							int end) {
//						return s.charAt(end - 1) != '.';
//					}
//
//				}, new TransformFilter() {
//					@Override
//					public String transformUrl(Matcher match, String url) {
//						return match.group(1);
//					}
//				});

	}

	/**
	 * 将用户名称转成超链接并匹配跳转
	 * 
	 * @param v
	 */
	public static void extractUName2Link(TextView v) {
		// :开始和结束的文字跳转
		Linkify.addLinks(v, TopicDefs.TRENDS_URL, TopicDefs.MENTIONSSCHEME,
				null, new TransformFilter() {
					@Override
					public String transformUrl(Matcher match, String url) {
						return match.group(1);
					}
				});
	}
	public static void extractUName2TitLeLink(final int uid,TextView v) {
		Linkify.addLinks(v, TopicDefs.TRENDS_URL3, TopicDefs.MENTIONSSCHEME4,
				null, new TransformFilter() {
					@Override
					public String transformUrl(Matcher match, String url) {
						return uid+"";
					}
				});
	}
	//439_jpg0_480x960_c.png
public static String patternCompile(String content){
	Pattern pattern = Pattern.compile("(0?)_+[0-9]+x+[0-9]+_");  
	Matcher matcher = pattern.matcher(content); //以验证127.400.600.2为例  
	//替换所有符合正则的数据 
	return matcher.replaceAll("_100x100_");
}
	
}
