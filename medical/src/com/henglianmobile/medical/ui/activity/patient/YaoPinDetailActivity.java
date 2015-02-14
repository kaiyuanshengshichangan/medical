package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.GridViewAdapter;
import com.henglianmobile.medical.adapter.patient.YaoPinDaoGouListAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.YaoPinDaoGouListObject;
import com.henglianmobile.medical.entity.patient.YaoPinDetailObject;
import com.henglianmobile.medical.ui.activity.HuanyouPagerActivity;
import com.henglianmobile.medical.ui.activity.HuanyouquanDetailActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.henglianmobile.medical.view.MyGridView;
import com.loopj.android.http.TextHttpResponseHandler;

public class YaoPinDetailActivity extends BaseActivity {
	private ImageView btn_back;
	private TextView tv_drugname,tv_drug_amount,tv_drug_introduce;
	private MyGridView gv_drug_pics;
	private int id;
	private List<String> pics;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_yaofang_detail);
		id = getIntent().getIntExtra("id", 1);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		tv_drugname = (TextView)findViewById(R.id.tv_drugname);
		tv_drug_amount = (TextView)findViewById(R.id.tv_drug_amount);
		tv_drug_introduce = (TextView)findViewById(R.id.tv_drug_introduce);
		gv_drug_pics = (MyGridView)findViewById(R.id.gv_drug_pics);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = Const.GETYAOFANGDETAILURL+id;
		getYaoFang(url);
	}
	
	private void getYaoFang(String url) {
		LogUtil.i("url", "YaoPinDetailActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(YaoPinDetailActivity.this,
						Tools.HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "YaoPinDetailActivity----res=" + responseString);
					Type type = new TypeToken<YaoPinDetailObject>() {
					}.getType();
					YaoPinDetailObject yaoPinDetailObject = TApplication.getInstance().gson.fromJson(
							responseString, type);
					if(yaoPinDetailObject!=null){
						showText(yaoPinDetailObject);
					}
				}
			}
		});
	}

	private void showText(YaoPinDetailObject yaoPinDetailObject) {
		tv_drugname.setText(yaoPinDetailObject.getDrugName());
		tv_drug_amount.setText(yaoPinDetailObject.getCountName());
		tv_drug_introduce.setText(yaoPinDetailObject.getContent());
		String drugPics = yaoPinDetailObject.getImgUrl();
		LogUtil.i("info", "topicPics====" + drugPics);
		pics = new ArrayList<String>();
		if(!TextUtils.isEmpty(drugPics)){
			String[] topics = drugPics.split(",");
			for (int i = 0; i < topics.length; i++) {
				pics.add(topics[i]);
			}
			GridViewAdapter adapter = new GridViewAdapter(
					YaoPinDetailActivity.this, pics);
			gv_drug_pics.setAdapter(adapter);
			gv_drug_pics
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							Intent intent = new Intent(
									YaoPinDetailActivity.this,
									HuanyouPagerActivity.class);
							intent.putExtra("position", position);
							LogUtil.i("info", "pics=" + pics.toString()
									+ ",position=" + position);
							intent.putStringArrayListExtra("pics", (ArrayList<String>) pics);
							YaoPinDetailActivity.this
									.startActivity(intent);
						}
					});
		}
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}
}
