package com.henglianmobile.medical.activity;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.R.id;
import com.henglianmobile.medical.R.layout;
import com.henglianmobile.medical.R.menu;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.LoginResultObject;
import com.henglianmobile.medical.ui.activity.PublishPatientStatusActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MD5Util;
import com.henglianmobile.medical.util.SPUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class LoginActivity extends BaseActivity {
	
	private Button[] btnArray = new Button[3];
	private ImageView btn_back;
	private EditText et_phone_number,et_password;
	private String phoneNumber,password;
	private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String haveLogin = SPUtil.getUtil(this).getFromSp(SPUtil.HAVELOGINTAG, SPUtil.NOTLOGIN);
        String logoutTag = getIntent().getStringExtra("logout");
        if (haveLogin.equals(SPUtil.LOGIN)) {
        	if (!"1".equals(logoutTag)) {
    			if (check()) {
    				logon();
    			}
    		}
        }
    }

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_login);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_login_back);
		btnArray[0] = (Button) findViewById(R.id.btn_register);
		btnArray[1] = (Button) findViewById(R.id.btn_forget_password);
		btnArray[2] = (Button) findViewById(R.id.btn_logon);
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		et_password = (EditText) findViewById(R.id.et_password);
		et_phone_number.setText(SPUtil.getUtil(this).getFromSp(SPUtil.USERNAME, ""));
		et_password.setText(SPUtil.getUtil(this).getFromSp(SPUtil.PWD, ""));
		
	}

	@Override
	public void addListener() {
		for (Button btn : btnArray) {
			btn.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login_back:
			finish();
			break;
		case R.id.btn_register:
			intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_forget_password:
			intent = new Intent(this, ForgetPwdActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_logon:
//			intent = new Intent(LoginActivity.this, MainActivity.class);
//			startActivity(intent);
			if(check()){
				logon();
			}
			break;

		default:
			break;
		}
	}

	private boolean check() {
		phoneNumber = et_phone_number.getText().toString().trim();
		password = et_password.getText().toString().trim();
		if(TextUtils.isEmpty(phoneNumber)){
			Tools.showMsg(this, "请输入手机号!");
			return false;
		}
		if(TextUtils.isEmpty(password)){
			Tools.showMsg(this, "请输入密码");
			return false;
		}
		return true;
	}

	private void logon() {
		Tools.showProgressDialog(this, "登陆中，请稍后...");
		btnArray[2].setEnabled(false);
		String url = Const.LOGONURL+"uname="+phoneNumber+"&upass="+MD5Util.generatePassword(password);
		sendGetHttpLogon(url);
	}

	private void sendGetHttpLogon(String url) {
		LogUtil.i("url", "LoginActivity--url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<LoginResultObject>>() {
					}.getType();
					List<LoginResultObject> loginResultObjects = TApplication.gson.fromJson(
							responseString, type);
					LoginResultObject loginResultObject = loginResultObjects.get(0);
					if("1".equals(loginResultObject.getResponse())){
						Tools.closeProgressDialog();
						//登录成功
						TApplication.getInstance().user = loginResultObject;
						SPUtil.getUtil(LoginActivity.this).saveToSp(SPUtil.USERNAME, phoneNumber);
						SPUtil.getUtil(LoginActivity.this).saveToSp(SPUtil.PWD, password);
						SPUtil.getUtil(LoginActivity.this).saveToSp(SPUtil.HAVELOGINTAG, SPUtil.LOGIN);
						intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);
					}else if("0".equals(loginResultObject.getResponse())){
						Tools.closeProgressDialog();
						Tools.showMsg(LoginActivity.this, "用户名不存在!");
						btnArray[2].setEnabled(true);
					}else if("2".equals(loginResultObject.getResponse())){
						Tools.closeProgressDialog();
						Tools.showMsg(LoginActivity.this, "密码错误，请重新输入!");
						btnArray[2].setEnabled(true);
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(LoginActivity.this, HTTP_ERROR);
				Tools.closeProgressDialog();
				btnArray[2].setEnabled(true);
			}
		});
	}
}
