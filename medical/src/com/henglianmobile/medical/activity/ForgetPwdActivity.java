package com.henglianmobile.medical.activity;

import java.lang.reflect.Type;

import org.apache.http.Header;

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
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.GetCodeObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * ��������
 * @author Administrator
 *
 */
public class ForgetPwdActivity extends BaseActivity {
	private Button btn_submit;
	private ImageView btn_back;
	private EditText et_phone_number,et_code;
	private TextView tv_getcode;
	private TimeCount time;
	private String code,phone;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_forget_password);
		time = new TimeCount(60000, 1000);//����CountDownTimer����
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		tv_getcode = (TextView)findViewById(R.id.tv_getcode);
		et_phone_number = (EditText)findViewById(R.id.et_phone_number);
		et_code = (EditText)findViewById(R.id.et_code);
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
			if(TextUtils.isEmpty(phone)){
				Tools.showMsg(this, "�������ֻ���!");
				return;
			}
			time.start();
			String url = Const.GETCODEURL+phone;
			getCodeHttp(url);
			break;
		case R.id.btn_submit:
			String checkCode = et_code.getText().toString().trim();
			phone = et_phone_number.getText().toString().trim();
			if(TextUtils.isEmpty(phone)){
				Tools.showMsg(this, "�������ֻ���!");
				return;
			}
//			if(TextUtils.isEmpty(checkCode)){
//				Tools.showMsg(this, "����������֤��!");
//				return;
//			}
//			if (!checkCode.equals(code)) {
//				Tools.showMsg(this, "���������֤�벻��ȷ������������!");
//				return;
//			}
			Intent intent = new Intent(ForgetPwdActivity.this, ChangePasswordActivity.class);
			intent.putExtra("phone", phone);
			startActivity(intent);
			this.finish();
			break;

		default:
			break;
		}
	}
	private void getCodeHttp(String url) {
		LogUtil.i("url", "ForgetPwdActivity-getCodeHttp--url="+url);
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
					if(getCodeObject!=null&&getCodeObject.getResponse() == 1){
						code = getCodeObject.getSendcode()+"";
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(ForgetPwdActivity.this, HTTP_ERROR);
			}
		});
	}
	/* ����һ������ʱ���ڲ��� */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// ��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}

		@Override
		public void onFinish() {// ��ʱ���ʱ����
			tv_getcode.setText("������֤");
			tv_getcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// ��ʱ������ʾ
			tv_getcode.setClickable(false);
			tv_getcode.setText(millisUntilFinished / 1000 + "��");
		}
	}
}
