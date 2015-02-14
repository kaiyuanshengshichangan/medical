package com.henglianmobile.medical.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.HuanyouquanPinglun;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HuanyouquanPinglunAdapter extends BaseAdapter {
	private List<HuanyouquanPinglun> list;
	private LayoutInflater mInflater;

	private void setData(List<HuanyouquanPinglun> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<HuanyouquanPinglun>();
		}
	}

	public HuanyouquanPinglunAdapter(Context context,
			List<HuanyouquanPinglun> list) {
		this.setData(list);
		this.mInflater = LayoutInflater.from(context);

	}

	public void changeData(List<HuanyouquanPinglun> list) {
		if (list != null) {
			this.setData(list);
			this.notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public HuanyouquanPinglun getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.huanyouquan_pinglun_list_item, null);
			holder.iv_comment_photo = (ImageView) convertView
					.findViewById(R.id.iv_comment_photo);
			holder.tv_comment_nick = (TextView) convertView
					.findViewById(R.id.tv_comment_nick);
			holder.tv_comment_content = (TextView) convertView
					.findViewById(R.id.tv_comment_content);
			holder.tv_comment_time = (TextView) convertView
					.findViewById(R.id.tv_comment_time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HuanyouquanPinglun commitResultList = list.get(position);
		if (commitResultList != null) {
			holder.tv_comment_nick.setText(commitResultList.getDcNickName());
			holder.tv_comment_content.setText(commitResultList.getMyAnswer());
			holder.tv_comment_time.setText(Tools.DateStrToDateStr(commitResultList.getAddDate()));
			String photoPath = commitResultList.getDcHeadImg();
			ImageLoader.getInstance().displayImage(photoPath,
					holder.iv_comment_photo, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());
		}
		return convertView;
	}

	class ViewHolder {
		private ImageView iv_comment_photo;
		private TextView tv_comment_nick, tv_comment_content, tv_comment_time;
	}
}
