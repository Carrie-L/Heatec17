package sanvio.libs.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.Log;

public class AudioUtils {
	public static void audioPlayer(Context context,String pFileName) {
	    //set up MediaPlayer    
	    MediaPlayer mp = new MediaPlayer();

	    try {
	    	AssetManager oAssetManager=context.getAssets();
	        AssetFileDescriptor descriptor = oAssetManager.openFd(pFileName);
	        mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
	        descriptor.close();
	        mp.prepare();
	        mp.setVolume(1f, 1f);
	        mp.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void audioPlayer(String pFileName)
	{
	    //set up MediaPlayer    
	    MediaPlayer mp = new MediaPlayer();

	    try {
	        mp.setDataSource(pFileName);
	        mp.prepare();
	        mp.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void audioPlayer(Context context,int pRegID) {
	    //set up MediaPlayer    
	    

	    try {
	    	MediaPlayer mediaPlayer = MediaPlayer.create( context, pRegID );
	    	mediaPlayer.setVolume(1f, 1f);
	    	mediaPlayer.setOnCompletionListener(new OnCompletionListener()
	    	{
	    	    @Override
	    	    public void onCompletion( MediaPlayer mp )
	    	    {
	    	        mp.release();
	    	    }
	    	} );
	    	mediaPlayer.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					Log.v("", "what:"+what+" extra:"+extra);
					return false;
				}
			});
	    	mediaPlayer.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
