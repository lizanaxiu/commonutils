package com.example.common.ui;

import com.example.commonutils.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class StartAnimationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ainmation_start);
		ImageView imageView = (ImageView) findViewById(R.id.start_image);
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);//动画渐变进入主程序
		animation.setDuration(3000);
		imageView.setImageResource(R.drawable.jyy);
		imageView.setAnimation(animation);
//		animation.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				Intent intent=new Intent(StartAnimationActivity.this,MainActivity.class);
//				startActivity(intent);
//
//			}
//		});
		new Handler(new Handler.Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				return false;
			}
		}).sendEmptyMessageDelayed(0, 3000);
	}

}
