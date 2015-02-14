package com.henglianmobile.medical.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GridViewAdapter extends BaseAdapter {
	private Context xContext;
	private List<String> pics;

	private void setPics(List<String> pics) {
		if (pics == null) {
			pics = new ArrayList<String>();
		} else {
			this.pics = pics;
		}
	}

	public GridViewAdapter(Context c, List<String> pics) {
		xContext = c;
		this.setPics(pics);

	}

	@Override
	public int getCount() {
		return pics.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView imageview;
		if (convertView == null) {
			imageview = new ImageView(xContext);
			imageview.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageview.setScaleType(ImageView.ScaleType.CENTER);
			// imageview.setFocusable(false);
		} else {
			imageview = (ImageView) convertView;
		}
		String picUrl = pics.get(position);
		if (picUrl != null&&!TextUtils.isEmpty(picUrl)) {
			ImageLoader.getInstance().displayImage(picUrl,
					imageview, TApplication.optionsImage,new MyAnimateFirstDisplayListener());
		}
		return imageview;
	}
}
