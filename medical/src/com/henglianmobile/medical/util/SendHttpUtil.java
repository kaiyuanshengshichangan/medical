package com.henglianmobile.medical.util;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 网络请求处理类
 * 
 * @author Administrator
 * 
 */
public class SendHttpUtil {
	/**
	 * 处理统一的返回值为0和1的网络请求
	 * 
	 * @param url
	 * @param context
	 * @param message1
	 * @param message2
	 */
	public static void submitHttpGet(String url, final Context context,
			final String message1, final String message2) {
		LogUtil.i("url", "PublishYongYaoLiShiActivity---url=" + url);
		HttpUtil.get(url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					try {
						JSONObject jsonObject = new JSONObject(responseString);
						int response = Integer.parseInt(jsonObject
								.getString("response"));
						if (response == 0) {
							Tools.showMsg(context, message1);
						} else if (response > 0) {
							//
							Tools.showMsg(context, message2);
							((Activity) context).finish();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(context, Tools.HTTP_ERROR);
			}
		});
	}

	public static void submitHttpPost(String url, RequestParams params,
			final Context context, final String message1, final String message2) {
		LogUtil.i("url", "PublishYongYaoLiShiActivity---url=" + url);
		HttpUtil.post(url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				if (statusCode == 200) {
					LogUtil.i("hck", responseString);
					int response = Integer.parseInt(responseString);
					if (response == 0) {
						Tools.showMsg(context, message1);
					} else if (response > 0) {
						//
						Tools.showMsg(context, message2);
						((Activity) context).finish();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Tools.showMsg(context, Tools.HTTP_ERROR);
			}
		});
	}
}
