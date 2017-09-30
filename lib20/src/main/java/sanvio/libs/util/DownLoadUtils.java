package sanvio.libs.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;

public class DownLoadUtils {
	/**
	 * 根据URL下载txt文件,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容 1.创建一个URL对象
	 * 2.通过URL对象,创建一个HttpURLConnection对象 3.得到InputStream 4.从InputStream当中读取数据
	 * 
	 * @param mUrlString
	 * @return
	 */
	public static String downLoadTxtFile(String mUrlString) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(getInputStreamFromURL(mUrlString)));
			if (buffer != null) {
				while ((line = buffer.readLine()) != null) {
					sb.append(line);
				}
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 下载文件，第一个参数是下载文件地址，第二个参数是保存文件地址
	 * 
	 * @param pUrlString
	 * @param pSaveFilePath
	 * @return
	 */
	public static boolean downFile(String pUrlString, String pSaveFilePath) {
		boolean result = false;
		FileOutputStream out = null;
		InputStream in = null;
		try {
			File file = null;
			if (!FileUtils.isFileExist(pSaveFilePath))
				file = FileUtils.createFile(pSaveFilePath);
			else
				file = new File(FileUtils.getRootPath() + pSaveFilePath);
			out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			in = getInputStreamFromURL(pUrlString);
			if (in != null) {
				while ((in.read(buffer)) != -1) {
					out.write(buffer);
				}
				out.flush();
				out.close();
				in.close();
				result = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			FileUtils.deleteFile(pSaveFilePath);
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e2) {
				e2.printStackTrace();
				FileUtils.deleteFile(pSaveFilePath);
			}
		}
		return result;
	}

	/**
	 * 根据URL下载txt文件并保存指定目录,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容 1.创建一个URL对象
	 * 2.通过URL对象,创建一个HttpURLConnection对象 3.得到InputStream 4.从InputStream当中读取数据
	 * 
	 * @param mUrlString
	 * @param mTargetPath
	 * @return
	 */
	public static boolean downLoadTxt2File(String mUrlString, String mTargetPath) {
		return FileUtils.writeFileFromInput(mTargetPath, mUrlString.substring(mUrlString.lastIndexOf("/") + 1), getInputStreamFromURL(mUrlString));
	}

	/**
	 * 
	 * @param urlStr
	 * @param path
	 * @return false:文件下载出错 true:文件下载成功
	 */
	public static boolean downZIPFile(String urlStr, String path) {
		ZipInputStream zipis = null;
		ZipOutputStream zipos = null;
		boolean result = false;
		String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1);

		// HttpURLConnection urlConn = null;
		// InputStream inputStream = null;
		// URL url;
		try {
			if (FileUtils.isFileExist(path + fileName))
				FileUtils.deleteFile(path + fileName);
			// fileUtils.createFile(path + fileName);

			// url = new URL(urlStr);
			// urlConn = (HttpURLConnection) url.openConnection();
			// inputStream = urlConn.getInputStream();

			InputStream in = getInputStreamFromURL(urlStr);
			if (in != null) {
				zipis = new ZipInputStream(getInputStreamFromURL(urlStr));
				if (zipis != null) {
					ZipEntry zipEntry;
					Log.v("inputStream", zipis.toString());
					File f = FileUtils.createFile(path + fileName);
					zipos = new ZipOutputStream(new FileOutputStream(f));
					Log.v("url", urlStr);
					byte[] bArray = new byte[1024];
					while ((zipEntry = zipis.getNextEntry()) != null) {

						zipos.setMethod(ZipOutputStream.DEFLATED);
						zipos.putNextEntry(zipEntry);
						int retVal = 0;
						while ((retVal = zipis.read(bArray)) != -1) {
							zipos.write(bArray, 0, retVal);
						}
						zipos.flush();
					}
					zipis.close();
					zipos.close();
					result = true;
				}
			}
		} catch (IOException ioe) {
			System.out.println("DownloadZip Err1: " + ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			try {
				if (null != zipis) {
					zipis.close();
				}
				if (null != zipos) {
					zipos.close();
				}
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("DownloadZip Err2: " + e.getMessage());
			}
		}
		return result;
	}

	public static boolean unZipFile(String fileName, String mTargetFilePath) {
		OutputStream os = null;
		InputStream is = null;
		ZipFile zFile = null;
		try {
			String strRootPath = FileUtils.getRootPath();
			if (fileName.indexOf(strRootPath) == -1)
				fileName = FileUtils.getRootPath() + fileName;

			zFile = new ZipFile(fileName);
			Enumeration<? extends ZipEntry> zList = zFile.entries();

			ZipEntry ze = null;
			byte[] buffer = new byte[1024];
			File file = FileUtils.createFile(mTargetFilePath);
			Log.v("create file", "root file path:" + mTargetFilePath);
			while (zList.hasMoreElements()) {
				ze = zList.nextElement();
				file = FileUtils.createFile(mTargetFilePath + ze.getName());
				Log.v("file name", mTargetFilePath + ze.getName());
				if (ze.isDirectory()) {
					file.mkdirs();
					continue;
				}
				os = new BufferedOutputStream(new FileOutputStream(getRealFileName(mTargetFilePath, ze.getName())));
				Log.v("file name", ze.getName());
				is = new BufferedInputStream(zFile.getInputStream(ze));
				int readLen = 0;
				while ((readLen = is.read(buffer)) != -1) {
					os.write(buffer, 0, readLen);
				}
				is.close();
				os.close();
			}
			zFile.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
				if (zFile != null)
					zFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 * @throws IOException
	 */
	public static File getRealFileName(String baseDir, String absFileName) throws IOException {
		String[] dirs = absFileName.split("/");
		File ret = FileUtils.createFile(baseDir);
		if (dirs.length >= 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
				if (!ret.exists())
					ret.mkdirs();
			}
			ret = new File(ret, dirs[dirs.length - 1]);
			return ret;
		}
		return ret;
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 */
	public static InputStream getInputStreamFromURL(String urlStr) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		URL url;
		try {
			url = new URL(urlStr);
			urlConn = (HttpURLConnection) url.openConnection();
			inputStream = urlConn.getInputStream();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("DownloadZip Err4: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("DownloadZip Err5: " + e.getMessage());
		}

		return inputStream;
	}

	public static boolean downImage(String strUrl, String strFileName) {
		try {
			URL url = new URL(strUrl);

			File file = new File(FileUtils.getRootPath() + strFileName);
			if (!file.exists())
				file.createNewFile();

			long startTime = System.currentTimeMillis();
			Log.d("ImageManager", "download begining");
			Log.d("ImageManager", "download url:" + url);
			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
			Log.d("ImageManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
			return true;
		} catch (IOException e) {
			Log.d("ImageManager", "Error: " + e);
			return false;
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param strUrl
	 * @param strfilePath
	 */
	public static boolean downImg(String strUrl, String strfilePath) {
		boolean result = false;
		Bitmap bm = null;
		try {
			String strRandomFilePath = "";
			boolean isRandomFile = false;

			byte[] data = getImage(strUrl);
			if (data != null) {
				bm = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
			}
			if (bm != null) {
				File file = null;
				if (!FileUtils.isFileExist(strfilePath))
					file = FileUtils.createFile(strfilePath);
				else {
					strRandomFilePath = strfilePath.substring(0, strfilePath.lastIndexOf("/") + 1) + String.valueOf(System.currentTimeMillis())
							+ strfilePath.substring(strfilePath.lastIndexOf("."));
					isRandomFile = true;
					file = FileUtils.createFile(strRandomFilePath);
				}

				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
				bos.close();
				bm = null;
				if (isRandomFile) {
					if (file.exists()) {
						if (FileUtils.isFileExist(strfilePath)) {
							FileUtils.deleteFile(strfilePath);
						}
						FileUtils.deleteFile(strfilePath);
						result = FileUtils.renameFromTo(strRandomFilePath, strfilePath);
						FileUtils.deleteFile(strRandomFilePath);
					}
				} else
					result = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (bm != null)
				bm = null;
		}
		return result;
	}

	/**
	 * 保存文件
	 * 
	 * @param strUrl
	 * @param strfilePath
	 */
	public static boolean downImgByFullPath(String strUrl, String strfilePath) {
		boolean result = false;
		Bitmap bm = null;
		try {
			String strRandomFilePath = "";
			boolean isRandomFile = false;

			byte[] data = getImage(strUrl);
			if (data != null) {
				bm = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
			}
			if (bm != null) {
				File file = null;
				if (!FileUtils.isFileExistByFullPath(strfilePath))
					file = FileUtils.createFileByFullPath(strfilePath);
				else {
					strRandomFilePath = strfilePath.substring(0, strfilePath.lastIndexOf("/") + 1) + String.valueOf(System.currentTimeMillis())
							+ strfilePath.substring(strfilePath.lastIndexOf("."));
					isRandomFile = true;
					file = FileUtils.createFileByFullPath(strRandomFilePath);
				}

				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
				bos.close();
				bm = null;
				if (isRandomFile) {
					if (file.exists()) {
						FileUtils.deleteFileByFullPath(strfilePath);
						result = FileUtils.renameFromToByFullName(strRandomFilePath, strfilePath);

						FileUtils.deleteFileByFullPath(strRandomFilePath);

						// if (FileUtils.copyFile(FileUtils.getRootPath()
						// + strRandomFilePath, strfilePath))
						// file.delete();
						// else
						// return false;
					}
				} else
					result = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (bm != null)
				bm = null;
		}
		return result;
	}

	/**
	 * Get image from newwork
	 * 
	 * @param path
	 *            The path of image
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getImage(String path) throws Exception {
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
	 * 
	 * @param inStream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	public static String readString(String filePath) {
		StringBuffer buffer = new StringBuffer();
		String strSdcard = FileUtils.getRootPath();
		if (filePath.indexOf(strSdcard) == -1)
			filePath = strSdcard + filePath;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");// 文件编码Unicode,UTF-8,ASCII,GB2312,Big5
			Reader in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1) {
				buffer.append((char) ch);
			}
			in.close();
			return buffer.toString(); // buffer.toString())就是读出的内容字符
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static Boolean downHtml(String pageURL, String filePath) {
		StringBuilder pageHTML = new StringBuilder();
		try {
			URL url = new URL(pageURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// connection.setRequestProperty("User-Agent", "MSIE 7.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FileUtils.writeStringToFile(pageHTML.toString(), filePath, false);
	}

	public static boolean downLoadPDF(String pUrlString, String pSaveFilePath) {
		boolean result = false;
		File file = new File(pSaveFilePath);
		// 如果目标文件已经存在，则删除。产生覆盖旧文件的效果
		if (file.exists()) {
			file.delete();
		}
		try {
			// 构造URL
			URL url = new URL(pUrlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			// int contentLength = con.getContentLength();
			// System.out.println("长度 :"+contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(pSaveFilePath);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
