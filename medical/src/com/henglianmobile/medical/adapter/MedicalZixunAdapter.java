package com.henglianmobile.medical.adapter;

import java.util.ArrayList;
import java.util.List;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.ZixunListObject;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MedicalZixunAdapter extends BaseAdapter {
	private Context context;
	private List<ZixunListObject> list;
	private LayoutInflater mInflater;
	
	private void setData(List<ZixunListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<ZixunListObject>();
		}
	}

	public MedicalZixunAdapter(Context context, List<ZixunListObject> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);

	}

	public void changeData(List<ZixunListObject> list){
		if(list!=null){
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
	public ZixunListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnNid();
	}

	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fragment_medicalzixun_list_item, null);
			holder.iv_zixun_image = (ImageView) convertView .findViewById(R.id.iv_zixun_image);
			holder.tv_zixun_title = (TextView) convertView .findViewById(R.id.tv_title);
			holder.tv_publish_time = (TextView) convertView.findViewById(R.id.tv_publish_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ZixunListObject zixunListObject = list.get(position);
		if (zixunListObject != null) {
			LogUtil.i("info", "name===="+zixunListObject.getDcNewTitle());
			holder.tv_zixun_title.setText(zixunListObject.getDcNewTitle());
			holder.tv_publish_time.setText(Tools.DateStrToDateStr(zixunListObject.getDtAddTime()));
			//ªÒ»°Õº∆¨
			String zixunPic = zixunListObject.getDcImgPath();
			LogUtil.i("info", "zixunPic===="+zixunPic);
			if (!TextUtils.isEmpty(zixunPic)) {
				ImageLoader.getInstance().displayImage(zixunPic,
						holder.iv_zixun_image, TApplication.optionsImage,new MyAnimateFirstDisplayListener());
			}
		}
		return convertView;
	}
	class ViewHolder {
		private TextView tv_zixun_title,tv_publish_time;
		private ImageView iv_zixun_image;
	}

}
