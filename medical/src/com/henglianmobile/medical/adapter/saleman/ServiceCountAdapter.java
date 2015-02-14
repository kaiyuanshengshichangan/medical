package com.henglianmobile.medical.adapter.saleman;

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
import com.henglianmobile.medical.entity.saleman.MyDoctorListObject;
import com.henglianmobile.medical.entity.saleman.ServiceListObject;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ServiceCountAdapter extends BaseAdapter {
	private String userId;
	private Context context;
	private List<ServiceListObject> list;
	private LayoutInflater mInflater;

	private void setData(List<ServiceListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<ServiceListObject>();
		}
	}

	public ServiceCountAdapter(Context context,
			List<ServiceListObject> list, String userid) {
		this.userId = userid;
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);
	}

	public void changeData(List<ServiceListObject> list) {
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
	public ServiceListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_saleman_service_count_list_item, null);
			holder.tv_doctor_name = (TextView) convertView
					.findViewById(R.id.tv_doctor_name);
			holder.tv_service_count = (TextView) convertView
					.findViewById(R.id.tv_service_count);
			holder.tv_service_day_count = (TextView) convertView
					.findViewById(R.id.tv_service_day_count);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ServiceListObject serviceListObject = list
				.get(position);
		if (serviceListObject != null) {
			holder.tv_doctor_name.setText(serviceListObject
					.getDcRealName());
			holder.tv_service_count.setText(serviceListObject
					.getServiceNum()+"´Î");
			holder.tv_service_day_count.setText(serviceListObject
					.getSumDay());
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_doctor_name, tv_service_count,tv_service_day_count;
	}
}
