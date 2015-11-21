package com.example.common.fragment;

import com.example.common.entity.Event;
import com.example.common.entity.Item;
import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventListFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//模拟网络请求
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					EventBus.getDefault().post(new Event(Item.ITEM));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
			}
		}).start();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	public void onEventMainThread(Event event){
		setListAdapter(new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_activated_1, android.R.id.text1,event.getItems()));
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		EventBus.getDefault().post(getListView().getItemAtPosition(position));
	}
}
