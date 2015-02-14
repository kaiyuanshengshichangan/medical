package com.henglianmobile.medical.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.ui.activity.ShowBitPicActivity;
import com.henglianmobile.medical.util.BitmapUtil;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 医生资格认证
 * 
 * @author Administrator
 * 
 */
public class DoctorQualificationActivity extends BaseActivity {
	private Button btn_submit;
	private ImageView btn_back;
	private EditText et_name, et_id_card, et_hospital, et_indications,
			et_keshi, et_bank, et_account, et_account_name;
	private String name, idCard, hospital, indications,keshi,bank,account,accountName;
	private ImageView iv_medical_renzheng;
	private String[] items = new String[] { "相册", "拍照" };
	private String picStr = "";
	private int userId;
	private Bitmap bitmapFromUri;
	private Uri uri;
	private Dialog dialog;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_doctor_qualification);
		userId = getIntent().getIntExtra("userId", 0);
	}

	@Override
	public void initViews() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		et_name = (EditText) findViewById(R.id.et_name);
		et_id_card = (EditText) findViewById(R.id.et_id_card);
		et_hospital = (EditText) findViewById(R.id.et_hospital);
		et_indications = (EditText) findViewById(R.id.et_indications);
		et_keshi = (EditText) findViewById(R.id.et_keshi);
		et_bank = (EditText) findViewById(R.id.et_bank);
		et_account = (EditText) findViewById(R.id.et_account);
		et_account_name = (EditText) findViewById(R.id.et_account_name);
		iv_medical_renzheng = (ImageView) findViewById(R.id.iv_medical_renzheng);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		iv_medical_renzheng.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.iv_medical_renzheng:
			if(bitmapFromUri == null){
				publishPhotoDialog();
			}else {
				dialog = new Dialog(this, R.style.dialog);
				dialog.setContentView(R.layout.dialog_pic_choose);
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
				Window dialogWindow = dialog.getWindow();
				TextView tv_change = (TextView) dialogWindow
						.findViewById(R.id.tv_change);
				TextView tv_show_big_pic = (TextView) dialogWindow
						.findViewById(R.id.tv_show_big_pic);
				tv_change.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.cancel();
						publishPhotoDialog();
					}
				});
				tv_show_big_pic.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(DoctorQualificationActivity.this, ShowBitPicActivity.class);
						intent.putExtra("pic", uri);
						intent.putExtra("pictag", "pic");
						startActivity(intent);
						dialog.cancel();
					}
				});
			}
			break;
		case R.id.btn_submit:
			if (Check()) {
				submit();
			}
			break;

		default:
			break;
		}
	}

	private void publishPhotoDialog() {
		// 创建一个对话提示框
		new AlertDialog.Builder(this).setTitle("照片")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intentFromGallery = null;
						Intent intentFromCapture = null;

						switch (which) {
						// 从相册获取图片
						case 0:
							intentFromGallery = new Intent(Intent.ACTION_PICK,
									null);
							intentFromGallery
									.setDataAndType(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											"image/*");
							DoctorQualificationActivity.this
									.startActivityForResult(intentFromGallery,
											0);
							break;
						// 拍照存储到sd上，名字为image.jpg
						case 1:
							intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);

							if (hasSdCard()) {
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												"image.jpg")));

							}
							DoctorQualificationActivity.this
									.startActivityForResult(intentFromCapture,
											1);
							break;

						default:
							break;
						}
					}
				}).show();
	}

	/**
	 * 判断SDK的状态
	 */
	private boolean hasSdCard() {
		try {
			return Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 当按下返回键的时候，resultCode为0
		if (resultCode == 0) {
			return;
		}
		switch (requestCode) {
		// 从相册跳转过来的
		case 0:
			if (data != null) {
				uri = data.getData();
				LogUtil.i("info", "----");
				bitmapFromUri = BitmapUtil.getBitmapFromUri(this, uri,800,800);
				iv_medical_renzheng.setImageBitmap(bitmapFromUri);
				picStr = Tools.encodeBase64File(bitmapFromUri);
			}
			break;
		// 从相机跳转过来的，不需要data里面的数据。只需要startActivityForResult中传入的返回码进行switch即可
		case 1:
			// 此行代码的路径是自己提前定义好的路径。
			File temp = new File(Environment.getExternalStorageDirectory(),
					"/image.jpg");
			// 转换成Uri
			uri= Uri.fromFile(temp);
			LogUtil.i("info", "----"+uri.getPath());
			bitmapFromUri = BitmapUtil.getBitmapFromUri(this, uri,800,800);
			iv_medical_renzheng.setImageBitmap(bitmapFromUri);
			picStr = Tools.encodeBase64File(bitmapFromUri);
			break;
		default:
			break;
		}
	}

	/**
	 * 非空验证
	 */
	private boolean Check() {
		name = et_name.getText().toString().trim();
		idCard = et_id_card.getText().toString().trim();
		hospital = et_hospital.getText().toString().trim();
		indications = et_indications.getText().toString().trim();
		keshi = et_keshi.getText().toString().trim();
		bank = et_bank.getText().toString().trim();
		account = et_account.getText().toString().trim();
		accountName = et_account_name.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Tools.showMsg(this, "请输入真实姓名!");
			return false;
		}
		if (TextUtils.isEmpty(idCard)) {
			Tools.showMsg(this, "请输入身份证号!");
			return false;
		}
		if (idCard.length()!=18) {
			Tools.showMsg(this, "请输入的身份证号格式不正确，请重新输入!");
			return false;
		}
		if (TextUtils.isEmpty(hospital)) {
			Tools.showMsg(this, "请输入所属医院!");
			return false;
		}
		if (TextUtils.isEmpty(keshi)) {
			Tools.showMsg(this, "请输入所属科室!");
			return false;
		}
		if (TextUtils.isEmpty(indications)) {
			Tools.showMsg(this, "请输入主治内容!");
			return false;
		}
		if (TextUtils.isEmpty(picStr)) {
			Tools.showMsg(this, "请上传医师资格证书!");
			return false;
		}
		return true;
	}

	private void submit() {
		Tools.showProgressDialog(this, "正在提交信息,请稍后...");
//		String url = "http://192.168.3.8:8084/BllHandle/DoctorApp.ashx";
		String url = Const.DOCTORQUALIFICATIONURL;
		RequestParams params = new RequestParams();
//		params.add("uid", "1");
		params.add("uid", userId + "");
		params.add("rname", name);
		params.add("idcard", idCard);
		params.add("hospital", hospital);
		params.add("actions", indications);
		params.add("doctorcard", picStr);
		params.add("depart", keshi);
		params.add("bankName", bank);
		params.add("accountNo", account);
		params.add("accountName", accountName);
		submitHttp(url, params);
	}

	private void submitHttp(String url, RequestParams params) {
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(DoctorQualificationActivity.this, HTTP_ERROR);
				Tools.closeProgressDialog();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				LogUtil.i("hck", responseString);
				if (statusCode == 200) {
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(DoctorQualificationActivity.this,
								"信息提交失败!");
						Tools.closeProgressDialog();
					} else if (response == 1) {
						Tools.showMsg(DoctorQualificationActivity.this,
								"信息提交成功!");
						Tools.closeProgressDialog();
						//发送广播
						Intent intent = new Intent(Constant.ACTIONDOCTORQUALIFICATIONSUCCESS);
						DoctorQualificationActivity.this.sendBroadcast(intent);
						DoctorQualificationActivity.this.finish();
					}

				}
			}
		});
	}

	private void submitHttp(String url) {
		LogUtil.i("url", "DoctorQualificationActivity--url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

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
							Tools.showMsg(DoctorQualificationActivity.this,
									"信息提交失败!");
						} else if (response == 1) {
							Tools.showMsg(DoctorQualificationActivity.this,
									"信息提交成功!");
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
				Tools.showMsg(DoctorQualificationActivity.this, HTTP_ERROR);
			}
		});
	}

	/**
	 * 
	 * @param context
	 * @param uri
	 * @param imageView
	 *            通过imageView得到imageView控件的宽和高，进行缩放填充
	 * @return
	 */
	public Bitmap getBitmapFromUri(Context context, Uri uri, ImageView imageView) {
		ContentResolver cr = context.getContentResolver();

		try {
			InputStream in = cr.openInputStream(uri);
//			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inJustDecodeBounds = true;
//			BitmapFactory.decodeStream(in, null, options);
//			try {
//				in.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			int mWidth = options.outWidth;
//			int mHeight = options.outHeight;
//			int screenWidth = imageView.getWidth();
//			int screenHeight = imageView.getHeight();
//
//			float scale = 1;
//			if (mWidth > screenWidth || mHeight > screenHeight) {
//				if (mWidth / mHeight > screenWidth / screenHeight) {
//					scale = mWidth / (float) screenWidth;
//				} else {
//					scale = mHeight / (float) screenHeight;
//				}
//			}
//
//			options = new BitmapFactory.Options();
//			options.inSampleSize = Math.round(scale);

			in = cr.openInputStream(uri);
			
			Bitmap bitmap = BitmapFactory.decodeStream(in);
//			Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);

			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
}
