package com.henglianmobile.medical.adapter;

import java.util.List;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter {
	// 界面列表
	private List<View> views;

	public ViewPagerAdapter(List<View> views) {
		this.views = views;
	}

	@Override
	// 销毁arg1位置的界面
	public void destroyItem(View arg0, int arg1, Object arg2) {
		// TODO Auto-generated method stub
		System.out.println("index:"+arg1);
		((ViewPager) arg0).removeView((View)arg2);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	// 获得当前页面数
	public int getCount() {
		// TODO Auto-generated method stub
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		// TODO Auto-generated method stub
		((ViewPager) arg0).addView(views.get(arg1), 0);
		return views.get(arg1);
	}

	// 判断是否有对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub
	}
	
	 @Override  
     public int getItemPosition(Object object) {  
      return POSITION_NONE;  
     }   
}