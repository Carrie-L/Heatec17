package sanvio.libs.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ResumeDownloader extends AsyncTask<String, Integer, Boolean>{
	protected String mfilePath,mURL;
	private Handler mDownLaodHandler;
	public  ResumeDownloader(String pfilePath,String pURL,Handler pDownLaodHandler){
		mfilePath=pfilePath;
		mURL=pURL;
		mDownLaodHandler=pDownLaodHandler;
	}
	
	public String GetFilePath() {
		return mfilePath;
	}
		 
	@Override
	protected Boolean doInBackground(String... params) {
		int result = 0;
		int downloaded,intFileSize;
		Message message;
		File file ;
		try {
			if (isCancelled()) {
				message = new Message();
	            message.what = 1;
	            message.arg1=result;
				mDownLaodHandler.sendMessage(message);
				return false;
			}
			URL url = new URL(mURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			
			if (!FileUtils.isFileExistByFullPath(mfilePath)){
				file = FileUtils.createFileByFullPath(mfilePath);
				downloaded=0;
			}
			else {
				file = new File(mfilePath);
				downloaded = (int) file.length();
				conn.setRequestProperty("Range", "bytes="+(file.length())+"-");
	        }
			
			intFileSize=conn.getContentLength()+downloaded;
			if (intFileSize==0) {
				message = new Message();
	            message.what = 2;
	            message.arg1=result;
				mDownLaodHandler.sendMessage(message);
			}else {
				FileOutputStream fos;
				message = new Message();
	            message.what = 0;
	            message.arg1=intFileSize;
	            message.arg2=downloaded;
				mDownLaodHandler.sendMessage(message);
				BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
				if (downloaded==0) {
					fos= new FileOutputStream(mfilePath);
				}else {
					fos= new FileOutputStream(mfilePath,true);
				}
				BufferedOutputStream  bout = new BufferedOutputStream(fos, 1024);
			    byte[] data = new byte[1024];
			    int x = 0;
			    while ((x = in.read(data, 0, 1024)) >= 0) {
			        if (isCancelled()) {
						break;
					}else {
						bout.write(data, 0, x);
				        downloaded += x;
					}
			    	message = new Message();
		            message.what = 1;
		            message.arg1=downloaded;
					mDownLaodHandler.sendMessage(message);		    
				}
			    if (downloaded>=intFileSize) {
					result=1;
				}
				message = new Message();
	            message.what = 2;
	            message.arg1=result;
				mDownLaodHandler.sendMessage(message);
				in.close();
				bout.close();
				fos.close();
			}
			
		    conn.disconnect();
		    
		} catch (Exception e) {
			e.printStackTrace();
			message = new Message();
            message.what = 2;
            message.arg1=0;
			mDownLaodHandler.sendMessage(message);
		}
		return true;  
	}

}
