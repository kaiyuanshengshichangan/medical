package com.henglianmobile.medical.ui.activity.doctor;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PatientBaseInfoLiActivity extends BaseActivity {
	private ImageView btn_back;
	private TextView tv_name,tv_age, tv_mobile, tv_area, tv_person_introduce;
	private ImageView iv_photo;
	private RadioButton radioMan, radioFemale;
	private UserInfoDetailObject userInfoDetailObject;

	@Override
	public void loadLayout() {
		// 使软键盘不自动弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_doctor_patient_base_info);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		tv_area = (TextView) findViewById(R.id.tv_area);
		tv_person_introduce = (TextView) findViewById(R.id.tv_person_introduce);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		radioMan = (RadioButton) findViewById(R.id.radio0);
		radioFemale = (RadioButton) findViewById(R.id.radio1);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userInfoDetailObject = (UserInfoDetailObject) getIntent().getSerializableExtra("userInfoDetailObject");
		showContent();
	}

	private void showContent() {
		int isex = userInfoDetailObject.getDnSex();
		if (isex == 0) {
			radioMan.setChecked(false);
			radioFemale.setChecked(true);
		} else if (isex == 1) {
			radioMan.setChecked(true);
			radioFemale.setChecked(false);
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
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;

		default:
			break;
		}
	}
}
