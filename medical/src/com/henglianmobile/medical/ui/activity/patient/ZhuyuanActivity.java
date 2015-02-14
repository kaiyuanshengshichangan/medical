package com.henglianmobile.medical.ui.activity.patient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.DoctorYuYueListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class ZhuyuanActivity extends BaseActivity {
	private Button btn_submit;
	private ImageView btn_back;
	private ImageView iv_disease_description, iv_pay, iv_doctor_confirm,
			iv_hospital_cure;
	private EditText et_disease_description, et_patient_mobile;
	private TextView tv_cure_date, tv_yuyue_time;
	private int dId;
	private String userId;
	private float zyPrice;
	private String description, yuyueDate, yuyueTime, mobile;
	private DoctorYuYueListObject doctorYuYueListObject = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doctorYuYueListObject = (DoctorYuYueListObject) getIntent()
				.getSerializableExtra("doctorYuYueListObject");
		if (doctorYuYueListObject != null) {
			et_disease_description.setText(doctorYuYueListObject.getDcOther());
			et_patient_mobile.setText(doctorYuYueListObject.getDcCellPhone());
			tv_cure_date.setText(doctorYuYueListObject.getStartDate());
			tv_yuyue_time.setText(doctorYuYueListObject.getStartHour());
			et_disease_description.setEnabled(false);
			et_patient_mobile.setEnabled(false);
			tv_cure_date.setEnabled(false);
			tv_yuyue_time.setEnabled(false);
			btn_submit.setEnabled(false);
			int isPayed = doctorYuYueListObject.getIsPayed();
			int dnIsAgree = doctorYuYueListObject.getDnIsAgree();
			if (isPayed == 1) {
				iv_disease_description
						.setImageResource(R.drawable.iv_circle_gray);
				if (dnIsAgree == 1) {
					iv_doctor_confirm
							.setImageResource(R.drawable.iv_circle_red);
				} else {
					iv_pay.setImageResource(R.drawable.iv_circle_red);
				}
			}
		}
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_zhuyuan);
		userId = TApplication.getInstance().user.getUid();
		dId = getIntent().getIntExtra("dId", 0);
		zyPrice = getIntent().getFloatExtra("zyPrice", 0);
		LogUtil.i("info", "dId=====" + dId);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		iv_disease_description = (ImageView) findViewById(R.id.iv_disease_description);
		iv_pay = (ImageView) findViewById(R.id.iv_pay);
		iv_doctor_confirm = (ImageView) findViewById(R.id.iv_doctor_confirm);
		iv_hospital_cure = (ImageView) findViewById(R.id.iv_hospital_cure);
		et_disease_description = (EditText) findViewById(R.id.et_disease_description);
		et_patient_mobile = (EditText) findViewById(R.id.et_patient_mobile);
		tv_cure_date = (TextView) findViewById(R.id.tv_cure_date);
		tv_yuyue_time = (TextView) findViewById(R.id.tv_yuyue_time);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		tv_cure_date.setOnClickListener(this);
		tv_yuyue_time.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_submit:
			if (check()) {
				submit();
			}
			break;
		case R.id.tv_cure_date:
			Tools.datePicker(this, tv_cure_date);
			break;
		case R.id.tv_yuyue_time:
			Tools.timePicker(this, tv_yuyue_time);
			break;

		default:
			break;
		}
	}

	private void submit() {
		btn_submit.setEnabled(false);
		String url = Const.ADDYUYUEURL;
		// + "uid=" + userId
		// + "&duid=" + dId
		// + "&types=" + Constant.ZHUYUAN
		// + "&maketime=" + yuyueDate
		// + "&startHour="+ yuyueTime
		// + "&endHour="+ yuyueTime
		// + "&cellphone=" + mobile
		// + "&content="+description;
		RequestParams params = new RequestParams();
		params.put("uid", userId);
		params.put("duid", dId);
		params.put("types", Constant.ZHUYUAN);
		params.put("maketime", yuyueDate);
		params.put("startHour", yuyueTime);
		params.put("endHour", yuyueTime);
		params.put("cellphone", mobile);
		params.put("content", description);
		submitHttpPost(url, params);
		// submitHttpGet(url);
	}

	private boolean check() {
		description = et_disease_description.getText().toString().trim();
		yuyueDate = tv_cure_date.getText().toString().trim();
		yuyueTime = tv_yuyue_time.getText().toString().trim();
		mobile = et_patient_mobile.getText().toString().trim();
		if (TextUtils.isEmpty(description)) {
			Tools.showMsg(this, "请输入病情描述!");
			return false;
		}
		if (TextUtils.isEmpty(yuyueDate)) {
			Tools.showMsg(this, "请选择预约日期!");
			return false;
		}
		if (TextUtils.isEmpty(yuyueTime)) {
			Tools.showMsg(this, "请选择预约时间!");
			return false;
		}
		if (TextUtils.isEmpty(mobile)) {
			Tools.showMsg(this, "请输入您的手机号!");
			return false;
		}
		return true;
	}

	private void submitHttpPost(String url, RequestParams params) {
		LogUtil.i("url", "ZhuyuanActivity---url=" + url);
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(ZhuyuanActivity.this, "预约失败!");
						btn_submit.setEnabled(true);
					} else if (response > 0) {
						// 预约成功，跳转到支付页面
						Intent intent = new Intent(ZhuyuanActivity.this,
								PayTypeSelectActivity.class);
						intent.putExtra("yuyueType", Constant.ZHUYUAN);
						intent.putExtra("makeId", response);
						intent.putExtra("price", zyPrice);
						startActivity(intent);
						ZhuyuanActivity.this.finish();
						btn_submit.setEnabled(true);
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(ZhuyuanActivity.this, HTTP_ERROR);
			}
		});
	}
}
