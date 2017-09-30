package sanvio.libs.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {
	private HashMap<String, SoftReference<clsImageItem>> imageCache;

	public AsyncImageLoader() {
		this.imageCache = new HashMap<String, SoftReference<clsImageItem>>();
	}

	public Bitmap loadDrawable(String pDownLoadRootUrl, String pLocalRootPath,
							String ImageFile, ImageCallback imageCallback) {
		Bitmap resultBitmap=null ;
		if(pDownLoadRootUrl.equals("")||pLocalRootPath.equals(""))
			return null;
		String DownLoadFullUrl,LocalFullPath;
		
		DownLoadFullUrl=pDownLoadRootUrl+ImageFile;
		LocalFullPath=pLocalRootPath+ImageFile;
		if (imageCache.containsKey(DownLoadFullUrl)) {
			SoftReference<clsImageItem> oSoftReference = imageCache.get(DownLoadFullUrl);
			clsImageItem oclsImageItem =oSoftReference.get();
			if (oclsImageItem!=null) {
				if (oclsImageItem.getFileStatus()=="1") {
					resultBitmap=oclsImageItem.getImageSource();
					if (resultBitmap==null) {
						resultBitmap=LoadImage(DownLoadFullUrl, LocalFullPath, LocalFullPath, imageCallback);
					}
				}else if (oclsImageItem.getFileStatus()=="3") {
					resultBitmap=LoadImage(DownLoadFullUrl, LocalFullPath, LocalFullPath, imageCallback);
				}
				else {
					oclsImageItem.AddListener(imageCallback);
					resultBitmap=null;
				}
			}else {
				resultBitmap=LoadImage(DownLoadFullUrl, LocalFullPath, LocalFullPath, imageCallback);
			}
			
		}else {
			resultBitmap=LoadImage(DownLoadFullUrl, LocalFullPath, LocalFullPath, imageCallback);
			
		}
		
		return resultBitmap;
	}
	
	private Bitmap LoadImage(String pDownLoadFullUrl, String pLocalFullPath,String ImageFile, ImageCallback imageCallback) {
		Bitmap resultBitmap=null ;
		if (FileUtils.isFileExist(pLocalFullPath)) {
			
			clsImageItem oclsImageItem=LoadLocalImg(pDownLoadFullUrl,pLocalFullPath);
			if (oclsImageItem!=null) {
				imageCache.put(pDownLoadFullUrl, new SoftReference<clsImageItem>(oclsImageItem));
				resultBitmap=oclsImageItem.getImageSource();
			}
			
		} else {
		
			new DownloadFileThread(pDownLoadFullUrl,pLocalFullPath,imageCallback).start();
		}
		return resultBitmap;
		
	}
	
	
	public void Destory() {
		if (imageCache!=null) {
			for (Iterator<SoftReference<clsImageItem>> iterator = imageCache.values().iterator(); iterator.hasNext();) {
				SoftReference<clsImageItem> oclSoftReference = (SoftReference<clsImageItem>) iterator.next();
				if (oclSoftReference!=null) {
					 clsImageItem oclsImageItem= oclSoftReference.get();
					 if (oclsImageItem !=null) {
						 Bitmap oBitmap= oclsImageItem.getImageSource();
						 if (oBitmap!=null) {
							 oBitmap.recycle();
						}
					}
				}
			}
		}
	}
	
	private class DownloadFileThread extends Thread{
		private String mDownLoadFullUrl,mFullRootPath;
		private ImageCallback imageCallback;
		
		public DownloadFileThread(String pDownLoadFullUrl,String pLocalFullPath,ImageCallback pimageCallback) {
			super();
			this.imageCallback = pimageCallback;
			mDownLoadFullUrl=pDownLoadFullUrl;
			mFullRootPath = pLocalFullPath;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Bitmap oBitmap=null;;
			clsImageItem oclsImageItem=new clsImageItem(null,mDownLoadFullUrl);
			oclsImageItem.FileStatus="2";
			oclsImageItem.AddListener(imageCallback);
			imageCache.put(mDownLoadFullUrl, new SoftReference<clsImageItem>(oclsImageItem));
			if (DownLoadUtils.downImg(mDownLoadFullUrl, mFullRootPath)) {
				if (FileUtils.isFileExist(mFullRootPath)) {
					try {
						oBitmap= BitmapFactory.decodeFile(mFullRootPath);
						oclsImageItem.setImageSource(oBitmap);
						oclsImageItem.setFileStatus("1");
						Message message = handler.obtainMessage(0, oclsImageItem);
						handler.sendMessage(message);
//						oclsImageItem.notifyListener();
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}
				}else {
					oclsImageItem.setFileStatus("3");
				}
				
			}else {
				oclsImageItem.setFileStatus("3");
			}
		}
		
	}
	
	static Handler handler = new Handler() {
		public void handleMessage(Message message) {
//			imageCallback.imageLoaded((Drawable) message.obj, pDownLoadRootUrl);
			((clsImageItem)message.obj).notifyListener();
		}
	};
	
	private clsImageItem LoadLocalImg(String pDownLoadFullUrl, String LocalFullPath) {
		Bitmap oBitmap=null;;
		try {
			oBitmap= BitmapFactory.decodeFile(LocalFullPath);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		clsImageItem oclsImageItem=new clsImageItem(oBitmap,pDownLoadFullUrl);
		oclsImageItem.FileStatus ="1";
		return oclsImageItem;
	}
//	public static Drawable loadImageFromUrl(String url) {
//		URL m;
//		InputStream i = null;
//		try {
//			m = new URL(url);
//			i = (InputStream) m.getContent();
//		} catch (MalformedURLException e1) {
//			e1.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Drawable d = Drawable.createFromStream(i, "src");
//		return d;
//	}

	
	public interface ImageCallback {
		public void imageLoaded(Bitmap pImageBitmap, String pDownLoadRootUrl);
	}
	
	private class clsImageItem{
		private Bitmap mImageSource; 
		private String FileStatus=""; 
		private String pDownLoadRootUrl=""; //1-downloaded 2-downloading 3-Download fail
		private List<ImageCallback> oLisenerList;
		public clsImageItem(Bitmap pImageSource,String ppDownLoadRootUrl) {
			mImageSource=pImageSource;
			pDownLoadRootUrl=ppDownLoadRootUrl;
			oLisenerList=new ArrayList<AsyncImageLoader.ImageCallback>();
		}
		public Bitmap getImageSource() {
			return mImageSource;
		}
		public void setImageSource(Bitmap mImageSource) {
			this.mImageSource = mImageSource;
		}
		public String getFileStatus() {
			return FileStatus;
		}
		public void setFileStatus(String fileStatus) {
			FileStatus = fileStatus;
		}
		public void AddListener(ImageCallback oImageCallback){
			oLisenerList.add(oImageCallback);
		}
		
		public void notifyListener(){
			for (ImageCallback oImageCallback : oLisenerList) {
				oImageCallback.imageLoaded(mImageSource, pDownLoadRootUrl);
			}
		}
		
		
	}
	
}
