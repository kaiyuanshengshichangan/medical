package com.henglianmobile.medical.adapter.patient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.entity.patient.YaoPinDaoGouListObject;
import com.henglianmobile.medical.util.LogUtil;

public class YaoPinDaoGouListAdapter extends BaseAdapter {
	private Context context;
	private List<YaoPinDaoGouListObject> list;
	private LayoutInflater mInflater;
	
	private void setData(List<YaoPinDaoGouListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<YaoPinDaoGouListObject>();
		}
	}

	public YaoPinDaoGouListAdapter(Context context, List<YaoPinDaoGouListObject> list) {
		LogUtil.i("info", "list.size="+list.size());
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);

	}

	public void changeData(List<YaoPinDaoGouListObject> list){
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
	public YaoPinDaoGouListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getId();
	}

	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_patient_yaopindaogou_list_item, null);
			holder.tv_drugName = (TextView) convertView .findViewById(R.id.tv_drugName);
			holder.tv_addtime = (TextView) convertView.findViewById(R.id.tv_addtime);
			holder.tv_get_drug = (TextView) convertView .findViewById(R.id.tv_get_drug);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		YaoPinDaoGouListObject yaoPinDaoGouListObject = list.get(position);
		if (yaoPinDaoGouListObject != null) {
			holder.tv_drugName.setText(yaoPinDaoGouListObject.getDrugName());
			holder.tv_addtime.setText(yaoPinDaoGouListObject.getAddTime());
			int id = yaoPinDaoGouListObject.getId();
			holder.tv_get_drug.setTag(id);
			holder.tv_get_drug.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int id = (Integer) v.getTag();
					
				}
			});
		}
		return convertView;
	}
	class ViewHolder {
		private TextView tv_drugName,tv_addtime,tv_get_drug;
	}

}
