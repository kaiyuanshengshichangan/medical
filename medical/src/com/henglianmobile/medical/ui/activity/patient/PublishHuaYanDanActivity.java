package com.henglianmobile.medical.ui.activity.patient;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.adapter.patient.SelectHuaYanDanAdapter;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.henglianmobile.medical.util.Util;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class PublishHuaYanDanActivity extends BaseActivity {
	private Button btn_publish;
	private ImageView btn_back;
	private GridView gv_patient_huayandan_pics;
	private ArrayList<Map<String, String>> list;
	private SelectHuaYanDanAdapter adapter;
	private ImageButton ibDel;

	private ArrayList<String> listfile = new ArrayList<String>();
	private PublishHuaYanDanReceiver receiver;
	
	private String userId;
	
	/**图片文件列表*/
	private List<File> smallPicList;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_patient_publish_huayandan);
		userId = TApplication.getInstance().user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_publish = (Button) findViewById(R.id.btn_publish);
		gv_patient_huayandan_pics = (GridView) findViewById(R.id.gv_patient_huayandan_pics);

		// 构建适配器
		list = new ArrayList<Map<String, String>>();
		adapter = new SelectHuaYanDanAdapter(this, list);
		gv_patient_huayandan_pics.setAdapter(adapter);
		// 注册广播接收器
		receiver = new PublishHuaYanDanReceiver();
		IntentFilter filter = new IntentFilter(
				Constant.ACTIONHUAYANDANSELECTPHOTO);
		registerReceiver(receiver, filter);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_publish.setOnClickListener(this);
		gv_patient_huayandan_pics
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							final int position, long id) {
						ibDel = (ImageButton) view.findViewById(R.id.ib_del);
						LogUtil.i("info", "ibDel");
						if (ibDel.getVisibility() == View.GONE) {
							LogUtil.i("info", "ibDel=" + ibDel.getVisibility());
							ibDel.setVisibility(View.VISIBLE);
						} else {
							ibDel.setVisibility(View.GONE);
							LogUtil.i("info",
									"ibDel====" + ibDel.getVisibility());
						}
						ibDel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								list.remove(position);
								adapter.notifyDataSetChanged();
								ibDel.setVisibility(View.GONE);
							}
						});
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_publish:
			publishPic();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*
		 * 
		 * 因为两种方式都用到了startActivityForResult方法，
		 * 
		 * 这个方法执行完后都会执行onActivityResult方法， 所以为了区别到底选择了那个方式获取图片要进行判断，
		 * 
		 * 这里的requestCode跟startActivityForResult里面第二个参数对应
		 */

		LogUtil.i("info", "----//------------=" + requestCode + "------------"
				+ resultCode);
		if (requestCode == Constant.REQUEST_CAMERA && resultCode == RESULT_OK) {
			HashMap<String, String> map = new HashMap<String, String>();
			File tempOutFile = SelectHuaYanDanAdapter.tempOutFile;
			map.put("image_id", "");
			map.put("path", tempOutFile.getAbsolutePath());
			list.add(map);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(receiver);
	}

	/**
	 * 自定义广播接收器
	 * 
	 * @author Administrator
	 * 
	 */
	private class PublishHuaYanDanReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				if (bundle.getStringArrayList("files") != null) {
					listfile = bundle.getStringArrayList("files");
					for (int i = 0; i < listfile.size(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("image_id", "");
						map.put("path", listfile.get(i));
						list.add(map);
					}
					adapter.notifyDataSetChanged();
				}
			}
		}
	}
	private void publishPic() {
		new Thread() {
			public void run() {
				// 开始压缩
				if (compressPhoto()) {
					// 压缩成功,开始上传
					mHandler.sendEmptyMessage(MESSAGE_COMPRESS_SUCCESS);
				} else {
					mHandler.sendEmptyMessage(MESSAGE_COMPRESS_FAILD);
				}
			}
		}.start();
	}

	/**
	 * 压缩图片，需要异步执行
	 * 
	 * @return
	 */
	public boolean compressPhoto() {
		if (list != null) {
			smallPicList = new ArrayList<File>();
			// 压缩图片
			for (Map<String, String> map : list) {
				String path = map.get("path");
				File photoFile = new File(path);
				if (photoFile.exists()) {
					File newFile = new File(getCacheDir(), photoFile.getName());
					if (saveBitmapToFile(Util.getimage(path, 800, 800), newFile)) {
						smallPicList.add(newFile);
					} else {
						LogUtil.i("info", "photo compress failed,path is '"
								+ path + "'");
						return false;
					}
				} else {
					LogUtil.i("info", "photo is not exist,path is '" + path
							+ "'");
					return false;
				}
			}
		}
		return true;
	}

	//
	/**
	 * 保存Bitmap到文件
	 * 
	 * @param bm
	 * @param newFile
	 * @return
	 */
	public boolean saveBitmapToFile(Bitmap bm, File newFile) {
		if (newFile == null) {
			return false;
		}
		if (newFile.exists()) {
			newFile.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(newFile);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			LogUtil.i("info",
					"photo save success,path is '" + newFile.getAbsolutePath()
							+ "'");
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.i("info",
					"photo save failed,path is '" + newFile.getAbsolutePath()
							+ "'");
			return false;
		}
		return true;
	}
	private void uploadPhoto(int position) {
		File picFile = smallPicList.get(position);
		if (picFile == null || !picFile.exists()) {
			return;
		}
		// 开始上传
		String url = Const.UPLOADHUAYANDANANDTIJIANBAOGAOURL;
		RequestParams params = new RequestParams();
		try {
			params.put("uid", userId);
			params.put("types", Constant.HUAYANDAN);
			String picContent = Tools.encodeBase64File(picFile);
			params.put("imgpath", picContent);
			HttpUtil.post(url, params, new TextHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] arg1,
						String responseString) {
					if (statusCode == 200) {
						LogUtil.i("hck", responseString);
						int response = Integer.parseInt(responseString);
						if (response == 0) {
							mHandler.sendEmptyMessage(MESSAGE_UPLOAD_FAILD);
						} else if (response == 1) {
							Message message = new Message();
							message.what = MESSAGE_UPLOADING;
							currentUploadPos = currentUploadPos + 1;
							mHandler.sendMessage(message);
						}
					}
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, String arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub

					mHandler.sendEmptyMessage(MESSAGE_UPLOAD_FAILD);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private final int MESSAGE_COMPRESS_SUCCESS = 100;
	private final int MESSAGE_COMPRESS_FAILD = 99;
	private final int MESSAGE_UPLOADING = 201;
	private final int MESSAGE_UPLOAD_SUCCESS = 200;
	private final int MESSAGE_UPLOAD_FAILD = 199;
	private final int MESSAGE_POST_SUCCESS = 300;
	private final int MESSAGE_POST_FAILD = 299;
	private static int currentUploadPos = 0;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_COMPRESS_SUCCESS:
				currentUploadPos = 0;
				uploadPhoto(currentUploadPos);
				break;
			case MESSAGE_UPLOADING:
				if (currentUploadPos + 1 > list.size()) {
					mHandler.sendEmptyMessage(MESSAGE_POST_SUCCESS);
				} else {
					uploadPhoto(currentUploadPos);
				}
				break;
			case MESSAGE_POST_SUCCESS:
				Tools.closeProgressDialog();
				Tools.showMsg(PublishHuaYanDanActivity.this, "发布成功");
				PublishHuaYanDanActivity.this.finish();
				break;
			case MESSAGE_UPLOAD_FAILD:
				Tools.closeProgressDialog();
				Tools.showMsg(PublishHuaYanDanActivity.this, "上传失败");
				break;
			default:
				break;
			}
		}
	};
}
