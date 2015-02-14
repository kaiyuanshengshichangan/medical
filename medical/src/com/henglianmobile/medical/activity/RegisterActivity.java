package com.henglianmobile.medical.activity;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.GetCodeObject;
import com.henglianmobile.medical.entity.LoginResultObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MD5Util;
import com.henglianmobile.medical.util.SPUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * ע��
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends BaseActivity {

	private EditText et_nick, et_phone, et_password, et_code,et_perform_password;
	private Button btn_submit, btn_agreement;
	private ImageView btn_back;
	private RelativeLayout rl_userType;
	private TextView tv_userType, tv_getcode;
	private Intent intent;
	private String userType, nick, phone, password,performPwd, checkCode, code;
	private int userId;
	private CheckBox agreement_check;

	private TextView tv_doctor, tv_patient, tv_saleman;
	private SelectUserTypePopupWindow popupWindow;
	private TimeCount time;

	private MyBroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		receiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter(
				Constant.ACTIONDOCTORQUALIFICATIONSUCCESS);
		registerReceiver(receiver, filter);
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_register);
		time = new TimeCount(60000, 1000);// ����CountDownTimer����
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_register_back);
		btn_agreement = (Button) findViewById(R.id.btn_agreement);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		et_nick = (EditText) findViewById(R.id.et_phone_number);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_password = (EditText) findViewById(R.id.et_password);
		et_perform_password = (EditText) findViewById(R.id.et_perform_password);
		et_code = (EditText) findViewById(R.id.et_code);
		tv_userType = (TextView) findViewById(R.id.tv_userType);
		tv_getcode = (TextView) findViewById(R.id.tv_getcode);
		rl_userType = (RelativeLayout) findViewById(R.id.rl_userType);
		agreement_check = (CheckBox) findViewById(R.id.agreement_check);
	}

	@Override
	public void addListener() {

		btn_agreement.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		rl_userType.setOnClickListener(this);
		tv_getcode.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register_back:
			this.finish();
			break;
		case R.id.btn_agreement:
			Intent it = new Intent(RegisterActivity.this,
					UserAgreementActivity.class);
			startActivity(it);
			break;
		case R.id.rl_userType:
			popupWindow = new SelectUserTypePopupWindow();
			popupWindow.showPopupWindow(tv_userType);
			break;
		case R.id.tv_getcode:// ��ȡ��֤��
			getCode();
			break;
		case R.id.btn_submit:
			if (check()) {
				submit();
			}
//			 intent = new Intent(RegisterActivity.this,
//			 DoctorQualificationActivity.class);
//			 // intent.putExtra("userId", userId);
//			 startActivity(intent);
			break;
		case R.id.tv_doctor:
			userType = Constant.DOCTOR;
			tv_userType.setText("ҽ��");
			popupWindow.dismiss();
			break;
		case R.id.tv_patient:
			userType = Constant.PATIENT;
			tv_userType.setText("����");
			popupWindow.dismiss();
			break;
		case R.id.tv_saleman:
			userType = Constant.SALEMAN;
			tv_userType.setText("ҵ��Ա");
			popupWindow.dismiss();
			break;

		default:
			break;
		}
	}

	// ��ȡ��֤��
	private void getCode() {
		phone = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			Tools.showMsg(this, "�������ֻ���!");
			return;
		}
		time.start();
		String url = Const.GETCODEURL + phone;
		getCodeHttp(url);
	}

	private void getCodeHttp(String url) {
		LogUtil.i("url", "RegisterActivity-getCodeHttp--url=" + url);
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
				Tools.showMsg(RegisterActivity.this, HTTP_ERROR);
			}
		});
	}

	/**
	 * ���зǿ���֤
	 * 
	 * @return
	 */
	private boolean check() {
		nick = et_nick.getText().toString().trim();
		password = et_password.getText().toString().trim();
		performPwd = et_perform_password.getText().toString().trim();
		checkCode = et_code.getText().toString().trim();
		phone = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(nick)) {
			Tools.showMsg(this, "�������ǳ�!");
			return false;
		}
		if (TextUtils.isEmpty(password)) {
			Tools.showMsg(this, "����������!");
			return false;
		}
		if (password.length()<6) {
			Tools.showMsg(this, "���볤����6~12λ������������!");
			et_password.setText("");
			return false;
		}
		if (!password.equals(performPwd)) {
			Tools.showMsg(this, "���������벻һ��������������");
			et_perform_password.setText("");
			return false;
		}
		if (TextUtils.isEmpty(phone)) {
			Tools.showMsg(this, "�������ֻ���!");
			return false;
		}
		// if (TextUtils.isEmpty(checkCode)) {
		// Tools.showMsg(this, "��������֤��!");
		// return false;
		// }
		// if (!checkCode.equals(code)) {
		// Tools.showMsg(this, "���������֤�벻��ȷ������������!");
		// return false;
		// }
		if (TextUtils.isEmpty(userType)) {
			Tools.showMsg(this, "��ѡ���û�����!");
			return false;
		}
		if (!agreement_check.isChecked()) {
			Tools.showMsg(this, "���Ķ���ͬ�⡶��ʹ֮���û�Э�顷");
			et_password.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * �ύ
	 */
	private void submit() {
		String url = Const.REGISTERURL + "uname=" + nick + "&upass="
				+ MD5Util.generatePassword(password) + "&cellphone=" + phone
				+ "&types=" + userType;
		sendGetHttp(url);
	}

	/**
	 * ����get����
	 * 
	 * @param url
	 */
	private void sendGetHttp(String url) {
		LogUtil.i("url", "RegisterActivity---url=" + url);
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
							Tools.showMsg(RegisterActivity.this,
									"ע��ʧ�ܣ����Ժ�����ע��!");
						} else if (response == -2) {
							Tools.showMsg(RegisterActivity.this,
									"���ֻ����Ѿ�ע�ᣬ������ֻ���!");
						} else if (response > 0) {
							Tools.showMsg(RegisterActivity.this, "ע��ɹ�!");
							userId = response;
							LogUtil.i("userId", "userId=" + userId);
							if (userType.equals(Constant.DOCTOR)) {
								intent = new Intent(RegisterActivity.this,
										DoctorQualificationActivity.class);
								intent.putExtra("userId", userId);
								startActivity(intent);
							} else {
								login();
							}
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
				Tools.showMsg(RegisterActivity.this, HTTP_ERROR);
			}
		});
	}

	/**
	 * �Զ���popupwindow ,,;
	 * 
	 * @author Administrator
	 * 
	 */
	class SelectUserTypePopupWindow extends PopupWindow {
		public SelectUserTypePopupWindow() {
			LayoutInflater inflater = (LayoutInflater) RegisterActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View conentView = inflater.inflate(R.layout.dialog, null);
			tv_doctor = (TextView) conentView.findViewById(R.id.tv_doctor);
			tv_patient = (TextView) conentView.findViewById(R.id.tv_patient);
			tv_saleman = (TextView) conentView.findViewById(R.id.tv_saleman);
			tv_doctor.setOnClickListener(RegisterActivity.this);
			tv_patient.setOnClickListener(RegisterActivity.this);
			tv_saleman.setOnClickListener(RegisterActivity.this);

			// ����SelectPicPopupWindow��View
			this.setContentView(conentView);
			// ����SelectPicPopupWindow��������Ŀ�
			this.setWidth(LayoutParams.MATCH_PARENT);
			// ����SelectPicPopupWindow��������ĸ�
			this.setHeight(LayoutParams.WRAP_CONTENT);
			// ����SelectPicPopupWindow��������ɵ��
			this.setFocusable(true);
			this.setOutsideTouchable(true);
			// ˢ��״̬
			this.update();
			// ʵ����һ��ColorDrawable��ɫΪ��͸��
			ColorDrawable dw = new ColorDrawable(0000000000);
			// ��back���������ط�ʹ����ʧ,������������ܴ���OnDismisslistener �����������ؼ��仯�Ȳ���
			this.setBackgroundDrawable(dw);
			this.setAnimationStyle(R.style.popwin_anim_style);
		}

		/**
		 * ��ʾpopupWindow
		 * 
		 * @param parent
		 */
		public void showPopupWindow(View parent) {
			if (!this.isShowing()) {
				// ��������ʽ��ʾpopupwindow
				this.showAsDropDown(parent);
			} else {
				this.dismiss();
			}
		}
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

	private void login() {
		String url = Const.LOGONURL + "uname=" + phone + "&upass="
				+ MD5Util.generatePassword(password);
		sendGetHttpLogon(url);
	}

	private void sendGetHttpLogon(String url) {
		LogUtil.i("url", "LoginActivity--url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<LoginResultObject>>() {
					}.getType();
					List<LoginResultObject> loginResultObjects = TApplication.gson
							.fromJson(responseString, type);
					LoginResultObject loginResultObject = loginResultObjects
							.get(0);
					if ("1".equals(loginResultObject.getResponse())) {
						// ��¼�ɹ�
						TApplication.getInstance().user = loginResultObject;
						SPUtil.getUtil(RegisterActivity.this).saveToSp(
								SPUtil.USERNAME, phone);
						SPUtil.getUtil(RegisterActivity.this).saveToSp(
								SPUtil.PWD, password);
						intent = new Intent(RegisterActivity.this,
								MainActivity.class);
						startActivity(intent);
						RegisterActivity.this.finish();
					} else if ("0".equals(loginResultObject.getResponse())) {
						Tools.showMsg(RegisterActivity.this, "�û���������!");
					} else if ("2".equals(loginResultObject.getResponse())) {
						Tools.showMsg(RegisterActivity.this, "�����������������!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(RegisterActivity.this, HTTP_ERROR);
			}
		});
	}

	private class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			login();
		}
	}
}
