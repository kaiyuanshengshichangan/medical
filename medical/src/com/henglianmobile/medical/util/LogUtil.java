package com.henglianmobile.medical.util;

import android.util.Log;
/**
 * ��־ͳһ�������ڴ˽���־ͨ���ʼ����ͳ�ȥ
 * @author Administrator
 *
 */
public class LogUtil {
	public static void e(String tag,String msg){
		if(!Constant.ISRELEASE){
			Log.e(tag, msg);
		}
	}
	public static void i(String tag,Object msg){
		if(!Constant.ISRELEASE){
			Log.i(tag, String.valueOf(msg));
		}
	}
}
