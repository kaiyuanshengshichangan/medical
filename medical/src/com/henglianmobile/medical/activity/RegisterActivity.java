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
 * 注册
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
		time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
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
		case R.id.tv_getcode:// 获取验证码
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
			tv_userType.setText("医生");
			popupWindow.dismiss();
			break;
		case R.id.tv_patient:
			userType = Constant.PATIENT;
			tv_userType.setText("患者");
			popupWindow.dismiss();
			break;
		case R.id.tv_saleman:
			userType = Constant.SALEMAN;
			tv_userType.setText("业务员");
			popupWindow.dismiss();
			break;

		default:
			break;
		}
	}

	// 获取验证码
	private void getCode() {
		phone = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			Tools.showMsg(this, "请输入手机号!");
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
	 * 进行非空验证
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
			Tools.showMsg(this, "请输入昵称!");
			return false;
		}
		if (TextUtils.isEmpty(password)) {
			Tools.showMsg(this, "请输入密码!");
			return false;
		}
		if (password.length()<6) {
			Tools.showMsg(this, "密码长度在6~12位，请重新输入!");
			et_password.setText("");
			return false;
		}
		if (!password.equals(performPwd)) {
			Tools.showMsg(this, "您两次密码不一样，请重新输入");
			et_perform_password.setText("");
			return false;
		}
		if (TextUtils.isEmpty(phone)) {
			Tools.showMsg(this, "请输入手机号!");
			return false;
		}
		// if (TextUtils.isEmpty(checkCode)) {
		// Tools.showMsg(this, "请输入验证码!");
		// return false;
		// }
		// if (!checkCode.equals(code)) {
		// Tools.showMsg(this, "您输入的验证码不正确，请重新输入!");
		// return false;
		// }
		if (TextUtils.isEmpty(userType)) {
			Tools.showMsg(this, "请选择用户类型!");
			return false;
		}
		if (!agreement_check.isChecked()) {
			Tools.showMsg(this, "请阅读并同意《天使之光用户协议》");
			et_password.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * 提交
	 */
	private void submit() {
		String url = Const.REGISTERURL + "uname=" + nick + "&upass="
				+ MD5Util.generatePassword(password) + "&cellphone=" + phone
				+ "&types=" + userType;
		sendGetHttp(url);
	}

	/**
	 * 发送get请求
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
									"注册失败，请稍后重新注册!");
						} else if (response == -2) {
							Tools.showMsg(RegisterActivity.this,
									"该手机号已经注册，请更换手机号!");
						} else if (response > 0) {
							Tools.showMsg(RegisterActivity.this, "注册成功!");
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
	 * 自定义popupwindow ,,;
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

			// 设置SelectPicPopupWindow的View
			this.setContentView(conentView);
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.MATCH_PARENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体可点击
			this.setFocusable(true);
			this.setOutsideTouchable(true);
			// 刷新状态
			this.update();
			// 实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0000000000);
			// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
			this.setBackgroundDrawable(dw);
			this.setAnimationStyle(R.style.popwin_anim_style);
		}

		/**
		 * 显示popupWindow
		 * 
		 * @param parent
		 */
		public void showPopupWindow(View parent) {
			if (!this.isShowing()) {
				// 以下拉方式显示popupwindow
				this.showAsDropDown(parent);
			} else {
				this.dismiss();
			}
		}
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
						// 登录成功
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
						Tools.showMsg(RegisterActivity.this, "用户名不存在!");
					} else if ("2".equals(loginResultObject.getResponse())) {
						Tools.showMsg(RegisterActivity.this, "密码错误，请重新输入!");
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
