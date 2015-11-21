package com.example.common.ui;

import com.example.commonutils.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
/**
 * 滑动卡添加
 * @author liz
 *
 */
public class WelcomGuidAct extends Activity {
	private Button button;
	private ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomguid);
		pager=(ViewPager) findViewById(R.id.welcom_viewpage);
		button=(Button) findViewById(R.id.welcom_guid_btn);
	}

}
