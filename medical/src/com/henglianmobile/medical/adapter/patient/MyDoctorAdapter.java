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
import com.henglianmobile.medical.entity.patient.MyDoctorBaseInfoObject;
import com.henglianmobile.medical.entity.patient.SearchDoctorBaseInfoObject;
import com.henglianmobile.medical.ui.activity.patient.GuahaoActivity;
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

public class MyDoctorAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private List<MyDoctorBaseInfoObject> list;
	private LayoutInflater mInflater;
	private Handler handler;

	private void setData(List<MyDoctorBaseInfoObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<MyDoctorBaseInfoObject>();
		}
	}

	public MyDoctorAdapter(Context context, List<MyDoctorBaseInfoObject> list,Handler handler) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);
		this.handler = handler;
	}

	public void changeData(List<MyDoctorBaseInfoObject> list) {
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
	public MyDoctorBaseInfoObject getItem(int position) {
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
					R.layout.activity_patient_my_doctor_list_item, null);
			holder.iv_doctor_photo = (ImageView) convertView
					.findViewById(R.id.iv_doctor_photo);
			holder.tv_doctor_name = (TextView) convertView
					.findViewById(R.id.tv_doctor_name);
			holder.tv_doctor_potision = (TextView) convertView
					.findViewById(R.id.tv_doctor_potision);
			holder.tv_hospital = (TextView) convertView
					.findViewById(R.id.tv_hospital);
			holder.tv_keshi = (TextView) convertView
					.findViewById(R.id.tv_keshi);
			holder.tv_doctor_action = (TextView) convertView
					.findViewById(R.id.tv_doctor_action);
			holder.tv_tuijianzhishu = (TextView) convertView
					.findViewById(R.id.tv_tuijianzhishu);
			holder.tv_delete_friend = (TextView) convertView
					.findViewById(R.id.tv_delete_friend);
			holder.ll_delete = (LinearLayout) convertView
					.findViewById(R.id.ll_delete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyDoctorBaseInfoObject myDoctorBaseInfoObject = list.get(position);
		if (myDoctorBaseInfoObject != null) {
			holder.tv_doctor_name.setText(myDoctorBaseInfoObject
					.getDcRealName());
			holder.tv_doctor_potision.setText(myDoctorBaseInfoObject
					.getDcPosition());
			holder.tv_hospital.setText(myDoctorBaseInfoObject.getDcHospital());
			holder.tv_keshi.setText(myDoctorBaseInfoObject.getDcDepart());
			holder.tv_doctor_action.setText(myDoctorBaseInfoObject
					.getDcActions());
			holder.tv_tuijianzhishu.setText(myDoctorBaseInfoObject.getDnHot());

			String photoPath = myDoctorBaseInfoObject.getDcHeadImg();
			ImageLoader.getInstance().displayImage(photoPath,
					holder.iv_doctor_photo, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());

			if(handler == null){
				holder.ll_delete.setVisibility(View.GONE);
			}else{
				holder.ll_delete.setVisibility(View.VISIBLE);
			}
			int isFriend = myDoctorBaseInfoObject.getIsFriend();
			if(isFriend == 1){
				holder.tv_delete_friend.setVisibility(View.VISIBLE);
			}else {
				holder.tv_delete_friend.setVisibility(View.GONE);
			}
			int mId = myDoctorBaseInfoObject.getDnMakeid();//预约号
			holder.tv_delete_friend.setTag(mId);
			holder.tv_delete_friend.setOnClickListener(this);
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_doctor_name, tv_doctor_potision, tv_hospital,
				tv_keshi, tv_doctor_action, tv_tuijianzhishu, tv_delete_friend;
		private ImageView iv_doctor_photo;
		private LinearLayout ll_delete;
	}

	@Override
	public void onClick(View v) {
		int dId = (Integer) v.getTag();
		switch (v.getId()) {
		case R.id.tv_delete_friend:// 我是患者（删除好友）
			String url = Const.PATIENTDELETEDOCTORURL+"uid="+TApplication.user.getUid()+"&mid="+dId;
			sendHttpGet(url);
			break;

		default:
			break;
		}
	}
	private void sendHttpGet(String url) {
		LogUtil.i("url", "YuYueJiuZhenAdapter---url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						int response = Integer.parseInt(jsonObject.getString("response"));
						if(response == 0){
							Tools.showMsg(context, "删除失败!");
						}else if(response == 1){
							//预约成功，跳转到支付页面
							Tools.showMsg(context, "删除成功!");
							handler.sendEmptyMessage(0);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(context, Tools.HTTP_ERROR);
			}
		});
	}
}
