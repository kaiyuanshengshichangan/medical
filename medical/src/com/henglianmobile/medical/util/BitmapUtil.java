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
	 * 处理五角星评分显示，裁剪图片
	 * @param bitmap
	 * @param scor
	 * @return
	 */
	public static Bitmap cutBitmap(Bitmap bitmap, float scor) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap bitmapResult = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);

		// 创建画布
		Canvas canvas = new Canvas(bitmapResult);

		float w = scor / 5 * (float) width;
		if (w == 0) {
			w += 1;
		}

		// 下面这句是关键,剪裁图片
		bitmap = Bitmap
				.createBitmap(bitmap, 0, 0, (int) w, height, null, false);
		// 将图片按照坐标0,0 去拼接到画布
		canvas.drawBitmap(bitmap, 0, 0, null);

		return bitmapResult;
	}
	/**
	 * 压缩图片分辨率
	 * 
	 * @param image
	 *            图片对象
	 * @param width
	 *            标准宽度
	 * @param height
	 *            标准高度
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
			//现在主流手机比较多是800*480分辨率
			ww = 480f;
			hh = 800f;
		} else {
			hh = width;
			ww = height;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
			baos.reset();//重置baos即清空baos  
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了  
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
		int be = (int) ((w / ww + h / hh) / 2);
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例  
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;//压缩好比例大小后再进行质量压缩  
	}
}
