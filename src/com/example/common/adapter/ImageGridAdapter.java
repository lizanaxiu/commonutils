package com.example.common.adapter;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.common.entity.ImageItem;
import com.example.common.selectPic.PickPhotoActivity;
import com.example.common.utils.BitmapCache;
import com.example.common.utils.BitmapCache.ImageCallback;
import com.example.commonutils.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @Description 图片列表适配器
 */
public class ImageGridAdapter extends BaseAdapter {

	private TextCallback textcallback = null;
	Activity activity = null;
	List<ImageItem> dataList = null;
	private Map<Integer, String> selectedMap = new TreeMap<Integer, String>();
	BitmapCache cache = null;
	private Handler mHandler = null;
	private int selectTotal = 0;
	private boolean allowLoad = true;

	public void lock() {
		this.allowLoad = false;
		notifyDataSetChanged();
	}

	public void unlock() {
		this.allowLoad = true;
		notifyDataSetChanged();
	}

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

	public ImageGridAdapter(Activity act, List<ImageItem> list, Handler mHandler) {
		this.activity = act;
		cache = BitmapCache.getInstance();
		this.dataList = list;
		this.mHandler = mHandler;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (null != dataList) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void updateSelectedStatus(int position, boolean selected) {
		if (null != dataList) {
			ImageItem item = dataList.get(position);
			item.setSelected(selected);
		}
	}

	public void setSelectTotalNum(int num) {
		selectTotal = num;
	}

	public int getSelectTotalNum() {
		return selectTotal;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		try {
			if (null == convertView) {
				holder = new Holder();
				convertView = View.inflate(activity,
						R.layout.tt_item_image_grid, null);
				holder.iv = (ImageView) convertView.findViewById(R.id.image);
				holder.selected = (ImageView) convertView
						.findViewById(R.id.isselected);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			setHolder(holder, position);

			if (getCount() - 1 == position) {
				convertView.setPadding(0, 0, 0, 30);
			} else {
				convertView.setPadding(0, 0, 0, 0);
			}

			return convertView;
		} catch (Exception e) {
			return null;
		}
	}

	private void setHolder(final Holder holder, final int position) {
		try {
			final ImageItem item = dataList.get(position);
			holder.iv.setTag(item.getImagePath());
			Bitmap bmp = cache.getCacheBitmap(item.getThumbnailPath(),
					item.getImagePath());
			if (null != bmp) {
				holder.iv.setImageBitmap(bmp);
			} else {
				if (allowLoad) {
					cache.displayBmp(holder.iv, item.getThumbnailPath(),
							item.getImagePath(), callback);
				} else {
					holder.iv
							.setImageResource(R.drawable.tt_default_album_grid_image);
				}
			}

			if (item.isSelected()) {
				holder.selected.setImageResource(R.drawable.checkbox_pressed);
			} else {
				holder.selected
						.setImageResource(R.drawable.tt_album_img_select_nor);
			}
			holder.iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String path = dataList.get(position).getImagePath();
					String thumbPath = dataList.get(position)
							.getThumbnailPath();
					Bitmap bmp = cache.getCacheBitmap(thumbPath, path);
					if (null != bmp && bmp == PickPhotoActivity.bimap) {
						Toast.makeText(
								activity,
								activity.getResources().getString(
										R.string.unavailable_image_file),
								Toast.LENGTH_LONG).show();
						return;
					}
					if (selectTotal <9) {
						item.setSelected(!item.isSelected());
						if (item.isSelected()) {
							holder.selected
									.setImageResource(R.drawable.checkbox_pressed);
							selectTotal++;
							if (null != textcallback){
								if(thumbPath!=null){
									textcallback.onListen(selectTotal, thumbPath);
								}else{
									textcallback.onListen(selectTotal, path);
								}
							}
							selectedMap.put(position, path);
						} else if (!item.isSelected()) {
							holder.selected
									.setImageResource(R.drawable.tt_album_img_select_nor);
							selectTotal--;
							if (null != textcallback){
								if(thumbPath!=null){
									textcallback.onListen(selectTotal, thumbPath);
								}else{
									textcallback.onListen(selectTotal, path);
								}
								
							}
								
							selectedMap.remove(position);
						}
					} else if (selectTotal >= 9) {
						if (item.isSelected() == true) {
							holder.selected
									.setImageResource(R.drawable.tt_album_img_select_nor);
							selectTotal--;
							if (null != textcallback){
								if(thumbPath!=null){
									textcallback.onListen(selectTotal, thumbPath);
								}else{
									textcallback.onListen(selectTotal, path);
								}
							}
							selectedMap.remove(position);
						} else {
							Message message = Message.obtain(mHandler, 0);
							message.sendToTarget();
						}
					}
				}

			});
		} catch (Exception e) {

		}
	}

	public Map<Integer, String> getSelectMap() {
		return selectedMap;
	}

	public void setSelectMap(Map<Integer, String> map) {
		if (null == map) {
			selectedMap.clear();
			return;
		}
		selectedMap = map;
	}

	public void setTextCallback(TextCallback listener) {
		textcallback = listener;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
	}

	public static interface TextCallback {
		public void onListen(int count, String path);
	}

}
