package com.henglianmobile.medical.adapter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.entity.PatientListObject;
import com.henglianmobile.medical.ui.activity.HuanyouPagerActivity;
import com.henglianmobile.medical.ui.activity.HuanyouPagerActivity1;
import com.henglianmobile.medical.util.LogUtil;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.henglianmobile.medical.util.Tools;
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

public class HuanyouquanListAdapter extends BaseAdapter{

	private Context context;
	private List<PatientListObject> list;
	private LayoutInflater mInflater;
	
	private void setData(List<PatientListObject> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<PatientListObject>();
		}
	}

	public HuanyouquanListAdapter(Context context, List<PatientListObject> list) {
		this.context = context;
		this.setData(list);
		this.mInflater = LayoutInflater.from(this.context);

	}

	public void changeData(List<PatientListObject> list){
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
	public PatientListObject getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getDnTid();
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
			convertView = mInflater.inflate(R.layout.fragment_huanyouquan_list_item, null);
			holder.iv_patient_photo = (ImageView) convertView .findViewById(R.id.iv_patient_photo);
			holder.iv_patient_pic = (ImageView) convertView .findViewById(R.id.iv_patient_pic);
			holder.iv_patient_pic1 = (ImageView) convertView .findViewById(R.id.iv_patient_pic1);
			holder.iv_patient_pic2 = (ImageView) convertView .findViewById(R.id.iv_patient_pic2);
			holder.iv_pinglun = (ImageView) convertView .findViewById(R.id.iv_pinglun);
			holder.tv_patient_nick = (TextView) convertView .findViewById(R.id.tv_patient_nick);
			holder.tv_patient_topic = (TextView) convertView.findViewById(R.id.tv_patient_topic);
			holder.tv_publish_time = (TextView) convertView .findViewById(R.id.tv_publish_time);
			holder.gv_patient_pics = (GridView) convertView.findViewById(R.id.gv_patient_pics);
			holder.ll_patient_pics = (LinearLayout) convertView.findViewById(R.id.ll_patient_pics);

			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PatientListObject patientListObject = list.get(position);
		if (patientListObject != null) {
			holder.tv_patient_nick.setText(patientListObject.getDcNickName());
			holder.tv_patient_topic.setText(patientListObject.getDcQuestion());
//			Date date = patientListObject.getDtAddTime();
			holder.tv_publish_time.setText(Tools.DateStrToDateStr(patientListObject.getDtAddTime()));
			//ªÒ»°Õº∆¨
			
			String photoPic = patientListObject.getDcHeadImg().trim();
			
			String topicPics = patientListObject.getDcIpath().trim();
			LogUtil.i("info", "topicPics===="+topicPics);
			List<String> pics = new ArrayList<String>();
			if(!"".equals(topicPics)){
				
				String[] topics = topicPics.split(",");
				for(int i=0;i<topics.length;i++){
					pics.add(topics[i]);
				}
				if(topics.length == 1){
					holder.iv_patient_pic.setVisibility(View.VISIBLE);
					holder.ll_patient_pics.setVisibility(View.GONE);
					holder.gv_patient_pics.setVisibility(View.GONE);
					String picUrlone = topics[0];
					ImageLoader.getInstance().displayImage(picUrlone,
							holder.iv_patient_pic, TApplication.optionsImage,new MyAnimateFirstDisplayListener());
					
					holder.iv_patient_pic.setTag(pics);
					holder.iv_patient_pic.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ArrayList<String> pics = (ArrayList<String>) v.getTag();
							Intent intent = new Intent(context, HuanyouPagerActivity.class);
							intent.putExtra("position", 0);
							LogUtil.i("info", "pics="+pics.toString()+",position="+0);
							intent.putStringArrayListExtra("pics", pics);
							context.startActivity(intent);
						}
					});
					
				}else if(topics.length == 2){
					holder.ll_patient_pics.setVisibility(View.VISIBLE);
					holder.iv_patient_pic.setVisibility(View.GONE);
					holder.gv_patient_pics.setVisibility(View.GONE);
					ImageLoader.getInstance().displayImage(topics[0],
							holder.iv_patient_pic1, TApplication.optionsImage,new MyAnimateFirstDisplayListener());
					ImageLoader.getInstance().displayImage(topics[1],
							holder.iv_patient_pic2, TApplication.optionsImage,new MyAnimateFirstDisplayListener());
					
					holder.iv_patient_pic1.setTag(pics);
					holder.iv_patient_pic1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ArrayList<String> pics = (ArrayList<String>) v.getTag();
							Intent intent = new Intent(context, HuanyouPagerActivity.class);
							intent.putExtra("position", 0);
							LogUtil.i("info", "pics="+pics.toString()+",position="+0);
							intent.putStringArrayListExtra("pics", pics);
							context.startActivity(intent);
						}
					});
					
					holder.iv_patient_pic2.setTag(pics);
					holder.iv_patient_pic2.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ArrayList<String> pics = (ArrayList<String>) v.getTag();
							Intent intent = new Intent(context, HuanyouPagerActivity.class);
							intent.putExtra("position", 1);
							LogUtil.i("info", "pics="+pics.toString()+",position="+1);
							intent.putStringArrayListExtra("pics", pics);
							context.startActivity(intent);
						}
					});
					
				}else if(topics.length >= 3){
					holder.gv_patient_pics.setVisibility(View.VISIBLE);
					holder.iv_patient_pic.setVisibility(View.GONE);
					holder.ll_patient_pics.setVisibility(View.GONE);
					
					GridViewAdapter adapter = new GridViewAdapter(context, pics);
					holder.gv_patient_pics.setAdapter(adapter);
					holder.gv_patient_pics.setTag(pics);
					holder.gv_patient_pics.setOnItemClickListener(new OnItemClickListener() {
						
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							ArrayList<String> pics = (ArrayList<String>) parent.getTag();
							Intent intent = new Intent(context, HuanyouPagerActivity1.class);
							intent.putExtra("position", position);
							LogUtil.i("info", "pics="+pics.toString()+",position="+position);
							intent.putStringArrayListExtra("pics", pics);
							context.startActivity(intent);
						}
					});
				}
			}else{
				holder.gv_patient_pics.setVisibility(View.GONE);
				holder.iv_patient_pic.setVisibility(View.GONE);
				holder.ll_patient_pics.setVisibility(View.GONE);
			}
			if (photoPic != null&&!TextUtils.isEmpty(photoPic)) {
				ImageLoader.getInstance().displayImage(photoPic,
						holder.iv_patient_photo, TApplication.optionsImage,new MyAnimateFirstDisplayListener());
			}
		}
		return convertView;
	}
	class ViewHolder {
		private TextView tv_patient_nick,tv_patient_topic,tv_publish_time;
		private ImageView iv_patient_photo,iv_patient_pic,iv_patient_pic1,iv_patient_pic2
							,iv_pinglun;
		private LinearLayout ll_patient_pics;
		private GridView gv_patient_pics;
	}
}
