package com.henglianmobile.medical.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MD5Util;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class ChangePasswordActivity extends BaseActivity {
	private Button btn_submit;
	private ImageView btn_back;
	private EditText et_password,et_confirm_password;
	private String phone;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_change_password);
		phone = getIntent().getStringExtra("phone");
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		et_password = (EditText)findViewById(R.id.et_password);
		et_confirm_password = (EditText)findViewById(R.id.et_confirm_password);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_submit:
			submit();
			break;

		default:
			break;
		}
	}

	private void submit() {
		String pwd = et_password.getText().toString().trim();
		String confirmPwd = et_confirm_password.getText().toString().trim();
		pwd = MD5Util.generatePassword(pwd);
		confirmPwd = MD5Util.generatePassword(confirmPwd);
		if(TextUtils.isEmpty(pwd)){
			Tools.showMsg(this, "请您输入新密码!");
			return;
		}
		if(TextUtils.isEmpty(confirmPwd)){
			Tools.showMsg(this, "请您输入确认密码!");
			return;
		}
		if(!pwd.equals(confirmPwd)){
			Tools.showMsg(this, "您两次输入的密码不一样，请重新输入!");
			return;
		}
		String url = Const.FORGETPASSWORDURL+"mobileNo="+phone+"&newPsw="+pwd;
		submitHttp(url);
	}

	private void submitHttp(String url) {
		LogUtil.i("url", "ChangePasswordActivity---url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", "ChangePasswordActivity===="+responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						int response = Integer.parseInt(jsonObject.getString("response"));
						if(response == 0){
							Tools.showMsg(ChangePasswordActivity.this, "修改失败!");
						}else if(response == 1){
							Tools.showMsg(ChangePasswordActivity.this, "修改成功!");
							ChangePasswordActivity.this.finish();
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
				Tools.showMsg(ChangePasswordActivity.this, HTTP_ERROR);
			}
		});
	}

}
