package com.henglianmobile.medical.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat.Builder;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.MainActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.util.FileUtil;
import com.henglianmobile.medical.util.LogUtil;

/***
 * ���°汾
 * 
 * @author zhangjia
 * 
 */
public class UpdateApkService extends Service {
	private static final int TIMEOUT = 10 * 1000;// ��ʱ
	private static final int DOWN_UPDATE = 3;
	private static final int DOWN_OK = 2;
	private static final int DOWN_ERROR = 1;
	private static final int DOWM_START = 0;
	private String down_url;

	private String app_name;

	// private NotificationManager notificationManager;
	// private Notification notification;

	private NotificationManager manager;
	private Builder builder;

	private Intent updateIntent;
	private PendingIntent pendingIntent;

	private int notification_id = 0;
	private Handler handler;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		down_url = TApplication.downloadUrl;
		LogUtil.i("info", "down_url="+down_url);
		app_name = intent.getStringExtra("app_name");
		// �����ļ�
		FileUtil.createFile(app_name);

		createNotification();

		createThread();

		return super.onStartCommand(intent, flags, startId);

	}

	/***
	 * ���߳�����
	 */
	public void createThread() {
		/***
		 * ����UI
		 */
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
					switch (msg.what) {
					case DOWM_START:
						builder.setContentText("��ʼ����...");
//								.setProgress(100, 0, false).setTicker("��ʼ����!");
						manager.notify(1, builder.build());
						break;
					case DOWN_OK:
						// ������ɣ������װ
						Uri uri = Uri.fromFile(FileUtil.updateFile);
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(uri,
								"application/vnd.android.package-archive");

						pendingIntent = PendingIntent.getActivity(
								UpdateApkService.this, 0, intent, 0);
						builder.setContentText("���سɹ��������װ")
								.setContentIntent(pendingIntent)
								.setProgress(0, 0, false)
								.setDefaults(Notification.DEFAULT_ALL)
								.setAutoCancel(true);
						manager.notify(1, builder.build());
						UpdateApkService.this.stopSelf();
						break;
					case DOWN_ERROR:
						// notification.setLatestEventInfo(UpdateService.this,
						// app_name, "����ʧ��", pendingIntent);
						builder.setContentText("����ʧ��").setAutoCancel(true)
								.setDefaults(Notification.DEFAULT_ALL);
						manager.notify(1, builder.build());
						UpdateApkService.this.stopSelf();
						// stopService(updateIntent);
						break;
					case DOWN_UPDATE:
						builder.setContentText("�����أ�" + msg.arg1 + "%")
								.setProgress(100, msg.arg1, false);
						manager.notify(1, builder.build());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					Message message = Message.obtain();
					message.what = DOWM_START;
					handler.sendMessage(message);
					long downloadSize = downloadUpdateFile(down_url,
							FileUtil.updateFile.toString());
					if (downloadSize > 0) {
						// ���سɹ�
						Message message1 = Message.obtain();
						message1.what = DOWN_OK;
						handler.sendMessage(message1);
					}

				} catch (Exception e) {
					e.printStackTrace();
					Message message2 = Message.obtain();
					message2.what = DOWN_ERROR;
					handler.sendMessage(message2);
				}
			}
		}).start();
	}

	/***
	 * ����֪ͨ��
	 */

	public void createNotification() {
		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(
				this, MainActivity.class), 0);
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		builder = new Builder(this)
				.setSmallIcon(android.R.drawable.stat_sys_download)
				.setContentTitle("������ʾ")
				.setLargeIcon(
						BitmapFactory.decodeResource(getResources(),
								R.drawable.ic_launcher)).setTicker("��ʾ")
				// .setDefaults(Notification.DEFAULT_ALL)
				.setContentIntent(intent).setAutoCancel(false);
	}
	/***
	 * �����ļ�
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public long downloadUpdateFile(String down_url, String file)
			throws Exception {
		int down_step = 1;// ��ʾstep
		int totalSize;// �ļ��ܴ�С
		int downloadCount = 0;// �Ѿ����غõĴ�С
		int updateCount = 0;// �Ѿ��ϴ����ļ���С
		InputStream inputStream;
		OutputStream outputStream;

		URL url = new URL(down_url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(TIMEOUT);
		httpURLConnection.setReadTimeout(TIMEOUT);
		// ��ȡ�����ļ���size
		totalSize = httpURLConnection.getContentLength();
		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");
		}
		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(file, false);// �ļ������򸲸ǵ�
		byte buffer[] = new byte[100];
		int readsize = 0;
		while ((readsize = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize;// ʱʱ��ȡ���ص��Ĵ�С
			/**
			 * ÿ������5%
			 */
			if (updateCount == 0
					|| (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				updateCount += down_step;
				Message msg = Message.obtain();
				msg.what = DOWN_UPDATE;
				msg.arg1 = updateCount;
				handler.sendMessage(msg);
			}

		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();

		return downloadCount;

	}

}
