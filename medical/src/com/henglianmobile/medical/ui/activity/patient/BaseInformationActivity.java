package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.BaseInformationResultObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class BaseInformationActivity extends BaseActivity {
	private TextView tv_base_information;
	private Button btn_publish;
	private ImageView btn_back;
	private String userId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_my_base_information);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		btn_publish = (Button)findViewById(R.id.btn_publish);
		tv_base_information = (TextView)findViewById(R.id.tv_base_information);
		tv_base_information.setMovementMethod(ScrollingMovementMethod.getInstance()); 
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_publish.setOnClickListener(this);
	}
	@Override
	protected void onResume() {
		super.onResume();
		String url = Const.GETPATIENTBASEINFORMATIONURL+userId;
		getDataHttp(url);
	}
	private void getDataHttp(String url) {
		LogUtil.i("url", "BaseInformationActivity---url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<BaseInformationResultObject>>() {
					}.getType();
					List<BaseInformationResultObject> informationResultObjects = TApplication.gson.fromJson(
							responseString, type);
					if(informationResultObjects.size()!=0){
						BaseInformationResultObject informationResultObject = informationResultObjects.get(0);
						if(informationResultObject!=null){
							tv_base_information.setText(informationResultObject.getDcInfo());
						}
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(BaseInformationActivity.this, Tools.HTTP_ERROR);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_publish:
			Intent intent = new Intent(BaseInformationActivity.this, PublishBaseInformationActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
