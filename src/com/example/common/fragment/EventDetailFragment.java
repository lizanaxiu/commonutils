package com.example.common.fragment;

import com.example.common.entity.Item;
import com.example.commonutils.R;

import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventDetailFragment extends Fragment {
	private TextView detailTv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_item_detail, container,false);
		detailTv=(TextView) view.findViewById(R.id.item_detail_tv);
		return view;
       
	}

	public void onEventMainThread(Item item) {
		if(item!=null){
			detailTv.setText(item.content);
		}
     
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
