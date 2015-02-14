package com.henglianmobile.medical.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.adapter.MainFragmentPageAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.service.UpdateApkService;
import com.henglianmobile.medical.ui.fragment.HuanyouquanFragment;
import com.henglianmobile.medical.ui.fragment.MedicalZiXunFragment;
import com.henglianmobile.medical.ui.fragment.WoDoctorFragment;
import com.henglianmobile.medical.ui.fragment.WoHuanzheFragment;
import com.henglianmobile.medical.ui.fragment.WoSalemanFragment;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private ViewPager pager;
	private ArrayList<Fragment> fragments;
	private MainFragmentPageAdapter adapter;
	private Button[] btnArray = new Button[3];
	private int currentFragmentIndex;
	private RelativeLayout toast_conten;
	private MainActivityReceiver receiver;
	
	private String userType;
    private double back_pressed;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		TApplication.instance.addActivity(this);
		setContentView(R.layout.activity_main);
		initView();
		addListener();
		checkVersion(); 
		receiver = new MainActivityReceiver();
		IntentFilter filter = new IntentFilter(
				Constant.ACTIONUPDATEUSERINFOSUCCESS);
		registerReceiver(receiver, filter);
		//获取用户信息
		String url = Const.GETUSERINFODETAILURL + TApplication.user.getUid();
		getDataHttp(url);
	}
	private void getDataHttp(String url) {
		LogUtil.i("url", "MainActivity--url=" + url);
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
					UserInfoDetailObject userInfoDetailObject1 = userInfoDetailObjects.get(0);
					TApplication.getInstance().userInfoDetailObject = userInfoDetailObject1;
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void initView() {
//		userType = "3";
		if(TApplication.getInstance().user!=null){
			userType = TApplication.getInstance().user.getUtype();
		}else{
			userType = Constant.PATIENT;
		}
		
		fragments = new ArrayList<Fragment>();
		pager = (ViewPager) findViewById(R.id.viewpager);

		HuanyouquanFragment huanyouquanFragment = new HuanyouquanFragment();
		fragments.add(huanyouquanFragment);
		MedicalZiXunFragment medicalZiXunFragment = new MedicalZiXunFragment();
		fragments.add(medicalZiXunFragment);
		WoDoctorFragment doctorFragment = new WoDoctorFragment();
		WoHuanzheFragment huanzheFragment = new WoHuanzheFragment();
		WoSalemanFragment salemanFragment = new WoSalemanFragment();
		if(userType.equals(Constant.PATIENT)){
			fragments.add(huanzheFragment);
		}else if(userType.equals(Constant.DOCTOR)){
			fragments.add(doctorFragment);
		}else if(userType.equals(Constant.SALEMAN)){
			fragments.add(salemanFragment);
		}
		
		adapter = new MainFragmentPageAdapter(getSupportFragmentManager(),
				fragments);
//		pager.setOffscreenPageLimit(3);
		pager.setAdapter(adapter);

		btnArray[0] = (Button) findViewById(R.id.btn_main_fragment_huanyouquan);
		btnArray[1] = (Button) findViewById(R.id.btn_main_fragment_medicalzixun);
		btnArray[2] = (Button) findViewById(R.id.btn_main_fragment_wo);
		toast_conten = (RelativeLayout) findViewById(R.id.toast_conten);
	}

	private void addListener() {
		for (Button btn : btnArray) {
			btn.setOnClickListener(this);
		}
		// 给ViewPager滑动事件加监听，当滑动时，按钮颜色随之改变
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int fragmentIndex) {
				// TODO Auto-generated method stub
				currentFragmentIndex = fragmentIndex;

				if(fragmentIndex == 0){
					setInitView();
					btnArray[0].setBackgroundResource(R.drawable.iv_huanyouquan_red);
				}else if(fragmentIndex == 1){
					setInitView();
					btnArray[1].setBackgroundResource(R.drawable.iv_medicalzixun_red);
				}else if(fragmentIndex == 2){
					setInitView();
					btnArray[2].setBackgroundResource(R.drawable.iv_wo_red);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_main_fragment_huanyouquan:
			setInitView();
			btnArray[0].setBackgroundResource(R.drawable.iv_huanyouquan_red);
			currentFragmentIndex = 0;
			break;
		case R.id.btn_main_fragment_medicalzixun:
			setInitView();
			btnArray[1].setBackgroundResource(R.drawable.iv_medicalzixun_red);
			currentFragmentIndex = 1;
			break;
		case R.id.btn_main_fragment_wo:
			setInitView();
			btnArray[2].setBackgroundResource(R.drawable.iv_wo_red);
			currentFragmentIndex = 2;
			break;
		}
		// 设置当前的fragment
		pager.setCurrentItem(currentFragmentIndex);

	}

	private void setInitView() {
		btnArray[0].setBackgroundResource(R.drawable.iv_huanyouquan_black);
		btnArray[1].setBackgroundResource(R.drawable.iv_medicalzixun_black);
		btnArray[2].setBackgroundResource(R.drawable.iv_wo_black);
	}
	/**
     * 点击两次返回退出系统
     * 
     * @param view
     */
    @Override
    public void onBackPressed() {
    	if (back_pressed + 3000 > System.currentTimeMillis()) {
            finish();
            TApplication.getInstance().exitActivities();
            super.onBackPressed();
        }
        else
            Tools.showCustomToast(MainActivity.this,"再次点击，退出美容院!",toast_conten);
        back_pressed = System.currentTimeMillis();
    }
	/***
	 * 检查是否更新版本
	 */
	public void checkVersion() {
		
		LogUtil.i("info", "zheli=====================");
		if (TApplication.localVersion < TApplication.serverVersion) {
			LogUtil.i("info", "localVersion=" + TApplication.localVersion);
			// 发现新版本，提示用户更新
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("软件升级")
					.setMessage("发现新版本,建议立即更新使用.")
					.setCancelable(false)
					.setPositiveButton("更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) { 
									// 开启更新服务UpdateService
									// 这里为了把update更好模块化，可以传一些updateService依赖的值
									// 如布局ID，资源ID，动态获取的标题,这里以app_name为例
									Intent updateIntent = new Intent(
											MainActivity.this,
											UpdateApkService.class);
									updateIntent.putExtra(
											"app_name",
											getResources().getString(
													R.string.app_name));
									startService(updateIntent);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			alert.create().show();

		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	/**
	 * 自定义广播接收器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MainActivityReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String url = Const.GETUSERINFODETAILURL + TApplication.user.getUid();
			getDataHttp(url);
		}
	}
}
