package com.henglianmobile.medical.ui.activity.doctor;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.LoginResultObject;
import com.henglianmobile.medical.entity.doctor.GetDingjiaObject;
import com.henglianmobile.medical.ui.activity.HuanyouquanDetailActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class DingjiaActivity extends BaseActivity {
	private Button btn_sure;
	private ImageView btn_back;
	private EditText et_rexiandingjia, et_personal_doctor_dingjia,
			et_guahaodingjia, et_zhuyuandingjia;
	private String rexianDingjia, personalDoctorDingjia, guahaoDingjia,
			zhuyuanDingjia;
	private List<GetDingjiaObject> getDingjiaObjects;
	private String userId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_doctor_dingjia);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		et_rexiandingjia = (EditText) findViewById(R.id.et_rexiandingjia);
		et_personal_doctor_dingjia = (EditText) findViewById(R.id.et_personal_doctor_dingjia);
		et_guahaodingjia = (EditText) findViewById(R.id.et_guahaodingjia);
		et_zhuyuandingjia = (EditText) findViewById(R.id.et_zhuyuandingjia);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = Const.GETDINGJIAURL+userId;
		getDataHttp(url);
	}
	private void getDataHttp(String url) {
		LogUtil.i("url", "DingjiaActivity--getDataHttp-url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String responseString) {
				if (arg0 == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<GetDingjiaObject>>() {
					}.getType();
					getDingjiaObjects = TApplication.gson.fromJson(
							responseString, type);
					if(getDingjiaObjects.size()!=0){
						GetDingjiaObject dingjiaObject = getDingjiaObjects.get(0);
						showContent(dingjiaObject);
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Tools.showMsg(DingjiaActivity.this,
						HTTP_ERROR);
			}
		});
	}

	private void showContent(GetDingjiaObject dingjiaObject) {
		et_rexiandingjia.setText(dingjiaObject.getDnHotLinePrice()+"");
		et_personal_doctor_dingjia.setText(dingjiaObject.getDnOwnPrice()+"");
		et_guahaodingjia.setText(dingjiaObject.getDnRegPrice()+"");
		et_zhuyuandingjia.setText(dingjiaObject.getDnInHosPrice()+"");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_sure:
			if (check()) {
				if(getDingjiaObjects.size()!=0){
					submitUpdate();
				}else {
					setPrice();
				}
			}
			break;

		default:
			break;
		}
	}

	private boolean check() {
		rexianDingjia = et_rexiandingjia.getText().toString().trim();
		personalDoctorDingjia = et_personal_doctor_dingjia.getText().toString()
				.trim();
		guahaoDingjia = et_guahaodingjia.getText().toString().trim();
		zhuyuanDingjia = et_zhuyuandingjia.getText().toString().trim();
		if (TextUtils.isEmpty(rexianDingjia)) {
			Tools.showMsg(this, "请输入热线定价!");
			return false;
		}
		if (TextUtils.isEmpty(personalDoctorDingjia)) {
			Tools.showMsg(this, "请输入私人医生定价!");
			return false;
		}
		if (TextUtils.isEmpty(guahaoDingjia)) {
			Tools.showMsg(this, "请输入挂号定价!");
			return false;
		}
		if (TextUtils.isEmpty(zhuyuanDingjia)) {
			Tools.showMsg(this, "请输入住院定价!");
			return false;
		}
		return true;
	}

	private void setPrice() {
		String url = Const.ADDDINGJIAURL + "uid=" + userId + "&hotlinep="
				+ rexianDingjia + "&ownp=" + personalDoctorDingjia + "&inhosp="
				+ zhuyuanDingjia+"&regp="+guahaoDingjia;
		submitAddHttpGet(url);
	}
	private void submitAddHttpGet(String url) {
		LogUtil.i("url", "DingjiaActivity--submitAddHttpGet-url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String responseString) {
				if (arg0 == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						// int response = Integer.parseInt(jsonObject
						// .getString("response"));
						String response = jsonObject.getString("response");
						if ("0".equals(response)) {
							Tools.showMsg(DingjiaActivity.this,
									"添加价格失败!");
						} else if ("1".equals(response)) {
							Tools.showMsg(DingjiaActivity.this,
									"添加价格成功!");
							DingjiaActivity.this.finish();
						} else if("2".equals(response)){
							Tools.showMsg(DingjiaActivity.this,
									"价格已存在!");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Tools.showMsg(DingjiaActivity.this,
						HTTP_ERROR);
			}
		});
	}

	private void submitUpdate() {
		String url = Const.UPDATEDINGJIAURL + "userid=" + userId + "&hotlinep="
				+ rexianDingjia + "&ownp=" + personalDoctorDingjia + "&inhosp="
				+ zhuyuanDingjia+"&regp="+guahaoDingjia;
		submitUpdateHttpGet(url);
	}

	private void submitUpdateHttpGet(String url) {
		LogUtil.i("url", "DingjiaActivity--submitHttpGet-url="+url);
		HttpUtil.get(url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String responseString) {
				if (arg0 == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						// int response = Integer.parseInt(jsonObject
						// .getString("response"));
						String response = jsonObject.getString("response");
						if ("0".equals(response)) {
							Tools.showMsg(DingjiaActivity.this,
									"修改价格失败!");
						} else if ("1".equals(response)) {
							Tools.showMsg(DingjiaActivity.this,
									"修改价格成功!");
							DingjiaActivity.this.finish();
						} 
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Tools.showMsg(DingjiaActivity.this,
						HTTP_ERROR);
			}
		});
	}
}
