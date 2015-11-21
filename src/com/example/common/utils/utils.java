package com.example.common.utils;

import com.example.custom.URLSpanNoUnderline;

import android.graphics.Color;
import android.media.AudioRecord;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.widget.TextView;


public class utils {
	/**
	 * 去除匹配后的下划线
	 * 
	 * @param textView
	 */
	public static void stripUnderlines(TextView textView) {
		try {
			Spannable s = (Spannable) textView.getText();
			URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
			for (URLSpan span : spans) {
				int start = s.getSpanStart(span);
				int end = s.getSpanEnd(span);
				s.removeSpan(span);
				span = new URLSpanNoUnderline(span.getURL());
				s.setSpan(span, start, end, 0);
				ForegroundColorSpan span_1 = new ForegroundColorSpan(Color.rgb(
						59, 125, 237));
				s.setSpan(span_1, start, end,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
			textView.setText(s);
		} catch (Exception e) {
		}

	}


}
