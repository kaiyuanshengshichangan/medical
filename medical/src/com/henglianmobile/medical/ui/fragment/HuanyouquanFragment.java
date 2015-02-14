package com.henglianmobile.medical.ui.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.adapter.HuanyouquanListAdapter;
import com.henglianmobile.medical.adapter.patient.MyDoctorAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientListObject;
import com.henglianmobile.medical.entity.patient.MyDoctorBaseInfoObject;
import com.henglianmobile.medical.ui.activity.HuanyouquanDetailActivity;
import com.henglianmobile.medical.ui.activity.PublishPatientStatusActivity;
import com.henglianmobile.medical.ui.activity.YiYouQuanActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class HuanyouquanFragment extends Fragment {

	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private List<MyDoctorBaseInfoObject> lists = new ArrayList<MyDoctorBaseInfoObject>();
	private MyDoctorAdapter adapter;
	private HuanyouquanListAdapter huanyouquanListAdapter;
	private List<PatientListObject> patientListObjects = new ArrayList<PatientListObject>();
	
	private int curPage = 1;
	private ImageView iv_publish;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_huanyouquan, null);
		setupView(view);
		// addListener();
		lists.clear();
		patientListObjects.clear();
		curPage = 1;
		getData();
		return view;
	}

	private void setupView(View view) {
		iv_publish = (ImageView) view.findViewById(R.id.iv_publish);
		LogUtil.i("info", TApplication.user.getUtype()+"=="+Constant.DOCTOR);
		if(TApplication.user.getUtype().equals(Constant.DOCTOR)){
			iv_publish.setVisibility(View.VISIBLE);
		}
		iv_publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HuanyouquanFragment.this.getActivity(),
						PublishPatientStatusActivity.class);
				intent.putExtra("dUserId", Integer.parseInt(TApplication.user.getUid()));
				HuanyouquanFragment.this.startActivity(intent);
			}
		});
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.lv_huanyouquan_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						mPullRefreshListView.setMode(Mode.BOTH);
						lists.clear();
						patientListObjects.clear();
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
				if(TApplication.user.getUtype().equals(Constant.DOCTOR)){
					LogUtil.i("info", "position=" + position);
					PatientListObject patientListObject = patientListObjects.get(position - 1);
					Intent intent = new Intent(HuanyouquanFragment.this.getActivity()
							, HuanyouquanDetailActivity.class);
					intent.putExtra("patientListObject", patientListObject);
					startActivity(intent);
				}else{
					LogUtil.i("info", "position="+position);
					MyDoctorBaseInfoObject myDoctorBaseInfoObject = lists.get(position-1);
					int dUserId = myDoctorBaseInfoObject.getDnDUserid();
					LogUtil.i("info", "dUserId="+dUserId);
					Intent intent = new Intent(HuanyouquanFragment.this.getActivity(), YiYouQuanActivity.class);
					intent.putExtra("dUserId", dUserId);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		adapter = null;
		huanyouquanListAdapter = null;
	}
	/**
	 * 访问网路获取数据
	 */
	private void getData() {
		String url = "";
		if(TApplication.user.getUtype().equals(Constant.DOCTOR)){
			url = Const.GETHUANYOUQUANLISTURL 
					+ "dUserId="+ TApplication.user.getUid()
					+ "&page=" + curPage 
					+ "&rows=" + Const.PAGEROWS;
			getData(url);
		}else{
			url = Const.GETYIYOUQUANDOCTORLISTURL 
					+ "userId=" + TApplication.user.getUid()
					+ "&userType=" + TApplication.user.getUtype()
					+ "&page=" + curPage 
					+ "&rows=" + Const.PAGEROWS;
			getHttpData(url);
		}
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "HuanyouquanFragment---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "HuanyouquanFragment----res=" + arg2);
				Tools.showMsg(HuanyouquanFragment.this.getActivity(), Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
				Tools.closeProgressDialog();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "HuanyouquanFragment----res=" + responseString);
					Type type = new TypeToken<List<MyDoctorBaseInfoObject>>() {
					}.getType();
					List<MyDoctorBaseInfoObject> myDoctorBaseInfoObjects = TApplication.getInstance().gson.fromJson(
							responseString, type);
					if (myDoctorBaseInfoObjects != null) {
						for (int i = 0; i < myDoctorBaseInfoObjects.size(); i++) {
							lists.add(myDoctorBaseInfoObjects.get(i));
						}
						if (myDoctorBaseInfoObjects.size() < Const.PAGEROWS) {
							if(curPage!=1){
								Tools.showMsg(HuanyouquanFragment.this.getActivity(), Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new MyDoctorAdapter(
									HuanyouquanFragment.this.getActivity(), lists,null);
							lv.setAdapter(adapter);

						} else {
							adapter.changeData(lists);
						}
					}
					mPullRefreshListView.onRefreshComplete();
					Tools.closeProgressDialog();
				}
			}
		});
	}
	private void getData(String url) {
		LogUtil.i("url", "HuanyouquanFragment---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "HuanyouquanFragment----res=" + arg2);
				Tools.showMsg(HuanyouquanFragment.this.getActivity(), Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
				Tools.closeProgressDialog();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "HuanyouquanFragment----res=" + responseString);
					Type type = new TypeToken<List<PatientListObject>>() {
					}.getType();
					List<PatientListObject> listObjects = TApplication.getInstance().gson.fromJson(
							responseString, type);
					if (listObjects != null) {
						for (int i = 0; i < listObjects.size(); i++) {
							patientListObjects.add(listObjects.get(i));
						}
						if (listObjects.size() < Const.PAGEROWS) {
							if(curPage!=1){
								Tools.showMsg(HuanyouquanFragment.this.getActivity(), Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (huanyouquanListAdapter == null) {
							huanyouquanListAdapter = new HuanyouquanListAdapter(
									HuanyouquanFragment.this.getActivity(), patientListObjects);
							lv.setAdapter(huanyouquanListAdapter);
							
						} else {
							huanyouquanListAdapter.changeData(patientListObjects);
						}
					}
					mPullRefreshListView.onRefreshComplete();
					Tools.closeProgressDialog();
				}
			}
		});
	}
}
