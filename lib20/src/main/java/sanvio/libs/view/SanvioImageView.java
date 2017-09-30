package sanvio.libs.view;

import sanvio.libs.util.AsyncDownloader;
import sanvio.libs.util.FileUtils;
import sanvio.libs.util.ImageCache;
import sanvio.libs.util.NetworkUtils;
import sanvio.libs.util.ResManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SanvioImageView extends LinearLayout implements IPageView {
	private ImageView mImageView;
	private TextView mTxtMsg;
	private ProgressBar mProgressBar;
	private Context mContext;
	private String mFilePath, mDownLoadURL, mFileName;
	private int mWidth = 0, mHeight = 0;

	private int mDefaultImage = 0;

	private String mOtherData;
	private AsyncdownloadImage oAsyncdownloadImage;
	private AsynLoadLocalImage oAsynLoadLocalImage;

	private String mViewStatus = "1";// 1--Free 2-loading 3-loaded 4-load failed

	private ImageCache imageCache;

	private boolean isAsyn = true, Clickable = false;

	private boolean isAnimation;

	public static int ProgressBarDrawable = -1;

	public SanvioImageView(Context context) {
		super(context);
		mContext = context;
		createViews();
	}

	public SanvioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		createViews();
	}

	private void createViews() {

		this.setBackgroundColor(Color.parseColor("#00000000"));
		this.setOrientation(LinearLayout.VERTICAL);
		this.setGravity(Gravity.CENTER);

		mImageView = new ImageView(mContext);
		mImageView.setVisibility(View.GONE);
		this.addView(mImageView);

		mProgressBar = new ProgressBar(mContext);
		mProgressBar.setVisibility(View.GONE);
		if(ProgressBarDrawable!=-1){
			mProgressBar.setIndeterminateDrawable(mContext.getResources().getDrawable(ProgressBarDrawable));
		}
		this.addView(mProgressBar, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		mTxtMsg = new TextView(mContext);
		mTxtMsg.setTextColor(Color.WHITE);
		this.addView(mTxtMsg, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	public void set(Drawable drawable) {
		mProgressBar.setIndeterminateDrawable(drawable);
	}

	public ImageView getInnerImageView() {
		return mImageView;
	}

	public String getStatus() {
		return mViewStatus;
	}

	public void setMsg(String text) {
		if (!text.equals(""))
			mTxtMsg.setText(text);
	}

	public void setAnimation(boolean isAnimation) {
		this.isAnimation = isAnimation;
	}

	public void setProgressBarDrawable(Drawable drawable) {
		if (drawable != null)
			mProgressBar.setIndeterminateDrawable(drawable);
	}

	public void setProgressBarSize(int width) {
		if (width > 0)
			mProgressBar.setLayoutParams(new LinearLayout.LayoutParams(width, width));
	}

	public void setImageScaleType(ScaleType scaleType) {
		mImageView.setScaleType(scaleType);
	}

	public void setImageViewSize(int pWidth, int pHeight) {
		mWidth = pWidth;
		mHeight = pHeight;
		if (mWidth > 0 && mHeight > 0) {
			this.getLayoutParams().width = mWidth;
			this.getLayoutParams().height = mHeight;

			mImageView.getLayoutParams().width = mWidth;
			mImageView.getLayoutParams().height = mHeight;
		}
	}

	public String GetFilePath() {
		String strFilePath = (String) mImageView.getTag();
		if (strFilePath == null) {
			strFilePath = "";
		}
		return strFilePath;
	}

	public void LoadImage(String pFilePath, String pDownLoadURL, String pFileName, OnClickListener pClickListener, ImageCache pimageCache) {
		if (pimageCache == null)
			imageCache = new ImageCache();
		else
			imageCache = pimageCache;
		LoadImage(pFilePath, pDownLoadURL, pFileName, pClickListener);
	}

	private void LoadImage(String pFilePath, String pDownLoadURL, String pFileName, OnClickListener pClickListener) {
		mImageView.setImageResource(0);
		try {
			if (!(mViewStatus.equals("1") || mViewStatus.equals("4"))) {
				return;
			}
			mFileName = pFileName;
			if (mFileName.equals("")) {
				Log.d("", "003 LoadImage mFileName=''");
				setImageResource();
				mImageView.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);
				mTxtMsg.setVisibility(View.GONE);
				mViewStatus = "4";
			} else {
				mFilePath = pFilePath + mFileName;
				mDownLoadURL = pDownLoadURL + mFileName;
				RefreshView("1");
			}
			if (pClickListener != null) {
				mImageView.setOnClickListener(pClickListener);
				Clickable = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadImgFromRes(int pResID) {
		mImageView.setImageResource(pResID);
		mViewStatus = "3";
		mImageView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		mTxtMsg.setVisibility(View.GONE);
	}

	private void setImageResource() {
		if (mDefaultImage == 0 && (mWidth > 0 || mHeight > 0)) {
			this.getLayoutParams().width = 0;
			this.getLayoutParams().height = 0;
			mImageView.getLayoutParams().width = 0;
			mImageView.getLayoutParams().height = 0;
		}
		mImageView.setImageResource(mDefaultImage);
	}

	public void RefreshView(String pRefreshMode) {
		mViewStatus = "2";

		if (pRefreshMode == "2") {
			FileUtils.deleteFile(mFilePath);
		}
		if (FileUtils.isFileExist(mFilePath)) {
			mImageView.setTag(mFilePath);
			if (isAsyn) {
				if (oAsynLoadLocalImage != null) {
					oAsynLoadLocalImage.cancel(true);
					oAsynLoadLocalImage = null;
				}
				oAsynLoadLocalImage = new AsynLoadLocalImage();
				oAsynLoadLocalImage.execute(mFilePath);
			} else {
				Bitmap bm = LoadLocalImage(mFilePath);
				if (bm != null) {
					resetSize(bm);
					mImageView.setImageBitmap(bm);
					mImageView.setVisibility(View.VISIBLE);
					mImageView.setTag(mFilePath);
					mViewStatus = "3";
				} else {
					setImageResource();
					Log.d("", "004 RefreshView  bm=null");
					mViewStatus = "4";
				}
			}
		} else {
			if (NetworkUtils.getNetWorkState(mContext) != 3) {
				if (oAsyncdownloadImage != null) {
					oAsyncdownloadImage.cancel(true);
					oAsyncdownloadImage = null;
				}

				oAsyncdownloadImage = new AsyncdownloadImage(mFilePath, mDownLoadURL);
				oAsyncdownloadImage.execute();
			} else if (mDefaultImage != 0) {
				mImageView.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);
				mTxtMsg.setVisibility(View.GONE);
				setImageResource();
				Log.d("", "005 RefreshView  not network");
				mViewStatus = "4";
			} else {
				setVisibility(View.GONE);
				mViewStatus = "4";
			}
		}
	}

	private Bitmap LoadLocalImage(String pFilePath) {
		Bitmap oBitmap = null;

		if (imageCache.containsKey(pFilePath)) {
			oBitmap = imageCache.getBitmap(pFilePath);
			if (oBitmap == null) {
				oBitmap = LoadImage(pFilePath);
				imageCache.putBitmap(pFilePath, oBitmap);
			}
		} else {
			oBitmap = LoadImage(pFilePath);
			if (oBitmap != null) {
				imageCache.putBitmap(pFilePath, oBitmap);
			}
		}
		return oBitmap;
	}

	private Bitmap LoadImage(String pFilePath) {
		Bitmap oBitmap = null;
		try {
			BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
			if (mHeight != 0 && mWidth != 0) {
				bitmapFactoryOptions.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(pFilePath, bitmapFactoryOptions);
				int yRatio = (int) Math.ceil(bitmapFactoryOptions.outHeight / mHeight);
				int xRatio = (int) Math.ceil(bitmapFactoryOptions.outWidth / mWidth);

				if (yRatio > 1 || xRatio > 1) {
					if (yRatio > xRatio) {
						bitmapFactoryOptions.inSampleSize = yRatio;
					} else {
						bitmapFactoryOptions.inSampleSize = xRatio;
					}
				} else {
					bitmapFactoryOptions.inSampleSize = 1;
				}
			} else {
				bitmapFactoryOptions.inSampleSize = 1;
			}

			bitmapFactoryOptions.inJustDecodeBounds = false;
			oBitmap = BitmapFactory.decodeFile(pFilePath, bitmapFactoryOptions);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return oBitmap;
	}

	private class AsynLoadLocalImage extends AsyncTask<String, Integer, Boolean> {
		private Bitmap bm;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mImageView.setTag(mFilePath);
			mProgressBar.setVisibility(View.GONE);
			mTxtMsg.setVisibility(View.GONE);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = false;
			try {
				bm = LoadLocalImage(params[0]);
				if (bm == null) {
					result = false;
				} else {
					result = true;
				}

			} catch (Exception e) {
				result = false;
				bm = null;
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mImageView != null && mImageView.getTag() != null && mImageView.getTag().toString().equals(mFilePath)) {
				if (result) {
					resetSize(bm);
					mImageView.setImageBitmap(bm);
					imgStartAnimation();
					mViewStatus = "3";
				} else {
					setImageResource();
					Log.d("", "006 AsynLoadLocalImage  result=false");
					mViewStatus = "4";
				}
				mImageView.setVisibility(View.VISIBLE);
			}
		}
	}

	public void ChangeImage(String pFilePath, String pDownLoadURL, String pFileName) {
		setOtherData(null);
		if (oAsynLoadLocalImage != null) {
			oAsynLoadLocalImage.cancel(true);
			oAsynLoadLocalImage = null;
		}
		mViewStatus = "1";
		try {
			mFileName = pFileName;
			if (mFileName.equals("")) {
				setImageResource();
				mImageView.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);
				mTxtMsg.setVisibility(View.GONE);
				mViewStatus = "4";
			} else {
				mFilePath = pFilePath + mFileName;
				mDownLoadURL = pDownLoadURL + mFileName;
				RefreshView("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DestoryImageView() {
		Log.d("", "DestoryImageView " + getClass().getName());
		if (Clickable) {
			mImageView.setOnClickListener(null);
		}
		mImageView.setImageBitmap(null);
		mImageView.setTag(null);
		mOtherData = "";
		mWidth = 0;
		mHeight = 0;

		if (oAsyncdownloadImage != null) {
			oAsyncdownloadImage.cancel(true);
			oAsyncdownloadImage = null;
		}
		if (oAsynLoadLocalImage != null) {
			oAsynLoadLocalImage.cancel(true);
			oAsynLoadLocalImage = null;
		}
		mViewStatus = "1";
	}

	public int getDefaultImage() {
		return mDefaultImage;
	}

	public void setDefaultImage(int pDefaultImage) {
		this.mDefaultImage = pDefaultImage;
	}

	public interface DownloadCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

	public String getOtherData() {
		return mOtherData == null ? "" : mOtherData;
	}

	public void setOtherData(String mOtherData) {
		this.mOtherData = mOtherData;
	}

	public String getViewStatus() {
		return mViewStatus;
	}

	public void setViewStatus() {
		mViewStatus = "1";
	}

	public void setAsyn(boolean isAsyn) {
		this.isAsyn = isAsyn;
	}

	public class AsyncdownloadImage extends AsyncDownloader {
		public AsyncdownloadImage(String pfilePath, String pURL) {
			super(pfilePath, pURL);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mImageView.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
			if (!mTxtMsg.getText().toString().equals(""))
				mTxtMsg.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (mFilePath.equals(super.GetFilePath())) {
				if (result) {
					Bitmap bm = LoadLocalImage(super.GetFilePath());
					if (bm != null) {
						mImageView.setTag(mFilePath);
						resetSize(bm);
						mImageView.setImageBitmap(bm);
						imgStartAnimation();
						mViewStatus = "3";
					} else {
						setImageResource();
						Log.d("", "002 AsyncdownloadImage onPostExecute bm=null");
						mViewStatus = "4";
					}
				} else {
					if (mDefaultImage == 0)
						setVisibility(View.GONE);
					else {
						setImageResource();
						Log.d("", "001 AsyncdownloadImage onPostExecute result=false");
					}
					mViewStatus = "4";
				}
				mImageView.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);
				mTxtMsg.setVisibility(View.GONE);
			}
		}

		@Override
		protected void DownloadData(String... params) {

		}
	}

	private void imgStartAnimation() {
		if (isAnimation) {
			Animation ani1 = AnimationUtils.loadAnimation(mContext.getApplicationContext(),
					ResManager.getResourseIdByName(mContext.getPackageName(), "anim", "fade_in"));
			mImageView.startAnimation(ani1);
		}
	}

	@Override
	public void DestoryView() {
		DestoryImageView();
	}

	private void resetSize(Bitmap bm) {
		if (mWidth != 0 && mHeight == 0) {
			int imgwidth = bm.getWidth();
			int imgheight = bm.getHeight();
			mHeight = imgheight * mWidth / imgwidth;
			mImageView.getLayoutParams().width = mWidth;
			mImageView.getLayoutParams().height = mHeight;
			SanvioImageView.this.getLayoutParams().width = mWidth;
			SanvioImageView.this.getLayoutParams().height = mHeight;
		}
	}

}
