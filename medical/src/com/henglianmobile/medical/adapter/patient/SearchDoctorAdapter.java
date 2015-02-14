package com.henglianmobile.medical.adapter.patient;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.patient.SearchDoctorBaseInfoObject;
import com.henglianmobile.medical.ui.activity.patient.GuahaoActivity;
import com.henglianmobile.medical.ui.activity.patient.PersonalDoctorActivity;
import com.henglianmobile.medical.ui.activity.patient.RexianActivity;
import com.henglianmobile.medical.ui.activity.patient.ZhuyuanActivity;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.SendHttpUtil;
import com.henglianmobile.medical.util.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SearchDoctorAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private List<SearchDoctorBaseInfoObject> list;
	private LayoutInflater mInflater;

	private AlertDialog.Builder builder;
	private AlertDialog dialog;

	private void setData(List<SearchDoctorBaseInfoObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<SearchDoctorBaseInfoObject>();
		}
	}

	public SearchDoctorAdapter(Context context,
			List<SearchDoctorBaseInfoObject> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);

	}

	public void changeData(List<SearchDoctorBaseInfoObject> list) {
		if (list != null) {
			LogUtil.i("info", "list.size====" + list.size());
			this.setData(list);
			this.notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public SearchDoctorBaseInfoObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnUserid();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_patient_search_doctor_list_item, null);
			holder.iv_doctor_photo = (ImageView) convertView
					.findViewById(R.id.iv_doctor_photo);
			holder.tv_doctor_name = (TextView) convertView
					.findViewById(R.id.tv_doctor_name);
			holder.tv_doctor_potision = (TextView) convertView
					.findViewById(R.id.tv_doctor_potision);
			holder.tv_hospital = (TextView) convertView
					.findViewById(R.id.tv_hospital);
			holder.tv_keshi = (TextView) convertView
					.findViewById(R.id.tv_keshi);
			holder.tv_doctor_action = (TextView) convertView
					.findViewById(R.id.tv_doctor_action);
			holder.tv_yuyue = (TextView) convertView
					.findViewById(R.id.tv_yuyue);
			// holder.tv_guahao_price = (TextView) convertView
			// .findViewById(R.id.tv_guahao_price);
			// holder.tv_personal_doctor_price = (TextView) convertView
			// .findViewById(R.id.tv_personal_doctor_price);
			// holder.tv_yuyuezhuyuan_price = (TextView) convertView
			// .findViewById(R.id.tv_yuyuezhuyuan);
			// holder.tv_rexian_price = (TextView) convertView
			// .findViewById(R.id.tv_rexian);
			holder.tv_tuijianzhishu = (TextView) convertView
					.findViewById(R.id.tv_tuijianzhishu);
			holder.tv_add_friend = (TextView) convertView
					.findViewById(R.id.tv_add_friend);
			// holder.ll_guahao = (LinearLayout) convertView
			// .findViewById(R.id.ll_guahao);
			// holder.ll_personal_doctor = (LinearLayout) convertView
			// .findViewById(R.id.ll_personal_doctor);
			// holder.ll_zhuyuan = (LinearLayout) convertView
			// .findViewById(R.id.ll_zhuyuan);
			// holder.ll_rexian = (LinearLayout) convertView
			// .findViewById(R.id.ll_rexian);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SearchDoctorBaseInfoObject doctorBaseInfoObject = list.get(position);
		if (doctorBaseInfoObject != null) {
			holder.tv_doctor_name.setText(doctorBaseInfoObject.getDcRealName());
			holder.tv_doctor_potision.setText("");
			holder.tv_hospital.setText(doctorBaseInfoObject.getDcHospital());
			holder.tv_keshi.setText(doctorBaseInfoObject.getDcDepart());
			holder.tv_doctor_action
					.setText(doctorBaseInfoObject.getDcActions());
			float guanhaoPrice = doctorBaseInfoObject.getDnRegPrice();
			float personalDoctorPrice = doctorBaseInfoObject.getDnOwnPrice();
			float yuyuezhuyuanPrice = doctorBaseInfoObject.getDnInHosPrice();
			float rexianPrice = doctorBaseInfoObject.getDnHotLinePrice();
			holder.tv_tuijianzhishu.setText(doctorBaseInfoObject.getDnHot());

			String photoPath = doctorBaseInfoObject.getDcHeadImg();
			ImageLoader.getInstance().displayImage(photoPath,
					holder.iv_doctor_photo, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());

			int dId = doctorBaseInfoObject.getDnUserid();
			holder.tv_yuyue.setTag(R.string.did, dId);
			holder.tv_yuyue.setTag(R.string.guahao, guanhaoPrice);
			holder.tv_yuyue.setTag(R.string.sirenyisheng, personalDoctorPrice);
			holder.tv_yuyue.setTag(R.string.zhuyuan, yuyuezhuyuanPrice);
			holder.tv_yuyue.setTag(R.string.rexian, rexianPrice);
			holder.tv_yuyue.setOnClickListener(this);

			holder.tv_add_friend.setTag(R.string.did, dId);
			holder.tv_add_friend.setOnClickListener(this);
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_doctor_name, tv_doctor_potision, tv_hospital,
				tv_keshi, tv_doctor_action, tv_yuyue, tv_tuijianzhishu,
				tv_add_friend;
		private ImageView iv_doctor_photo;
	}

	private RadioButton rbtn_jiuzhen, rbtn_zhuyuan, rbtn_rexian,
			rbtn_sirenyisheng;
	private RadioGroup rg_yuyue_type;
	private TextView tv_sure,tv_cancel;
	private int yuyueType=0;
	private Intent intent;

	@Override
	public void onClick(View v) {
		final int dId = (Integer) v.getTag(R.string.did);
		switch (v.getId()) {
		case R.id.tv_add_friend:// 添加好友
			if(TextUtils.isEmpty(TApplication.getInstance().userInfoDetailObject.getDcRealName())){
				Tools.showMsg(context, "请先进入账号管理，填写个人资料!");
				return ;
			}
			String url = Const.PATIENTADDDOCTORURL + "uid="
					+ TApplication.user.getUid() + "&duid=" + dId + "&types=5";
			SendHttpUtil.submitHttpGet(url, context, "添加失败", "添加成功");
			break;
		case R.id.tv_yuyue:// 预约
			if(TextUtils.isEmpty(TApplication.getInstance().userInfoDetailObject.getDcRealName())){
				Tools.showMsg(context, "请先进入账号管理，填写个人资料!");
				return ;
			}
			yuyueType = 0;
			final float ghPrice = (Float) v.getTag(R.string.guahao);
			final float pdPrice = (Float) v.getTag(R.string.sirenyisheng);
			final float zyPrice = (Float) v.getTag(R.string.zhuyuan);
			final float rxPrice = (Float) v.getTag(R.string.rexian);
			builder = new AlertDialog.Builder(context);
			View view = LayoutInflater.from(context).inflate(
					R.layout.dialog_yuyue, null);
			rg_yuyue_type = (RadioGroup) view.findViewById(R.id.rg_yuyue_type);
			rbtn_jiuzhen = (RadioButton) view.findViewById(R.id.rbtn_jiuzhen);
			rbtn_zhuyuan = (RadioButton) view.findViewById(R.id.rbtn_zhuyuan);
			rbtn_rexian = (RadioButton) view.findViewById(R.id.rbtn_rexian);
			rbtn_sirenyisheng = (RadioButton) view.findViewById(R.id.rbtn_sirenyisheng);
			tv_sure = (TextView) view.findViewById(R.id.tv_sure);
			tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
			rbtn_jiuzhen.setText("预约就诊"+ghPrice+"元");
			rbtn_zhuyuan.setText("预约住院"+zyPrice+"元");
			rbtn_rexian.setText("预约热线"+rxPrice+"元");
			rbtn_sirenyisheng.setText("私人医生"+pdPrice+"元/月");
			rg_yuyue_type
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							if (checkedId == R.id.rbtn_jiuzhen) {
								yuyueType = 0;//就诊
							} else if (checkedId == R.id.rbtn_zhuyuan) {
								yuyueType = 1;//住院
							} else if (checkedId == R.id.rbtn_rexian) {
								yuyueType = 2;//热线
							} else if (checkedId == R.id.rbtn_sirenyisheng) {
								yuyueType = 3;//私人医生
							}
						}
					});
			tv_cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			tv_sure.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(yuyueType == 0){
						intent = new Intent(context, GuahaoActivity.class);
						intent.putExtra("dId", dId);
						intent.putExtra("ghPrice", ghPrice);
						context.startActivity(intent);
					}else if(yuyueType == 1){
						intent = new Intent(context, ZhuyuanActivity.class);
						intent.putExtra("dId", dId);
						intent.putExtra("zyPrice", zyPrice);
						context.startActivity(intent);
					}else if(yuyueType == 2){
						intent = new Intent(context, RexianActivity.class);
						intent.putExtra("dId", dId);
						intent.putExtra("rxPrice", rxPrice);
						context.startActivity(intent);
					}else if(yuyueType == 3){
						intent = new Intent(context, PersonalDoctorActivity.class);
						intent.putExtra("dId", dId);
						intent.putExtra("pdPrice", pdPrice);
						context.startActivity(intent);
					}
					dialog.dismiss();
				}
			});
			builder.setView(view);
			dialog = builder.create();
			dialog.show();
			break;
		}
	}
}
