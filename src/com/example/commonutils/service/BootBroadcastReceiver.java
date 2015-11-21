package com.example.commonutils.service;

import com.example.common.ui.BootStartDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {

	static final String action_boot="android.intent.action.USER_PRESENT"; 
//	Intent.ACTION_SCREEN_ON
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("BootBroadcastReceiver"," intent.getAction()==="+intent.getAction());
		if (intent.getAction().equals(action_boot)){ 
			Intent ootStartIntent=new Intent(context,BootStartDemo.class); 
			ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.startActivity(ootStartIntent); 
		}

	}

}
