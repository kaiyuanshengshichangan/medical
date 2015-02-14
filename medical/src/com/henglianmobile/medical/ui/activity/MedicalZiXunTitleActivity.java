package com.henglianmobile.medical.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.util.LogUtil;

public class MedicalZiXunTitleActivity extends BaseActivity {
	private ImageView btn_back;
	private WebView wv_zixun;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String titleUrl = getIntent().getStringExtra("titleUrl");
//		String url = Const.GETMEDICALZIXUNDETAILURL + id;
		WebSettings webSettings = wv_zixun.getSettings();
		// ����WebView���ԣ��ܹ�ִ��Javascript�ű�
		webSettings.setJavaScriptEnabled(true);
		// ���ÿ��Է����ļ�
		webSettings.setAllowFileAccess(true);
		// ����֧������
		webSettings.setBuiltInZoomControls(true);
		// ������Ҫ��ʾ����ҳ
		wv_zixun.loadUrl(titleUrl);
		// ����Web��ͼ
		wv_zixun.setWebViewClient(new MyWebViewClient());
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_medicalzixun_title);
		id = getIntent().getIntExtra("id", 0);
		LogUtil.i("url", "MedicalZiXunDetailActivity---id=" + id);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		wv_zixun = (WebView) findViewById(R.id.wv_zixun);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		}
	}

	// Web��ͼ
	private class MyWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}
