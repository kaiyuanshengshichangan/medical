package com.henglianmobile.medical.ui.activity.saleman;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
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
import com.henglianmobile.medical.adapter.saleman.MyDoctorAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.saleman.MyDoctorListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class MyDoctorActivity extends BaseActivity {
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<MyDoctorListObject> lists = new ArrayList<MyDoctorListObject>();
	private MyDoctorAdapter adapter;
	private String userId;
	private int curPage = 1;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_saleman_mydoctor);
		userId = TApplication.user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_mydoctor_list);
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
	 * http://115.28.147.21:2233/api/make/getdoctormake?userid=154&types
	 * =1&isagree=0&pages=1&rows=10
	 */
	public void getData() {
		String url = Const.GETMYDOCTORURL + "userId=" + userId+"&page="+curPage+"&rows="+Const.PAGEROWS+"&keyword=";
		getHttpData(url);
	}
	private void getHttpData(String url) {
		LogUtil.i("url", "MyDoctorActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "MyDoctorActivity----res=" + arg2);

				Tools.showMsg(MyDoctorActivity.this,
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MyDoctorActivity----res="
							+ responseString);
					Type type = new TypeToken<List<MyDoctorListObject>>() {
					}.getType();
					List<MyDoctorListObject> myDoctorListObjects = TApplication.gson.fromJson(responseString, type);
					if (myDoctorListObjects != null) {
						for (int i = 0; i < myDoctorListObjects.size(); i++) {
							lists.add(myDoctorListObjects.get(i));
						}
						if (myDoctorListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(
										MyDoctorActivity.this,
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new MyDoctorAdapter(
									MyDoctorActivity.this,
									lists,userId);
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
