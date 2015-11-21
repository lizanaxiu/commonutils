package com.example.custom;

import android.text.TextPaint;
import android.text.style.URLSpan;
/**
 * 自定义 去除超链接下划线
 * @author liz
 *
 */
public class URLSpanNoUnderline extends URLSpan{
	public URLSpanNoUnderline(String url) {
		super(url);
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		super.updateDrawState(ds);
		ds.setUnderlineText(false);
	}
}
