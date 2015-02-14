package com.henglianmobile.medical.ui.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.henglianmobile.medical.adapter.MedicalZixunAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.ZixunListObject;
import com.henglianmobile.medical.entity.ZixunTitleObject;
import com.henglianmobile.medical.ui.activity.MedicalZiXunDetailActivity1;
import com.henglianmobile.medical.ui.activity.MedicalZiXunTitleActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MedicalZiXunFragment extends Fragment {

	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private View headerVeiw;
	private MedicalZixunAdapter adapter;
	private ImageView iv_zixun_header_view;
	private List<ZixunListObject> lists = new ArrayList<ZixunListObject>();
	private int curPage = 1;
	private String titleUrl;
	
	private MyBroadcastReceiver receiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		receiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter(Constant.ACTIONPUBLISHHUANYOUQUANSUCCESS);
		this.getActivity().registerReceiver(receiver, filter);
		lists.clear();
		curPage = 1;
		getData();
		getBaseData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.getActivity().unregisterReceiver(receiver);
	}
	private void getBaseData() {
		String url = Const.GETMEDICALZIXUNTITLEURL;
		getBaseHttpData(url);
	}

	private void getBaseHttpData(String url) {
		LogUtil.i("url", "FriendsFragment--getBaseHttpData-url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(MedicalZiXunFragment.this.getActivity(),
						Tools.HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MedicalZiXunFragment----res="
							+ responseString);
					Type type = new TypeToken<ZixunTitleObject>() {
					}.getType();
					ZixunTitleObject zixunTitleObject = TApplication
							.getInstance().gson.fromJson(responseString, type);
					String titlePath = zixunTitleObject.getImgUrl();
					ImageLoader.getInstance().displayImage(titlePath,
							iv_zixun_header_view, TApplication.optionsImage,
							new MyAnimateFirstDisplayListener());
					titleUrl = zixunTitleObject.getLinkUrl();
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_medicalzixun, null);
		setupView(view);
		return view;
	}

	private void setupView(View view) {
		headerVeiw = LayoutInflater.from(this.getActivity()).inflate(
				R.layout.fragment_medicalzixun_list_header_view, null);
		iv_zixun_header_view = (ImageView) headerVeiw
				.findViewById(R.id.iv_zixun_header_view);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.lv_medicalzixun_list);
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
						// Ë¢ÐÂÍê³É
						curPage++;
						mPullRefreshListView.setMode(Mode.BOTH);
						getData();
					}

				});
		lv = mPullRefreshListView.getRefreshableView();
		lv.addHeaderView(headerVeiw);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 1) {
					return;
				}
				LogUtil.i("info", "position=" + position);
				ZixunListObject zixunListObject = lists.get(position - 2);
				int dnNid = zixunListObject.getDnNid();
				Intent intent = new Intent(MedicalZiXunFragment.this
						.getActivity(), MedicalZiXunDetailActivity1.class);
				intent.putExtra("id", dnNid);
				startActivity(intent);
			}
		});
		iv_zixun_header_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MedicalZiXunFragment.this
						.getActivity(), MedicalZiXunTitleActivity.class);
				intent.putExtra("titleUrl", titleUrl);
				startActivity(intent);
			}
		});
	}

	private void getData() {
		String url = Const.GETMEDICALZIXUNLISTURL + "page=" + curPage
				+ "&rows=" + Const.PAGEROWS + "&strquery=";
		getHttpData(url);
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "FriendsFragment---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(MedicalZiXunFragment.this.getActivity(),
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MedicalZiXunFragment----res="
							+ responseString);
					Type type = new TypeToken<List<ZixunListObject>>() {
					}.getType();
					List<ZixunListObject> zixunListObjects = TApplication
							.getInstance().gson.fromJson(responseString, type);
					if (zixunListObjects != null) {
						for (int i = 0; i < zixunListObjects.size(); i++) {
							lists.add(zixunListObjects.get(i));
						}
						if (zixunListObjects.size() < Const.PAGEROWS) {
							if (curPage != 1) {
								Tools.showMsg(
										MedicalZiXunFragment.this.getActivity(),
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new MedicalZixunAdapter(
									MedicalZiXunFragment.this.getActivity(),
									lists);
							lv.setAdapter(adapter);

						} else {
							LogUtil.i("info", "lists.size()==" + lists.size());
							adapter.changeData(lists);
						}
					}
					mPullRefreshListView.onRefreshComplete();
				}
			}
		});
	}
	private class MyBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			lists.clear();
			adapter = null;
			curPage = 1;
			getData();
		}
	}
}
