package com.henglianmobile.medical.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.henglianmobile.medical.crouton.Crouton;
import com.henglianmobile.medical.crouton.Style;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * ����Ϊһ�����࣬�����˸��ֳ��ù��÷���
 * 1�����ļ�תΪBase64�ַ���
 * 2����BitmapתΪBase64�ַ���
 * 3������Bitmap���ļ�
 * 4��������ת��Ϊһ����ʽ���ַ���
 * 5����һ����ʽ������ת��Ϊ������
 * 6����ʾ��ʾ��Ϣ
 * 7��������ʾ�Ի��򣬲�����������ť
 * 8����ʾѡ�����ڶԻ����ı�����
 * 9����ʾʱ��Ի��� ֻ��ʾʱ����
 * 10����ʾһ���������������Ի���
 * 11���رչ����������Ի���
 * 12��XXXX-XX-XX��ʽ�������ַ���ת�����ڼ�
 * @author Administrator
 * 
 */
public class Tools {
	public static final String HTTP_ERROR = "���粻ͨ����鿴�������绷���ٳ����ԣ�";
	public static final String LOAD_ALL = "�Ѿ�����ȫ�����ݣ�";

	/**
	 * ���ļ�תΪBase64�ַ���
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(File file) throws Exception {
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}

	/**
	 * ��BitmapתΪBase64�ַ���
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		// ��bitmap�������������֤ͼƬ����100%
		bitmap.compress(CompressFormat.PNG, 100, baos);
		// ת����byte����
		byte[] appicon = baos.toByteArray();// תΪbyte����
		// ��base64���뽫byte����ת�����ַ���
		return Base64.encodeToString(appicon, Base64.DEFAULT);

	}

	/**
	 * ����Bitmap���ļ�
	 * 
	 * @param bm
	 * @param newFile
	 * @return
	 */
	public static boolean saveBitmapToFile(Bitmap bm, File newFile) {
		if (newFile == null) {
			return false;
		}
		if (newFile.exists()) {
			newFile.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(newFile);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			LogUtil.i("info",
					"photo save success,path is '" + newFile.getAbsolutePath()
							+ "'");
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.i("info",
					"photo save failed,path is '" + newFile.getAbsolutePath()
							+ "'");
			return false;
		}
		return true;
	}

	/**
	 * ������ת��Ϊһ����ʽ���ַ���
	 * 
	 * @param date
	 * @return
	 */
	public static String DateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * ��һ����ʽ������ת��Ϊ������
	 * �磺Tools.StringDateToLong(startDate,"yyyy-MM-dd")
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long StringDateToLong(String date, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date).getTime();// ����
	}

	/**
	 * ��ʾ��ʾ��Ϣ
	 * 
	 * @param context
	 * @param text
	 */
	public static void showMsg(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ������ʾ�Ի��򣬲�����������ť
	 * 
	 * @param c
	 *            Ҫ������ҳ��
	 * @param title
	 *            ��������
	 * @param msg
	 *            ��ʾ����
	 * @param neutralBtnText
	 *            ȷ����ť����
	 * @param neutralBtnListen
	 *            ȷ����ť�������¼�
	 * @param egativeBtnText
	 *            ȡ����ť����
	 * @param egativeBtnListen
	 *            ȡ����ť�������¼�
	 */
	public static void showDialog(Context c, String title, String msg,
			String neutralBtnText, OnClickListener neutralBtnListen,
			String egativeBtnText, OnClickListener egativeBtnListen) {
		new AlertDialog.Builder(c).setTitle(title).setMessage(msg)
				.setNeutralButton(neutralBtnText, neutralBtnListen)
				.setNegativeButton(egativeBtnText, egativeBtnListen).show();

	}

	/**
	 * ��ʾѡ�����ڶԻ����ı�����
	 * 
	 * @param context
	 * @param textView
	 */
	public static void datePicker(Context context, final TextView textView) {
		Calendar c = Calendar.getInstance();

		// ���ڶԻ���
		DatePickerDialog dialog = // �̳���AlertDialog
		new DatePickerDialog(context, new OnDateSetListener() {

			// �ص�����
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				textView.setText(year
						+ "-"
						+ ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1)
								: (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth
										: dayOfMonth));
			}
		},// �ص�
				c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));

		// ��ʾ�Ի���
		dialog.show();
	}

	/**
	 * ��ʾʱ��Ի��� ֻ��ʾʱ����
	 * */
	public static void timePicker(Context context, final TextView textView) {
		Calendar c = Calendar.getInstance();
		TimePickerDialog d = new TimePickerDialog(context,
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {

						textView.setText((hourOfDay < 10 ? "0" + hourOfDay
								: hourOfDay)
								+ ":"
								+ (minute < 10 ? "0" + minute : minute));
					}
				}, // �ص�����
				c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
		d.show();
	}

	public static ProgressDialog progressDialog;

	/**
	 * ��ʾһ���������Ի���
	 * */
	public static void showProgressDialog(Context context, String message) {
		try {
			if (progressDialog == null) {
				progressDialog = ProgressDialog.show(context, null, message);
				// progressDialog.setCancelable(true);
			} else if (progressDialog != null) {
				if (!progressDialog.isShowing())
					progressDialog.show();
				progressDialog.setMessage(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �رչ����Ի���
	 */
	public static void closeProgressDialog() {
		try {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.cancel();
				progressDialog = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * XXXX-XX-XX��ʽ�������ַ���ת�����ڼ�
	 * 
	 * @param dateString
	 * @return
	 */
	public static String showWeek(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int weekofday = c.get(Calendar.DAY_OF_WEEK);
		String returnResault = "";
		switch (weekofday) {
		case 1:
			returnResault = "������";
			break;
		case 2:
			returnResault = "����һ";
			break;
		case 3:
			returnResault = "���ڶ�";
			break;
		case 4:
			returnResault = "������";
			break;
		case 5:
			returnResault = "������";
			break;
		case 6:
			returnResault = "������";
			break;
		case 7:
			returnResault = "������";
			break;
		}
		return returnResault;
	}

	/**
	 * �Է��ص����ڽ��д���
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String DateStrToDateStr(String dateStr) {
		if(dateStr!=null){
			String date = dateStr.substring(0, 10);
			String time = dateStr.substring(11, 19);
			
			return date + " " + time;
		}else{
			return "";
		}
	}
	public static void showCustomToast(Context context,String msg,ViewGroup viewGroup) {
		Crouton.makeText((Activity)context, msg, Style.ALERT, viewGroup).show();
	}
}
