package com.example.common.selectPic;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.common.adapter.ImageBucketAdapter;
import com.example.common.entity.ImageBucket;
import com.example.common.entity.ImageItem;
import com.example.common.utils.AlbumHelper;
import com.example.commonutils.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ListView;
/**
 * 选择图片入口 相册列表
 * @author liz
 * 实现了扫描本地带有图片的文件夹功能，
 * 根据每个文件夹获取到里面的图片集合，
 * 包含预览 大图，图片地址等字段
 *
 */
public class PickPhotoActivity extends Activity {

	private Gallery gallery;
	private Gallery gallery1;
	private ArrayList<File> files;
	List<ImageBucket> dataList = null;
	AlbumHelper helper = null;
	ListView listView = null;
	public static Bitmap bimap = null;
	ImageBucketAdapter adapter = null;
	private String from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_photo);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.tt_default_album_grid_image);
		helper = AlbumHelper.getHelper(getApplicationContext());
		dataList = helper.getImagesBucketList(false);
		listView = (ListView) findViewById(R.id.list);
		adapter = new ImageBucketAdapter(this, dataList);
		findViewById(R.id.cancel_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List<ImageItem> images = dataList.get(position).imageList;
				Intent intent = new Intent(PickPhotoActivity.this,
						ImageGridActivity.class);
				intent.putExtra("imagelist", (Serializable) images);
				intent.putExtra(ImageGridActivity.EXTRA_ALBUM_NAME,
						dataList.get(position).bucketName);
				startActivity(intent);
				finish();
			}
		});
		listView.setAdapter(adapter);

	}
   /**
    * 锟斤拷取sd锟斤拷路锟斤拷
    * @return
    */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}


}
