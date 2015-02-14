package com.henglianmobile.medical.adapter.patient;

import java.util.ArrayList;
import java.util.List;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientListObject;
import com.henglianmobile.medical.entity.patient.YongYaoLiShiListObject;
import com.henglianmobile.medical.ui.activity.HuanyouPagerActivity;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YongYaoLiShiListAdapter extends BaseAdapter{

	private Context context;
	private List<YongYaoLiShiListObject> list;
	private LayoutInflater mInflater;
	
	private void setData(List<YongYaoLiShiListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<YongYaoLiShiListObject>();
		}
	}

	public YongYaoLiShiListAdapter(Context context, List<YongYaoLiShiListObject> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);
		LogUtil.i("info", "list.size===="+list.size());

	}

	public void changeData(List<YongYaoLiShiListObject> list){
		if(list!=null){
			LogUtil.i("info", "list.size===="+list.size());
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
	public YongYaoLiShiListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnID();
	}
	//private TextView ,,tv_publish_time;
	//private ImageView ,,,
	//					,,,;
	//private GridView ;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_patient_yongyaolishi_list_item, null);
			holder.tv_drug_used = (TextView) convertView.findViewById(R.id.tv_drug_used);
			holder.tv_use_time = (TextView) convertView .findViewById(R.id.tv_use_time);

			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		YongYaoLiShiListObject yongYaoLiShiListObject = list.get(position);
		LogUtil.i("info", "list.size===="+yongYaoLiShiListObject.toString());
		holder.tv_drug_used.setText(yongYaoLiShiListObject.getDcMakePast());
		holder.tv_use_time.setText(yongYaoLiShiListObject.getDtEditTime());
		
		return convertView;
	}
	class ViewHolder {
		private TextView tv_drug_used,tv_use_time;
	}
}
