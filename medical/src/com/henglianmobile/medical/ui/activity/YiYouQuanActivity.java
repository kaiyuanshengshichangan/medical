package com.henglianmobile.medical.ui.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.HuanyouquanListAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientListObject;
import com.henglianmobile.medical.ui.fragment.HuanyouquanFragment;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class YiYouQuanActivity extends BaseActivity {
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private ImageView iv_publish,btn_back;
	private HuanyouquanListAdapter adapter;
	private List<PatientListObject> lists = new ArrayList<PatientListObject>();
	private int curPage = 1;

	private int dUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		super.onResume();
		lists.clear();
		curPage = 1;
		getData();
	}
	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_yiyouquan);
		dUserId = getIntent().getIntExtra("dUserId", 0);
		LogUtil.i("info", "-----dUserId="+dUserId);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		iv_publish = (ImageView) findViewById(R.id.iv_publish);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_huanyouquan_list);
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

				LogUtil.i("info", "position=" + position);
				PatientListObject patientListObject = lists.get(position - 1);
				Intent intent = new Intent(YiYouQuanActivity.this
						, HuanyouquanDetailActivity.class);
				intent.putExtra("patientListObject", patientListObject);
				startActivity(intent);
			}
		});
	}

	@Override
	public void addListener() {
		iv_publish.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_publish:
			Intent intent = new Intent(YiYouQuanActivity.this,
					PublishPatientStatusActivity.class);
			intent.putExtra("dUserId", dUserId);
			YiYouQuanActivity.this.startActivity(intent);
			break;
		case R.id.btn_back:
			this.finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 访问网路获取数据
	 */
	private void getData() {
		String url = Const.GETHUANYOUQUANLISTURL 
				+ "dUserId="+ dUserId
				+ "&page=" + curPage 
				+ "&rows=" + Const.PAGEROWS;
		getHttpData(url);
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "HuanyouquanFragment---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "HuanyouquanFragment----res=" + arg2);
				
				Tools.showMsg(YiYouQuanActivity.this, Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "HuanyouquanFragment----res=" + responseString);
					Type type = new TypeToken<List<PatientListObject>>() {
					}.getType();
					List<PatientListObject> friendsTopicLists = TApplication.getInstance().gson.fromJson(
							responseString, type);
					if (friendsTopicLists != null) {
						for (int i = 0; i < friendsTopicLists.size(); i++) {
							lists.add(friendsTopicLists.get(i));
						}
						if (friendsTopicLists.size() < Const.PAGEROWS) {
							if(curPage!=1){
								Tools.showMsg(YiYouQuanActivity.this, Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new HuanyouquanListAdapter(
									YiYouQuanActivity.this, lists);
							lv.setAdapter(adapter);

						} else {
//							adapter = null;
//							adapter = new HuanyouquanListAdapter(
//									HuanyouquanFragment.this.getActivity(), lists);
//							lv.setAdapter(adapter);
							adapter.changeData(lists);
						}
					}
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
	}
}
