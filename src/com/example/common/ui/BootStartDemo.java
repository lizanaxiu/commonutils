package com.example.common.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.commonutils.R;
/**
 * 测试通过系统锁屏和开锁的广播启动程序的入口activity
 * @author liz
 *
 */
public class BootStartDemo extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
//		new Thread() {
//			public void run() {
//				try {
//					/* 10秒后关闭页面 */
//					sleep(10000);
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					finish(); // 关闭页面
//				}
//			}
//		}.start();

	}

}
