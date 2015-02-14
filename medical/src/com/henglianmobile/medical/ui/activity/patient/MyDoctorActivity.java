package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.patient.MyDoctorAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.MyDoctorBaseInfoObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class MyDoctorActivity extends BaseActivity {
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<MyDoctorBaseInfoObject> lists = new ArrayList<MyDoctorBaseInfoObject>();
	private MyDoctorAdapter adapter;
	private int curPage = 1;
	private String userId;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				lists.clear();
				curPage = 1;
				getData();
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_patient_mydoctor);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_doctor_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						mPullRefreshListView.setMode(Mode.BOTH);
						lists.clear();
						curPage = 1;
						getData();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// 刷新完成
						curPage++;
						mPullRefreshListView.setMode(Mode.BOTH);
						getData();
					}
				});
		lv = mPullRefreshListView.getRefreshableView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// LogUtil.i("info", "position="+position);
				MyDoctorBaseInfoObject myDoctorBaseInfoObject = lists
						.get(position - 1);
				int dId = myDoctorBaseInfoObject.getDnUserid();
				Intent intent = new Intent(MyDoctorActivity.this,
						DoctorDetailActivity.class);
				intent.putExtra("dId", dId);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lists.clear();
		curPage = 1;
		getData();
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}

	/**
	 * 访问网路获取数据
	 */
	private void getData() {
		String url = Const.PATIENTMYDOCTORLISTURL + "uid=" + userId + "&page="
				+ curPage + "&rows=" + Const.PAGEROWS;
		getHttpData(url);
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "MyDoctorActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "MyDoctorActivity----res=" + arg2);

				Tools.showMsg(MyDoctorActivity.this, Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MyDoctorActivity----res="
							+ responseString);
					Type type = new TypeToken<List<MyDoctorBaseInfoObject>>() {
					}.getType();
					List<MyDoctorBaseInfoObject> myDoctorBaseInfoObjects = TApplication.gson
							.fromJson(responseString, type);
					if (myDoctorBaseInfoObjects != null) {
						for (int i = 0; i < myDoctorBaseInfoObjects.size(); i++) {
							lists.add(myDoctorBaseInfoObjects.get(i));
						}
						if (myDoctorBaseInfoObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(MyDoctorActivity.this,
										Tools.LOAD_ALL);
							} else if (curPage == 1
									&& myDoctorBaseInfoObjects.size() == 0) {
								Tools.showMsg(MyDoctorActivity.this,
										"您还没有加医生为好友，请搜索医生进行操作!");
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new MyDoctorAdapter(
									MyDoctorActivity.this, lists, handler);
							lv.setAdapter(adapter);

						} else {
							// adapter = null;
							// adapter = new HuanyouquanListAdapter(
							// HuanyouquanFragment.this.getActivity(), lists);
							// lv.setAdapter(adapter);
							adapter.changeData(lists);
						}
					}
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
	}
}
