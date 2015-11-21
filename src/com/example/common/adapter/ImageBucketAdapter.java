
package com.example.common.adapter;

import java.util.List;

import com.example.common.entity.ImageBucket;
import com.example.common.utils.BitmapCache;
import com.example.common.utils.BitmapCache.ImageCallback;
import com.example.commonutils.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description 相册适配器
 */
public class ImageBucketAdapter extends BaseAdapter {

    private Activity act;
    public static int selectedPosition = -1;

    // 图片集列表
    List<ImageBucket> dataList;
    BitmapCache cache;
    ImageCallback callback = new ImageCallback() {
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

    public ImageBucketAdapter(Activity act, List<ImageBucket> list) {
        this.act = act;
        dataList = list;
        cache = BitmapCache.getInstance();
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
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @SuppressLint("ResourceAsColor") @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        try {
            if (null == convertView) {
                holder = new Holder();
                convertView = View.inflate(act, R.layout.tt_item_image_pick, null);
                holder.iv = (ImageView) convertView.findViewById(R.id.image);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.count = (TextView) convertView.findViewById(R.id.count);
                holder.albumArrow = (ImageView) convertView
                        .findViewById(R.id.im_album_arrow);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            ImageBucket item = dataList.get(position);
            holder.count.setText("(" + item.count + ")");
            String nameStr = item.bucketName;
            if (nameStr.length() > 14) {
                nameStr = nameStr.substring(0, 14) + "...";
            }
            holder.name.setText(nameStr);
            if (item.imageList != null && item.imageList.size() > 0) {
                String thumbPath = item.imageList.get(0).getThumbnailPath();
                String sourcePath = item.imageList.get(0).getImagePath();
                holder.iv.setTag(sourcePath);
                Bitmap bmp = cache.getCacheBitmap(thumbPath, sourcePath);
                if (bmp != null) {
                    holder.iv.setImageBitmap(bmp);
                } else {
                    cache.displayBmp(holder.iv, thumbPath, sourcePath, callback);
                }
            } else {
                holder.iv.setImageBitmap(null);
            }
            if (position == selectedPosition) {
                convertView.setBackgroundResource(R.drawable.tt_album_item_sel_bk);
                holder.albumArrow.setImageResource(R.drawable.common_right_arow_bg);
                holder.name.setTextColor(Color.WHITE);
                holder.count.setTextColor(Color.WHITE);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
                holder.albumArrow.setImageResource(R.drawable.common_right_arow_bg);
                holder.name.setTextColor(Color.BLACK);
                holder.count.setTextColor(R.color.album_list_item_count_color);
            }
            return convertView;
        } catch (Exception e) {
            return null;
        }
    }

    class Holder {
        private ImageView iv;
        private TextView name;
        private TextView count;
        private ImageView albumArrow;
    }
}
