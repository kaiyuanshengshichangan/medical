package com.henglianmobile.medical.ui.activity.patient;

import java.text.ParseException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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

public class PersonalDoctorActivity extends BaseActivity {
	private Button btn_submit;
	private ImageView btn_back;
	private TextView tv_start_date, tv_end_date;
	private EditText et_disease_description;
	private int dId;
	private String userId;
	private float pdPrice;
	private String yuyueDateStart, yuyueDateEnd, content;
	private DoctorYuYueListObject doctorYuYueListObject = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doctorYuYueListObject = (DoctorYuYueListObject) getIntent()
				.getSerializableExtra("doctorYuYueListObject");
		if (doctorYuYueListObject != null) {
			et_disease_description.setText(doctorYuYueListObject.getDcOther());
			tv_start_date.setText(doctorYuYueListObject.getStartDate());
			tv_end_date.setText(doctorYuYueListObject.getEndDate());
			et_disease_description.setEnabled(false);
			tv_start_date.setEnabled(false);
			tv_end_date.setEnabled(false);
			btn_submit.setEnabled(false);
			int isPayed = doctorYuYueListObject.getIsPayed();

		}
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_personal_doctor);
		dId = getIntent().getIntExtra("dId", 0);
		userId = TApplication.user.getUid();
		pdPrice = getIntent().getFloatExtra("pdPrice", 0);
		LogUtil.i("info", "dId=====" + dId);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		tv_start_date = (TextView) findViewById(R.id.tv_start_date);
		tv_end_date = (TextView) findViewById(R.id.tv_end_date);
		et_disease_description = (EditText) findViewById(R.id.et_disease_description);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		tv_start_date.setOnClickListener(this);
		tv_end_date.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.tv_start_date:
			Tools.datePicker(this, tv_start_date);
			break;
		case R.id.tv_end_date:
			Tools.datePicker(this, tv_end_date);
			break;
		case R.id.btn_submit:
			if (check()) {
				submit();
			}
			break;

		default:
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
		// + "&types=" + Constant.PERSONALDOCTOR
		// + "&maketime=" + yuyueDateStart
		// + "&startHour=" + yuyueDateStart
		// + "&endHour=" + yuyueDateEnd
		// + "&cellphone=" + "000000"
		// + "&content="+content;
		RequestParams params = new RequestParams();
		params.put("uid", userId);
		params.put("duid", dId);
		params.put("types", Constant.PERSONALDOCTOR);
		params.put("maketime", yuyueDateStart);
		params.put("startHour", yuyueDateStart);
		params.put("endHour", yuyueDateEnd);
		params.put("cellphone", TApplication.getInstance().userInfoDetailObject
				.getDcCellPhone());
		params.put("content", content);
		submitHttpPost(url, params);
		// submitHttpGet(url);
	}

	private boolean check() {
		yuyueDateStart = tv_start_date.getText().toString().trim();
		yuyueDateEnd = tv_end_date.getText().toString().trim();
		content = et_disease_description.getText().toString().trim();
		try {
			startTime = Tools.StringDateToLong(yuyueDateStart, "yyyy-MM-dd");// 毫秒
			endTime = Tools.StringDateToLong(yuyueDateEnd, "yyyy-MM-dd");
			LogUtil.i("info", "startTime=" + startTime + ",endTime=" + endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (TextUtils.isEmpty(yuyueDateStart)) {
			Tools.showMsg(this, "请选择预约开始时间!");
			return false;
		}
		if (TextUtils.isEmpty(yuyueDateEnd)) {
			Tools.showMsg(this, "请选择预约结束时间!");
			return false;
		}
		if (startTime >= endTime) {
			Tools.showMsg(this, "您选择的结束时间小于开始时间，请重新选择!");
			return false;
		}
		if (TextUtils.isEmpty(content)) {
			Tools.showMsg(this, "请输入您的病症!");
			return false;
		}
		return true;
	}

	private void submitHttpPost(String url, RequestParams params) {
		LogUtil.i("url", "PersonalDoctorActivity---url=" + url);
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(PersonalDoctorActivity.this, "预约失败!");
						btn_submit.setEnabled(true);
					} else if (response > 0) {
						// 预约成功，跳转到支付页面
						Intent intent = new Intent(PersonalDoctorActivity.this,
								PayTypeSelectActivity.class);
						intent.putExtra("yuyueType", Constant.PERSONALDOCTOR);
						intent.putExtra("makeId", response);
						intent.putExtra("price", pdPrice);
						startActivity(intent);
						PersonalDoctorActivity.this.finish();
						btn_submit.setEnabled(true);
					}
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(PersonalDoctorActivity.this, HTTP_ERROR);
			}
		});
	}
	// private class MyBroadcastReceiver extends BroadcastReceiver{
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// submit();
	// }
	// }
}
