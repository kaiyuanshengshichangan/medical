package com.henglianmobile.medical.adapter.patient;

import java.util.ArrayList;
import java.util.List;

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
import com.henglianmobile.medical.entity.patient.DoctorYuYueZhuYuanListObject;
import com.henglianmobile.medical.ui.activity.doctor.YuYueJiuZhenActivity;
import com.henglianmobile.medical.ui.activity.patient.GuahaoActivity;
import com.henglianmobile.medical.ui.activity.patient.PayTypeSelectActivity;
import com.henglianmobile.medical.ui.activity.patient.PersonalDoctorActivity;
import com.henglianmobile.medical.ui.activity.patient.RexianActivity;
import com.henglianmobile.medical.ui.activity.patient.ZhuyuanActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class YuYueZhuYuanAdapter extends BaseAdapter {
	private String userId;
	private Context context;
	private List<DoctorYuYueListObject> list;
	private LayoutInflater mInflater;

	private void setData(List<DoctorYuYueListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<DoctorYuYueListObject>();
		}
	}

	public YuYueZhuYuanAdapter(Context context,
			List<DoctorYuYueListObject> list, String userid) {
		this.userId = userid;
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);
	}

	public void changeData(List<DoctorYuYueListObject> list) {
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
	public DoctorYuYueListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnMakeid();
	}

	ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_patient_yuyuezhuyuan_list_item, null);
			holder.iv_doctor_photo = (ImageView) convertView
					.findViewById(R.id.iv_doctor_photo);
			holder.tv_doctor_name = (TextView) convertView
					.findViewById(R.id.tv_doctor_name);
			holder.tv_chuangweihao = (TextView) convertView
					.findViewById(R.id.tv_chuangweihao);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_hospital = (TextView) convertView
					.findViewById(R.id.tv_hospital);
			holder.tv_doctor_position = (TextView) convertView
					.findViewById(R.id.tv_doctor_position);
			holder.tv_yuyue_date = (TextView) convertView
					.findViewById(R.id.tv_yuyue_date);
			holder.tv_yuyue_state = (TextView) convertView
					.findViewById(R.id.tv_yuyue_state);
			holder.tv_pay = (TextView) convertView
					.findViewById(R.id.tv_pay);
			holder.tv_add_yuyue_time = (TextView) convertView
					.findViewById(R.id.tv_add_yuyue_time);
			holder.tv_yuyue_start_time = (TextView) convertView
					.findViewById(R.id.tv_yuyue_start_time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		DoctorYuYueListObject doctorYuYueZhuYuanListObject = list
				.get(position);
		if (doctorYuYueZhuYuanListObject != null) {
			holder.tv_doctor_name.setText(doctorYuYueZhuYuanListObject
					.getDcRealName());
			holder.tv_chuangweihao.setText(doctorYuYueZhuYuanListObject
					.getBedNo());
			float price = doctorYuYueZhuYuanListObject.getDnMakePrice();
			holder.tv_price.setText(price + "");
			holder.tv_hospital.setText(doctorYuYueZhuYuanListObject
					.getDcHospital());
			holder.tv_doctor_position.setText(doctorYuYueZhuYuanListObject
					.getDcPosition());
			holder.tv_yuyue_date.setText(doctorYuYueZhuYuanListObject
					.getStartDate());
			holder.tv_add_yuyue_time.setText(doctorYuYueZhuYuanListObject
					.getDtAddTime());
			holder.tv_yuyue_start_time.setText(doctorYuYueZhuYuanListObject
					.getStartHour());
			int isAgree = doctorYuYueZhuYuanListObject.getDnIsAgree();
			int isPayed = doctorYuYueZhuYuanListObject.getIsPayed();
			int makeId = doctorYuYueZhuYuanListObject.getDnMakeid();
			if(isPayed == 0){
				holder.tv_pay.setVisibility(View.VISIBLE);
				holder.tv_pay.setTag(R.string.did, makeId);
				holder.tv_pay.setTag(R.string.price, price);
				holder.tv_pay.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int makeId = (Integer) v.getTag(R.string.did);
						float price = (Float) v.getTag(R.string.price);
						Intent intent = new Intent(context, PayTypeSelectActivity.class);
						intent.putExtra("yuyueType", Constant.ZHUYUAN);
						intent.putExtra("makeId", makeId);
						intent.putExtra("price", price);
						context.startActivity(intent);
					}
				});
				holder.tv_yuyue_state.setText("等待支付");
			}else if(isPayed == 1){
				holder.tv_pay.setVisibility(View.GONE);
				if(isAgree == 0){
					holder.tv_yuyue_state.setText("预约等待");
				}else if(isAgree == 1){
					holder.tv_yuyue_state.setText("预约成功");
				}else if(isAgree == 2){
					holder.tv_yuyue_state.setText("预约失败");
				}
			}
			String headImg = doctorYuYueZhuYuanListObject.getDcHeadImg();
			ImageLoader.getInstance().displayImage(headImg,
					holder.iv_doctor_photo, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_doctor_name, tv_chuangweihao, tv_price,
				tv_hospital, tv_doctor_position, tv_yuyue_date, tv_yuyue_state,
				tv_pay, tv_add_yuyue_time,
				tv_yuyue_start_time;
		private ImageView iv_doctor_photo;
	}
}
