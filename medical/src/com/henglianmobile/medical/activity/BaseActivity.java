package com.henglianmobile.medical.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Window;

import com.henglianmobile.medical.app.TApplication;
/**
 * ÿ��Activity�Ļ���
 * @author Administrator
 *
 */
public abstract class BaseActivity extends Activity implements OnClickListener{
	public final String HTTP_ERROR = "���粻ͨ����鿴�������绷���ٳ����ԣ�";
	public final String LOAD_ALL = "�Ѿ�����ȫ�����ݣ�";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TApplication.instance.addActivity(this);
		// ���ر�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		init();
	}
	/**
	 * ��ʼ������
	 */
	private void init() {
		loadLayout();
		initViews();
		addListener();
	}
	/**
	 * ���벼��
	 */
	public abstract void loadLayout();
	/**
	 * ��ʼ������
	 */
	public abstract void initViews();
	/**
	 * ��Ӽ�����
	 */
	public abstract void addListener();
}
