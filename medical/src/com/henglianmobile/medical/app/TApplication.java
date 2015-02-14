package com.henglianmobile.medical.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.henglianmobile.medical.R;
import com.henglianmobile.medical.entity.LoginResultObject;
import com.henglianmobile.medical.entity.UserInfoDetailObject;
import com.henglianmobile.medical.parse.ParseXml;
import com.henglianmobile.medical.util.Const;
import com.henglianmobile.medical.util.LogUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class TApplication extends Application {

	public static TApplication instance;
	public UserInfoDetailObject userInfoDetailObject = new UserInfoDetailObject();
	public static Gson gson = new Gson();
	public static LoginResultObject user;
	/** ���ڻ״̬��activity���� */
	private static List<Activity> activeActivities;
	
	/** ����ͼƬ������ */
	public static int picsCount;
	/** DisplayImageOptions����������ͼƬ��ʾ���� */
	public static DisplayImageOptions optionsImage;
	
	/** �汾���� */
	public static int localVersion;// ���ذ�װ�汾
	public static String localVersionName;// ���ذ�װ�汾��
	public static int serverVersion;// �������汾
	public static String downloadDir = "medical/";// ��װĿ¼
	public static String downloadUrl;
	private HashMap<String, String> map = new HashMap<String, String>();

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			HashMap<String, String> map=new HashMap<String, String>();
			map=(HashMap<String, String>) msg.obj;
			serverVersion=Integer.valueOf(map.get("versionCode"));
			LogUtil.i("info", "serverVersion="+serverVersion);
			downloadUrl=map.get("url");
			LogUtil.i("info", "downloadUrl"+downloadUrl);
		};
	};
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		activeActivities = new ArrayList<Activity>();
		initImageLoader(getApplicationContext());
		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions
		optionsImage = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.load)
				.showImageForEmptyUri(R.drawable.mine_avatar)
				.showImageOnFail(R.drawable.mine_avatar).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).build();
		getLocationVersion();
		new Thread(){
			public void run() {
				try {
					//������ȡ�������ϵİ汾��Ϣ
					URL url=new URL(Const.APK_VERSION_URL);
					HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
					conn.setConnectTimeout(5000);  
					InputStream inputStream =conn.getInputStream();
					map=ParseXml.parseXml(inputStream);
					Message msg=Message.obtain();
					msg.obj=map;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			};
		}.start();
	}

	/**
	 * ��activity��ӵ�����
	 * 
	 * @param paramActivity
	 */
	public void addActivity(Activity paramActivity) {
		activeActivities.add(paramActivity);
	}

	/**
	 * �˳�����Activity
	 */
	public void exitActivities() {
		for (Activity activity : activeActivities) {
			activity.finish();
			LogUtil.i("info", "activity" + activity);
		}
		// ��Ӧ��ȫ���,��ȫ�˳�
		System.exit(0);
	}

	/**
	 * ��ȡTApplicationʵ��
	 * 
	 * @return
	 */
	public static TApplication getInstance() {
		return instance;
	}

	/**
	 * ����ImageLoader
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3)// �̳߳��ڼ��ص�����
				.threadPriority(Thread.NORM_PRIORITY - 2)// �����̵߳����ȼ�  
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(10 * 512 * 512).memoryCacheSize(10 * 512 * 512)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	/**
	 * ��ȡ�嵥�ļ��еİ汾��
	 */
	private void getLocationVersion() {
		try {
			//��ȡ�嵥�ļ��еİ汾��
			PackageInfo packageInfo = getApplicationContext()
					.getPackageManager().getPackageInfo(getPackageName(), 0);
			localVersion = packageInfo.versionCode;
			localVersionName=packageInfo.versionName;
			LogUtil.i("info", "localVersion=" + localVersion);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
