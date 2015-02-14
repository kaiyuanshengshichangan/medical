package com.henglianmobile.medical.ui.activity.patient;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;

public class RemittanceInfoActivity extends BaseActivity {
	private ImageView btn_back;
	private Button btn_sure;
	private TextView tv_order_no,tv_amount;
	private String amount,orderNo;
	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_remittance_info);
		amount = getIntent().getStringExtra("amount");
		orderNo = getIntent().getStringExtra("orderNo");
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		tv_order_no = (TextView)findViewById(R.id.tv_order_no);
		tv_amount = (TextView)findViewById(R.id.tv_amount);
		tv_order_no.setText(orderNo);
		tv_amount.setText(amount);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_sure:
			this.finish();
			break;
		default:
			break;
		}
	}//RemittanceInfo

}
