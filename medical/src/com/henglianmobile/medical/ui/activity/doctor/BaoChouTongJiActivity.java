package com.henglianmobile.medical.ui.activity.doctor;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.saleman.CommissionMyDoctorAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.saleman.CommissionListObject;
import com.henglianmobile.medical.ui.activity.saleman.CommissionActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class BaoChouTongJiActivity extends BaseActivity {
	private ImageView btn_back;
	private TextView[] TVArray = new TextView[12];
	private String userId;
	private String startDate="",endDate="";
	
	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_doctor_baochoutongji);
		userId = TApplication.user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		TVArray[0] = (TextView)findViewById(R.id.tv_start_date);
		TVArray[1] = (TextView)findViewById(R.id.tv_end_date);
		TVArray[2] = (TextView)findViewById(R.id.tv_sure);
		TVArray[3] = (TextView)findViewById(R.id.tv_guahao_count);
		TVArray[4] = (TextView)findViewById(R.id.tv_guahao_commission);
		TVArray[5] = (TextView)findViewById(R.id.tv_rexian_count);
		TVArray[6] = (TextView)findViewById(R.id.tv_rexian_commission);
		TVArray[7] = (TextView)findViewById(R.id.tv_zhuyuan_count);
		TVArray[8] = (TextView)findViewById(R.id.tv_zhuyuan_commission);
		TVArray[9] = (TextView)findViewById(R.id.tv_personal_patient_count);
		TVArray[10] = (TextView)findViewById(R.id.tv_personal_patient_commission);
		TVArray[11] = (TextView)findViewById(R.id.tv_personal_patient_date_count);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		TVArray[0].setOnClickListener(this);
		TVArray[1].setOnClickListener(this);
		TVArray[2].setOnClickListener(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getData();
	}

	private void getData() {
		String url = Const.BAOCHOUTONGJIURL + "doctorId=" + userId
				+"&startTime="+startDate+"&endTime="+endDate;
		getHttpData(url);
	}
	private void getHttpData(String url) {
		LogUtil.i("url", "BaoChouTongJiActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "BaoChouTongJiActivity----res=" + arg2);
				Tools.showMsg(BaoChouTongJiActivity.this,
						Tools.HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "BaoChouTongJiActivity----res="
							+ responseString);
					Type type = new TypeToken<List<CommissionListObject>>() {
					}.getType();
					List<CommissionListObject> commissionListObjects = TApplication.gson.fromJson(responseString, type);
					if (commissionListObjects != null) {
						for(int i = 0;i < commissionListObjects.size();i ++){
							CommissionListObject commissionListObject = commissionListObjects.get(i);
							if(commissionListObject.getDnMakeType() == 1){//挂号
								TVArray[3].setText(commissionListObject.getServiceNum()+"个");
								TVArray[4].setText(commissionListObject.getSumPrice()+"元");
							}else if(commissionListObject.getDnMakeType() == 3){//热线
								TVArray[5].setText(commissionListObject.getServiceNum()+"个");
								TVArray[6].setText(commissionListObject.getSumPrice()+"元");
							}else if(commissionListObject.getDnMakeType() == 2){//住院
								TVArray[7].setText(commissionListObject.getServiceNum()+"个");
								TVArray[8].setText(commissionListObject.getSumPrice()+"元");
							}else if(commissionListObject.getDnMakeType() == 4){//住院
								TVArray[9].setText(commissionListObject.getServiceNum()+"个");
								TVArray[10].setText(commissionListObject.getSumPrice()+"元");
								TVArray[11].setText("共"+commissionListObject.getSumMonth()+"月");
							}
						}
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.tv_start_date:
			Tools.datePicker(this, TVArray[0]);
			break;
		case R.id.tv_end_date:
			Tools.datePicker(this, TVArray[1]);
			break;
		case R.id.tv_sure:
			try {
				startDate = TVArray[0].getText().toString();
				endDate = TVArray[1].getText().toString();
				LogUtil.i("res", "BaoChouTongJiActivity----res="+startDate+"--"+endDate);
				if(!TextUtils.isEmpty(startDate)&&!TextUtils.isEmpty(endDate)){
					long lStartDate = Tools.StringDateToLong(startDate,"yyyy-MM-dd");//毫秒
					long lEndDate = Tools.StringDateToLong(endDate,"yyyy-MM-dd");
					if (lStartDate>=lEndDate) {
						Tools.showMsg(this, "您选择的结束时间小于开始时间，请重新选择!");
						return;
					}
				}
				clearText();
				getData();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	private void clearText() {
		TVArray[3].setText("0个");
		TVArray[4].setText("0元");
		TVArray[5].setText("0个");
		TVArray[6].setText("0元");
		TVArray[7].setText("0个");
		TVArray[8].setText("0元");
		TVArray[9].setText("0个");
		TVArray[10].setText("0元");
		TVArray[11].setText("共0月");
	}
}
