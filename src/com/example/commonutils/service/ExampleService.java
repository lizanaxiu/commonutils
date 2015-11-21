package com.example.commonutils.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ExampleService extends Service {
	private static final String TAG = "ExampleService";
	private Intent intent;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "LocalTestService--------onBind");
		this.intent = intent;
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "LocalTestService--------onCreate");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		 Log.i(TAG, "LocalTestService--------onDestroy");
		// Intent localIntent = new Intent();
		// localIntent.setClass(this, ExampleService.class); //销毁时重新启动Service
		// this.startService(localIntent);
		// MyApplication.getInstance().startService();
		
//		Intent intent = new Intent("com.dbjtech.waiqin.destroy");
//		sendBroadcast(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "LocalTestService--------onRebind");
		this.intent = intent;
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		this.intent = intent;
		Log.d(TAG, "onStart==========");
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				for (int i = 0; i < 1000000; i++) {
//					Log.d(TAG, "msg========" + i);
//				}
//			}
//		}).start();
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "LocalTestService--------onUnbind");
		return super.onStartCommand(intent, flags, Service.START_NOT_STICKY);
	}
}