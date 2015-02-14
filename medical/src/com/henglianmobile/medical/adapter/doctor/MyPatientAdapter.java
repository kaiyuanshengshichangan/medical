package com.henglianmobile.medical.adapter.doctor;

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
import com.henglianmobile.medical.entity.doctor.MyPatientBaseInfoObject;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPatientAdapter extends BaseAdapter {
	private Context context;
	private List<MyPatientBaseInfoObject> list;
	private LayoutInflater mInflater;

	private void setData(List<MyPatientBaseInfoObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<MyPatientBaseInfoObject>();
		}
	}

	public MyPatientAdapter(Context context, List<MyPatientBaseInfoObject> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);

	}

	public void changeData(List<MyPatientBaseInfoObject> list) {
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
	public MyPatientBaseInfoObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnUserid();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_doctor_my_patient_list_item, null);
			holder.iv_patient_photo = (ImageView) convertView
					.findViewById(R.id.iv_patient_photo);
			holder.tv_patient_name = (TextView) convertView
					.findViewById(R.id.tv_patient_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyPatientBaseInfoObject myPatientBaseInfoObject = list.get(position);
		if (myPatientBaseInfoObject != null) {
			holder.tv_patient_name.setText(myPatientBaseInfoObject
					.getDcRealName());
			String headImg = myPatientBaseInfoObject.getDcHeadImg();
			ImageLoader.getInstance().displayImage(headImg,
					holder.iv_patient_photo, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_patient_name;
		private ImageView iv_patient_photo;
	}
}
