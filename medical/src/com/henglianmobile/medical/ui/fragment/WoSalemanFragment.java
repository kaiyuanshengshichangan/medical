package com.henglianmobile.medical.ui.fragment;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import com.doublefi123.diary.widget.CircularImage;
import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.LoginActivity;
import com.henglianmobile.medical.activity.MainActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.LoginResultObject;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.ui.activity.saleman.CommissionActivity;
import com.henglianmobile.medical.ui.activity.saleman.GuaHaoCountActivity;
import com.henglianmobile.medical.ui.activity.saleman.MyDoctorActivity;
import com.henglianmobile.medical.ui.activity.saleman.PersonalDoctorActivity;
import com.henglianmobile.medical.ui.activity.saleman.PhoneCountActivity;
import com.henglianmobile.medical.ui.activity.saleman.ZhangHaoGuanLiActivity;
import com.henglianmobile.medical.ui.activity.saleman.ZhuYuanCountActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.SPUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WoSalemanFragment extends Fragment implements OnClickListener {
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
		View view = inflater.inflate(R.layout.fragment_wo_saleman, null);
		setupView(view);
		addListener();
		// setData();
		return view;
	}

	private void setupView(View view) {
		btnArray[0] = (ImageView) view.findViewById(R.id.btn_mydoctor);
		btnArray[1] = (ImageView) view.findViewById(R.id.btn_phone_count);
		btnArray[2] = (ImageView) view.findViewById(R.id.btn_guahao_count);
		btnArray[3] = (ImageView) view.findViewById(R.id.btn_zhuyuan_count);
		btnArray[4] = (ImageView) view.findViewById(R.id.btn_commission);
		btnArray[5] = (ImageView) view.findViewById(R.id.btn_personal_doctor);
		iv_photo = (ImageView) view.findViewById(R.id.iv_photo111);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
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
					tv_name.setText(userInfoDetailObject.getDcRealName());
					String photoPath = userInfoDetailObject.getDcHeadImg();
					ImageLoader.getInstance().displayImage(photoPath, iv_photo,
							TApplication.optionsImage,
							new MyAnimateFirstDisplayListener());
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
		case R.id.btn_mydoctor:
			intent = new Intent(this.getActivity(), MyDoctorActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_phone_count://热线数量
			intent = new Intent(this.getActivity(), PhoneCountActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_guahao_count://挂号数量
			intent = new Intent(this.getActivity(), GuaHaoCountActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_zhuyuan_count://住院数量
			intent = new Intent(this.getActivity(), ZhuYuanCountActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_commission://提成
			intent = new Intent(this.getActivity(), CommissionActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_personal_doctor://私人医生
			intent = new Intent(this.getActivity(),
					PersonalDoctorActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_photo111:
			intent = new Intent(this.getActivity(),
					ZhangHaoGuanLiActivity.class);
			intent.putExtra("user", userInfoDetailObject);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
