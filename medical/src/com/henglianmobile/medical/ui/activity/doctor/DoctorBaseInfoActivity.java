package com.henglianmobile.medical.ui.activity.doctor;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doublefi123.diary.widget.CircularImage;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.activity.LoginActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.ui.activity.ShowBitPicActivity;
import com.henglianmobile.medical.ui.activity.UpdateMobileActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.SPUtil;
import com.henglianmobile.medical.util.Tools;
import com.henglianmobile.medical.util.UploadPhotoUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DoctorBaseInfoActivity extends BaseActivity {
	private Button btn_sure;
	private ImageView btn_back;
	private EditText et_name, et_age, et_keshi, et_hospital, et_content,
			et_zhuzhi;
	private String name, age, keshi, hospital, content, zhuzhi, userType;
	private ImageView iv_doctor_renzheng;
	private CircularImage iv_photo;
	private RelativeLayout rl_doctor_type,rl_update_mobile,rl_baochoutongji;
	private TextView tv_assistant_doctor, tv_attending_doctor,
			tv_co_chief_of_doctor, tv_chief_of_doctor, tv_academician,
			tv_userType, tv_logout,tv_mobile;
	private RadioGroup rg_sex;
	private RadioButton radioMan, radioFemale;
	private SelectUserTypePopupWindow popupWindow;
	private UserInfoDetailObject userInfoDetailObject;
	private int sex;
	private String userId;
	private String renzhengPath;
	
	private Intent intent;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_doctor_base_info);
		userInfoDetailObject = (UserInfoDetailObject) getIntent()
				.getSerializableExtra("user");
		userId = TApplication.user.getUid();
	}

	@Override
	public void initViews() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_age = (EditText) findViewById(R.id.et_age);
		et_keshi = (EditText) findViewById(R.id.et_keshi);
		et_zhuzhi = (EditText) findViewById(R.id.et_zhuzhi);
		et_hospital = (EditText) findViewById(R.id.et_hospital);
		et_content = (EditText) findViewById(R.id.et_content);
		iv_photo = (CircularImage) findViewById(R.id.iv_photo);
		iv_doctor_renzheng = (ImageView) findViewById(R.id.iv_doctor_renzheng);
		rl_doctor_type = (RelativeLayout) findViewById(R.id.rl_doctor_type);
		rl_update_mobile = (RelativeLayout) findViewById(R.id.rl_update_mobile);
		rl_baochoutongji = (RelativeLayout) findViewById(R.id.rl_baochoutongji);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		tv_userType = (TextView) findViewById(R.id.tv_userType);
		tv_logout = (TextView) findViewById(R.id.tv_logout);
		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
		radioMan = (RadioButton) findViewById(R.id.radio0);
		radioFemale = (RadioButton) findViewById(R.id.radio1);
	}

	@Override
	public void addListener() {
		btn_back.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
		iv_photo.setOnClickListener(this);
		rl_doctor_type.setOnClickListener(this);
		rl_update_mobile.setOnClickListener(this);
		rl_baochoutongji.setOnClickListener(this);
		tv_logout.setOnClickListener(this);
		iv_doctor_renzheng.setOnClickListener(this);
		rg_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0) {
					// 选择了男
					sex = 1;
				} else if (checkedId == R.id.radio1) {
					// 选择了女
					sex = 0;
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showContent();
	}

	private void showContent() {
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
		et_keshi.setText(userInfoDetailObject.getDcDepart());
		et_hospital.setText(userInfoDetailObject.getDcHospital());
		et_content.setText(userInfoDetailObject.getDcSign());
		et_zhuzhi.setText(userInfoDetailObject.getDcActions());
		tv_userType.setText(userInfoDetailObject.getDcPosition());
		tv_mobile.setText(userInfoDetailObject.getDcCellPhone());
		String photoPath = userInfoDetailObject.getDcHeadImg();
		ImageLoader.getInstance().displayImage(photoPath, iv_photo,
				TApplication.optionsImage, new MyAnimateFirstDisplayListener());
		renzhengPath = userInfoDetailObject.getDcDoctorCart();
		ImageLoader.getInstance().displayImage(renzhengPath,
				iv_doctor_renzheng, TApplication.optionsImage,
				new MyAnimateFirstDisplayListener());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.iv_photo:
			UploadPhotoUtil.publishPhotoDialog(DoctorBaseInfoActivity.this);
			break;
		case R.id.btn_sure:
			if (check()) {
				submit();
			}
			break;
		case R.id.rl_doctor_type:
			popupWindow = new SelectUserTypePopupWindow();
			popupWindow.showPopupWindow(rl_doctor_type);
			break;
		case R.id.tv_assistant_doctor:
			tv_userType.setText("助理医生");
			popupWindow.dismiss();
			break;
		case R.id.tv_attending_doctor:
			tv_userType.setText("主治医生");
			popupWindow.dismiss();
			break;
		case R.id.tv_co_chief_of_doctor:
			tv_userType.setText("副主任医生");
			popupWindow.dismiss();
			break;
		case R.id.tv_chief_of_doctor:
			tv_userType.setText("主任医生");
			popupWindow.dismiss();
			break;
		case R.id.tv_academician:
			tv_userType.setText("院士");
			popupWindow.dismiss();
			break;
		case R.id.rl_update_mobile:
			intent = new Intent(this, UpdateMobileActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_baochoutongji:
			intent = new Intent(this, BaoChouTongJiActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_doctor_renzheng:
			intent = new Intent(this, ShowBitPicActivity.class);
			intent.putExtra("renzhengPath", renzhengPath);
			intent.putExtra("pictag", "pic_url");
			startActivity(intent);
			break;
		case R.id.tv_logout:
			SPUtil.getUtil(this).saveToSp(SPUtil.PWD, "");
			intent = new Intent(this, LoginActivity.class);
			intent.putExtra("logout", "1");
			startActivity(intent);
			TApplication.getInstance().exitActivities();
			break;

		default:
			break;
		}
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
				Uri uri = data.getData();
				UploadPhotoUtil.startPhotoZoom(uri,DoctorBaseInfoActivity.this);
			}
			break;
		// 从相机跳转过来的，不需要data里面的数据。只需要startActivityForResult中传入的返回码进行switch即可
		case 1:
			// 此行代码的路径是自己提前定义好的路径。
			File temp = new File(Environment.getExternalStorageDirectory(),
					"/image.jpg");
			// 转换成Uri
			Uri fromFile = Uri.fromFile(temp);

			UploadPhotoUtil.startPhotoZoom(fromFile,DoctorBaseInfoActivity.this);
			break;
		case 2:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
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
	/**
	 * 保存裁剪之后的图片数据
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
		}
	}
	/**
	 * 上传头像
	 * @param picBase64Str
	 */
	private void UploadPhoto(String picBase64Str) {
		// String url = "http://192.168.3.8:8084/BllHandle/upuserimg.ashx?";
		String url = Const.UPLOADPHOTOURL;
		RequestParams params = new RequestParams();
		params.add("uid", userId);
		params.add("types", "1");
		params.add("imgpath", picBase64Str);
		LogUtil.i("url", "url=" + url + params.toString());
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				LogUtil.i("hck", "ZhangHaoGuanLiActivity---上传头像" + arg2);
				Tools.showMsg(DoctorBaseInfoActivity.this, HTTP_ERROR);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", "ZhangHaoGuanLiActivity---上传头像"
							+ responseString);
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(DoctorBaseInfoActivity.this, "头像上传失败!");
					} else if (response == 1) {
						Tools.showMsg(DoctorBaseInfoActivity.this, "头像上传成功!");
					}
				}
			}
		});
	}
	private boolean check() {
		name = et_name.getText().toString().trim();
		age = et_age.getText().toString().trim();
		keshi = et_keshi.getText().toString().trim();
		zhuzhi = et_zhuzhi.getText().toString().trim();
		hospital = et_hospital.getText().toString().trim();
		content = et_content.getText().toString().trim();
		userType = tv_userType.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Tools.showMsg(this, "请输入您的真实姓名!");
			return false;
		}
		if (TextUtils.isEmpty(age)) {
			Tools.showMsg(this, "请输入您的年龄!");
			return false;
		}
		if (TextUtils.isEmpty(keshi)) {
			Tools.showMsg(this, "请输入您所在的科室!");
			return false;
		}
		if (TextUtils.isEmpty(zhuzhi)) {
			Tools.showMsg(this, "请输入您主治的病症!");
			return false;
		}
		if (TextUtils.isEmpty(hospital)) {
			Tools.showMsg(this, "请输入您所在的医院!");
			return false;
		}
		if (TextUtils.isEmpty(userType)) {
			Tools.showMsg(this, "请选择您的从医资格!");
			return false;
		}
		if (TextUtils.isEmpty(content)) {
			Tools.showMsg(this, "请输入您的个人简介!");
			return false;
		}
		return true;
	}

	private void submit() {
		String url = Const.UPDATEUSERINFODETAILURL;
//				+ "uid=" + userId
//				+ "&sname=" + name 
//				+ "&sex=" + sex
//				+ "&age=" + age 
//				+ "&depart="+keshi
//				+ "&action="+zhuzhi
//				+ "&hospital=" + hospital 
//				+ "&position=" + userType 
//				+ "&sinfo=" + content
//				+ "&address=";
		RequestParams params = new RequestParams();
		params.put("uid", userId);
		params.put("sname", name);
		params.put("sex", sex);
		params.put("age", age);
		params.put("sinfo", content);
		params.put("address", "");
		params.put("action", zhuzhi);
		params.put("hospital", hospital);
		params.put("depart", keshi);
		params.put("position", userType);
		submitHttpPost(url,params);
	}
	private void submitHttpPost(String url,RequestParams params) {
		LogUtil.i("url", "DoctorBaseInfoActivity-submitHttpGet-url=" + url);
		HttpUtil.post(url,params, new TextHttpResponseHandler() {

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
							Tools.showMsg(DoctorBaseInfoActivity.this, "修改失败!");
						} else if (response == 1) {

							Tools.showMsg(DoctorBaseInfoActivity.this, "修改成功!");
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
				Tools.showMsg(DoctorBaseInfoActivity.this, HTTP_ERROR);
			}
		});
	}
	/**
	 * 自定义popupwindow ,,;
	 * 
	 * @author Administrator
	 * 
	 */
	class SelectUserTypePopupWindow extends PopupWindow {
		public SelectUserTypePopupWindow() {
			LayoutInflater inflater = (LayoutInflater) DoctorBaseInfoActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View conentView = inflater.inflate(R.layout.dialog_doctor_type,
					null);
			// , ,
			// , , ;
			tv_assistant_doctor = (TextView) conentView
					.findViewById(R.id.tv_assistant_doctor);
			tv_attending_doctor = (TextView) conentView
					.findViewById(R.id.tv_attending_doctor);
			tv_co_chief_of_doctor = (TextView) conentView
					.findViewById(R.id.tv_co_chief_of_doctor);
			tv_chief_of_doctor = (TextView) conentView
					.findViewById(R.id.tv_chief_of_doctor);
			tv_academician = (TextView) conentView
					.findViewById(R.id.tv_academician);
			tv_assistant_doctor.setOnClickListener(DoctorBaseInfoActivity.this);
			tv_attending_doctor.setOnClickListener(DoctorBaseInfoActivity.this);
			tv_co_chief_of_doctor
					.setOnClickListener(DoctorBaseInfoActivity.this);
			tv_chief_of_doctor.setOnClickListener(DoctorBaseInfoActivity.this);
			tv_academician.setOnClickListener(DoctorBaseInfoActivity.this);

			// 设置SelectPicPopupWindow的View
			this.setContentView(conentView);
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.MATCH_PARENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体可点击
			this.setFocusable(true);
			this.setOutsideTouchable(true);
			// 刷新状态
			this.update();
			// 实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0000000000);
			// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
			this.setBackgroundDrawable(dw);
			this.setAnimationStyle(R.style.popwin_anim_style);
		}

		/**
		 * 显示popupWindow
		 * 
		 * @param parent
		 */
		public void showPopupWindow(View parent) {
			if (!this.isShowing()) {
				// 以下拉方式显示popupwindow
				this.showAsDropDown(parent);
			} else {
				this.dismiss();
			}
		}
	}
}
