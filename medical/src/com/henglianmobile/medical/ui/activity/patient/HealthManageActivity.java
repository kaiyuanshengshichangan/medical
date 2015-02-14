package com.henglianmobile.medical.ui.activity.patient;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.util.Constant;

public class HealthManageActivity extends BaseActivity {
	private ImageView btn_back;
	private LinearLayout[] llArray = new LinearLayout[5];
	private Intent intent;

	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_patient_health_manage);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		llArray[0] = (LinearLayout)findViewById(R.id.ll_base_info);
		llArray[1] = (LinearLayout)findViewById(R.id.ll_huayandan);
		llArray[2] = (LinearLayout)findViewById(R.id.ll_yongyaolishi);
		llArray[3] = (LinearLayout)findViewById(R.id.ll_tijianbaogao);
		llArray[4] = (LinearLayout)findViewById(R.id.ll_health_manage_method);
	}

	@Override
	public void addListener() {
		for (LinearLayout ll : llArray) {
			ll.setOnClickListener(this);
		}
		btn_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.ll_base_info://基本信息
			intent = new Intent(this, BaseInformationActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_huayandan://化验单
			intent = new Intent(this, HuaYanDanActivity.class);
			intent.putExtra(Constant.HEALTH_MANAGE_KEY, Constant.PATIENT_HEALTH_MANAGE_VALUE);
			startActivity(intent);
			break;
		case R.id.ll_yongyaolishi://用药历史
			intent = new Intent(this, YongYaoLiShiActivity.class);
			intent.putExtra(Constant.HEALTH_MANAGE_KEY, Constant.PATIENT_HEALTH_MANAGE_VALUE);
			startActivity(intent);
			break;
		case R.id.ll_tijianbaogao://体检报告
			intent = new Intent(this, TiJianBaoGaoActivity.class);
			intent.putExtra(Constant.HEALTH_MANAGE_KEY, Constant.PATIENT_HEALTH_MANAGE_VALUE);
			startActivity(intent);
			break;
		case R.id.ll_health_manage_method://健康管理办法
			intent = new Intent(this, HealthManageMethodActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
