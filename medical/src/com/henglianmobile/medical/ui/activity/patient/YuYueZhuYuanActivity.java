package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.patient.YuYueZhuYuanAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.DoctorYuYueListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class YuYueZhuYuanActivity extends BaseActivity {
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<DoctorYuYueListObject> lists = new ArrayList<DoctorYuYueListObject>();
	private YuYueZhuYuanAdapter adapter;
	private String userId;
	private int curPage = 1;

	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_patient_yuyuezhuyuan);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_yuyuezhuyuan_list);
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
				
				LogUtil.i("info", "position="+position);
				DoctorYuYueListObject doctorYuYueListObject = lists.get(position-1);
				Intent intent = new Intent(YuYueZhuYuanActivity.this, ZhuyuanActivity.class);
				intent.putExtra("doctorYuYueListObject", doctorYuYueListObject);
				startActivity(intent);
			}
		});
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lists.clear();
		curPage = 1;
		getData();
	}
	@Override
	public void onClick(View v) {
		this.finish();
	}
	/**
	 * 访问网路获取数据
	 * http://115.28.147.21:2233/api/make/getdoctormake?userid=154&types
	 * =1&isagree=0&pages=1&rows=10
	 */
	public void getData() {
		String url = Const.PATIENTTYUYUEJIUZHENLISTURL + "uid=" + userId
				+ "&type=" + Constant.ZHUYUAN+"&page="+curPage+"&rows="+Const.PAGEROWS;
		getHttpData(url);
	}
	private void getHttpData(String url) {
		LogUtil.i("url", "YuYueZhuYuanActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "YuYueZhuYuanActivity----res=" + arg2);

				Tools.showMsg(YuYueZhuYuanActivity.this,
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "YuYueJiuZhenActivity----res="
							+ responseString);
					Type type = new TypeToken<List<DoctorYuYueListObject>>() {
					}.getType();
					List<DoctorYuYueListObject> doctorYuYueZhuYuanListObjects = TApplication.gson.fromJson(responseString, type);
					if (doctorYuYueZhuYuanListObjects != null) {
						for (int i = 0; i < doctorYuYueZhuYuanListObjects.size(); i++) {
							lists.add(doctorYuYueZhuYuanListObjects.get(i));
						}
						if (doctorYuYueZhuYuanListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(
										YuYueZhuYuanActivity.this,
										Tools.LOAD_ALL);
							} else if (curPage == 1
									&& doctorYuYueZhuYuanListObjects.size() == 0) {
								Tools.showMsg(YuYueZhuYuanActivity.this,
										"您还没有预约住院，请搜索医生进行操作!");
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new YuYueZhuYuanAdapter(
									YuYueZhuYuanActivity.this,
									lists,userId);
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
