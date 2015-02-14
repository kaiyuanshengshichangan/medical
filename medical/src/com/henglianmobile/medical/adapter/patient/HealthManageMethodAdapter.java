package com.henglianmobile.medical.adapter.patient;

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
import com.henglianmobile.medical.entity.patient.HealthManageMethodListObject;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HealthManageMethodAdapter extends BaseAdapter {
	private Context context;
	private List<HealthManageMethodListObject> list;
	private LayoutInflater mInflater;

	private void setData(List<HealthManageMethodListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<HealthManageMethodListObject>();
		}
	}

	public HealthManageMethodAdapter(Context context,
			List<HealthManageMethodListObject> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);

	}

	public void changeData(List<HealthManageMethodListObject> list) {
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
	public HealthManageMethodListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_patient_health_manage_method_list_item,
					null);
			holder.iv_doctor_photo = (ImageView) convertView
					.findViewById(R.id.iv_doctor_photo);
			holder.tv_doctor_name = (TextView) convertView
					.findViewById(R.id.tv_doctor_name);
			holder.tv_doctor_keshi = (TextView) convertView
					.findViewById(R.id.tv_doctor_keshi);
			holder.tv_hospital = (TextView) convertView
					.findViewById(R.id.tv_hospital);
			holder.tv_doctor_position = (TextView) convertView
					.findViewById(R.id.tv_doctor_position);
			holder.tv_doctor_advice = (TextView) convertView
					.findViewById(R.id.tv_doctor_advice);
			holder.tv_add_time = (TextView) convertView
					.findViewById(R.id.tv_add_time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HealthManageMethodListObject healthManageMethodListObject = list
				.get(position);

		holder.tv_doctor_name.setText(healthManageMethodListObject
				.getDcRealName());
		holder.tv_doctor_keshi.setText(healthManageMethodListObject
				.getDcDepart());
		holder.tv_hospital
				.setText(healthManageMethodListObject.getDcHospital());
		holder.tv_doctor_position.setText("");
		holder.tv_doctor_advice.setText(healthManageMethodListObject
				.getDcContent());
		holder.tv_add_time.setText(healthManageMethodListObject.getDtAddTime());
		String photoPath = healthManageMethodListObject.getDcHeadImg();
		ImageLoader.getInstance().displayImage(photoPath,
				holder.iv_doctor_photo, TApplication.optionsImage,
				new MyAnimateFirstDisplayListener());
		return convertView;
	}

	class ViewHolder {
		private ImageView iv_doctor_photo;
		private TextView tv_doctor_name, tv_doctor_keshi, tv_hospital,
				tv_doctor_position, tv_doctor_advice, tv_add_time;
	}
}
