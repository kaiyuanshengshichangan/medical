package com.henglianmobile.medical.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * ͨ��async��ܷ���http����
 * @author Administrator
 *
 */
public class HttpUtil {
	private static AsyncHttpClient client = new AsyncHttpClient();
	/**ʵ�������� */   
	static {
		client.setTimeout(11000); //�������ӳ�ʱ����������ã�Ĭ��Ϊ10s    
	}

	/**��һ������url��ȡһ��string���� */  
	public static void get(String urlString, AsyncHttpResponseHandler res) {

		client.get(urlString, res);
	}

	//url���������
	public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	//url���������
	public static void post(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.post(urlString, params, res);
	}
	//������������ȡjson�����������   
	public static void get(String urlString, JsonHttpResponseHandler res) {
		client.get(urlString, res);
	}

	//����������ȡjson����������� 
	public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	//��������ʹ�ã��᷵��byte����   
	public static void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}

	public static AsyncHttpClient getClient() {
		return client;
	}
}