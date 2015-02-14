package com.henglianmobile.medical.adapter.doctor;

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

public class YuYuePersonalDoctorAdapter extends BaseAdapter implements
		OnClickListener {
	private String userId;
	private Context context;
	private List<PatientYuYueListObject> list;
	private LayoutInflater mInflater;
	private Handler handler;

	private void setData(List<PatientYuYueListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<PatientYuYueListObject>();
		}
	}

	public YuYuePersonalDoctorAdapter(Context context,
			List<PatientYuYueListObject> list, String userid, Handler handler) {
		this.userId = userid;
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);
		this.handler = handler;
	}

	public void changeData(List<PatientYuYueListObject> list) {
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
	public PatientYuYueListObject getItem(int position) {
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
					R.layout.activity_doctor_personal_doctor_yuyue_list_item,
					null);
			holder.iv_patient_photo = (ImageView) convertView
					.findViewById(R.id.iv_patient_photo);
			holder.tv_patient_name = (TextView) convertView
					.findViewById(R.id.tv_patient_name);
			holder.tv_patient_add_time = (TextView) convertView
					.findViewById(R.id.tv_patient_add_time);
			holder.tv_date_start = (TextView) convertView
					.findViewById(R.id.tv_date_start);
			holder.tv_date_end = (TextView) convertView
					.findViewById(R.id.tv_date_end);
			holder.tv_agree = (TextView) convertView
					.findViewById(R.id.tv_agree);
			holder.tv_refuse = (TextView) convertView
					.findViewById(R.id.tv_refuse);
			holder.tv_other = (TextView) convertView
					.findViewById(R.id.tv_other);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PatientYuYueListObject yuYueJiuZhenListObject = list.get(position);
		if (yuYueJiuZhenListObject != null) {
			holder.tv_patient_name.setText(yuYueJiuZhenListObject
					.getDcRealName());
			holder.tv_date_start.setText(yuYueJiuZhenListObject.getStartDate());
			holder.tv_date_end.setText(yuYueJiuZhenListObject.getEndDate());
			holder.tv_other.setText(yuYueJiuZhenListObject.getDcOther());
			holder.tv_patient_add_time.setText(Tools
					.DateStrToDateStr(yuYueJiuZhenListObject.getDtAddTime()));
			int dnIsAgree = yuYueJiuZhenListObject.getDnIsAgree();
			if (dnIsAgree == 1) {
				holder.tv_agree.setTextColor(Color.rgb(232, 84, 74));
				holder.tv_refuse.setTextColor(Color.rgb(166, 166, 166));
				holder.tv_agree.setEnabled(false);
				holder.tv_refuse.setEnabled(false);
			} else if (dnIsAgree == 2) {
				holder.tv_agree.setTextColor(Color.rgb(166, 166, 166));
				holder.tv_refuse.setTextColor(Color.rgb(232, 84, 74));
				holder.tv_agree.setEnabled(false);
				holder.tv_refuse.setEnabled(false);
			}
			String headImg = yuYueJiuZhenListObject.getDcHeadImg();
			ImageLoader.getInstance().displayImage(headImg,
					holder.iv_patient_photo, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());
			int mid = yuYueJiuZhenListObject.getDnMakeid();
			holder.tv_agree.setTag(mid);
			holder.tv_refuse.setTag(mid);
			holder.tv_agree.setOnClickListener(this);
			holder.tv_refuse.setOnClickListener(this);
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_patient_name, tv_patient_add_time, tv_agree,
				tv_refuse, tv_date_start, tv_date_end,tv_other;
		private ImageView iv_patient_photo;
	}

	private int isagree;

	@Override
	public void onClick(View v) {
		int mid = (Integer) v.getTag();
		switch (v.getId()) {
		case R.id.tv_agree:// 同意（有号）
			isagree = 1;
			break;
		case R.id.tv_refuse:// 拒绝(无号)
			isagree = 2;
			break;
		}
		String url = Const.DOCTORSOLVEYUYUEJIUZHENLISTURL + "uid=" + userId
				+ "&mid=" + mid + "&isagree=" + isagree + "&bedNo=0000";
		;
		sendHttpGet(url);
	}

	private void sendHttpGet(String url) {
		LogUtil.i("url", "YuYueJiuZhenAdapter---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						int response = Integer.parseInt(jsonObject
								.getString("response"));
						if (response == 0) {
							Tools.showMsg(context, "处理失败!");
						} else if (response == 1) {
							// 预约成功，跳转到支付页面
							Tools.showMsg(context, "处理成功!");
							// if(isagree == 1){
							// holder.tv_have_number.setTextColor(Color.rgb(232,
							// 84, 74));
							// holder.tv_have_no_number.setTextColor(Color.rgb(166,
							// 166, 166));
							// }
							// if(isagree == 2){
							// holder.tv_have_number.setTextColor(Color.rgb(166,
							// 166, 166));
							// holder.tv_have_no_number.setTextColor(Color.rgb(232,
							// 84, 74));
							// }
							// holder.tv_have_number.setEnabled(false);
							// holder.tv_have_no_number.setEnabled(false);
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
