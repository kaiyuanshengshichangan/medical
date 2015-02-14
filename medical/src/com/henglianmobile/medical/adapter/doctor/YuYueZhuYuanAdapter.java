package com.henglianmobile.medical.adapter.doctor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientYuYueListObject;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.HttpUtil;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class YuYueZhuYuanAdapter extends BaseAdapter implements OnClickListener {
	private String userId;
	private Context context;
	private List<PatientYuYueListObject> list;
	private LayoutInflater mInflater;
	private Handler handler;

	private AlertDialog.Builder builder;

	private void setData(List<PatientYuYueListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<PatientYuYueListObject>();
		}
	}

	public YuYueZhuYuanAdapter(Context context,
			List<PatientYuYueListObject> list, String userid, Handler handler) {
		this.userId = userid;
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);
		this.handler = handler;
	}

	public void changeData(List<PatientYuYueListObject> list) {
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
	public PatientYuYueListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnMakeid();
	}

	ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_doctor_yuyuezhuyuan_list_item, null);
			holder.iv_patient_photo = (ImageView) convertView
					.findViewById(R.id.iv_patient_photo);
			holder.tv_patient_name = (TextView) convertView
					.findViewById(R.id.tv_patient_name);
			holder.tv_patient_add_time = (TextView) convertView
					.findViewById(R.id.tv_patient_add_time);
			holder.tv_zhuyuan_date = (TextView) convertView
					.findViewById(R.id.tv_zhuyuan_date);
			holder.tv_zhuyuan_time_start = (TextView) convertView
					.findViewById(R.id.tv_zhuyuan_time_start);
			holder.tv_have_bed = (TextView) convertView
					.findViewById(R.id.tv_have_bed);
			holder.tv_have_no_bed = (TextView) convertView
					.findViewById(R.id.tv_have_no_bed);
			holder.tv_chuangweihao = (TextView) convertView
					.findViewById(R.id.tv_chuangweihao);
			holder.tv_other = (TextView) convertView
					.findViewById(R.id.tv_other);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PatientYuYueListObject yuYueJiuZhenListObject = list.get(position);
		if (yuYueJiuZhenListObject != null) {
			holder.tv_patient_name.setText(yuYueJiuZhenListObject
					.getDcRealName());
			holder.tv_zhuyuan_date.setText(yuYueJiuZhenListObject
					.getStartDate());
			holder.tv_zhuyuan_time_start.setText(yuYueJiuZhenListObject
					.getStartHour());
			holder.tv_patient_add_time.setText(Tools
					.DateStrToDateStr(yuYueJiuZhenListObject.getDtAddTime()));
			holder.tv_other.setText(yuYueJiuZhenListObject.getDcOther());
			int dnIsAgree = yuYueJiuZhenListObject.getDnIsAgree();
			if (dnIsAgree == 1) {
				holder.tv_have_bed.setTextColor(Color.rgb(232, 84, 74));
				holder.tv_have_no_bed.setTextColor(Color.rgb(166, 166, 166));
				holder.tv_have_bed.setEnabled(false);
				holder.tv_have_no_bed.setEnabled(false);
			} else if (dnIsAgree == 2) {
				holder.tv_have_bed.setTextColor(Color.rgb(166, 166, 166));
				holder.tv_have_no_bed.setTextColor(Color.rgb(232, 84, 74));
				holder.tv_have_bed.setEnabled(false);
				holder.tv_have_no_bed.setEnabled(false);
			}
			String headImg = yuYueJiuZhenListObject.getDcHeadImg();
			ImageLoader.getInstance().displayImage(headImg,
					holder.iv_patient_photo, TApplication.optionsImage,
					new MyAnimateFirstDisplayListener());
			int mid = yuYueJiuZhenListObject.getDnMakeid();
			holder.tv_have_bed.setTag(R.string.mid, mid);
			holder.tv_have_bed.setTag(R.string.convert_view, convertView);

			holder.tv_have_no_bed.setTag(R.string.mid, mid);
			holder.tv_have_bed.setOnClickListener(this);
			holder.tv_have_no_bed.setOnClickListener(this);
			holder.tv_chuangweihao.setTag(convertView);
			holder.tv_chuangweihao.setOnClickListener(this);
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_patient_name, tv_zhuyuan_date,
				tv_zhuyuan_time_start, tv_have_bed, tv_have_no_bed,
				tv_patient_add_time, tv_chuangweihao, tv_other;
		private ImageView iv_patient_photo;
	}

	private int isagree;

	@Override
	public void onClick(View v) {
		String chuangweihao = "00--00--00";
		int mid = 0;
		String url = "";
		switch (v.getId()) {
		case R.id.tv_have_bed:// 同意（有号）
			mid = (Integer) v.getTag(R.string.mid);
			View view = (View) v.getTag(R.string.convert_view);
			chuangweihao = ((ViewHolder) view.getTag()).tv_chuangweihao
					.getText().toString();
			if (TextUtils.isEmpty(chuangweihao)) {
				Tools.showMsg(context, "请选择床位号!");
				return;
			}
			isagree = 1;
			url = Const.DOCTORSOLVEYUYUEJIUZHENLISTURL + "mid=" + mid
					+ "&isagree=" + isagree + "&bedNo=" + chuangweihao;
			LogUtil.i("info", "url===" + url);
			sendHttpGet(url);
			break;
		case R.id.tv_have_no_bed:// 拒绝(无号)
			mid = (Integer) v.getTag(R.string.mid);
			isagree = 2;
			url = Const.DOCTORSOLVEYUYUEJIUZHENLISTURL + "mid=" + mid
					+ "&isagree=" + isagree + "&bedNo=" + chuangweihao;
			LogUtil.i("info", "url===" + url);
			sendHttpGet(url);
			break;
		case R.id.tv_chuangweihao:// 选择床位号
			View view2 = (View) v.getTag();
			selectChuangWeiHao(view2);
			break;
		}

	}

	private void sendHttpGet(String url) {
		LogUtil.i("url", "YuYueJiuZhenAdapter---url=" + url);
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
							Tools.showMsg(context, "处理失败!");
						} else if (response == 1) {
							// 预约成功，跳转到支付页面
							Tools.showMsg(context, "处理成功!");
							handler.sendEmptyMessage(0);
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
				Tools.showMsg(context, Tools.HTTP_ERROR);
			}
		});
	}

	private void selectChuangWeiHao(final View view) {

		builder = new AlertDialog.Builder(context);
		View view1 = LayoutInflater.from(context).inflate(
				R.layout.select_chuangweihao_dialog, null);
		final EditText et_floor_number = (EditText) view1
				.findViewById(R.id.et_floor_number);
		final EditText et_room_number = (EditText) view1
				.findViewById(R.id.et_room_number);
		final EditText et_bed_number = (EditText) view1
				.findViewById(R.id.et_bed_number);
		builder.setTitle("请输入：楼号-房间号-床位号");
		builder.setView(view1);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String floorNum = et_floor_number.getText().toString().trim();
				String roomNum = et_room_number.getText().toString().trim();
				String bedNum = et_bed_number.getText().toString().trim();
				if (TextUtils.isEmpty(floorNum)) {
					Tools.showMsg(context, "请输入楼房号");
					return;
				}
				if (TextUtils.isEmpty(roomNum)) {
					Tools.showMsg(context, "请输入房间号");
					return;
				}
				if (TextUtils.isEmpty(bedNum)) {
					Tools.showMsg(context, "请输入床位号");
					return;
				}
				String chuangweihao = floorNum + "--" + roomNum + "--" + bedNum;
				((ViewHolder) view.getTag()).tv_chuangweihao
						.setText(chuangweihao);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
}
