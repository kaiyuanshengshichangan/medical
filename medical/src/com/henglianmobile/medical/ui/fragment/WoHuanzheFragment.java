package com.henglianmobile.medical.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.ui.activity.patient.DoctorPhoneActivity;
import com.henglianmobile.medical.ui.activity.patient.HealthManageActivity;
import com.henglianmobile.medical.ui.activity.patient.MyDoctorActivity;
import com.henglianmobile.medical.ui.activity.patient.MyPersonalDoctorActivity;
import com.henglianmobile.medical.ui.activity.patient.SearchDoctorActivity;
import com.henglianmobile.medical.ui.activity.patient.YaoPinDaoGouActivity;
import com.henglianmobile.medical.ui.activity.patient.YuYueJiuZhenActivity;
import com.henglianmobile.medical.ui.activity.patient.YuYueZhuYuanActivity;
import com.henglianmobile.medical.ui.activity.patient.ZhangHaoGuanLiActivity;
import com.henglianmobile.medical.ui.activity.patient.ZhangHuChongZhiActivity;
import com.henglianmobile.medical.util.Tools;

public class WoHuanzheFragment extends Fragment implements OnClickListener {
	private ImageView[] btnArray = new ImageView[9];
	private EditText et_search;
	private Intent intent;
	private Button btn_search;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wo_huanzhe, null);
		setupView(view);
		addListener();
//		setData();
		return view;
	}

	private void setupView(View view) {
		btnArray[0] = (ImageView)view.findViewById(R.id.btn_mydoctor);
		btnArray[1] = (ImageView)view.findViewById(R.id.btn_doctor_phone);
		btnArray[2] = (ImageView)view.findViewById(R.id.btn_yuyueguahao);
		btnArray[3] = (ImageView)view.findViewById(R.id.btn_yuyuezhuyuan);
		btnArray[4] = (ImageView)view.findViewById(R.id.btn_myself_doctor);
		btnArray[5] = (ImageView)view.findViewById(R.id.btn_health_manage);
		btnArray[6] = (ImageView)view.findViewById(R.id.btn_yaopindaogou);
		btnArray[7] = (ImageView)view.findViewById(R.id.btn_my_account);
		btnArray[8] = (ImageView)view.findViewById(R.id.btn_zhanghuchongzhi);
		et_search = (EditText)view.findViewById(R.id.et_search);
		btn_search = (Button)view.findViewById(R.id.btn_search);
	}

	private void addListener() {
		for (ImageView btn : btnArray) {
			btn.setOnClickListener(this);
		}
		btn_search.setOnClickListener(this);
		et_search.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_mydoctor:
			intent = new Intent(this.getActivity(), MyDoctorActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_doctor_phone:
			intent = new Intent(this.getActivity(), DoctorPhoneActivity.class);
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
		case R.id.btn_myself_doctor:
			intent = new Intent(this.getActivity(), MyPersonalDoctorActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_health_manage:
			if(TextUtils.isEmpty(TApplication.getInstance().userInfoDetailObject.getDcRealName())){
				Tools.showMsg(this.getActivity(), "请先进入账号管理，填写个人资料!");
				return ;
			}
			intent = new Intent(this.getActivity(), HealthManageActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_yaopindaogou:
			if(TextUtils.isEmpty(TApplication.getInstance().userInfoDetailObject.getDcRealName())){
				Tools.showMsg(this.getActivity(), "请先进入账号管理，填写个人资料!");
				return ;
			}
			intent = new Intent(this.getActivity(), YaoPinDaoGouActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_my_account:
			intent = new Intent(this.getActivity(), ZhangHaoGuanLiActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_zhanghuchongzhi:
			if(TextUtils.isEmpty(TApplication.getInstance().userInfoDetailObject.getDcRealName())){
				Tools.showMsg(this.getActivity(), "请先进入账号管理，填写个人资料!");
				return ;
			}
			intent = new Intent(this.getActivity(), ZhangHuChongZhiActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_search://查找医生
			intent = new Intent(this.getActivity(), SearchDoctorActivity.class);
			intent.putExtra("search", et_search.getText().toString().trim());
			startActivity(intent);
			btn_search.setEnabled(false);
			btn_search.setVisibility(View.GONE);
			break;
		case R.id.et_search://
			if(btn_search.getVisibility() == View.GONE){
				btn_search.setEnabled(true);
				btn_search.setVisibility(View.VISIBLE);
			}else{
				btn_search.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}
}
