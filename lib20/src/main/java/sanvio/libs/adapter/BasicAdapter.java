package sanvio.libs.adapter;

import sanvio.libs.util.ImageCache;
import android.widget.BaseAdapter;

/**
*/
public abstract class BasicAdapter extends BaseAdapter {

	protected ImageCache imageCache;

	public BasicAdapter() {
		imageCache = new ImageCache();
	}

	public void destroyCache() {
		if (imageCache != null)
			imageCache.destroy();
	}
}
