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
 * @author liz ����ͼƬѹ�������ӣ�����ͼƬ���ز�����
 */
public class TestCompressBitmap extends Activity {

	private ImageView image1, image2;

	private Bitmap bitmap;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);

		// �� asset ��ȡ�����ȷźõ�ͼƬ
		InputStream is = null;
		try {
			is = this.getAssets().open("bb.jpg");

			// ��ʾԭʼͼƬ
			bitmap = BitmapFactory.decodeStream(is);
			image1.setImageBitmap(bitmap);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ��ʾѹ��ͼƬ
		try {
			is = this.getAssets().open("bb.jpg");

			// ��ʾԭʼͼƬ
			image2.setImageBitmap(revitionImageSize("bb.jpg", 200));
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Bitmap revitionImageSize(String path, int size) throws IOException {
		// ȡ��ͼƬ
		InputStream temp = this.getAssets().open(path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// �������������Ϊbitmap�����ڴ�ռ䣬ֻ��¼һЩ��ͼƬ����Ϣ������ͼƬ��С����˵���˾���Ϊ���ڴ��Ż�
		options.inJustDecodeBounds = true;
		// ͨ������ͼƬ�ķ�ʽ��ȡ��options�����ݣ��������������java�ĵ�ַ��������ֵ��
		BitmapFactory.decodeStream(temp, null, options);
		// �ر���
		temp.close();

		// ����ѹ����ͼƬ
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			// ��һ���Ǹ���Ҫ���õĴ�С��ʹ��͸߶�������
			if ((options.outWidth >> i <= size)
					&& (options.outHeight >> i <= size)) {
				// ����ȡ������ע�⣺����һ��Ҫ�ٴμ��أ����ܶ���ʹ��֮ǰ������
				temp = this.getAssets().open(path);
				// ���������ʾ �����ɵ�ͼƬΪԭʼͼƬ�ļ���֮һ��
				options.inSampleSize = (int) Math.pow(2.0D, i);
				Log.d("tag",
						"(int) Math.pow(2.0D, i)===" + (int) Math.pow(2.0D, i)
								+ "aa==" + i);
				// ����֮ǰ����Ϊ��true������Ҫ��Ϊfalse������ʹ�������ͼƬ
				options.inJustDecodeBounds = false;

				bitmap = BitmapFactory.decodeStream(temp, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}

}