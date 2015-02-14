package com.henglianmobile.medical.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Window;

import com.henglianmobile.medical.app.TApplication;
/**
 * 每个Activity的基类
 * @author Administrator
 *
 */
public abstract class BaseActivity extends Activity implements OnClickListener{
	public final String HTTP_ERROR = "网络不通，请查看您的网络环境再充重试！";
	public final String LOAD_ALL = "已经加载全部数据！";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TApplication.instance.addActivity(this);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		init();
	}
	/**
	 * 初始化数据
	 */
	private void init() {
		loadLayout();
		initViews();
		addListener();
	}
	/**
	 * 载入布局
	 */
	public abstract void loadLayout();
	/**
	 * 初始化数据
	 */
	public abstract void initViews();
	/**
	 * 添加监听器
	 */
	public abstract void addListener();
}
