package com.henglianmobile.medical.ui.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.activity.LoginActivity;
import com.henglianmobile.medical.activity.MainActivity;
import com.henglianmobile.medical.adapter.GridViewAdapter;
import com.henglianmobile.medical.adapter.HuanyouquanPinglunAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.HuanyouquanPinglun;
import com.henglianmobile.medical.entity.LoginResultObject;
import com.henglianmobile.medical.entity.PatientListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.ListViewUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.SPUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HuanyouquanDetailActivity extends BaseActivity {
	private TextView tv_patient_nick, tv_patient_topic, tv_publish_time,
			tv_pinglun_count;
	private ImageView iv_patient_photo, iv_patient_pic, iv_patient_pic1,
			iv_patient_pic2, iv_pinglun;
	private LinearLayout ll_patient_pics,ll_pinglun;
	private GridView gv_patient_pics;
	private Button btn_send;
	private ImageView btn_back;
	private ListView lv_pinglun;
	private LinearLayout ll_submit_commit;
	private EditText et_comment;
	private int id;
	private PatientListObject patientListObject;
	private String userId;
	
	private ScrollView scrollView1;
	private LayoutParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		patientListObject = (PatientListObject) getIntent()
				.getSerializableExtra("patientListObject");
		id = patientListObject.getDnTid();
		userId = TApplication.getInstance().user.getUid();
		showContent(patientListObject);
		// 获取评论
		String url = Const.GETHUANYOUQUANPINGLUNURL + id;
		getDataHttp(url);
	}

	@Override
	public void loadLayout() {
		// 使软键盘不自动弹出
//		getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_huanyouquan_detail);
	}

	@Override
	public void initViews() {
		scrollView1 = (ScrollView)findViewById(R.id.scrollView1);
		iv_patient_photo = (ImageView) findViewById(R.id.iv_patient_photo);
		iv_patient_pic = (ImageView) findViewById(R.id.iv_patient_pic);
		iv_patient_pic1 = (ImageView) findViewById(R.id.iv_patient_pic1);
		iv_patient_pic2 = (ImageView) findViewById(R.id.iv_patient_pic2);
		iv_pinglun = (ImageView) findViewById(R.id.iv_pinglun);
		tv_patient_nick = (TextView) findViewById(R.id.tv_patient_nick);
		tv_patient_topic = (TextView) findViewById(R.id.tv_patient_topic);
		tv_publish_time = (TextView) findViewById(R.id.tv_publish_time);
		gv_patient_pics = (GridView) findViewById(R.id.gv_patient_pics);
		ll_patient_pics = (LinearLayout) findViewById(R.id.ll_patient_pics);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		lv_pinglun = (ListView) findViewById(R.id.lv_pinglun);
		et_comment = (EditText) findViewById(R.id.et_comment);
		ll_submit_commit = (LinearLayout) findViewById(R.id.ll_submit_commit);
		ll_pinglun = (LinearLayout) findViewById(R.id.ll_pinglun);
		btn_send = (Button) findViewById(R.id.btn_send);
		tv_pinglun_count = (TextView) findViewById(R.id.tv_pinglun_count);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		ll_pinglun.setOnClickListener(this);
		btn_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.ll_pinglun:
			if (ll_submit_commit.getVisibility() == View.GONE) {
				ll_submit_commit.setVisibility(View.VISIBLE);
				
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
//				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				
				params = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 0.9f);
				scrollView1.setLayoutParams(params);
			} else {
				ll_submit_commit.setVisibility(View.GONE);
				params = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f);
				scrollView1.setLayoutParams(params);
				
			}
			break;
		case R.id.btn_send:
			String comment = et_comment.getText().toString().trim();
			if (TextUtils.isEmpty(comment)) {
				Tools.showMsg(this, "发送内容不能为空!");
				return;
			}
			String url = Const.ADDHUANYOUQUANPINGLUNURL + "qid=" + id + "&uid="
					+ userId + "&answer=" + comment;
			sendHttpGet(url);
			break;

		}
	}

	private void sendHttpGet(String url) {
		LogUtil.i("url", "获取评论----url==" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String responseString) {
				if (arg0 == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						// int response = Integer.parseInt(jsonObject
						// .getString("response"));
						String response = jsonObject.getString("response");
						if ("0".equals(response)) {
							Tools.showMsg(HuanyouquanDetailActivity.this,
									"发送评论失败!");
						} else if ("1".equals(response)) {
							Tools.showMsg(HuanyouquanDetailActivity.this,
									"评论发送成功!");
							ll_submit_commit.setVisibility(View.GONE);
							params = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f);
							scrollView1.setLayoutParams(params);
							String url = Const.GETHUANYOUQUANPINGLUNURL + id;
							getDataHttp(url);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(HuanyouquanDetailActivity.this, HTTP_ERROR);
			}
		});
	}

	private void getDataHttp(String url) {
		LogUtil.i("url", "获取评论----url==" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String responseString) {
				if (arg0 == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<HuanyouquanPinglun>>() {
					}.getType();
					List<HuanyouquanPinglun> huanyouquanPingluns = TApplication.gson
							.fromJson(responseString, type);
					HuanyouquanPinglunAdapter adapter = new HuanyouquanPinglunAdapter(
							HuanyouquanDetailActivity.this, huanyouquanPingluns);
					lv_pinglun.setAdapter(adapter);
					ListViewUtil.setListViewHeightBasedOnChildren(lv_pinglun);
					tv_pinglun_count.setText(huanyouquanPingluns.size() + "");
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void showContent(PatientListObject patientListObject) {
		LogUtil.i("info", "name====" + patientListObject.getDcNickName());
		tv_patient_nick.setText(patientListObject.getDcNickName());
		tv_patient_topic.setText(patientListObject.getDcQuestion());
		tv_publish_time.setText(Tools.DateStrToDateStr(patientListObject
				.getDtAddTime()));
		// 获取图片

		String photoPic = patientListObject.getDcHeadImg();

		String topicPics = patientListObject.getDcIpath();
		LogUtil.i("info", "topicPics====" + topicPics);
		List<String> pics = new ArrayList<String>();
		if (!"".equals(topicPics)) {

			String[] topics = topicPics.split(",");
			for (int i = 0; i < topics.length; i++) {
				pics.add(topics[i]);
			}
			if (topics.length == 1) {
				iv_patient_pic.setVisibility(View.VISIBLE);
				ll_patient_pics.setVisibility(View.GONE);
				gv_patient_pics.setVisibility(View.GONE);
				String picUrlone = topics[0];
				ImageLoader.getInstance().displayImage(picUrlone,
						iv_patient_pic, TApplication.optionsImage,
						new MyAnimateFirstDisplayListener());

				iv_patient_pic.setTag(pics);
				iv_patient_pic.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ArrayList<String> pics = (ArrayList<String>) v.getTag();
						Intent intent = new Intent(
								HuanyouquanDetailActivity.this,
								HuanyouPagerActivity.class);
						intent.putExtra("position", 0);
						LogUtil.i("info", "pics=" + pics.toString()
								+ ",position=" + 0);
						intent.putStringArrayListExtra("pics", pics);
						HuanyouquanDetailActivity.this.startActivity(intent);
					}
				});

			} else if (topics.length == 2) {
				ll_patient_pics.setVisibility(View.VISIBLE);
				iv_patient_pic.setVisibility(View.GONE);
				gv_patient_pics.setVisibility(View.GONE);
				ImageLoader.getInstance().displayImage(topics[0],
						iv_patient_pic1, TApplication.optionsImage,
						new MyAnimateFirstDisplayListener());
				ImageLoader.getInstance().displayImage(topics[1],
						iv_patient_pic2, TApplication.optionsImage,
						new MyAnimateFirstDisplayListener());

				iv_patient_pic1.setTag(pics);
				iv_patient_pic1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ArrayList<String> pics = (ArrayList<String>) v.getTag();
						Intent intent = new Intent(
								HuanyouquanDetailActivity.this,
								HuanyouPagerActivity.class);
						intent.putExtra("position", 0);
						LogUtil.i("info", "pics=" + pics.toString()
								+ ",position=" + 0);
						intent.putStringArrayListExtra("pics", pics);
						HuanyouquanDetailActivity.this.startActivity(intent);
					}
				});

				iv_patient_pic2.setTag(pics);
				iv_patient_pic2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ArrayList<String> pics = (ArrayList<String>) v.getTag();
						Intent intent = new Intent(
								HuanyouquanDetailActivity.this,
								HuanyouPagerActivity.class);
						intent.putExtra("position", 1);
						LogUtil.i("info", "pics=" + pics.toString()
								+ ",position=" + 1);
						intent.putStringArrayListExtra("pics", pics);
						HuanyouquanDetailActivity.this.startActivity(intent);
					}
				});

			} else if (topics.length >= 3) {
				gv_patient_pics.setVisibility(View.VISIBLE);
				iv_patient_pic.setVisibility(View.GONE);
				ll_patient_pics.setVisibility(View.GONE);

				GridViewAdapter adapter = new GridViewAdapter(
						HuanyouquanDetailActivity.this, pics);
				gv_patient_pics.setAdapter(adapter);
				gv_patient_pics.setTag(pics);
				gv_patient_pics
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								ArrayList<String> pics = (ArrayList<String>) parent
										.getTag();
								Intent intent = new Intent(
										HuanyouquanDetailActivity.this,
										HuanyouPagerActivity.class);
								intent.putExtra("position", position);
								LogUtil.i("info", "pics=" + pics.toString()
										+ ",position=" + position);
								intent.putStringArrayListExtra("pics", pics);
								HuanyouquanDetailActivity.this
										.startActivity(intent);
							}
						});
			}
		}
		if (photoPic != null && !TextUtils.isEmpty(photoPic)) {
			ImageLoader.getInstance().displayImage(photoPic, iv_patient_photo,
					TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());
		}
	}
}
