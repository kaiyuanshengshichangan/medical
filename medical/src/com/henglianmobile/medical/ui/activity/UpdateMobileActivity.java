package com.henglianmobile.medical.ui.activity;

import java.lang.reflect.Type;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.activity.LoginActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.GetCodeObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class UpdateMobileActivity extends BaseActivity {
	private Button btn_submit;
	private ImageView btn_back;
	private EditText et_phone_number, et_code;
	private TextView tv_getcode;
	private TimeCount time;
	private String code, phone, userId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_update_mobile);
		userId = TApplication.user.getUid();
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		tv_getcode = (TextView) findViewById(R.id.tv_getcode);
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		et_code = (EditText) findViewById(R.id.et_code);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		tv_getcode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.tv_getcode:
			phone = et_phone_number.getText().toString().trim();
			if (TextUtils.isEmpty(phone)) {
				Tools.showMsg(this, "请输入手机号!");
				return;
			}
			time.start();
			String url = Const.GETCODEURL + phone;
			getCodeHttp(url);
			break;
		case R.id.btn_submit:
			String checkCode = et_code.getText().toString().trim();
			phone = et_phone_number.getText().toString().trim();
			if (TextUtils.isEmpty(phone)) {
				Tools.showMsg(this, "请输入手机号!");
				return;
			}
			// if(TextUtils.isEmpty(checkCode)){
			// Tools.showMsg(this, "请您输入验证码!");
			// return;
			// }
			// if (!checkCode.equals(code)) {
			// Tools.showMsg(this, "您输入的验证码不正确，请重新输入!");
			// return;
			// }
			String updateMobileUrl = Const.UPDATEMOBILEURL+"userId="+userId+"&mobileNo="+phone;
			updateMobileHttp(updateMobileUrl);
			break;

		default:
			break;
		}
	}

	private void updateMobileHttp(String updateMobileUrl) {
		LogUtil.i("url", "UpdateMobileActivity---url="+updateMobileUrl);
		HttpUtil.get(updateMobileUrl, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String responseString) {
				if(arg0 == 200){
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						// int response = Integer.parseInt(jsonObject
						// .getString("response"));
						int response = jsonObject.getInt("response");
						if (response == 0) {
							Tools.showMsg(UpdateMobileActivity.this,
									"修改失败!");
						} else if (response == 1) {
							Tools.showMsg(UpdateMobileActivity.this,
									"修改成功!");
							Intent intent = new Intent(UpdateMobileActivity.this, LoginActivity.class);
							startActivity(intent);
							TApplication.getInstance().exitActivities();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				Tools.showMsg(UpdateMobileActivity.this, HTTP_ERROR);
			}
		});
	}

	private void getCodeHttp(String url) {
		LogUtil.i("url", "UpdateMobileActivity-getCodeHttp--url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<GetCodeObject>() {
					}.getType();
					GetCodeObject getCodeObject = TApplication.gson.fromJson(
							responseString, type);
					if (getCodeObject != null
							&& getCodeObject.getResponse() == 1) {
						code = getCodeObject.getSendcode() + "";
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(UpdateMobileActivity.this, HTTP_ERROR);
			}
		});
	}

	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			tv_getcode.setText("重新验证");
			tv_getcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			tv_getcode.setClickable(false);
			tv_getcode.setText(millisUntilFinished / 1000 + "秒");
		}
	}
}
