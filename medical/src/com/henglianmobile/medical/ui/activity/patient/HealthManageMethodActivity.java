package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
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
import com.henglianmobile.medical.adapter.patient.HealthManageMethodAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.HealthManageMethodListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class HealthManageMethodActivity extends BaseActivity {
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<HealthManageMethodListObject> lists = new ArrayList<HealthManageMethodListObject>();
	private HealthManageMethodAdapter adapter;
	private int curPage = 1;
	private String userId;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_health_manage_method);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_health_manage_method_list);
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
				HealthManageMethodListObject healthManageMethodListObject = lists
						.get(position - 1);
				Intent intent = new Intent(HealthManageMethodActivity.this,
						HealthManageMethodDetailActivity.class);
				intent.putExtra("healthManageMethodListObject", healthManageMethodListObject);
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
		String url = Const.GETHEALTHMANAGEMETHODURL + "sickerId=" + userId + "&page="
				+ curPage + "&rows=" + Const.PAGEROWS+"&query=";
		getHttpData(url);
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "HealthManageMethodActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "HealthManageMethodActivity----res=" + arg2);

				Tools.showMsg(HealthManageMethodActivity.this, Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "HealthManageMethodActivity----res="
							+ responseString);
					Type type = new TypeToken<List<HealthManageMethodListObject>>() {
					}.getType();
					List<HealthManageMethodListObject> healthManageMethodListObjects = TApplication.gson
							.fromJson(responseString, type);
					if (healthManageMethodListObjects != null) {
						for (int i = 0; i < healthManageMethodListObjects.size(); i++) {
							lists.add(healthManageMethodListObjects.get(i));
						}
						if (healthManageMethodListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(HealthManageMethodActivity.this,
										Tools.LOAD_ALL);
							} else if (curPage == 1
									&& healthManageMethodListObjects.size() == 0) {
								Tools.showMsg(HealthManageMethodActivity.this,
										"抱歉!医生还没有给您推荐健康管理方案!");
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new HealthManageMethodAdapter(
									HealthManageMethodActivity.this, lists);
							lv.setAdapter(adapter);

						} else {
							adapter.changeData(lists);
						}
					}
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
	}
}
