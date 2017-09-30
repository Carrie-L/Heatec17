package sanvio.libs.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public abstract class AsyncDownloader extends AsyncTask<String, Integer, Boolean>{
	protected String mfilePath,mURL;
	
	public  AsyncDownloader(String pfilePath,String pURL){
		mfilePath=pfilePath;
		mURL=pURL;
	}
	
	public String GetFilePath() {
		return mfilePath;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		DownloadData(params);
		return downImgByFullPath(mURL, mfilePath);
	}
	
	private boolean downImgByFullPath(String strUrl, String strfilePath) {
		boolean result = false;
		String strRandomFilePath = "";
		boolean isRandomFile = false;
		try {
			if (isCancelled()) {
				return false;
			}

			byte[] data = getImage(strUrl);
			if (data != null) {
				File file = null;
				if (!FileUtils.isFileExistByFullPath(strfilePath))
					file = FileUtils.createFileByFullPath(strfilePath);
				else {
					strRandomFilePath = strfilePath.substring(0,
							strfilePath.lastIndexOf("/") + 1)
							+ String.valueOf(System.currentTimeMillis())
							+ strfilePath.substring(strfilePath
									.lastIndexOf("."));
					isRandomFile = true;
					file = FileUtils.createFileByFullPath(strRandomFilePath);
				}
				BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
				fos.write(data);
                fos.close();
                if (isRandomFile) {
					if (file.exists()) {
						FileUtils.deleteFileByFullPath(strfilePath);
						result = FileUtils.renameFromToByFullName(
								strRandomFilePath, strfilePath);

						FileUtils.deleteFileByFullPath(strRandomFilePath);
					}
				} else
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}  
		return result;
	}
	
	private byte[] getImage(String path) throws Exception {
		if(path.equals(""))
			return null;
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return readStream(inStream);
		}
		return null;
	}

	/**
	 * Get data from stream
	 */
	private byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			
			if (isCancelled()) {
				outStream.reset();
				outStream.close();
				outStream=null;
				inStream.close();
				return null;
			}else {
				outStream.write(buffer, 0, len);
			}
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}
	
	@Override
	protected abstract void onPostExecute(Boolean result);
	protected abstract void DownloadData(String... params);
}

