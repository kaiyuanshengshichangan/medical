package com.henglianmobile.medical.activity;

import com.henglianmobile.medical.R;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class UserAgreementActivity extends BaseActivity {
	private ImageView btn_back;
	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_user_agreement);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		WebView wView = (WebView)findViewById(R.id.wv);   
        WebSettings wSet = wView.getSettings();   
        wSet.setJavaScriptEnabled(true);   
                   
        wView.loadUrl("file:///android_asset/user_agreement.html");  
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		this.finish();
	}

}
