package com.henglianmobile.medical.ui.activity.patient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class ZhangHuChongZhiActivity extends BaseActivity {

	private ImageView btn_back;
	private Button btn_sure;
	private EditText et_amount;
	private String amount;

	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_patient_recharge1);
		// setContentView(R.layout.activity_patient_recharge);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		et_amount = (EditText) findViewById(R.id.et_amount);
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
			amount = et_amount.getText().toString().trim();
			if(TextUtils.isEmpty(amount)){
				Tools.showMsg(this, "请输入充值金额");
				return;
			}
			String url = Const.GETRECHARGEORDERNOURL
				+ "userId=" + TApplication.user.getUid()
				+ "&amount=" + amount;
			Tools.showProgressDialog(this, "请稍后...");
			getOrderNo(url);
			break;
		default:
			break;
		}
	}

	private void getOrderNo(String url) {
		LogUtil.i("url", "ZhangHuChongZhiActivity---url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						String response = jsonObject.getString("response");
						if(TextUtils.isEmpty(response)){
							Tools.showMsg(ZhangHuChongZhiActivity.this, "提交信息失败，请稍后重试!");
						}else {
							//跳转页面
							Tools.closeProgressDialog();
							Intent intent = new Intent(ZhangHuChongZhiActivity.this, RemittanceInfoActivity.class);
							intent.putExtra("orderNo", response);
							intent.putExtra("amount", amount);
							startActivity(intent);
							ZhangHuChongZhiActivity.this.finish();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Tools.closeProgressDialog();
						e.printStackTrace();
					}
					
				}else{
					Tools.closeProgressDialog();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(ZhangHuChongZhiActivity.this, HTTP_ERROR);
				Tools.closeProgressDialog();
			}
		});
	}
}
