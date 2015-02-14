package com.henglianmobile.medical.ui.fragment;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.ui.activity.doctor.DingjiaActivity;
import com.henglianmobile.medical.ui.activity.doctor.DoctorBaseInfoActivity;
import com.henglianmobile.medical.ui.activity.doctor.MyPatientActivity;
import com.henglianmobile.medical.ui.activity.doctor.MyPersonalPatientActivity;
import com.henglianmobile.medical.ui.activity.doctor.MyPhoneActivity;
import com.henglianmobile.medical.ui.activity.doctor.YuYueJiuZhenActivity;
import com.henglianmobile.medical.ui.activity.doctor.YuYueZhuYuanActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WoDoctorFragment extends Fragment implements OnClickListener {
	private ImageView[] btnArray = new ImageView[6];
	private ImageView iv_photo;
	private TextView tv_name;
	private Intent intent;
	private String userId;
	private UserInfoDetailObject userInfoDetailObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = TApplication.getInstance().user.getUid();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wo_doctor, null);
		setupView(view);
		addListener();
//		setData();1
		return view;
	}

	private void setupView(View view) {
		btnArray[0] = (ImageView)view.findViewById(R.id.btn_my_patient);
		btnArray[1] = (ImageView)view.findViewById(R.id.btn_my_phone);
		btnArray[2] = (ImageView)view.findViewById(R.id.btn_yuyueguahao);
		btnArray[3] = (ImageView)view.findViewById(R.id.btn_yuyuezhuyuan);
		btnArray[4] = (ImageView)view.findViewById(R.id.btn_dingjia);
		btnArray[5] = (ImageView)view.findViewById(R.id.btn_personal_doctor);
		iv_photo = (ImageView)view.findViewById(R.id.iv_photo1);
		tv_name = (TextView)view.findViewById(R.id.tv_name);
	}
	
	private void addListener() {
		for (ImageView btn : btnArray) {
			btn.setOnClickListener(this);
		}
		iv_photo.setOnClickListener(this);
	}
	@Override
	public void onResume() {
		super.onResume();
		String url = Const.GETUSERINFODETAILURL + userId;
		getDataHttp(url);
	}
	private void getDataHttp(String url) {
		LogUtil.i("url", "WoSalemanFragment--url=" + url);
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
					userInfoDetailObject = userInfoDetailObjects.get(0);
					String photoPath = userInfoDetailObject.getDcHeadImg();
					ImageLoader.getInstance().displayImage(photoPath, iv_photo,
							TApplication.optionsImage,
							new MyAnimateFirstDisplayListener());
					tv_name.setText(userInfoDetailObject.getDcRealName());
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_my_patient:
			intent = new Intent(this.getActivity(), MyPatientActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_my_phone:
			intent = new Intent(this.getActivity(), MyPhoneActivity.class);
			startActivity(intent);
			
			break;
		case R.id.btn_yuyueguahao:
			intent = new Intent(this.getActivity(), YuYueJiuZhenActivity.class);
			startActivity(intent);
			
			break;
		case R.id.btn_yuyuezhuyuan:
			intent = new Intent(this.getActivity(), YuYueZhuYuanActivity.class);
			startActivity(intent);
			
			break;
		case R.id.btn_dingjia:
			intent = new Intent(this.getActivity(), DingjiaActivity.class);
			startActivity(intent);
			
			break;
		case R.id.btn_personal_doctor:
			intent = new Intent(this.getActivity(), MyPersonalPatientActivity.class);
			startActivity(intent);
			
			break;
		case R.id.iv_photo1:
			intent = new Intent(this.getActivity(), DoctorBaseInfoActivity.class);
			intent.putExtra("user", userInfoDetailObject);
			startActivity(intent);
			
			break;

		default:
			break;
		}
	}
}
