package com.henglianmobile.medical.adapter.saleman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientYuYueListObject;
import com.henglianmobile.medical.entity.patient.SearchDoctorBaseInfoObject;
import com.henglianmobile.medical.entity.patient.DoctorYuYueListObject;
import com.henglianmobile.medical.entity.saleman.MyDoctorListObject;
import com.henglianmobile.medical.ui.activity.doctor.YuYueJiuZhenActivity;
import com.henglianmobile.medical.ui.activity.patient.GuahaoActivity;
import com.henglianmobile.medical.ui.activity.patient.PersonalDoctorActivity;
import com.henglianmobile.medical.ui.activity.patient.RexianActivity;
import com.henglianmobile.medical.ui.activity.patient.ZhuyuanActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommissionMyDoctorAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> list;
	private LayoutInflater mInflater;

	private void setData(List<Map<String, Object>> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<Map<String,Object>>();
		}
	}

	public CommissionMyDoctorAdapter(Context context,
			List<Map<String, Object>> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);
	}

	public void changeData(List<Map<String, Object>> list) {
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
	public Map<String, Object> getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return (Integer) getItem(position).get("id");
	}

	ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_saleman_commission_mydoctor_list_item, null);
			holder.tv_doctor_name = (TextView) convertView
					.findViewById(R.id.tv_doctor_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> map = list
				.get(position);
		if (map != null) {
			holder.tv_doctor_name.setText((String) map.get("name"));
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_doctor_name;
	}
}
