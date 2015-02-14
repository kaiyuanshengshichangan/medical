package com.henglianmobile.medical.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.util.Constant;

public class ImgFileListActivity extends BaseActivity implements OnItemClickListener{

	private ListView listView;
	private Util util;
	private ImgFileListAdapter listAdapter;
	private List<FileTraversal> locallist;
	/**选择照片的标记*/
	private String tag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView=(ListView) findViewById(R.id.listView1);
		util=new Util(this);
		locallist=util.LocalImgFileList();
		List<HashMap<String, String>> listdata=new ArrayList<HashMap<String,String>>();
		Bitmap bitmap[] = null;
		if (locallist!=null) {
			bitmap=new Bitmap[locallist.size()];
			for (int i = 0; i < locallist.size(); i++) {
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("filecount", locallist.get(i).filecontent.size()+"张");
				map.put("imgpath", locallist.get(i).filecontent.get(0)==null?null:(locallist.get(i).filecontent.get(0)));
				map.put("filename", locallist.get(i).filename);
				listdata.add(map);
			}
		}
		listAdapter=new ImgFileListAdapter(this, listdata);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent=new Intent(this,ImgsActivity.class);
		Bundle bundle=new Bundle();
		bundle.putParcelable("data", locallist.get(arg2));
		bundle.putString(Constant.PHOTOTAG, tag);
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loadLayout() {
		setContentView(R.layout.pic_imgfilelist);
		tag = getIntent().getStringExtra(Constant.PHOTOTAG);
	}
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}
	
}
