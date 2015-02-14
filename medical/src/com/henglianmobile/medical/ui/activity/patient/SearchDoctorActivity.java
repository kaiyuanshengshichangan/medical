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
import com.henglianmobile.medical.adapter.patient.SearchDoctorAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.SearchDoctorBaseInfoObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class SearchDoctorActivity extends BaseActivity {
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<SearchDoctorBaseInfoObject> lists = new ArrayList<SearchDoctorBaseInfoObject>();
	private SearchDoctorAdapter adapter;
	private int curPage = 1;
	private String query;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_search_doctor_list);
		query = getIntent().getStringExtra("search");
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

				SearchDoctorBaseInfoObject searchDoctorBaseInfoObject = lists
						.get(position - 1);
				int dId = searchDoctorBaseInfoObject.getDnUserid();
				Intent intent = new Intent(SearchDoctorActivity.this,
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
		String url = Const.SEARCHDOCTORLISTURL + "page=" + curPage + "&rows="
				+ Const.PAGEROWS + "&query=" + query;
		getHttpData(url);
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "SearchDoctorActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "SearchDoctorActivity----res=" + arg2);

				Tools.showMsg(SearchDoctorActivity.this,
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "SearchDoctorActivity----res="
							+ responseString);
					Type type = new TypeToken<List<SearchDoctorBaseInfoObject>>() {
					}.getType();
					List<SearchDoctorBaseInfoObject> doctorBaseInfoObjects = TApplication.gson.fromJson(responseString, type);
					if (doctorBaseInfoObjects != null) {
						for (int i = 0; i < doctorBaseInfoObjects.size(); i++) {
							lists.add(doctorBaseInfoObjects.get(i));
						}
						if (doctorBaseInfoObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(
										SearchDoctorActivity.this,
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new SearchDoctorAdapter(
									SearchDoctorActivity.this,
									lists);
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
