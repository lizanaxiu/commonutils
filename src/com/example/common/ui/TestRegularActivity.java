package com.example.common.ui;

import com.example.common.chat.ChatActivity;
import com.example.common.utils.FaceConversionUtil;
import com.example.common.utils.utils;
import com.example.commonutils.R;
import com.example.custom.URLSpanNoUnderline;
import com.example.regular.TopicPattern;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

/**
 * 正则匹配和跳转测试
 * 
 * @author liz 表情的匹配 聊天页面的跳转
 * 
 */
public class TestRegularActivity extends Activity {
	TextView textView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testregular);
		initData();
		textView = (TextView) findViewById(R.id.test_regular);
		String content="@布兰 @反方向的钟 @龟仙人 @好好学习 asdsdsdsfsdfdsfd";
		textView.setText(content);
		TopicPattern.extractMention2Link(textView);
		// String content =
		// "defsldfkldsgt[苦逼][媚眼]$SDFDS(002.SH)$...http://ssd.com试啦[媚眼]测[惊讶]你妹[苦逼][媚眼]测试啦[ds是@佛山的佛我dfddf发否开始的频繁看是得分是佛dsfdfdfd山的佛实得分";
		// http://192.168.10.61/load/mfimg01/M00/00/3B/wKgKP1X:ehuAbvobABZC:3Q7tco314_jpg0_480x960_c.png

		// regular();
		// measureTextViewHeight(15, getWindowManager().getDefaultDisplay()
		// .getWidth());
		// ((Object) textView).getLineEnd(2) ;
		// textView.getLineBounds(2, bounds);
		// getString();
//		SpannableString spannableString = FaceConversionUtil.getInstace()
//				.getExpressionString(getApplicationContext(),
//						textView.getText().toString());
//		textView.setText(spannableString);
		// TopicPattern.extractMention2Link(textView);

	}

	private int measureTextViewHeight(int textSize, int deviceWidth) {
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(deviceWidth,
				MeasureSpec.AT_MOST);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		textView.measure(widthMeasureSpec, heightMeasureSpec);
		return textView.getMeasuredHeight();
	}

	private void getString() {
		Layout layout = textView.getLayout();
		int line = textView.getLayout().getLineCount();
		String result = "";
		String text = layout.getText().toString();
		if (line < 3) {
			textView.setText(result);
			return;
		}
		for (int i = 0; i < 2; i++) {
			int start = layout.getLineStart(i);
			int end = layout.getLineEnd(i);
			if (i == 0) {
				result += text.substring(start, end);
			} else if (i == 1) {
				result += text.substring(start, end - 3) + "...";
				textView.setText(result);
			}

		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("tag",
				textView.getLineCount() + "ssss===" + textView.getLineHeight()
						+ "getLeft=" + textView.getLeft() + "getRight="
						+ textView.getRight() + "getRotation="
						+ textView.getRotation());
	}

	// 跳转到聊天页面
	public void TurnToChat(View view) {
		Intent intent = new Intent(TestRegularActivity.this, ChatActivity.class);
		startActivity(intent);
	}

	private void initData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(getApplication());
			}
		}).start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void regular() {
		// 否$深圳(003343.SH)$实
		String content = "defsldfkldsgt[苦逼][媚眼]...http://ssd.com试啦[媚眼]测[惊讶]你妹[苦逼][媚眼]测试啦[ds是@佛山的佛 我dfddf发否开始的频繁看是得分是佛dsfdfdfd山的佛实得分";
		SpannableString spannableString = FaceConversionUtil.getInstace()
				.getExpressionString(getApplicationContext(), content);
		textView.setText(spannableString);
		TopicPattern.extractMention2Link(textView);
		// utils.stripUnderlines(textView);//去除匹配文字的下划线
	}
    /**
     * 匹配一段文字中的特殊文字，并替换为其他文字
     */
	private void replaceMatchWords() {
		String content = "http://192.168.10.61/load/mfimg01/M00/00/3B/wKgKP1X:ehuAbvobABZC:3Q7tco314_jpg0_480x960_c.png";
		String content2 = TopicPattern.patternCompile(content);
		textView.setText(content2);
	}
}
