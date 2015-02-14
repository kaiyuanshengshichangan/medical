package com.henglianmobile.medical.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.MyPagerAdapter;
import com.henglianmobile.medical.util.LogUtil;

public class HuanyouPagerActivity extends BaseActivity {
	private ViewPager pager;
	private int position;
	private List<String> pics = new ArrayList<String>();
	private MyPagerAdapter adapter;
	private int cou;
	private TextView tv_pagercount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_huanyou_pager);

	}

	@Override
	public void initViews() {
		tv_pagercount=(TextView) findViewById(R.id.tv_pagercount);
		
		getIntent().getStringExtra("url");
		position = getIntent().getIntExtra("position", 0);
		cou=position;
		pics = getIntent().getStringArrayListExtra("pics");
		LogUtil.i("info", "youdongxima===" + ",position=" + position
				+ ",pics=" + pics);
		
		LogUtil.i("info", "-------------------------------");
		pager = (ViewPager) findViewById(R.id.pager);
//		ImageAdapter adapter = new ImageAdapter();
		adapter = new MyPagerAdapter(this, pics);
		pager.setAdapter(adapter);
		pager.setCurrentItem(position);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				cou=arg0;
				tv_pagercount.setText(arg0+1+"/"+pics.size());
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}

}
