package com.henglianmobile.medical.adapter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.ui.activity.HuanyouPagerActivity;
import com.henglianmobile.medical.util.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPagerAdapter extends PagerAdapter {
	private List<String> pics ;
	private LayoutInflater inflater;
	private Context context;
	
	private final static String ALBUM_PATH  
    = Environment.getExternalStorageDirectory() + "/download_test/";  
	private ProgressDialog mSaveDialog = null;  
	private Bitmap mBitmap;  
	private String mFileName;  
	private String mSaveMessage;
	String[] items = new String[] { "保存图片", "投诉" };
	
	private void setListData(List<String> pics){
		if(pics == null){
			this.pics = new ArrayList<String>();
		}else{
			this.pics = pics;
		}
	}
	    

	public MyPagerAdapter(Context context, List<String> pics) {
		this.setListData(pics);
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		LogUtil.i("info", "Url + pics.toString==="+ pics.toString());
	}

	@Override
	public int getCount() {
		return pics.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0.equals(arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	private int position;
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View imageLayout = inflater.inflate(R.layout.item_pager_image,
				container, false);
		assert imageLayout != null;
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
		
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((HuanyouPagerActivity)context).finish();
			}
		});
		new Thread(connectNet).start();  
		imageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				Dialog();
				return false;
			}
		});
		
		
//		final ProgressBar spinner = (ProgressBar) imageLayout
//				.findViewById(R.id.loading);
		
		ImageLoader.getInstance().displayImage( pics.get(position),
				imageView, TApplication.optionsImage);
		this.position=position;
		System.out.println("filePath@=" + pics.get(position));

		container.addView(imageLayout, 0);
		return imageLayout;
	}
	
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
	public void Dialog(){
		new AlertDialog.Builder(this.context)
		.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				switch (arg1) {
				case 0:
					mSaveDialog = ProgressDialog.show(context, "保存图片", "图片正在保存中，请稍等...", true);  
	                new Thread(saveFileRunnable).start(); 
					break;
				case 1:
//					Intent intent=new Intent(context, ComplainTousu.class);
//					context.startActivity(intent);
					break;
				default:
					break;
				}
			}
			
		}).setPositiveButton("取消", null).show();
	}
	
	
    /** 
     * Get image from newwork 
     * @param path The path of image 
     * @return InputStream 
     * @throws Exception 
     */  
    public InputStream getImageStream(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return conn.getInputStream();  
        }  
        return null;  
    }  
   
    /** 
     * 保存文件 
     * @param bm 
     * @param fileName 
     * @throws IOException 
     */  
    public void saveFile(Bitmap bm, String fileName) throws IOException {  
        File dirFile = new File(ALBUM_PATH);  
        if(!dirFile.exists()){  
            dirFile.mkdir();  
        }  
        File myCaptureFile = new File(ALBUM_PATH + fileName);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
    }  
  
    private Runnable saveFileRunnable = new Runnable(){  
        @Override  
        public void run() {  
            try {  
                saveFile(mBitmap, mFileName);  
                mSaveMessage = "图片保存成功！"; 
                System.out.println("mFileName2="+mFileName);
            } catch (IOException e) {  
                mSaveMessage = "图片保存失败！";  
                e.printStackTrace();  
            }  
            messageHandler.sendMessage(messageHandler.obtainMessage());  
        }  
  
    };  
    private Handler messageHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            mSaveDialog.dismiss();  
            Toast.makeText(context, mSaveMessage, Toast.LENGTH_SHORT).show();  
        }  
    };  
    /* 
     * 连接网络 
     * 由于在4.0中不允许在主线程中访问网络，所以需要在子线程中访问 
     */ 
    private int cou;
    private Runnable connectNet = new Runnable(){  
		@Override  
        public void run() {  
            try {  
                mFileName =  getDateString(new Date())+".jpg";
                System.out.println("mFileName1="+mFileName);
                String filePath = pics.get(position);
                System.out.println("filePath#="+filePath);
                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/  
                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));  
                //********************************************************************/  
            } catch (Exception e) {  
                //Toast.makeText(context,"无法链接网络！", 0).show();  
                e.printStackTrace();  
            }  
        }  
    };  
    /** 根据日期类型获取String类型的日期文本,默认为yyyy-M-d */
	public String getDateString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
		return sdf.format(date);
	}
}
