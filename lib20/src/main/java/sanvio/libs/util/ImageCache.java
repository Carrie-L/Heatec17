package sanvio.libs.util;

import java.lang.ref.SoftReference;
import java.util.WeakHashMap;

import android.graphics.Bitmap;

public class ImageCache {
	protected WeakHashMap<String, SoftReference<Bitmap>> imageCache;

	public ImageCache() {
		imageCache = new WeakHashMap<String, SoftReference<Bitmap>>();
	}

	public void destroy() {
		Bitmap bm;
		for (SoftReference<Bitmap> iterable_element : imageCache.values()) {
			bm = iterable_element.get();
			if (bm != null&& !bm.isRecycled()) {
				bm.recycle();
				bm = null;
			}
		}
		imageCache.clear();
	}
	
	public  boolean containsKey(String key){
		if(imageCache==null)
			return false;
		return imageCache .containsKey(key);
	}
	
	public Bitmap getBitmap(String filePath){
		try {
			return imageCache.get(filePath).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void putBitmap(String filePath,Bitmap bitmap ){
		try {
			imageCache.put(filePath, new SoftReference<Bitmap>(bitmap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public WeakHashMap<String, SoftReference<Bitmap>> getHashMap(){
		return imageCache;
	}
	 
}
