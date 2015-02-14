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
		// �жϳ����ǵڼ������У�����ǵ�һ����������ת������ҳ��
		if (haveLogin.equals(SPUtil.NOTLOGIN)) {
			//��ת������ҳ
			Intent intent = new Intent(WelcomeActivity.this, GuidanceViewActivity.class);
			startActivity(intent);
			finish();
		} else if(haveLogin.equals(SPUtil.LOGIN)){
			//��ת����½ҳ
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
