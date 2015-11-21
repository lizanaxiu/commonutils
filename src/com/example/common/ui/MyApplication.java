package com.example.common.ui;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

public class MyApplication extends Application {
	public static MyApplication application = null;
	private static final String INTENT_ACTION = "com.example.common.service";
	public synchronized static MyApplication getInstance() {
		if (application == null) {
			application = new MyApplication();
		}
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// 创建默认的ImageLoader配置参数
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(configuration);
	}
	public void startService(){
		// 定义服务链接对象
		ServiceConnection conn = new ServiceConnection() {

					@Override
					public void onServiceDisconnected(ComponentName name) {
						Toast.makeText(getApplicationContext(),
								"ServiceConnection onServiceDisconnected",
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onServiceConnected(ComponentName name, IBinder service) {
						Toast.makeText(getApplicationContext(),
								"ServiceConnection onServiceConnected",
								Toast.LENGTH_SHORT).show();

					}
				};
				Intent intent=new Intent(INTENT_ACTION);
		this.bindService(intent,conn,Context.BIND_AUTO_CREATE);
	}
	

}
