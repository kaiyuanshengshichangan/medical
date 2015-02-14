package com.henglianmobile.medical.ui.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.GridViewZiXunAdapter;
import com.henglianmobile.medical.adapter.MedicalZixunAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.ZixunDetailObject;
import com.henglianmobile.medical.entity.ZixunListObject;
import com.henglianmobile.medical.ui.fragment.MedicalZiXunFragment;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;

public class MedicalZiXunDetailActivity extends BaseActivity {
	private ImageView btn_back;
	private TextView tv_zixun_title, tv_zixun_date, tv_zixun_content,
			tv_comment_count;
	private GridView gv_zixun_pics;
	private ListView lv_comment;
	private ImageView iv_comment, iv_zan, iv_share;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = Const.GETMEDICALZIXUNDETAILURL + id;
		getHttpData(url);
	}

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_medicalzixun_detail);
		id = getIntent().getIntExtra("id", 0);
		LogUtil.i("url", "MedicalZiXunDetailActivity---id=" + id);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		tv_zixun_title = (TextView) findViewById(R.id.tv_rexiandingjia);
		tv_zixun_date = (TextView) findViewById(R.id.tv_zixun_date);
		tv_zixun_content = (TextView) findViewById(R.id.tv_zixun_content);
		tv_comment_count = (TextView) findViewById(R.id.tv_comment_count);
		gv_zixun_pics = (GridView) findViewById(R.id.gv_zixun_pics);
		lv_comment = (ListView) findViewById(R.id.lv_comment);
		iv_comment = (ImageView) findViewById(R.id.iv_comment);
		iv_zan = (ImageView) findViewById(R.id.iv_zan);
		iv_share = (ImageView) findViewById(R.id.iv_share);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		iv_comment.setOnClickListener(this);
		iv_zan.setOnClickListener(this);
		iv_share.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.iv_comment:

			break;
		case R.id.iv_zan:

			break;
		case R.id.iv_share:

			break;

		default:
			break;
		}
	}

	private void getHttpData(String url) {
		LogUtil.i("url", "MedicalZiXunDetailActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(MedicalZiXunDetailActivity.this, Tools.HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("res", "MedicalZiXunDetailActivity----res="
							+ responseString);
					Type type = new TypeToken<List<ZixunDetailObject>>() {
					}.getType();
					List<ZixunDetailObject> detailObjects = TApplication.gson.fromJson(responseString, type);
					ZixunDetailObject zixunDetailObject = detailObjects.get(0);
					showContent(zixunDetailObject);
				}
			}
		});
	}

	protected void showContent(ZixunDetailObject zixunDetailObject) {
		//tv_zixun_title, tv_zixun_date, tv_zixun_content
		tv_zixun_title.setText(zixunDetailObject.getDcNewTitle());
		tv_zixun_date.setText(zixunDetailObject.getDtEditTime());
		tv_zixun_content.setText(zixunDetailObject.getDcContent());
		String picUrls = zixunDetailObject.getDcIpath();
		List<String> pics = new ArrayList<String>();
		if(!TextUtils.isEmpty(picUrls)){
			String[] topics = picUrls.split(",");
			for(int i=0;i<topics.length;i++){
				pics.add(topics[i]);
			}
		}
		GridViewZiXunAdapter adapter = new GridViewZiXunAdapter(this, pics);
		gv_zixun_pics.setAdapter(adapter);
	}

}
