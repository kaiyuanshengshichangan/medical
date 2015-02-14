package com.henglianmobile.medical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henglianmobile.medical.R;


/**
 * @author admin
 * ��Ƭ����,�ϴ���Ƭʱ�������б�Adapter
 */
public class ChoosePhotoDlgAdapter extends BaseAdapter {

	public List<String> list;
	private Context context;
	LayoutInflater inflater;

	public ChoosePhotoDlgAdapter(Context context, List<String> list) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	public int getCount() {
		return list == null ? 0 : list.size();
	}

	public Object getItem(int location) {
		return list.get(location);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final String item = list.get(position);
		// Item��λ��
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_dialog, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(item);
		return convertView;
	}

	public final class ViewHolder {
		public TextView name;
	}
}