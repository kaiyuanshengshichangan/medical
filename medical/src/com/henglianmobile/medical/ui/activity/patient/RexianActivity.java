package com.henglianmobile.medical.ui.activity.patient;

import java.text.ParseException;

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

public class RexianActivity extends BaseActivity {
	private Button btn_submit;
	private ImageView btn_back;
	private TextView tv_yuyue_date, tv_yuyue_time_end, tv_yuyue_time_start;
	private EditText et_patient_mobile;
	private String userId;
	private int dId;
	private float rxPrice;
	private String yuyueDate, mobile, yuyueTimeStart, yuyueTimeEnd;
	private DoctorYuYueListObject doctorYuYueListObject = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doctorYuYueListObject = (DoctorYuYueListObject) getIntent()
				.getSerializableExtra("doctorYuYueListObject");
		if (doctorYuYueListObject != null) {
			et_patient_mobile.setText(doctorYuYueListObject.getDcCellPhone());
			tv_yuyue_date.setText(doctorYuYueListObject.getStartDate());
			tv_yuyue_time_start.setText(doctorYuYueListObject.getStartHour());
			tv_yuyue_time_end.setText(doctorYuYueListObject.getEndHour());
			et_patient_mobile.setEnabled(false);
			tv_yuyue_date.setEnabled(false);
			tv_yuyue_time_start.setEnabled(false);
			tv_yuyue_time_end.setEnabled(false);
			btn_submit.setEnabled(false);
			int isPayed = doctorYuYueListObject.getIsPayed();
			int dnIsAgree = doctorYuYueListObject.getDnIsAgree();
			// if(isPayed == 1){
			// iv_disease_description.setImageResource(R.drawable.iv_circle_gray);
			// if(dnIsAgree == 1){
			// iv_doctor_confirm.setImageResource(R.drawable.iv_circle_red);
			// }else{
			// iv_pay.setImageResource(R.drawable.iv_circle_red);
			// }
			// }
		}
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_rexian);
		userId = TApplication.getInstance().user.getUid();
		dId = getIntent().getIntExtra("dId", 0);
		rxPrice = getIntent().getFloatExtra("rxPrice", 0);
		LogUtil.i("info", "dId=====" + dId);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		et_patient_mobile = (EditText) findViewById(R.id.et_patient_mobile);
		tv_yuyue_date = (TextView) findViewById(R.id.tv_yuyue_date);
		tv_yuyue_time_start = (TextView) findViewById(R.id.tv_yuyue_time_start);
		tv_yuyue_time_end = (TextView) findViewById(R.id.tv_yuyue_time_end);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		tv_yuyue_date.setOnClickListener(this);
		tv_yuyue_time_start.setOnClickListener(this);
		tv_yuyue_time_end.setOnClickListener(this);
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
		case R.id.tv_yuyue_date:
			Tools.datePicker(this, tv_yuyue_date);
			break;
		case R.id.tv_yuyue_time_start:
			Tools.timePicker(this, tv_yuyue_time_start);
			break;
		case R.id.tv_yuyue_time_end:
			Tools.timePicker(this, tv_yuyue_time_end);
			break;
		}
	}

	private long startTime;
	private long endTime;

	private void submit() {
		btn_submit.setEnabled(false);
		String url = Const.ADDYUYUEURL;
		// + "uid=" + userId
		// + "&duid=" + dId
		// + "&types=" + Constant.REXIAN
		// + "&maketime=" + yuyueDate
		// + "&startHour=" + yuyueTimeStart
		// + "&endHour=" + yuyueTimeEnd
		// + "&cellphone=" + mobile
		// + "&content=no";
		RequestParams params = new RequestParams();
		params.put("uid", userId);
		params.put("duid", dId);
		params.put("types", Constant.REXIAN);
		params.put("maketime", yuyueDate);
		params.put("startHour", yuyueTimeStart);
		params.put("endHour", yuyueTimeEnd);
		params.put("cellphone", mobile);
		params.put("content", "no");
		submitHttpPost(url, params);
		// submitHttpGet(url);
	}

	private boolean check() {
		yuyueDate = tv_yuyue_date.getText().toString().trim();
		mobile = et_patient_mobile.getText().toString().trim();
		yuyueTimeStart = tv_yuyue_time_start.getText().toString().trim();
		yuyueTimeEnd = tv_yuyue_time_end.getText().toString().trim();

		try {
			startTime = Tools.StringDateToLong(yuyueTimeStart, "dd:hh");// 毫秒
			endTime = Tools.StringDateToLong(yuyueTimeEnd, "dd:hh");
			LogUtil.i("info", "startTime=" + startTime + ",endTime=" + endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (TextUtils.isEmpty(yuyueDate)) {
			Tools.showMsg(this, "请选择预约日期!");
			return false;
		}
		if (TextUtils.isEmpty(yuyueTimeStart)) {
			Tools.showMsg(this, "请选择预约开始时间!");
			return false;
		}
		if (TextUtils.isEmpty(yuyueTimeEnd)) {
			Tools.showMsg(this, "请选择预约结束时间!");
			return false;
		}
		if (startTime >= endTime) {
			Tools.showMsg(this, "您选择的结束时间小于开始时间，请重新选择!");
			return false;
		}
		if (TextUtils.isEmpty(mobile)) {
			Tools.showMsg(this, "请输入您的手机号!");
			return false;
		}
		return true;
	}

	private void submitHttpPost(String url, RequestParams params) {
		LogUtil.i("url", "RexianActivity---url=" + url);
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(RexianActivity.this, "预约失败!");
						btn_submit.setEnabled(true);
					} else if (response > 0) {
						// 预约成功，跳转到支付页面
						Intent intent = new Intent(RexianActivity.this,
								PayTypeSelectActivity.class);
						intent.putExtra("yuyueType", Constant.REXIAN);
						intent.putExtra("makeId", response);
						intent.putExtra("price", rxPrice);
						startActivity(intent);
						RexianActivity.this.finish();
						btn_submit.setEnabled(true);
					}
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(RexianActivity.this, HTTP_ERROR);
			}
		});
	}
}
