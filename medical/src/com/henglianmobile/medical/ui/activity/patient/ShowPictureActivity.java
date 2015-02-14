package com.henglianmobile.medical.ui.activity.patient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShowPictureActivity extends BaseActivity {
	private ImageView btn_back;
	private ImageView iv_picture;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_show_picture);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		iv_picture = (ImageView) findViewById(R.id.iv_picture);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		iv_picture.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String path = getIntent().getStringExtra("picPath");
		ImageLoader.getInstance().displayImage(path, iv_picture,
				TApplication.optionsImage, new MyAnimateFirstDisplayListener());
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}
}
