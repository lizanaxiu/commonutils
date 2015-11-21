package com.example.commonutils.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("com.dbjtech.waiqin.destroy")) {
			// 在这里写重新启动service的相关操作
			startUploadService(context);
		}

	}

	private void startUploadService(Context context) {
		Intent intent = new Intent("com.example.common.service");
		context.startActivity(intent);

	}
}
