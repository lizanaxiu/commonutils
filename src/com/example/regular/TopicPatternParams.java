package com.example.regular;

import java.util.regex.Pattern;

/**
 * 正则表达式类
 * liz
 */
public class TopicPatternParams {
//	http://www.baidu.com
    public static final Pattern WEB_URL = Pattern
            .compile("http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]");
    public static final Pattern TOPIC_URL = Pattern
            .compile("#[\\p{Print}\\p{InCJKUnifiedIdeographs}&&[^#]]+#");
    public static final Pattern MENTION_URL = Pattern
            .compile("@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26}");
    public static final Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");

    public static final String WEB_SCHEME = "http://";
    public static final String TOPIC_SCHEME ="test.com.aa"+ ".topic://";
    public static final String MENTION_SCHEME ="test.com.aa" + ".mention://";
    public static final String STOCK_SCHEME ="test.com.aa" + ".stock://";

}
