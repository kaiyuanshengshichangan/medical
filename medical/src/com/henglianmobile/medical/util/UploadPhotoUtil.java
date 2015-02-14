package com.henglianmobile.medical.util;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
/**
 * �ϴ�ͷ�񹤾���
 * �����ü�ͼƬ
 * @author Administrator
 *
 */
public class UploadPhotoUtil {

	private static String[] items = new String[] { "���", "����" };
	/**
	 * ѡ����Ƭ����ʾ��
	 */
	public static void publishPhotoDialog(final Context context) {

		// ����һ���Ի���ʾ��
		new AlertDialog.Builder(context).setTitle("��Ƭ")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intentFromGallery = null;
						Intent intentFromCapture = null;

						switch (which) {
						// ������ȡͼƬ
						case 0:
							intentFromGallery = new Intent(Intent.ACTION_PICK,
									null);
							intentFromGallery
									.setDataAndType(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											"image/*");
							((Activity)context).startActivityForResult(
									intentFromGallery, 0);
							break;
						// ���մ洢��sd�ϣ�����Ϊimage.jpg
						case 1:
							intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);

							if (hasSdCard()) {
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												"image.jpg")));

							}
							((Activity)context).startActivityForResult(
									intentFromCapture, 1);
							break;

						default:
							break;
						}
					}
				}).show();
	}
	/*
	 * �ж�SDK��״̬
	 */
	private static boolean hasSdCard() {
		try {
			return Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public static void startPhotoZoom(Uri uri,Context context) {
		/*
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����, ��ֱ�ӵ����ؿ�ģ�С����C C++
		 * ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô ��������...���
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		((Activity)context).startActivityForResult(intent, 2);
	}
}
