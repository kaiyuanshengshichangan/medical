package com.henglianmobile.medical.ui.activity.doctor;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.entity.doctor.MyPatientBaseInfoObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PatientDetailActivity extends BaseActivity {
	private TextView tv_name,tv_age,tv_mobile,tv_area,tv_person_introduce;
	private ImageView iv_photo;
	private ImageView btn_back;
	private int uId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_doctor_my_patient_disease_info);
		uId = getIntent()
				.getIntExtra("uId", 0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String url = Const.GETUSERINFODETAILURL + uId;
		getDataHttp(url);
	}
	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		tv_area = (TextView) findViewById(R.id.tv_area);
		tv_person_introduce = (TextView) findViewById(R.id.tv_person_introduce);
		
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
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
					if(userInfoDetailObjects!=null&&userInfoDetailObjects.size()!=0){
						UserInfoDetailObject userInfoDetailObject = userInfoDetailObjects.get(0);
						showContent(userInfoDetailObject);
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
	private void showContent(UserInfoDetailObject userInfoDetailObject) {
		int isex = userInfoDetailObject.getDnSex();
		if (isex == 0) {
			iv_photo.setImageResource(R.drawable.iv_female);
		} else if (isex == 1) {
			iv_photo.setImageResource(R.drawable.iv_man);
		}
		tv_name.setText(userInfoDetailObject.getDcRealName());
		tv_age.setText(userInfoDetailObject.getDnAge() + "");
		tv_mobile.setText(userInfoDetailObject.getDcCellPhone());
		tv_area.setText(userInfoDetailObject.getDcAddress());
		tv_person_introduce.setText(userInfoDetailObject.getDcSign());
		String photoPath = userInfoDetailObject.getDcHeadImg();
		ImageLoader.getInstance().displayImage(photoPath, iv_photo,
				TApplication.optionsImage, new MyAnimateFirstDisplayListener());
	}
	@Override
	public void onClick(View v) {
		this.finish();
	}
}
