package com.henglianmobile.medical.ui.activity.doctor;

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
import com.henglianmobile.medical.adapter.doctor.MyPatientAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.doctor.MyPatientBaseInfoObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class MyPatientActivity extends BaseActivity {
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<MyPatientBaseInfoObject> lists = new ArrayList<MyPatientBaseInfoObject>();
	private MyPatientAdapter adapter;
	private int curPage = 1;
	private String userId;
	
	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_doctor_mypatient);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_patient_list);
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
				MyPatientBaseInfoObject myPatientBaseInfoObject = lists
						.get(position - 1);
				int uId = myPatientBaseInfoObject.getDnUserid();
				
				Intent intent = new Intent(MyPatientActivity.this,
						PatientDetailActivity.class);
				intent.putExtra("uId", uId);
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
		String url = Const.DOCTORMYPATIENTURL + "dUid=" + userId + "&type=0" + "&page="
				+ curPage + "&rows=" + Const.PAGEROWS;
		getHttpData(url);
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "MyPatientActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "MyPatientActivity----res=" + arg2);

				Tools.showMsg(MyPatientActivity.this, Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MyPatientActivity----res="
							+ responseString);
					Type type = new TypeToken<List<MyPatientBaseInfoObject>>() {
					}.getType();
					List<MyPatientBaseInfoObject> myPatientBaseInfoObjects = TApplication.gson
							.fromJson(responseString, type);
					if (myPatientBaseInfoObjects != null) {
						for (int i = 0; i < myPatientBaseInfoObjects.size(); i++) {
							lists.add(myPatientBaseInfoObjects.get(i));
						}
						if (myPatientBaseInfoObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(MyPatientActivity.this,
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new MyPatientAdapter(
									MyPatientActivity.this, lists);
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
