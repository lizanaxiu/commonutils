package com.example.common.ui;

import java.io.IOException;
import java.io.InputStream;
import com.example.commonutils.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * 
 * @author liz 关于图片压缩的例子，降低图片像素不变形
 */
public class TestCompressBitmap extends Activity {

	private ImageView image1, image2;

	private Bitmap bitmap;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);

		// 从 asset 中取出事先放好的图片
		InputStream is = null;
		try {
			is = this.getAssets().open("bb.jpg");

			// 显示原始图片
			bitmap = BitmapFactory.decodeStream(is);
			image1.setImageBitmap(bitmap);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 显示压缩图片
		try {
			is = this.getAssets().open("bb.jpg");

			// 显示原始图片
			image2.setImageBitmap(revitionImageSize("bb.jpg", 200));
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Bitmap revitionImageSize(String path, int size) throws IOException {
		// 取得图片
		InputStream temp = this.getAssets().open(path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
		options.inJustDecodeBounds = true;
		// 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
		BitmapFactory.decodeStream(temp, null, options);
		// 关闭流
		temp.close();

		// 生成压缩的图片
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			// 这一步是根据要设置的大小，使宽和高都能满足
			if ((options.outWidth >> i <= size)
					&& (options.outHeight >> i <= size)) {
				// 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
				temp = this.getAssets().open(path);
				// 这个参数表示 新生成的图片为原始图片的几分之一。
				options.inSampleSize = (int) Math.pow(2.0D, i);
				Log.d("tag",
						"(int) Math.pow(2.0D, i)===" + (int) Math.pow(2.0D, i)
								+ "aa==" + i);
				// 这里之前设置为了true，所以要改为false，否则就创建不出图片
				options.inJustDecodeBounds = false;

				bitmap = BitmapFactory.decodeStream(temp, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}

}