package com.henglianmobile.medical.ui.activity.patient;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.GridViewAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientListObject;
import com.henglianmobile.medical.entity.patient.HealthManageMethodListObject;
import com.henglianmobile.medical.ui.activity.HuanyouPagerActivity;
import com.henglianmobile.medical.ui.activity.HuanyouquanDetailActivity;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HealthManageMethodDetailActivity extends BaseActivity {
	private TextView tv_doctor_name, tv_hospital, tv_doctor_keshi,
			tv_doctor_advice, tv_time;
	private ImageView iv_doctor_photo, iv_patient_pic, iv_patient_pic1,
			iv_patient_pic2;
	private LinearLayout ll_patient_pics;
	private GridView gv_patient_pics;
	private ImageView btn_back;
	private HealthManageMethodListObject healthManageMethodListObject;
	private List<String> pics;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_health_manage_mothod_detail);
	}

	@Override
	public void initViews() {
		tv_doctor_name = (TextView) findViewById(R.id.tv_doctor_name);
		tv_hospital = (TextView) findViewById(R.id.tv_hospital);
		tv_doctor_keshi = (TextView) findViewById(R.id.tv_doctor_keshi);
		tv_doctor_advice = (TextView) findViewById(R.id.tv_doctor_advice);
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_doctor_photo = (ImageView) findViewById(R.id.iv_doctor_photo);
		iv_patient_pic = (ImageView) findViewById(R.id.iv_patient_pic);
		iv_patient_pic1 = (ImageView) findViewById(R.id.iv_patient_pic1);
		iv_patient_pic2 = (ImageView) findViewById(R.id.iv_patient_pic2);
		ll_patient_pics = (LinearLayout) findViewById(R.id.ll_patient_pics);
		gv_patient_pics = (GridView) findViewById(R.id.gv_patient_pics);
		btn_back = (ImageView) findViewById(R.id.btn_back);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		healthManageMethodListObject = (HealthManageMethodListObject) getIntent()
				.getSerializableExtra("healthManageMethodListObject");
		setText(healthManageMethodListObject);
	}

	private void setText(HealthManageMethodListObject healthManageMethod) {
		tv_doctor_name.setText(healthManageMethod.getDcRealName());
		tv_hospital.setText(healthManageMethod.getDcHospital());
		tv_doctor_keshi.setText(healthManageMethod.getDcDepart());
		tv_doctor_advice.setText(healthManageMethod.getDcContent());
		tv_time.setText(healthManageMethod.getDtAddTime());
		String photoPath = healthManageMethod.getDcHeadImg();
		ImageLoader.getInstance().displayImage(photoPath, iv_doctor_photo,
				TApplication.optionsImage, new MyAnimateFirstDisplayListener());

		String topicPics = healthManageMethod.getDcIpath();
		LogUtil.i("info", "topicPics====" + topicPics);
		pics = new ArrayList<String>();
		if (!"".equals(topicPics)) {

			String[] topics = topicPics.split(",");
			for (int i = 0; i < topics.length; i++) {
				pics.add(topics[i]);
			}
			if (topics.length == 1) {
				iv_patient_pic.setVisibility(View.VISIBLE);
				ll_patient_pics.setVisibility(View.GONE);
				gv_patient_pics.setVisibility(View.GONE);
				String picUrlone = topics[0];
				ImageLoader.getInstance().displayImage(picUrlone,
						iv_patient_pic, TApplication.optionsImage,
						new MyAnimateFirstDisplayListener());

				iv_patient_pic.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(
								HealthManageMethodDetailActivity.this,
								HuanyouPagerActivity.class);
						intent.putExtra("position", 0);
						LogUtil.i("info", "pics=" + pics.toString()
								+ ",position=" + 0);
						intent.putStringArrayListExtra("pics",
								(ArrayList<String>) pics);
						HealthManageMethodDetailActivity.this
								.startActivity(intent);
					}
				});

			} else if (topics.length == 2) {
				ll_patient_pics.setVisibility(View.VISIBLE);
				iv_patient_pic.setVisibility(View.GONE);
				gv_patient_pics.setVisibility(View.GONE);
				ImageLoader.getInstance().displayImage(topics[0],
						iv_patient_pic1, TApplication.optionsImage,
						new MyAnimateFirstDisplayListener());
				ImageLoader.getInstance().displayImage(topics[1],
						iv_patient_pic2, TApplication.optionsImage,
						new MyAnimateFirstDisplayListener());

				iv_patient_pic1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(
								HealthManageMethodDetailActivity.this,
								HuanyouPagerActivity.class);
						intent.putExtra("position", 0);
						LogUtil.i("info", "pics=" + pics.toString()
								+ ",position=" + 0);
						intent.putStringArrayListExtra("pics",
								(ArrayList<String>) pics);
						HealthManageMethodDetailActivity.this
								.startActivity(intent);
					}
				});

				iv_patient_pic2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(
								HealthManageMethodDetailActivity.this,
								HuanyouPagerActivity.class);
						intent.putExtra("position", 1);
						LogUtil.i("info", "pics=" + pics.toString()
								+ ",position=" + 1);
						intent.putStringArrayListExtra("pics",
								(ArrayList<String>) pics);
						HealthManageMethodDetailActivity.this
								.startActivity(intent);
					}
				});

			} else if (topics.length >= 3) {
				gv_patient_pics.setVisibility(View.VISIBLE);
				iv_patient_pic.setVisibility(View.GONE);
				ll_patient_pics.setVisibility(View.GONE);

				GridViewAdapter adapter = new GridViewAdapter(
						HealthManageMethodDetailActivity.this, pics);
				gv_patient_pics.setAdapter(adapter);
				gv_patient_pics
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								Intent intent = new Intent(
										HealthManageMethodDetailActivity.this,
										HuanyouPagerActivity.class);
								intent.putExtra("position", position);
								LogUtil.i("info", "pics=" + pics.toString()
										+ ",position=" + position);
								intent.putStringArrayListExtra("pics",
										(ArrayList<String>) pics);
								HealthManageMethodDetailActivity.this
										.startActivity(intent);
							}
						});
			}
		}
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}

}
