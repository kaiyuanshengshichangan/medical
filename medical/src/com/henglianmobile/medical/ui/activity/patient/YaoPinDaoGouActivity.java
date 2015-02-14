package com.henglianmobile.medical.ui.activity.patient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.henglianmobile.medical.adapter.MedicalZixunAdapter;
import com.henglianmobile.medical.adapter.patient.YaoPinDaoGouListAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.ZixunListObject;
import com.henglianmobile.medical.entity.patient.YaoPinDaoGouListObject;
import com.henglianmobile.medical.ui.activity.MedicalZiXunDetailActivity1;
import com.henglianmobile.medical.ui.fragment.MedicalZiXunFragment;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class YaoPinDaoGouActivity extends BaseActivity {

	private Button btn_publish_yaofang;
	private ImageView btn_back;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lv;
	private View headerVeiw;
	private YaoPinDaoGouListAdapter adapter;
	private List<YaoPinDaoGouListObject> lists = new ArrayList<YaoPinDaoGouListObject>();
	private int curPage = 1;
	private String userId;

	@Override
	public void loadLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_patient_yaopindaogou);
		userId = TApplication.user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView)findViewById(R.id.btn_back);
		btn_publish_yaofang = (Button)findViewById(R.id.btn_publish_yaofang);
		headerVeiw = LayoutInflater.from(this).inflate(
				R.layout.fragment_medicalzixun_list_header_view, null);
		mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.lv_yaopindaigou_list);
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
						//刷新完成
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
				if(position == 1){
					return;
				}
				LogUtil.i("info", "position="+position);
				YaoPinDaoGouListObject yaoPinDaoGouListObject = lists.get(position-2);
				int yId = yaoPinDaoGouListObject.getId();
				Intent intent = new Intent(YaoPinDaoGouActivity.this, YaoPinDetailActivity.class);
				intent.putExtra("id", yId);
				startActivity(intent);
			}
		});
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_publish_yaofang.setOnClickListener(this);
	}
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
	private void getData() {
		String url = Const.YAOPINDAOGOULISTURL+"userId="+userId + "&page=" + curPage + "&rows=" + Const.PAGEROWS+"&keyWord=";
		getHttpData(url);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_publish_yaofang://上传药方
			Intent intent = new Intent(this, UploadYaoFangActivity.class);
			this.startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "YaoPinDaoGouActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(YaoPinDaoGouActivity.this,
						Tools.HTTP_ERROR);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "YaoPinDaoGouActivity----res=" + responseString);
					Type type = new TypeToken<List<YaoPinDaoGouListObject>>() {
					}.getType();
					List<YaoPinDaoGouListObject> yaoPinDaoGouListObjects = TApplication.getInstance().gson.fromJson(
							responseString, type);
					if (yaoPinDaoGouListObjects!=null) {
						for (int i = 0; i < yaoPinDaoGouListObjects.size(); i++) {
							lists.add(yaoPinDaoGouListObjects.get(i));
						}
						if (yaoPinDaoGouListObjects.size() < Const.PAGEROWS) {
							if(curPage!=1){
								Tools.showMsg(YaoPinDaoGouActivity.this,
										Tools.LOAD_ALL);
							}
						} else {
							mPullRefreshListView.setMode(Mode.BOTH);
							// pageNum = pageNum + 1;
						}
						if (adapter == null) {
							adapter = new YaoPinDaoGouListAdapter(
									YaoPinDaoGouActivity.this, lists);
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
}
