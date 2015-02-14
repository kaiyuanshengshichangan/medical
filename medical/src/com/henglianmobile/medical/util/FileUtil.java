package com.henglianmobile.medical.util;
import java.io.File;
import java.io.IOException;

import android.os.Environment;

import com.henglianmobile.medical.app.TApplication;

public class FileUtil {
	public static File updateDir = null;
	public static File updateFile = null;

	/***
	 * �����ļ�
	 */
	public static void createFile(String name) {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			updateDir = new File(Environment.getExternalStorageDirectory()
					+ "/" + TApplication.downloadDir);
			updateFile = new File(updateDir + "/" + name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
