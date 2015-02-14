package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.activity.DoctorQualificationActivity;
import com.henglianmobile.medical.activity.RegisterActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.GetCodeObject;
import com.henglianmobile.medical.ui.activity.ShowBitPicActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 选择支付方式
 * 
 * @author Administrator
 * 
 */
public class PayTypeSelectActivity extends BaseActivity {

	private RadioGroup rg_pay_type;
	private ImageView btn_back;
	private Button btn_submit;
	private TextView tv_balance, tv_pay_money;
	private Dialog dialog;

	private int payType = 1;
	private Intent intent;

	private String url,code;
	private String yuyueType;
	private int makeId;
	private float price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String accountUrl = Const.GETBALANCEURL + TApplication.user.getUid();
		getBalanceHttp(accountUrl);
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_pay_type_select);
		yuyueType = getIntent().getStringExtra("yuyueType");
		makeId = getIntent().getIntExtra("makeId", 0);
		price = getIntent().getFloatExtra("price", 0);
	}

	private void getBalanceHttp(String accountUrl) {
		LogUtil.i("url", "PayTypeSelectActivity---getBalanceHttp="
				+ accountUrl);
		HttpUtil.get(accountUrl, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "PayTypeSelectActivity---getBalanceHttp-res="
						+ arg2);
				Tools.showMsg(PayTypeSelectActivity.this, Tools.HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						double response = jsonObject.getDouble("response");
						tv_balance.setText(response + "元");
						tv_pay_money.setText(price + "元");
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		});
	}

	@Override
	public void initViews() {
		rg_pay_type = (RadioGroup) findViewById(R.id.rg_pay_type);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		tv_pay_money = (TextView) findViewById(R.id.tv_pay_money);
		tv_balance = (TextView) findViewById(R.id.tv_balance);
		rg_pay_type.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.rbtn_alipay) {
					// 支付宝支付
					payType = 0;
				} else if (checkedId == R.id.rbtn_balance) {
					// 手机银行支付
					payType = 1;
				}
			}
		});
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
			btn_submit.setEnabled(false);
			if (payType == 0) {
				// 支付宝支付
				LogUtil.i("info", "支付宝支付");
				Tools.showMsg(this, "暂不支持支付宝支付，请使用余额支付!");
			} else if (payType == 1) {
//				Tools.showProgressDialog(this, "请稍后...");
				// submit();
//				String url = Const.GETCODEURL
//					+ TApplication.getInstance().userInfoDetailObject
//							.getDcCellPhone();
//				getCodeHttp(url);
				showDialog();
			}
			break;

		default:
			break;
		}
	}

	private void getCodeHttp(String url) {
		LogUtil.i("url", "PayTypeSelectActivity-getCodeHttp--url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<GetCodeObject>() {
					}.getType();
					GetCodeObject getCodeObject = TApplication.gson.fromJson(
							responseString, type);
					if (getCodeObject != null
							&& getCodeObject.getResponse() == 1) {
						code = getCodeObject.getSendcode() + "";
						showDialog();
					}
				}
				btn_submit.setEnabled(true);
				Tools.closeProgressDialog();
				Tools.showMsg(PayTypeSelectActivity.this, "验证码已发送至您的手机!");
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(PayTypeSelectActivity.this, HTTP_ERROR);
				btn_submit.setEnabled(true);
				Tools.closeProgressDialog();
			}
		});
	}

	private void showDialog() {
		dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(R.layout.dialog_check_code);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		Window dialogWindow = dialog.getWindow();
		final EditText et_code = (EditText) dialogWindow
				.findViewById(R.id.et_code);
		Button btn_sure = (Button) dialogWindow.findViewById(R.id.btn_sure);
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String code1 = et_code.getText().toString().trim();
//				if(TextUtils.isEmpty(code1)){
//					Tools.showMsg(PayTypeSelectActivity.this, "请输入验证码!");
//					return;
//				}
//				if(!code1.equals(code)){
//					Tools.showMsg(PayTypeSelectActivity.this, "验证码不正确，请重新输入!");
//					return;
//				}
				submit();
				dialog.dismiss();
			}
		});
	}

	private void submit() {
		// 余额支付
		LogUtil.i("info", "余额支付");
		url = Const.PAYFROMBALANCEURL + "userId="
				+ TApplication.user.getUid() + "&type=" + yuyueType
				+ "&oId=" + makeId + "&amount=" + price;
		Tools.showProgressDialog(this, "正在支付...");
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Tools.closeProgressDialog();
				payHttp(url);
				
			}
		}, 2000);
	}

	protected void payHttp(String url) {
		LogUtil.i("url", "PayTypeSelectActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						int response = Integer.parseInt(jsonObject
								.getString("response"));
						if (response == 0) {
							Tools.showMsg(PayTypeSelectActivity.this, "支付失败!");
						} else if (response == 1) {
							Tools.showMsg(PayTypeSelectActivity.this, "支付成功!");
							PayTypeSelectActivity.this.finish();
						} else if (response == -1) {
							Tools.showMsg(PayTypeSelectActivity.this,
									"余额不足，请及时充值!");
							PayTypeSelectActivity.this.finish();
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
				Tools.showMsg(PayTypeSelectActivity.this, HTTP_ERROR);
			}
		});
	}
}
