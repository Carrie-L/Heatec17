package com.adsale.HEATEC.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.view.TitleView;
import com.polites.android.GestureImageView;
import com.polites.android.GestureImageViewListener;

import sanvio.libs.util.FileUtils;

public class ScaleImageActivity extends AppCompatActivity {
	private GestureImageView image;
	private String mPath = "";
	private String mUrl = "";
	private String mImg = "";
	private Bitmap mBitmap;

	private TitleView titleView;
	private ProgressBar progressBar;

	private int mMaxWidth, mMaxHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_scale_image);

		image = (GestureImageView) findViewById(R.id.image);
		titleView = (TitleView) findViewById(R.id.titleView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		titleView.setTitle("");
		titleView.setOnBackListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ScaleImageActivity.this.finish();
			}
		});

		image.setScaleType(ScaleType.CENTER_INSIDE);

		Intent i = getIntent();
		if (i != null) {
			mPath = i.getStringExtra("Path");
			mUrl = i.getStringExtra("Url");
			mImg = i.getStringExtra("Img");
		}

		final View view = findViewById(R.id.view);
		view.post(new Runnable() {
			@Override
			public void run() {
				mMaxWidth = view.getWidth();
				mMaxHeight = view.getHeight();
				if (mPath != null && !mPath.equals("") && mImg != null && !mImg.equals("")) {
					if (new File(mPath + mImg).exists()) {
						new AsyncDowdLoad().execute("0");
					} else if (mUrl != null && !mUrl.equals(""))
						new AsyncDowdLoad().execute("1");
				}

			}
		});
	}

	GestureImageViewListener gestureImageViewListener = new GestureImageViewListener() {

		@Override
		public void onTouch(float x, float y) {
		}

		@Override
		public void onScale(float scale) {
		}

		@Override
		public void onPosition(float x, float y) {
		}
	};

	class AsyncDowdLoad extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {

			try {
				if (params[0].equals("0")) {
					mBitmap = BitmapFactory.decodeFile(mPath + mImg);
				} else {
					URL url = new URL(mUrl + mImg);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream inputStream = conn.getInputStream();
					mBitmap = BitmapFactory.decodeStream(inputStream);
					FileUtils.saveBitmapToSdcard(mPath + mImg, mBitmap);
				}
			} catch (OutOfMemoryError e1) {
				LogUtil.e("", "OutOfMemoryError");
				try {
					mBitmap = FileUtils.LoadImage(mPath + mImg, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			image.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			if (mBitmap == null) {
				image.setImageResource(0);
			} else {
				float i = 1;
				if (mMaxWidth > 0 && mMaxHeight > 0) {
					i = Math.min((mMaxWidth * 1f / mBitmap.getWidth()), (mMaxHeight * 1f / mBitmap.getHeight()));
				}
				// float scale = image.getScale() / i;
				image.setMinScale(0.5f);
				image.setMaxScale(5f);
				image.setStartingScale(i);
				image.setImageBitmap(mBitmap);
			}
			// float x, y, scale;
			// if (result) {
			// LogUtil.e("", "Reload Big Picture");
			// Display display = getWindowManager().getDefaultDisplay();
			// float mWidth = display.getWidth();
			// float i = (float) mBitmap.getWidth() / mWidth;
			// scale = image.getScale() / i;
			// x = image.getImageX();
			// y = image.getImageY();
			// image.setImageBitmap(mBitmap);
			// image.setStartingScale(scale);
			// image.setStartingPosition(x, y);
			// image.setTag("Big");
			// }
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBitmap != null && mBitmap.isRecycled() == false) {
			mBitmap.recycle();
			mBitmap = null;
			System.gc();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
		return false;
	}
}
