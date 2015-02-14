package com.henglianmobile.medical.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.adapter.ViewPagerAdapter;
import com.henglianmobile.medical.util.SPUtil;

public class GuidanceViewActivity extends BaseActivity implements
		OnPageChangeListener{

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private ImageView[] dots;
	private int currentIndex;
	private ImageView iv_know1,iv_know2,iv_know3;

	@Override
	public void loadLayout() {
		setContentView(R.layout.guidepage);
	}

	@Override
	public void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.guide_page1, null));
		views.add(inflater.inflate(R.layout.guide_page2, null));
		views.add(inflater.inflate(R.layout.guide_page3, null));
		iv_know1 = (ImageView) views.get(0)
				.findViewById(R.id.iv_know1);
		iv_know2 = (ImageView) views.get(1)
				.findViewById(R.id.iv_know2);
		iv_know3 = (ImageView) views.get(2)
				.findViewById(R.id.iv_know3);
		
		vpAdapter = new ViewPagerAdapter(views);
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
		initDots();
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[views.size()];

		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(false);
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(true);
	}

	@Override
	public void addListener() {
		iv_know1.setOnClickListener(this);
		iv_know2.setOnClickListener(this);
		iv_know3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_know1:
			vp.setCurrentItem(1);
			break;
		case R.id.iv_know2:
			vp.setCurrentItem(2);
			break;
		case R.id.iv_know3:
			StartApp();
			break;

		default:
			break;
		}
	}

	private void StartApp() {
		Intent intent = new Intent(GuidanceViewActivity.this, LoginActivity.class);
		startActivity(intent);
		GuidanceViewActivity.this.finish();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);
		currentIndex = position;
	}

}
