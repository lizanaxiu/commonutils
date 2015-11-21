package com.example.common.selectPic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.common.adapter.ImageGridAdapter;
import com.example.common.adapter.ImageGridAdapter.TextCallback;
import com.example.common.config.Constants;
import com.example.common.entity.ImageItem;
import com.example.common.utils.BitmapCache;
import com.example.common.utils.BitmapCache.ImageCallback;
import com.example.commonutils.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Description  单个相册的图片列表
 * @author liz
 */
public class ImageGridActivity extends Activity implements OnTouchListener {
	private List<ImageItem> dataList = null;
	private GridView gridView = null;
	private TextView title = null;
	private ImageView cancel = null;
	private static Button sendBtn = null;
	private ImageView leftBtn = null;
	private static Context context = null;
	private static String CHAT_USER_ID = null;
	private static ImageGridAdapter adapter = null;
	private String name = null;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static final String EXTRA_ALBUM_NAME = "name";
	public static final String EXTRA_ADAPTER_NAME = "adapter";
	public static final String EXTRA_CHAT_USER_ID = "chat_user_id";
	private ArrayList<String> selectedDataList = new ArrayList<String>();
	private ArrayList<String> existDataList = new ArrayList<String>();// 初始化时候已经选择的列表集合
	private HashMap<String, ImageView> hashMap = new HashMap<String, ImageView>();// 记录地址和图片，方便删除和添加
	private LinearLayout selectedImageLayout;
	private BitmapCache cache = null;//  缓存
	private static int total = 0;// 被选择的总数
	private int picNum = 0;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(
						ImageGridActivity.this,
						getResources().getString(R.string.pic_limit)
								+ Constants.PIC_SELECTED_LIMIT_NUM
								+ getResources().getString(
										R.string.pic_limit_text2), 400).show();
				break;
			default:
				break;
			}
		}
	};

	OnScrollListener onScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_FLING:
				adapter.lock();
				break;
			case OnScrollListener.SCROLL_STATE_IDLE:
				adapter.unlock();
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				adapter.lock();
				break;
			default:
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tt_activity_image_grid);
		context = this;
		cache = BitmapCache.getInstance();
		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				"imagelist");
		name = (String) getIntent().getSerializableExtra(EXTRA_ALBUM_NAME);
		CHAT_USER_ID = (String) getIntent().getSerializableExtra(
				EXTRA_CHAT_USER_ID);
		initView();
		initAdapter();
	}

	/**
	 * 锟斤拷始锟斤拷锟斤拷锟?
	 */
	private void initAdapter() {
		if (selectedDataList != null && selectedDataList.size() > 0) {
			sendBtn.setText(context.getResources().getString(
					R.string.umeng_socialize_send_btn_str)
					+ "("
					+ selectedDataList.size()
					+ "/"
					+ Constants.PIC_SELECTED_LIMIT_NUM + ")");
			total = selectedDataList.size();
			for (final ImageItem item : dataList) {
				final String path = item.getThumbnailPath();
				if (selectedDataList.contains(path)) {
					item.setSelected(true);
					ImageView imageView = (ImageView) LayoutInflater.from(
							ImageGridActivity.this).inflate(
							R.layout.choose_imageview, selectedImageLayout,
							false);
					imageView.setTag(path);
					selectedImageLayout.addView(imageView);
					hashMap.put(item.getThumbnailPath(), imageView);
					Bitmap bmp = cache.getCacheBitmap(item.getThumbnailPath(),
							null);
					if (null != bmp) {
						imageView.setImageBitmap(bmp);
					} else {
						cache.displayBmp(imageView, path, path, callback);
					}
					imageView.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							removePath(path);
							adapter.notifyDataSetChanged();
						}
					});
				}
			}
		}

		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		adapter.setSelectTotalNum(selectedDataList.size());
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count, String path) {
				refreshView(path);
				setSendText(count);
			}
		});
		gridView.setAdapter(adapter);
		gridView.setOnScrollListener(onScrollListener);
	}
    /**
     * 初始化
     */
	private void initView() {
		selectedImageLayout = (LinearLayout) findViewById(R.id.selected_image_layout);
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.notifyDataSetChanged();

			}
		});

		title = (TextView) findViewById(R.id.base_fragment_title);

		if (name.length() > 12) {
			name = name.substring(0, 11) + "...";
		}
		title.setText(name);
		leftBtn = (ImageView) findViewById(R.id.back_btn);
		leftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ImageGridActivity.this,
						PickPhotoActivity.class);
				startActivity(intent);
				ImageGridActivity.this.finish();
			}
		});
		cancel = (ImageView) findViewById(R.id.cancel_img);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.setSelectMap(null);
				setResult(RESULT_OK, null);
				if (picNum >= 0) {
					
						existDataList.addAll(selectedDataList.subList(0, picNum));
				}
				ImageGridActivity.this.finish();
			}
		});
		sendBtn = (Button) findViewById(R.id.finish);
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
					ImageGridActivity.this.finish();
				
				
			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (RESULT_OK != resultCode)
			return;
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		setAdapterSelectedMap(null);
		super.onStop();
	}
    /**
     * 更新选择的条目数
     * @param selNum
     */
	public static void setSendText(int selNum) {
		if (selNum == 0) {
			sendBtn.setText(context.getResources().getString(
					R.string.umeng_socialize_send_btn_str));
		} else {
			sendBtn.setText(context.getResources().getString(
					R.string.umeng_socialize_send_btn_str)
					+ "("
					+ selNum
					+ "/"
					+ Constants.PIC_SELECTED_LIMIT_NUM
					+ ")");
		}

	}

	/**
	 * 刷锟斤拷页锟斤拷
	 */
	private void refreshView(final String path) {
		if (!hashMap.containsKey(path)) {
			ImageView imageView = (ImageView) LayoutInflater.from(
					ImageGridActivity.this).inflate(R.layout.choose_imageview,
					selectedImageLayout, false);
			selectedImageLayout.addView(imageView);

			hashMap.put(path, imageView);
			selectedDataList.add(path);
			imageView.setTag(path);
			Bitmap bmp = cache.getCacheBitmap(path, null);
			if (null != bmp) {
				imageView.setImageBitmap(bmp);
			} else {
				cache.displayBmp(imageView, path, null, callback);
			}
			imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			sendBtn.setText(context.getResources().getString(
					R.string.umeng_socialize_send_btn_str)
					+ "("
					+ selectedDataList.size()
					+ "/"
					+ Constants.PIC_SELECTED_LIMIT_NUM + ")");
		} else {
			removePath(path);
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷选锟叫碉拷状态
	 * 
	 * @param map
	 */
	public static void setAdapterSelectedMap(Map<Integer, String> map) {
		Iterator<Integer> it = adapter.getSelectMap().keySet().iterator();
		if (map != null) {
			while (it.hasNext()) {
				int key = (Integer) it.next();
				if (map.containsKey(key)) {
					adapter.updateSelectedStatus(key, true);
				} else {
					adapter.updateSelectedStatus(key, false);
				}
			}
			adapter.setSelectMap(map);
			adapter.setSelectTotalNum(map.size());
		} else {
			while (it.hasNext()) {
				int key = (Integer) it.next();
				adapter.updateSelectedStatus(key, false);
			}
			adapter.setSelectMap(null);
			adapter.setSelectTotalNum(0);
		}
		adapter.notifyDataSetChanged();
	}

	public static ImageGridAdapter getAdapter() {
		return adapter;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			adapter.unlock();
			break;
		}
		return false;
	}

	/**
	 * 图片回调
	 */
	ImageCallback callback = new ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			try {
				if (null != imageView && null != bitmap) {
					String url = (String) params[0];
					if (null != url && url.equals((String) imageView.getTag())) {
						((ImageView) imageView).setImageBitmap(bitmap);
					} else {
					}
				} else {
				}
			} catch (Exception e) {
			}
		}
	};

	/**
	 * 删除选中的条目
	 * 
	 * @param path
	 * @return
	 */
	private boolean removePath(String path) {
		if (hashMap.containsKey(path)) {
			selectedImageLayout.removeView(hashMap.get(path));
			hashMap.remove(path);
			removeOneData(selectedDataList, path);
			sendBtn.setText(context.getResources().getString(
					R.string.umeng_socialize_send_btn_str)
					+ "("
					+ selectedDataList.size()
					+ "/"
					+ Constants.PIC_SELECTED_LIMIT_NUM + ")");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从选择列表中删除某一条目
	 * 
	 * @param arrayList
	 * @param s
	 */
	private void removeOneData(ArrayList<String> arrayList, String s) {
		if (arrayList != null) {
			for (int i = 0; i < arrayList.size(); i++) {
				if (arrayList.get(i).equals(s)) {
					arrayList.remove(i);
					return;
				}
			}
		}

	}
}
