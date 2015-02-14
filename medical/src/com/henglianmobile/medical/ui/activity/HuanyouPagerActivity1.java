package com.henglianmobile.medical.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.MyPagerAdapter;
import com.henglianmobile.medical.adapter.issue5.GalleryFlipAdapter;
import com.henglianmobile.medical.util.LogUtil;

public class HuanyouPagerActivity1 extends BaseActivity {
	private FlipViewController flipView;
	
	private int position;
	private List<String> pics = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void loadLayout() {
		
		getIntent().getStringExtra("url");
		position = getIntent().getIntExtra("position", 0);
		pics = getIntent().getStringArrayListExtra("pics");
		LogUtil.i("info", "youdongxima===" + ",position=" + position
				+ ",pics=" + pics);
		
		flipView = new FlipViewController(this,FlipViewController.HORIZONTAL);
//		flipView.setAdapter(new GalleryFlipAdapter(this, flipView,pics));
		flipView.setAdapter(new GalleryFlipAdapter(this, flipView,pics), position);
	    setContentView(flipView);
		
	}

	@Override
	public void initViews() {
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void addListener() {
		
	}

}
