package com.example.common.ui;

import com.example.common.selectPic.PickPhotoActivity;
import com.example.commonutils.R;
import com.example.commonutils.service.ExampleService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 程序的主要入口
 * 
 * @author liz
 * 
 */
public class MainActivity extends Activity {
	private boolean isStart = false;
	Intent intent;
	private ScreenBroadcastReceiver screenBroadcastReceiver = null;
	private static final String INTENT_ACTION = "com.example.common.service";
//	private ServiceConnection conn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intent = new Intent(MainActivity.this, ExampleService.class);
//		new Intent(INTENT_ACTION)
		bindListener();

	}

	@Override
	protected void onResume() {
		super.onResume();
		registerScreenBroadcastReceiver();
	}

	private void bindListener() {
		findViewById(R.id.stop_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// unbindService(conn);
				stopService(intent);
			}
		});
		findViewById(R.id.start_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startService(intent);
				// bindService(new Intent(INTENT_ACTION), conn,
				// Context.BIND_AUTO_CREATE);
			}
		});
		findViewById(R.id.select_pic_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								PickPhotoActivity.class);
						startActivity(intent);

					}
				});
	}
	private void registerScreenBroadcastReceiver() {
		screenBroadcastReceiver = new ScreenBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);// 当屏幕锁屏的时候触发
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);// 当屏幕解锁的时候触发
		intentFilter.addAction(Intent.ACTION_USER_PRESENT);// 当用户重新唤醒手持设备时触发
		this.registerReceiver(screenBroadcastReceiver, intentFilter);
		Log.i("screenBR", "screenBroadcastReceiver注册了");
	}

	// 重写广播
	class ScreenBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String strAction = intent.getAction();
			if (Intent.ACTION_SCREEN_OFF.equals(strAction)) {
				// 屏幕锁屏
				Log.i("screenBR", "屏幕锁屏：ACTION_SCREEN_OFF触发");
				Toast.makeText(context, "锁屏了", Toast.LENGTH_SHORT).show();
			} else if (Intent.ACTION_SCREEN_ON.equals(strAction)) {
				// 屏幕解锁(实际测试效果，不能用这个来判断解锁屏幕事件)
				// 【因为这个是解锁的时候触发，而解锁的时候广播还未注册】
				Log.i("screenBR", "屏幕解锁：ACTION_SCREEN_ON触发");
				Toast.makeText(context, "解锁了", Toast.LENGTH_SHORT).show();
			} else if (Intent.ACTION_USER_PRESENT.equals(strAction)) {
				// 屏幕解锁(该Action可以通过静态注册的方法注册)
				// 在解锁之后触发的，广播已注册
				Log.i("screenBR", "屏幕解锁：ACTION_USER_PRESENT触发");
				Toast.makeText(context, "解锁了", Toast.LENGTH_SHORT).show();
			} else {
				// nothing
			}
		}

	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
}
