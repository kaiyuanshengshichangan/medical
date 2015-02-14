package com.henglianmobile.medical.ui.activity;

import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.henglianmobile.medical.R;
import com.henglianmobile.medical.activity.BaseActivity;
import com.henglianmobile.medical.app.TApplication;
import com.henglianmobile.medical.util.MyAnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShowBitPicActivity extends BaseActivity {

	private ImageView iv_picture;
	private String renzhengPath;
	private String pictag;
	private Uri uri;

	@Override
	public void loadLayout() {
		setContentView(R.layout.activity_show_picture);
		pictag = getIntent().getStringExtra("pictag");
	}

	@Override
	public void initViews() {
		iv_picture = (ImageView) findViewById(R.id.iv_picture);
		if (pictag.equals("pic")) {
			uri = getIntent().getParcelableExtra("pic");
			Bitmap bitmapFromUri = getBitmapFromUri(this, uri);
			iv_picture.setImageBitmap(bitmapFromUri);
		} else if (pictag.equals("pic_url")) {
			renzhengPath = getIntent().getStringExtra("renzhengPath");
			ImageLoader.getInstance().displayImage(renzhengPath, iv_picture,
					TApplication.optionsImage, new MyAnimateFirstDisplayListener());
		}
		iv_picture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShowBitPicActivity.this.finish();
			}
		});
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	public Bitmap getBitmapFromUri(Context context, Uri uri) {
		ContentResolver cr = context.getContentResolver();

		try {
			InputStream in = cr.openInputStream(uri);

			in = cr.openInputStream(uri);
			
			Bitmap bitmap = BitmapFactory.decodeStream(in);

			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
}
