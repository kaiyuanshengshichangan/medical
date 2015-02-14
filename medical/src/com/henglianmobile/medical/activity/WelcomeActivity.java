package com.henglianmobile.medical.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.henglianmobile.medical.util.SPUtil;

public class WelcomeActivity extends BaseActivity {

	@Override
	public void loadLayout() {
	}

	@Override
	public void initViews() {
		checkIsFirstLogin();
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	private void checkIsFirstLogin() {
		String haveLogin = SPUtil.getUtil(this).getFromSp(SPUtil.HAVELOGINTAG, SPUtil.NOTLOGIN);
		// 判断程序是第几次运行，如果是第一次运行则跳转到引导页面
		if (haveLogin.equals(SPUtil.NOTLOGIN)) {
			//跳转到引导页
			Intent intent = new Intent(WelcomeActivity.this, GuidanceViewActivity.class);
			startActivity(intent);
			finish();
		} else if(haveLogin.equals(SPUtil.LOGIN)){
			//跳转到登陆页
			ShowDefault();
		}
	}

	private void ShowDefault() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, 0);
	}

}
