package com.henglianmobile.medical.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;

public class BitmapUtil {

	/**
	 * ���������������ʾ���ü�ͼƬ
	 * @param bitmap
	 * @param scor
	 * @return
	 */
	public static Bitmap cutBitmap(Bitmap bitmap, float scor) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap bitmapResult = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);

		// ��������
		Canvas canvas = new Canvas(bitmapResult);

		float w = scor / 5 * (float) width;
		if (w == 0) {
			w += 1;
		}

		// ��������ǹؼ�,����ͼƬ
		bitmap = Bitmap
				.createBitmap(bitmap, 0, 0, (int) w, height, null, false);
		// ��ͼƬ��������0,0 ȥƴ�ӵ�����
		canvas.drawBitmap(bitmap, 0, 0, null);

		return bitmapResult;
	}
	/**
	 * ѹ��ͼƬ�ֱ���
	 * 
	 * @param image
	 *            ͼƬ����
	 * @param width
	 *            ��׼���
	 * @param height
	 *            ��׼�߶�
	 * @return
	 */
	public static Bitmap getBitmapFromUri(Context context, Uri uri, float width, float height) {
		ContentResolver cr = context.getContentResolver();
		Bitmap image = null;
		try {
			InputStream in = cr.openInputStream(uri);
			image = BitmapFactory.decodeStream(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (image == null) {
			return null;
		}
		float ww, hh;
		if (width < 0 || height <= 0) {
			//���������ֻ��Ƚ϶���800*480�ֱ���
			ww = 480f;
			hh = 800f;
		} else {
			hh = width;
			ww = height;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {//�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���    
			baos.reset();//����baos�����baos  
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��  
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		newOpts.inPreferredConfig = Config.RGB_565;//����ͼƬ��ARGB888��RGB565
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		//���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
		int be = (int) ((w / ww + h / hh) / 2);
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//�������ű���  
		//���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;//ѹ���ñ�����С���ٽ�������ѹ��  
	}
}
