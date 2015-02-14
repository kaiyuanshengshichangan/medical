package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.util.BitmapUtil;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DoctorDetailActivity extends BaseActivity {
	private ImageView btn_back;
	private TextView tv_name, tv_age, tv_keshi, tv_zhuzhi, tv_hospital,
			tv_userType, tv_content, tv_tuijianzhishu;
	private ImageView iv_photo,iv_start_big,iv_sex;
	private int dId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_doctor_detail);
		dId = getIntent().getIntExtra("dId", 0);
	}

	@Override
	public void initViews() {
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_keshi = (TextView) findViewById(R.id.tv_keshi);
		tv_zhuzhi = (TextView) findViewById(R.id.tv_zhuzhi);
		tv_hospital = (TextView) findViewById(R.id.tv_hospital);
		tv_userType = (TextView) findViewById(R.id.tv_userType);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_tuijianzhishu = (TextView) findViewById(R.id.tv_tuijianzhishu);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		iv_start_big = (ImageView) findViewById(R.id.iv_start_big);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		iv_sex = (ImageView) findViewById(R.id.iv_sex);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = Const.GETUSERINFODETAILURL + dId;
		getDataHttp(url);
	}

	private void getDataHttp(String url) {
		LogUtil.i("url", "DoctorDetailActivity--url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<UserInfoDetailObject>>() {
					}.getType();
					List<UserInfoDetailObject> userInfoDetailObjects = TApplication.gson
							.fromJson(responseString, type);
					UserInfoDetailObject userInfoDetailObject = userInfoDetailObjects
							.get(0);
					showContent(userInfoDetailObject);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(DoctorDetailActivity.this, HTTP_ERROR);
			}
		});
	}

	private void showContent(UserInfoDetailObject userInfoDetailObject) {
		tv_name.setText(userInfoDetailObject.getDcRealName());
		tv_age.setText(userInfoDetailObject.getDnAge()+"");
		tv_keshi.setText(userInfoDetailObject.getDcDepart());
		tv_zhuzhi.setText(userInfoDetailObject.getDcActions());
		tv_hospital.setText(userInfoDetailObject.getDcHospital());
		tv_userType.setText(userInfoDetailObject.getDcPosition());
		tv_content.setText(userInfoDetailObject.getDcSign());
		String hot = userInfoDetailObject.getDnHot();
		float scor = (Float.parseFloat(hot))/20;
		tv_tuijianzhishu.setText(hot);
		String headImg = userInfoDetailObject.getDcHeadImg();
		ImageLoader.getInstance().displayImage(headImg, iv_photo,
				TApplication.optionsImage, new MyAnimateFirstDisplayListener());
		int isex = userInfoDetailObject.getDnSex();
		if (isex == 0) {//女
			iv_sex.setImageResource(R.drawable.iv_female);
		} else if (isex == 1) {
			iv_sex.setImageResource(R.drawable.iv_man);
		}
		
		Bitmap bitmap = BitmapFactory.decodeResource(
				DoctorDetailActivity.this.getResources(), R.drawable.start_big);
		//处理五角星评分
		LogUtil.i("info", "scor="+scor);
		bitmap = BitmapUtil.cutBitmap(bitmap, scor);
		iv_start_big.setImageBitmap(bitmap);
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}

}
