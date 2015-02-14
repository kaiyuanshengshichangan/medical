package com.henglianmobile.medical.adapter.patient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.adapter.ChoosePhotoDlgAdapter;
import com.henglianmobile.medical.logic.ImgFileListActivity;
import com.henglianmobile.medical.ui.activity.PublishPatientStatusActivity;
import com.henglianmobile.medical.ui.activity.patient.PublishHuaYanDanActivity;
import com.henglianmobile.medical.ui.activity.patient.PublishTiJianBaoGaoActivity;
import com.henglianmobile.medical.util.Constant;
import com.henglianmobile.medical.util.LogUtil;

public class SelectTiJianBaoGaoAdapter extends BaseAdapter {
	private FinalBitmap fb;
	private Context mContext;
	private List<Map<String, String>> mList = null;
	private LayoutInflater inflater = null;
	public static File tempOutFile;
	public static final String DCIM = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
			.toString();
	public static final String DCIM_DIRECTORY = DCIM + "/Camera/";

	public SelectTiJianBaoGaoAdapter(Context context,
			List<Map<String, String>> list) {
		this.mContext = context;
		if (list == null) {
			mList = new ArrayList<Map<String, String>>();
		} else {
			this.mList = list;
		}
		inflater = LayoutInflater.from(mContext);
		fb = FinalBitmap.create(mContext);// 初始化FinalBitmap模块
		fb.configLoadingImage(R.drawable.ic_launcher);
		fb.configBitmapMaxWidth(100);
		fb.configBitmapMaxHeight(100);
		fb.configBitmapLoadThreadSize(10);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.item_result, null);
			holder = new Holder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.iv_friend_photo);
			holder.addButton = (Button) convertView.findViewById(R.id.btn_add);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.img.setVisibility(View.VISIBLE);
		holder.addButton.setVisibility(View.GONE);
		if (position == mList.size()) {
			holder.img.setVisibility(View.GONE);
			holder.addButton.setVisibility(View.VISIBLE);
			holder.addButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					List<String> list = new ArrayList<String>();
					list.add("从相册中选取");
					list.add("使用拍照");
					final Dialog dialog = new Dialog(mContext, R.style.dialog);
					dialog.setContentView(R.layout.dialog_choose);
					dialog.setCanceledOnTouchOutside(true);
					dialog.show();
					Window dialogWindow = dialog.getWindow();
					ListView listView = (ListView) dialogWindow
							.findViewById(R.id.listView);
					listView.setAdapter(new ChoosePhotoDlgAdapter(mContext,
							list));
					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							if (position == 1) {
								tempOutFile = new File(DCIM_DIRECTORY, "temp_"
										+ getDateString(new Date()) + ".jpg");
								LogUtil.i("pic", "-SelectTiJianBaoGaoAdapter-----------------"+tempOutFile.getAbsolutePath());
								Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
								getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempOutFile));
								((PublishTiJianBaoGaoActivity) mContext).startActivityForResult(getImageByCamera,
												Constant.REQUEST_CAMERA);
							} else {
								Intent intent = new Intent(mContext,
										ImgFileListActivity.class);
								intent.putExtra(Constant.PHOTOTAG, Constant.PIC_TIJIANBAOGAO);
								((PublishTiJianBaoGaoActivity) mContext).startActivity(intent);
							}
							dialog.dismiss();
						}
					});
				}
			});
		} else {
			final Map<String, String> map = mList.get(position);
			fb.display(holder.img, map.get("path"));
		}

		return convertView;
	}

	private class Holder {
		public ImageView img = null;
		public Button addButton = null;
	}

	/** 根据日期类型获取String类型的日期文本,默认为yyyy-M-d */
	public String getDateString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.US);
		return sdf.format(date);
	}

}
