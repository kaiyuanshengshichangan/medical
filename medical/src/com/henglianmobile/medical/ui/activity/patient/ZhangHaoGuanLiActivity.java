package com.henglianmobile.medical.ui.activity.patient;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doublefi123.diary.widget.CircularImage;
import com.google.gson.reflect.TypeToken;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.activity.LoginActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.ui.activity.UpdateMobileActivity;
import com.henglianmobile.medical.ui.activity.UpdatePasswordActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.SPUtil;
import com.henglianmobile.medical.util.Tools;
import com.henglianmobile.medical.util.UploadPhotoUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ZhangHaoGuanLiActivity extends BaseActivity {
	private Button btn_sure;
	private ImageView btn_back;
	private EditText et_name, et_age, et_area, et_person_introduce;
	private String name, age, mobile, address, userIntroduce;
	private RelativeLayout rl_account_chongzhi, rl_update_password,
			rl_update_mobile;
	private TextView tv_account_money, tv_logout, tv_mobile;
	private CircularImage iv_photo;
	private RadioGroup rg_sex;
	private RadioButton radioMan, radioFemale;
	private String userId;
	private UserInfoDetailObject userInfoDetailObject;
	private int sex = 1;
	private Intent intent;

	// private String[] items = new String[] { "���", "����" };

	@Override
	public void loadLayout() {
		// ʹ����̲��Զ�����
		setContentView(R.layout.activity_patient_base_info);
		userId = TApplication.user.getUid();
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		tv_account_money = (TextView) findViewById(R.id.tv_account_money);
		tv_logout = (TextView) findViewById(R.id.tv_logout);
		et_name = (EditText) findViewById(R.id.et_name);
		et_age = (EditText) findViewById(R.id.et_age);
		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		et_area = (EditText) findViewById(R.id.et_area);
		et_person_introduce = (EditText) findViewById(R.id.et_person_introduce);
		rl_account_chongzhi = (RelativeLayout) findViewById(R.id.rl_account_chongzhi);
		rl_update_password = (RelativeLayout) findViewById(R.id.rl_update_password);
		rl_update_mobile = (RelativeLayout) findViewById(R.id.rl_update_mobile);
		iv_photo = (CircularImage) findViewById(R.id.iv_photo);
		rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
		radioMan = (RadioButton) findViewById(R.id.radio0);
		radioFemale = (RadioButton) findViewById(R.id.radio1);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
		tv_logout.setOnClickListener(this);
		rl_account_chongzhi.setOnClickListener(this);
		rl_update_password.setOnClickListener(this);
		rl_update_mobile.setOnClickListener(this);
		iv_photo.setOnClickListener(this);
		rg_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0) {
					// ѡ������
					sex = 1;
				} else if (checkedId == R.id.radio1) {
					// ѡ����Ů
					sex = 0;
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = Const.GETUSERINFODETAILURL + userId;
		getDataHttp(url);
		// ��ȡ��ǰ�˻����
		String accountUrl = Const.GETBALANCEURL + userId;
		getBalanceHttp(accountUrl);
	}

	private void getBalanceHttp(String accountUrl) {
		LogUtil.i("url", "ZhangHaoGuanLiActivity---getBalanceHttp="
				+ accountUrl);
		HttpUtil.get(accountUrl, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("res", "ZhangHaoGuanLiActivity---getBalanceHttp-res="
						+ arg2);
				Tools.showMsg(ZhangHaoGuanLiActivity.this, Tools.HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						double response = jsonObject.getDouble("response");
						tv_account_money.setText(response + "Ԫ");
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		});
	}

	private void getDataHttp(String url) {
		LogUtil.i("url", "ZhangHaoGuanLiActivity--url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					Type type = new TypeToken<List<UserInfoDetailObject>>() {
					}.getType();
					List<UserInfoDetailObject> userInfoDetailObjects = TApplication.gson
							.fromJson(responseString, type);
					userInfoDetailObject = userInfoDetailObjects.get(0);
					showContent(userInfoDetailObjects);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void showContent(List<UserInfoDetailObject> userInfoDetailObjects) {
		int isex = userInfoDetailObject.getDnSex();
		if (isex == 0) {
			radioMan.setChecked(false);
			radioFemale.setChecked(true);
		} else if (isex == 1) {
			radioMan.setChecked(true);
			radioFemale.setChecked(false);
		}
		et_name.setText(userInfoDetailObject.getDcRealName());
		et_age.setText(userInfoDetailObject.getDnAge() + "");
		tv_mobile.setText(userInfoDetailObject.getDcCellPhone());
		et_area.setText(userInfoDetailObject.getDcAddress());
		et_person_introduce.setText(userInfoDetailObject.getDcSign());
		String photoPath = userInfoDetailObject.getDcHeadImg();
		ImageLoader.getInstance().displayImage(photoPath, iv_photo,
				TApplication.optionsImage, new MyAnimateFirstDisplayListener());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_sure:
			if (check()) {
				submit();
			}
			break;
		case R.id.rl_update_mobile:
			intent = new Intent(this, UpdateMobileActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_account_chongzhi:
			intent = new Intent(this, ZhangHuChongZhiActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_update_password:
			intent = new Intent(this, UpdatePasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_logout:
			SPUtil.getUtil(this).saveToSp(SPUtil.PWD, "");
			intent = new Intent(this, LoginActivity.class);
			intent.putExtra("logout", "1");
			startActivity(intent);
			TApplication.getInstance().exitActivities();
			break;
		case R.id.iv_photo:
			UploadPhotoUtil.publishPhotoDialog(ZhangHaoGuanLiActivity.this);
			break;

		default:
			break;
		}
	}

	// /**
	// * ѡ����Ƭ����ʾ��
	// */
	// public void publishPhotoDialog() {
	//
	// // ����һ���Ի���ʾ��
	// new AlertDialog.Builder(this).setTitle("��Ƭ")
	// .setItems(items, new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// Intent intentFromGallery = null;
	// Intent intentFromCapture = null;
	//
	// switch (which) {
	// // ������ȡͼƬ
	// case 0:
	// intentFromGallery = new Intent(Intent.ACTION_PICK,
	// null);
	// intentFromGallery
	// .setDataAndType(
	// MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	// "image/*");
	// ZhangHaoGuanLiActivity.this.startActivityForResult(
	// intentFromGallery, 0);
	// break;
	// // ���մ洢��sd�ϣ�����Ϊimage.jpg
	// case 1:
	// intentFromCapture = new Intent(
	// MediaStore.ACTION_IMAGE_CAPTURE);
	//
	// if (hasSdCard()) {
	// intentFromCapture.putExtra(
	// MediaStore.EXTRA_OUTPUT,
	// Uri.fromFile(new File(Environment
	// .getExternalStorageDirectory(),
	// "image.jpg")));
	//
	// }
	// ZhangHaoGuanLiActivity.this.startActivityForResult(
	// intentFromCapture, 1);
	// break;
	//
	// default:
	// break;
	// }
	// }
	// }).show();
	// }
	//
	// /*
	// * �ж�SDK��״̬
	// */
	// private boolean hasSdCard() {
	// try {
	// return Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return false;
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// �����·��ؼ���ʱ��resultCodeΪ0
		if (resultCode == 0) {
			return;
		}
		switch (requestCode) {
		// �������ת������
		case 0:
			if (data != null) {
				Uri uri = data.getData();
				// System.out.println("5����" + uri);
				// Bitmap bitmapFromUri = getBitmapFromUri(this, uri,
				// iv_headimg);
				// setBitmapToImageView(bitmapFromUri, uri);
				// this.uri = uri;
				UploadPhotoUtil
						.startPhotoZoom(uri, ZhangHaoGuanLiActivity.this);
			}
			break;
		// �������ת�����ģ�����Ҫdata��������ݡ�ֻ��ҪstartActivityForResult�д���ķ��������switch����
		case 1:
			// ���д����·�����Լ���ǰ����õ�·����
			File temp = new File(Environment.getExternalStorageDirectory(),
					"/image.jpg");
			// ת����Uri
			Uri fromFile = Uri.fromFile(temp);
			// this.uri = fromFile;

			UploadPhotoUtil.startPhotoZoom(fromFile,
					ZhangHaoGuanLiActivity.this);
			break;
		case 2:
			/**
			 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� �ڼ���֮��������ֲ����⣬Ҫ���²ü�������
			 * ��ǰ����ʱ���ᱨNullException��С��ֻ ������ط����£���ҿ��Ը��ݲ�ͬ����ں��ʵ� �ط����жϴ����������
			 * 
			 */
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;
		}
	}

	// /**
	// * �ü�ͼƬ����ʵ��
	// *
	// * @param uri
	// */
	// private void startPhotoZoom(Uri uri) {
	// /*
	// * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
	// * yourself_sdk_path/docs/reference/android/content/Intent.html
	// * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����, ��ֱ�ӵ����ؿ�ģ�С����C C++
	// * ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô ��������...���
	// */
	// Intent intent = new Intent("com.android.camera.action.CROP");
	// intent.setDataAndType(uri, "image/*");
	// // �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
	// intent.putExtra("crop", "true");
	// // aspectX aspectY �ǿ�ߵı���
	// intent.putExtra("aspectX", 1);
	// intent.putExtra("aspectY", 1);
	// // outputX outputY �ǲü�ͼƬ���
	// intent.putExtra("outputX", 150);
	// intent.putExtra("outputY", 150);
	// intent.putExtra("return-data", true);
	// startActivityForResult(intent, 2);
	// }

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			iv_photo.setImageBitmap(photo);
			String picBase64Str = Tools.encodeBase64File(photo);
			UploadPhoto(picBase64Str);
			Tools.showProgressDialog(this, "�����ϴ�ͷ��");
		}
	}

	private void UploadPhoto(String picBase64Str) {
		// String url = "http://192.168.3.8:8084/BllHandle/upuserimg.ashx?";
		String url = Const.UPLOADPHOTOURL;
		RequestParams params = new RequestParams();
		params.add("uid", userId);
		// params.add("uid", "1");
		params.add("types", "1");
		params.add("imgpath", picBase64Str);
		LogUtil.i("url", "url=" + url + params.toString());
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("hck", "ZhangHaoGuanLiActivity---�ϴ�ͷ��" + arg2);
				Tools.showMsg(ZhangHaoGuanLiActivity.this, HTTP_ERROR);
				Tools.closeProgressDialog();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", "ZhangHaoGuanLiActivity---�ϴ�ͷ��"
							+ responseString);
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(ZhangHaoGuanLiActivity.this, "ͷ���ϴ�ʧ��!");
						Tools.closeProgressDialog();
					} else if (response == 1) {
						Tools.showMsg(ZhangHaoGuanLiActivity.this, "ͷ���ϴ��ɹ�!");
						Tools.closeProgressDialog();
					}
				}
			}
		});
	}

	private void UploadPhoto1(String picBase64Str) {
		String url = Const.UPLOADPHOTOURL + "uid=" + userId
				+ "&types=1&imgpath=" + picBase64Str;
		LogUtil.i("url", "ZhangHaoGuanLiActivity----url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(ZhangHaoGuanLiActivity.this, HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						int response = Integer.parseInt(jsonObject
								.getString("response"));
						if (response == 0) {
							Tools.showMsg(ZhangHaoGuanLiActivity.this,
									"ͷ���ϴ�ʧ��!");
						} else if (response == 1) {
							Tools.showMsg(ZhangHaoGuanLiActivity.this,
									"ͷ���ϴ��ɹ�!");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});
	}

	private boolean check() {
		// name,age,mobile,address,userIntroduce;
		name = et_name.getText().toString().trim();
		age = et_age.getText().toString().trim();
		mobile = tv_mobile.getText().toString().trim();
		address = et_area.getText().toString().trim();
		userIntroduce = et_person_introduce.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Tools.showMsg(this, "������������ʵ����!");
			return false;
		}
		if (TextUtils.isEmpty(age)) {
			Tools.showMsg(this, "��������������!");
			return false;
		}
		if (TextUtils.isEmpty(address)) {
			Tools.showMsg(this, "���������ĵ�ַ!");
			return false;
		}
		if (TextUtils.isEmpty(userIntroduce)) {
			Tools.showMsg(this, "���������ĸ������!");
			return false;
		}
		return true;
	}

	private void submit() {
		String url = Const.UPDATEUSERINFODETAILURL;
		// + "uid=" + userId
		// + "&sname=" + name
		// + "&sex=" + sex
		// + "&age=" + age
		// + "&sinfo=" + userIntroduce
		// + "&address=" + address
		// + "&action=&hospital=&depart=&position=";
		RequestParams params = new RequestParams();
		params.put("uid", userId);
		params.put("sname", name);
		params.put("sex", sex);
		params.put("age", age);
		params.put("sinfo", userIntroduce);
		params.put("address", address);
		params.put("action", "");
		params.put("hospital", "");
		params.put("depart", "");
		params.put("position", "");
		submitHttpPost(url, params);
	}

	private void submitHttpPost(String url, RequestParams params) {
		LogUtil.i("url", "ZhangHaoGuanLiActivity-submitHttpGet-url=" + url);
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				if (statusCode == 200) {
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(ZhangHaoGuanLiActivity.this, "�޸�ʧ��!");
					} else if (response == 1) {
						Tools.showMsg(ZhangHaoGuanLiActivity.this, "�޸ĳɹ�!");
						ZhangHaoGuanLiActivity.this.finish();
						// ���͹㲥
						Intent intent = new Intent(
								Constant.ACTIONUPDATEUSERINFOSUCCESS);
						ZhangHaoGuanLiActivity.this.sendBroadcast(intent);
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}
}
