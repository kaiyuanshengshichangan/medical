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
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.saleman.ServiceCountAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.saleman.ServiceListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class GuaHaoCountActivity extends BaseActivity {

	private ImageView btn_back;
	private TextView tv_start_date,tv_end_date,tv_sure;
	private String startDate="",endDate="";
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<ServiceListObject> lists = new ArrayList<ServiceListObject>();
	private ServiceCountAdapter adapter;
	private String userId;
	private int curPage = 1;
	
	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_saleman_guahao_count);
		userId = TApplication.user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_start_date = (TextView) findViewById(R.id.tv_start_date);
		tv_end_date = (TextView) findViewById(R.id.tv_end_date);
		tv_sure = (TextView) findViewById(R.id.tv_sure);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_guahao_count_list);
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
		tv_start_date.setOnClickListener(this);
		tv_end_date.setOnClickListener(this);
		tv_sure.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.tv_start_date:
			Tools.datePicker(this, tv_start_date);
			break;
		case R.id.tv_end_date:
			Tools.datePicker(this, tv_end_date);
			break;
		case R.id.tv_sure:
			if(check()){
				lists.clear();
				curPage = 1;
				getData();
			}
			break;

		default:
			break;
		}
	}

	private boolean check() {
		try {
			startDate = tv_start_date.getText().toString();
			endDate = tv_end_date.getText().toString();
			long lStartDate = Tools.StringDateToLong(startDate,"yyyy-MM-dd");//毫秒
			long lEndDate = Tools.StringDateToLong(endDate,"yyyy-MM-dd");
			if (lStartDate>=lEndDate) {
				Tools.showMsg(this, "您选择的结束时间小于开始时间，请重新选择!");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 访问网路获取数据
	 * http://115.28.147.21:2233/api/make/getdoctormake?userid=154&types
	 * =1&isagree=0&pages=1&rows=10
	 */
	public void getData() {
		String url = Const.GETFUWUXIANGMUCOUNTURL + "userId=" + userId+"&page="+curPage+"&rows="+Const.PAGEROWS
				+"&type="+Constant.SERVICEGUAHAO+"&startTime="+startDate+"&endTime="+endDate;
		getHttpData(url);
	}
	private void getHttpData(String url) {
		LogUtil.i("url", "GuaHaoCountActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "GuaHaoCountActivity----res=" + arg2);
				Tools.showMsg(GuaHaoCountActivity.this,
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "GuaHaoCountActivity----res="
							+ responseString);
					Type type = new TypeToken<List<ServiceListObject>>() {
					}.getType();
					List<ServiceListObject> serviceListObjects = TApplication.gson.fromJson(responseString, type);
					if (serviceListObjects != null) {
						for (int i = 0; i < serviceListObjects.size(); i++) {
							lists.add(serviceListObjects.get(i));
						}
						if (serviceListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(
										GuaHaoCountActivity.this,
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new ServiceCountAdapter(
									GuaHaoCountActivity.this,
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
