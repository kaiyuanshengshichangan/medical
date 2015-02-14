package com.henglianmobile.medical.ui.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.renderscript.Type;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MD5Util;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class UpdatePasswordActivity extends BaseActivity {
	private Button btn_submit,btn_show;
	private ImageView btn_back;
	private EditText et_old_password, et_new_password, et_confirm_password;
	private String userId;
	private String oldPwd, newPwd, confirmPwd;
	private boolean isHidden=true;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_update_password);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_show = (Button) findViewById(R.id.btn_show);
		et_old_password = (EditText) findViewById(R.id.et_old_password);
		et_new_password = (EditText) findViewById(R.id.et_new_password);
		et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		btn_show.setOnClickListener(this);
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
		case R.id.btn_show:
			if (isHidden) {
                //设置EditText文本为可见的
				et_old_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //设置EditText文本为隐藏的
            	et_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            isHidden = !isHidden;
            et_old_password.postInvalidate();
            //切换后将EditText光标置于末尾
            CharSequence charSequence = et_old_password.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
			break;

		default:
			break;
		}
	}

	private boolean check() {
		oldPwd = et_old_password.getText().toString().trim();
		newPwd = et_new_password.getText().toString().trim();
		confirmPwd = et_confirm_password.getText().toString().trim();
		if (TextUtils.isEmpty(oldPwd)) {
			Tools.showMsg(this, "原密码不能为空!");
			return false;
		}
		if (TextUtils.isEmpty(newPwd)) {
			Tools.showMsg(this, "新密码不能为空!");
			return false;
		}
		if (TextUtils.isEmpty(confirmPwd)) {
			Tools.showMsg(this, "确认密码不能为空!");
			return false;
		}
		if (!newPwd.equals(confirmPwd)) {
			Tools.showMsg(this, "新密码两次输入结果不一样，请重新输入!");
			return false;
		}
		return true;
	}

	private void submit() {
		oldPwd = MD5Util.generatePassword(oldPwd);
		newPwd = MD5Util.generatePassword(newPwd);
		String url = Const.UPDATEPASSWORDURL + "uid=" + userId + "&oldpass="
				+ oldPwd + "&newpass=" + newPwd;
		sendGetHttp(url);
	}

	private void sendGetHttp(String url) {
		LogUtil.i("url", "UpdatePasswordActivity---url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {
			
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
							Tools.showMsg(UpdatePasswordActivity.this,
									"修改密码失败!");
						} else if (response == 1) {
							Tools.showMsg(UpdatePasswordActivity.this,
									"密码修改成功!");
							UpdatePasswordActivity.this.finish();
						} else if(response == 2){
							Tools.showMsg(UpdatePasswordActivity.this,
									"原密码错误!");
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
				Tools.showMsg(UpdatePasswordActivity.this, HTTP_ERROR);
			}
		});
	}

}
