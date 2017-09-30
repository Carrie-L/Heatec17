package com.adsale.HEATEC.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.adsale.HEATEC.App;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sanvio.libs.util.FileUtils;

/**
 * Created by Carrie on 2017/9/6.
 */

public class DownloadUtil {
    private static final String TAG="DownloadUtil";

    /**
     *
     * @param zipDownUrl   下載url
     * @param zipName  zip包名：77.zip
     * @param zipPath  解压到文件夹下，文件夹的完整路径，后面要有/：/data/user/0/com.adsale.HEATEC/app_Heatec/WebContent/100/
     */
    public static void downZipAndUnzip(final String zipDownUrl, final String zipName, final String zipPath) {
        LogUtil.i(TAG,"zipDownPath="+zipDownUrl);
        LogUtil.i(TAG,"zipName="+zipName);
        LogUtil.i(TAG,"zipPath="+zipPath);


        Request request = new Request.Builder().url(zipDownUrl).build();
        Callback callback = new Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                LogUtil.e(TAG, zipName + "下载失败");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                LogUtil.e(TAG, "下载webcontent.zip成功：" + zipName);

                InputStream is = response.body().byteStream();
                boolean isUnZiped = unpackZip(zipName, is, zipPath);
                LogUtil.e(TAG, zipName + "-----isUnZiped=" + isUnZiped);

                if (isUnZiped) {
                    LogUtil.e(TAG, zipName + "写出成功");
                    // oWebContentDBHelper.downloaded(webContent.getPageId());
                }
                is.close();
                response.body().close();
            }

        };
        callByOkHttp(request, callback);
    }

    /**
     *
     * <font color="#f97798">解压zip</font>
     *
     * @param zipname
     * @param zipPath zip包的绝对文件夹（不包含.zip，.zip的上一级）
     * @return <font color="#f97798"></font>
     * @return boolean
     * @version 创建时间：2016年6月3日 上午9:33:06
     */
    public static boolean unpackZip(String zipname, InputStream is, String zipPath) {
        long startTime = System.currentTimeMillis();
        ZipInputStream zis;
        try {
            String filename;
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            if (!TextUtils.isEmpty(zipname)) {

                if (zipname.contains(".zip")) {
                    zipname = zipname.replace(".zip", "");
                }

                if (!new File(zipPath).exists()) {
                    new File(zipPath).mkdir();
                }
                // zipPath=/storage/emulated/0/com.adsale.ChinaPlas/WebContent/MI00000035/
                LogUtil.i(TAG, "zipPath=" + zipPath);

                boolean unpack=false;

                while ((ze = zis.getNextEntry()) != null) {
                    // zapis do souboru
                    filename = ze.getName();
                    LogUtil.i(TAG, "______filename="+filename);
                    // Need to create directories if not exists, or it will
                    // generate
                    // an Exception...
                    if (ze.isDirectory()) {
                        File fmd = new File(zipPath + filename);
                        LogUtil.i(TAG, "ze.isDirectory:  =" + zipPath + filename);
                        fmd.mkdirs();
                        continue;
                    }


                    String fileName=zipPath + filename;
                    LogUtil.i(TAG, "zipPath + filename =" + zipPath + filename);
                    File file=new File(fileName);
                    if(file.exists()){

                    }


                    FileOutputStream fout = new FileOutputStream(zipPath + filename);
                    // cteni zipu a zapis
                    while ((count = zis.read(buffer)) != -1) {
                        fout.write(buffer, 0, count);
                    }

                    fout.close();
                    zis.closeEntry();
                    unpack=true;
                }
                zis.close();
                return unpack;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        long endTime = System.currentTimeMillis();
        LogUtil.e(TAG, "解压" + zipname + "的时间为" + (endTime - startTime) + "ms");
        return false;
    }

    private static void callByOkHttp(Request request, Callback callback) {
        App.mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 可以下载txt，img等
     * @param url 下载链接
     * @param targetPath 要下载到哪个路径，绝对路径
     */
    public static void downloadFile(String url,final String targetPath){
        Request request = new Request.Builder().url(url).build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call arg0, IOException arg1) {
                LogUtil.e(TAG,  "下载失败: "+targetPath);
            }

            @Override
            public void onResponse(Call arg0, okhttp3.Response response) throws IOException {
                LogUtil.e(TAG, "下载成功："+targetPath);
                writeFileToSD(response.body().bytes(), targetPath);
                response.body().close();
            }
        };
        callByOkHttp(request, callback);
    }

    public static void writeFileToSD(final byte[] bytes,final String fileName){
        try {
            String path=fileName.substring(0, fileName.lastIndexOf("/")+1);
            File filePath = new File(path);
            File file = new File(fileName);

            if (!filePath.exists()) {
                filePath.mkdir();
            }
            if (!file.exists()) {
                filePath.createNewFile();
            }

            FileOutputStream stream = new FileOutputStream(file);
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            LogUtil.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

}
