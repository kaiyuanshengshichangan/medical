package com.henglianmobile.medical.ui.activity.doctor;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.ui.activity.patient.HuaYanDanActivity;
import com.henglianmobile.medical.ui.activity.patient.TiJianBaoGaoActivity;
import com.henglianmobile.medical.ui.activity.patient.YongYaoLiShiActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PatientHealthManageActivity extends BaseActivity {
	private ImageView btn_back;
	private TextView tv_patient_name, tv_patient_age;
	private ImageView iv_patient_photo,iv_sex;
	private RelativeLayout[] llArray = new RelativeLayout[4];
	private Intent intent;
	private String pId;

	private UserInfoDetailObject userInfoDetailObject;

	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_doctor_patient_health_manage);
		pId = getIntent().getStringExtra("pId");
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_patient_name = (TextView) findViewById(R.id.tv_patient_name);
		tv_patient_age = (TextView) findViewById(R.id.tv_patient_age);
		iv_patient_photo = (ImageView) findViewById(R.id.iv_patient_photo);
		iv_sex = (ImageView) findViewById(R.id.iv_sex);
		llArray[0] = (RelativeLayout) findViewById(R.id.ll_huayandan);
		llArray[1] = (RelativeLayout) findViewById(R.id.ll_yongyaolishi);
		llArray[2] = (RelativeLayout) findViewById(R.id.ll_tijianbaogao);
		llArray[3] = (RelativeLayout) findViewById(R.id.ll_health_manage_method);
	}

	@Override
	public void addListener() {
		for (RelativeLayout ll : llArray) {
			ll.setOnClickListener(this);
		}
		btn_back.setOnClickListener(this);
		iv_patient_photo.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = Const.GETUSERINFODETAILURL + pId;
		getDataHttp(url);
	}

	private void getDataHttp(String url) {
		LogUtil.i("url", "ZhangHaoGuanLiActivity--url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<UserInfoDetailObject>>() {
					}.getType();
					List<UserInfoDetailObject> userInfoDetailObjects = TApplication.gson
							.fromJson(responseString, type);
					if (userInfoDetailObjects.size() != 0) {
						userInfoDetailObject = userInfoDetailObjects.get(0);
						tv_patient_name.setText(userInfoDetailObject
								.getDcRealName());
						tv_patient_age.setText(userInfoDetailObject.getDnAge()+"");
						int sex = userInfoDetailObject.getDnSex();
						if (sex == 0) {//女性
							iv_sex.setBackgroundResource(R.drawable.iv_female);
						} else if (sex == 1) {
							iv_sex.setBackgroundResource(R.drawable.iv_man);
						}
						String photoPath = userInfoDetailObject.getDcHeadImg();
						ImageLoader.getInstance().displayImage(photoPath,
								iv_patient_photo, TApplication.optionsImage,
								new MyAnimateFirstDisplayListener());
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.iv_patient_photo:// 基本信息
			intent = new Intent(this, PatientBaseInfoLiActivity.class);
			intent.putExtra("userInfoDetailObject", userInfoDetailObject);
			startActivity(intent);
			break;
		case R.id.ll_huayandan:// 化验单
			intent = new Intent(this, HuaYanDanActivity.class);
			intent.putExtra(Constant.HEALTH_MANAGE_KEY,
					Constant.DOCTOR_HEALTH_MANAGE_VALUE);
			intent.putExtra("pId", pId);
			startActivity(intent);
			break;
		case R.id.ll_yongyaolishi:// 用药历史
			intent = new Intent(this, YongYaoLiShiActivity.class);
			intent.putExtra(Constant.HEALTH_MANAGE_KEY,
					Constant.DOCTOR_HEALTH_MANAGE_VALUE);
			intent.putExtra("pId", pId);
			startActivity(intent);
			break;
		case R.id.ll_tijianbaogao:// 体检报告
			intent = new Intent(this, TiJianBaoGaoActivity.class);
			intent.putExtra(Constant.HEALTH_MANAGE_KEY,
					Constant.DOCTOR_HEALTH_MANAGE_VALUE);
			intent.putExtra("pId", pId);
			startActivity(intent);
			break;
		case R.id.ll_health_manage_method:// 发布健康管理方案
			intent = new Intent(this, PublishHealthManageMethodActivity.class);
			intent.putExtra("pId", pId);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
