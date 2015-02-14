package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
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
import com.henglianmobile.medical.adapter.patient.HuaYanDanListAdapter;
import com.henglianmobile.medical.adapter.patient.YongYaoLiShiListAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.HuaYanDanListObject;
import com.henglianmobile.medical.entity.patient.YongYaoLiShiListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class YongYaoLiShiActivity extends BaseActivity {
	private Button btn_publish;
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private YongYaoLiShiListAdapter adapter;
	private List<YongYaoLiShiListObject> lists = new ArrayList<YongYaoLiShiListObject>();
	private int curPage = 1;
	private String userId,healthManageType;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_yongyaolishi);
		healthManageType = getIntent().getStringExtra(Constant.HEALTH_MANAGE_KEY);
		if(healthManageType.equals(Constant.PATIENT_HEALTH_MANAGE_VALUE)){
			userId = TApplication.user.getUid();
		}else if(healthManageType.equals(Constant.DOCTOR_HEALTH_MANAGE_VALUE)){
			userId = getIntent().getStringExtra("pId");
		}
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		btn_publish = (Button)findViewById(R.id.btn_publish);
		if(healthManageType.equals(Constant.PATIENT_HEALTH_MANAGE_VALUE)){
			btn_publish.setVisibility(View.VISIBLE);
		}else if(healthManageType.equals(Constant.DOCTOR_HEALTH_MANAGE_VALUE)){
			btn_publish.setVisibility(View.GONE);
		}
		mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.lv_yongyaolishi_list);
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
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_publish.setOnClickListener(this);
	}
	@Override
	protected void onResume() {
		super.onResume();
		lists.clear();
		curPage = 1;
		getData();

	}

	private void getData() {
		String url = Const.GETYONGYAOLISHIURL + userId + "&page=" + curPage
				+ "&rows=" + Const.PAGEROWS;
		getDataHttp(url);
	}

	private void getDataHttp(String url) {
		LogUtil.i("url", "YongYaoLiShiActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<YongYaoLiShiListObject>>() {
					}.getType();
					List<YongYaoLiShiListObject> yongYaoLiShiListObjects = TApplication.gson
							.fromJson(responseString, type);
					if (yongYaoLiShiListObjects != null) {
						for (int i = 0; i < yongYaoLiShiListObjects.size(); i++) {
							lists.add(yongYaoLiShiListObjects.get(i));
						}
						if (yongYaoLiShiListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(YongYaoLiShiActivity.this,
										Tools.LOAD_ALL);
							} else if (curPage == 1
									&& yongYaoLiShiListObjects.size() == 0) {
								Tools.showMsg(YongYaoLiShiActivity.this,
										"还没有上传用药历史!");
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new YongYaoLiShiListAdapter(
									YongYaoLiShiActivity.this, lists);
							lv.setAdapter(adapter);

						} else {
							// adapter = null;
							// adapter = new HuanyouquanListAdapter(
							// HuanyouquanFragment.this.getActivity(), lists);
							// lv.setAdapter(adapter);
							adapter.changeData(lists);
						}
					}else{
						Tools.showMsg(YongYaoLiShiActivity.this,
								Tools.LOAD_ALL);
					}
					mPullRefreshListView.onRefreshComplete();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(YongYaoLiShiActivity.this, Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_publish:
			Intent intent = new Intent(this, PublishYongYaoLiShiActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
