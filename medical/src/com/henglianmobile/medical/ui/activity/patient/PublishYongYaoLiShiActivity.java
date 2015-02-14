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
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.SendHttpUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class PublishYongYaoLiShiActivity extends BaseActivity {
	private Button btn_sure;
	private ImageView btn_back;
	private EditText et_patient_yongyaolishi;
	private String userId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_publish_yongyaolishi);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		btn_sure = (Button)findViewById(R.id.btn_sure);
		et_patient_yongyaolishi = (EditText) findViewById(R.id.et_patient_yongyaolishi);
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
			String other = et_patient_yongyaolishi.getText().toString().trim();
			if (TextUtils.isEmpty(other)) {
				Tools.showMsg(this, "请输入您的病情基本情况");
				return;
			}
			String url = Const.ADDPATIENTYONGYAOLISHIURL;
//					+ "uid=" + userId
//					+ "&other=" + other;
			RequestParams params = new RequestParams();
			params.put("uid", userId);
			params.put("other", other);
//			submitHttpPost(url,params);
//			submitHttpGet(url);
			SendHttpUtil.submitHttpPost(url,params, this, "添加失败!", "添加成功!");
			break;
		default:
			break;
		}
	}
	private void submitHttpGet(String url) {
		LogUtil.i("url", "PublishYongYaoLiShiActivity---url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						int response = Integer.parseInt(jsonObject.getString("response"));
						if(response == 0){
							Tools.showMsg(PublishYongYaoLiShiActivity.this, "添加失败!");
						}else if(response == 1){
							//
							Tools.showMsg(PublishYongYaoLiShiActivity.this, "添加成功!");
							PublishYongYaoLiShiActivity.this.finish();
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
				Tools.showMsg(PublishYongYaoLiShiActivity.this, Tools.HTTP_ERROR);
			}
		});
	}
}
