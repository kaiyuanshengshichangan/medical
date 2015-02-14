package com.henglianmobile.medical.ui.activity.doctor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.doctor.PersonalPatientAdapter;
import com.henglianmobile.medical.adapter.doctor.YuYuePersonalDoctorAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientYuYueListObject;
import com.henglianmobile.medical.entity.doctor.PersonalPatientListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class MyPersonalPatientActivity extends BaseActivity {
	private ImageView btn_back;
	private TextView tv_my_personal_patient,tv_yuyue_patient;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<PatientYuYueListObject> lists = new ArrayList<PatientYuYueListObject>();
	private YuYuePersonalDoctorAdapter adapter;
	private List<PersonalPatientListObject> lists2 = new ArrayList<PersonalPatientListObject>();
	private PersonalPatientAdapter adapter2;
	private String userId;
	private int curPage = 1;
	private int patientType = 0;//患者类型（0-预约患者，1-专属患者）

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				lists.clear();
				curPage = 1;
				getData1();
				break;

			default:
				break;
			}
		};
	};
	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_doctor_personal_patient);
		userId = TApplication.user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_my_personal_patient = (TextView) findViewById(R.id.tv_my_personal_patient);
		tv_yuyue_patient = (TextView) findViewById(R.id.tv_yuyue_patient);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_my_personal_patient_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						mPullRefreshListView.setMode(Mode.BOTH);
						lists.clear();
						lists2.clear();
						curPage = 1;
						if(patientType == 0){
							getData1();
						}else{
							getData2();
						}
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// 刷新完成
						curPage++;
						mPullRefreshListView.setMode(Mode.BOTH);
						if(patientType == 0){
							getData1();
						}else{
							getData2();
						}
					}
				});
		lv = mPullRefreshListView.getRefreshableView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lists.clear();
		curPage = 1;
		getData1();
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		tv_my_personal_patient.setOnClickListener(this);
		tv_yuyue_patient.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.tv_my_personal_patient://我的专属患者
			initText();
			tv_my_personal_patient.setBackgroundColor(Color.rgb(232, 84, 74));
			tv_my_personal_patient.setTextColor(Color.WHITE);
			patientType = 1;
			clear();
			curPage = 1;
			getData2();
			break;
		case R.id.tv_yuyue_patient://预约患者
			initText();
			tv_yuyue_patient.setBackgroundColor(Color.rgb(232, 84, 74));
			tv_yuyue_patient.setTextColor(Color.WHITE);
			patientType = 0;
			clear();
			curPage = 1;
			getData1();
			break;

		default:
			break;
		}
	}

	private void clear() {
		lists2.clear();
		lists.clear();
		if(adapter!=null){
			adapter.notifyDataSetChanged();
			adapter = null;
		}
		if(adapter2!=null){
			adapter2.notifyDataSetChanged();
			adapter2 = null;
		}
	}

	private void initText() {
		tv_my_personal_patient.setBackgroundResource(R.drawable.shape005);
		tv_yuyue_patient.setBackgroundResource(R.drawable.shape005);
		tv_my_personal_patient.setTextColor(Color.rgb(232, 84, 74));
		tv_yuyue_patient.setTextColor(Color.rgb(232, 84, 74));
	}

	/**
	 * 访问网路获取数据
	 * 我是医生-私人医生-预约患者
	 */
	public void getData1() {
		String url = Const.DOCTORGETYUYUEJIUZHENLISTURL + "dUid=" + userId
				+ "&type=" + Constant.PERSONALDOCTOR + "&page=" + curPage + "&rows="
				+ Const.PAGEROWS + "&isagree=10";
		getHttpData1(url);
	}
	/**
	 * 访问网路获取数据
	 * 我是医生-私人医生-预约患者
	 */
	public void getData2() {
		String url = Const.DOCTORMYPERSONALPATIENTURL + "dUid=" + userId
				+ "&page=" + curPage + "&type=4" + "&rows=" + Const.PAGEROWS;
		getHttpData2(url);
	}
	private void getHttpData1(String url) {
		LogUtil.i("url", "MyPersonalPatientActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(MyPersonalPatientActivity.this,
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MyPersonalPatientActivity----res="
							+ responseString);
					Type type = new TypeToken<List<PatientYuYueListObject>>() {
					}.getType();
					List<PatientYuYueListObject> patientYuYueJiuZhenListObjects = TApplication.gson.fromJson(responseString, type);
					if (patientYuYueJiuZhenListObjects != null) {
						for (int i = 0; i < patientYuYueJiuZhenListObjects.size(); i++) {
							lists.add(patientYuYueJiuZhenListObjects.get(i));
						}
						if (patientYuYueJiuZhenListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(
										MyPersonalPatientActivity.this,
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
						}
						if (adapter == null) {
							adapter = new YuYuePersonalDoctorAdapter(
									MyPersonalPatientActivity.this,
									lists,userId,handler);
							lv.setAdapter(adapter);
							lv.setOnItemClickListener(null);
						} else {
							adapter.changeData(lists);
						}
					}
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
	}
	private void getHttpData2(String url) {
		LogUtil.i("url", "MyPersonalPatientActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(MyPersonalPatientActivity.this,
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MyPersonalPatientActivity----res="
							+ responseString);
					Type type = new TypeToken<List<PersonalPatientListObject>>() {
					}.getType();
					List<PersonalPatientListObject> personalPatientListObjects = TApplication.gson.fromJson(responseString, type);
					if (personalPatientListObjects != null) {
						for (int i = 0; i < personalPatientListObjects.size(); i++) {
							lists2.add(personalPatientListObjects.get(i));
						}
						if (personalPatientListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(
										MyPersonalPatientActivity.this,
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
						}
						if (adapter2 == null) {
							adapter2 = new PersonalPatientAdapter(
									MyPersonalPatientActivity.this,
									lists2);
							lv.setAdapter(adapter2);
							lv.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
									PersonalPatientListObject personalPatientListObject = lists2.get(position-1);
									String pId = personalPatientListObject.getDnUserid()+"";
									Intent intent = new Intent(MyPersonalPatientActivity.this, PatientHealthManageActivity.class);
									intent.putExtra("pId", pId);
									MyPersonalPatientActivity.this.startActivity(intent);
								}
							});
							
						} else {
							adapter2.changeData(lists2);
						}
					}
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
	}
}
