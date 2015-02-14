package com.henglianmobile.medical.ui.activity.patient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class PublishBaseInformationActivity extends BaseActivity {
	private Button btn_sure;
	private ImageView btn_back;
	private EditText et_patient_information;
	private String userId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_publish_my_base_information);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		et_patient_information = (EditText) findViewById(R.id.et_patient_information);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_sure:
			String other = et_patient_information.getText().toString().trim();
			if (TextUtils.isEmpty(other)) {
				Tools.showMsg(this, "���������Ĳ���������");
				return;
			}
			String url = Const.ADDPATIENTBASEINFORMATIONURL;
			// + "userid=" + userId
			// + "&other=" + other;
			RequestParams params = new RequestParams();
			params.put("userid", userId);
			params.put("other", other);
			submitHttpPost(url, params);
			// submitHttpGet(url);
			break;
		}
	}

	private void submitHttpPost(String url, RequestParams params) {
		LogUtil.i("url", "PublishBaseInformationActivity---url=" + url);
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(PublishBaseInformationActivity.this,
								"���ʧ��!");
					} else if (response == 1) {
						// ԤԼ�ɹ�����ת��֧��ҳ��
						Tools.showMsg(PublishBaseInformationActivity.this,
								"��ӳɹ�!");
						PublishBaseInformationActivity.this.finish();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(PublishBaseInformationActivity.this,
						Tools.HTTP_ERROR);
			}
		});
	}
}
