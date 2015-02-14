package com.henglianmobile.medical.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class WrapContentHeightViewPager extends ViewPager {
	public WrapContentHeightViewPager(Context context) {
	    super(context);
	  }

	  public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }

	  @Override
	  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	    int height = 0;
	    //�����������child�ĸ߶�
	    for (int i = 0; i < getChildCount(); i++) {
	      View child = getChildAt(i);
	      child.measure(widthMeasureSpec,
	          MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	      int h = child.getMeasuredHeight();
	      if (h > height) //��������view�ĸ߶ȡ�
	        height = h;
	    }

	    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
	        MeasureSpec.EXACTLY);

	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	  }
}