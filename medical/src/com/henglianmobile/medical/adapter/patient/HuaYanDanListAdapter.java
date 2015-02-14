package com.henglianmobile.medical.adapter.patient;

import java.util.ArrayList;
import java.util.List;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientListObject;
import com.henglianmobile.medical.entity.patient.HuaYanDanListObject;
import com.henglianmobile.medical.ui.activity.HuanyouPagerActivity;
import com.henglianmobile.medical.ui.activity.patient.ShowPictureActivity;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HuaYanDanListAdapter extends BaseAdapter {

	private Context context;
	private List<HuaYanDanListObject> list;
	private LayoutInflater mInflater;

	private void setData(List<HuaYanDanListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<HuaYanDanListObject>();
		}
	}

	public HuaYanDanListAdapter(Context context, List<HuaYanDanListObject> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);

	}

	public void changeData(List<HuaYanDanListObject> list) {
		if (list != null) {
			LogUtil.i("info", "list.size====" + list.size());
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
	public HuaYanDanListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnID();
	}

	// private TextView ,,tv_publish_time;
	// private ImageView ,,,
	// ,,,;
	// private GridView ;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_patient_huayandan_list_item, null);
			holder.tv_upload_date = (TextView) convertView
					.findViewById(R.id.tv_upload_date);
			holder.iv_huayandan_pic = (ImageView) convertView
					.findViewById(R.id.iv_huayandan_pic);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HuaYanDanListObject huaYanDanListObject = list.get(position);
		if (huaYanDanListObject != null) {
			holder.tv_upload_date.setText(huaYanDanListObject.getDtEditTime());
			// ªÒ»°Õº∆¨
			String huaYanDanPic = huaYanDanListObject.getDcImgPath();
			ImageLoader.getInstance().displayImage(huaYanDanPic,
					holder.iv_huayandan_pic, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());
			holder.iv_huayandan_pic.setTag(huaYanDanPic);
			holder.iv_huayandan_pic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String picPath = (String) v.getTag();
					Intent intent = new Intent(context, ShowPictureActivity.class);
					intent.putExtra("picPath", picPath);
					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_upload_date;
		private ImageView iv_huayandan_pic;
	}
}
