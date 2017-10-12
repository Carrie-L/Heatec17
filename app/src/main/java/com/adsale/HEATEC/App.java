package com.adsale.HEATEC;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.adsale.HEATEC.dao.DBHelper;
import com.adsale.HEATEC.dao.DaoMaster;
import com.adsale.HEATEC.dao.DaoSession;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import sanvio.libs.util.CrashHandler;
import sanvio.libs.util.DisplayUtils;


public class App extends Application {
    public static final String TAG = "App";
    // 谷歌统计 跟踪 ID
    // <!-- Beta UA-60584757-2 -->
    // <!-- Release UA-27731363-8 -->
    private static final String PROPERTY_ID = "UA-27731363-25";

    public static final boolean IsBlackoutDate = true;


    public static int SearchIndustryCount;

    public static String RootDir;
    public static String filesDir;
    public static AssetManager mAssetManager;
    public static Resources mResources;
    public static SharedPreferences mSPConfig;

    public static final String FTP_INFO_CONTENTLIST_VERSION = "ftp_info_contentList_version";

    public static OkHttpClient mOkHttpClient;
    public static String SP_CONFIG = "config";

    public static int mPageIndex = 0;

    public static boolean isNetworkAvailable = true;
    public static SharedPreferences mSP_updateTime;

    private DaoMaster daoMaster;
    public SQLiteDatabase db;
    public static final String DATABASE_NAME = "cle17.db";
    public static String mAppVersion;
    public static int mVersionCode;
    private DaoSession daoSession;
    public static DBHelper mDBHelper;
    public static String DB_PATH = "";// 在手机里存放数据库的位置

    public static boolean isTablet;


    public synchronized void sendTracker(String category, String action) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker mGaTracker = analytics.newTracker(PROPERTY_ID);
        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
        eventBuilder.setCategory(category);
        eventBuilder.setAction(action);
        eventBuilder.setLabel("" + (new Date()).getTime());
        mGaTracker.send(eventBuilder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        boolean isPad = getResources().getBoolean(R.bool.isTablet);
        LogUtil.i("app", "isPad=" + isPad);

        isTablet = (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;


        //just for phone now
        isTablet=false;
        LogUtil.i("app", "0000isTablet=" + isTablet);

        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();

        isNetworkAvailable = isNetworkAvailable();

        Fresco.initialize(this);

        // Capturing the error information
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        // UI Utility
        DisplayUtils.calculateScaling(getApplicationContext());

        // 创建默认的ImageLoader配置参数
        // ImageLoaderConfiguration configuration =
        // ImageLoaderConfiguration.createDefault(getApplicationContext());
        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                this).writeDebugLogs() // 打印log信息
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

		DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + getPackageName() + "/databases";
        RootDir = getDir("Heatec", MODE_PRIVATE).getAbsolutePath().concat("/");
        filesDir = getFilesDir().getAbsolutePath() + "/";
        mAssetManager = getAssets();
        mResources = getResources();

        mSP_updateTime = getSharedPreferences("GetMasterLastUpdateTime", MODE_PRIVATE);
        mSPConfig = getSharedPreferences("config", MODE_PRIVATE);

        getDbHelper();

    }

    /**
     * 解决旋转屏幕 国际化语言失效的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        LogUtil.e("APP", "setLocaleLanguage: Locale= " + SystemMethod.getLocale(getApplicationContext()));
        SystemMethod.setLocaleLanguage(getApplicationContext());


    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    private void getDbHelper() {
        mDBHelper = new DBHelper.Builder(getDaoSession(), daoMaster, db).build();
    }

    public static SQLiteDatabase openDatabase(String dbfile) {
        LogUtil.i(TAG, "openDatabase:" + dbfile);
        SQLiteDatabase db;
        try {
            File dir = new File(DB_PATH);
            if (!dir.exists())
                dir.mkdir();
            if (!(new File(dbfile).exists())) {
                LogUtil.e(TAG, "数据库不存在，从raw中导入");
                // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = mResources.openRawResource(R.raw.cle17); // 欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[4000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
                LogUtil.e(TAG, "Copy db file Success ！");
                db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            } else {
                LogUtil.e(TAG, "数据库存在，直接打开");
                db = SQLiteDatabase.openDatabase(dbfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            }
            return db;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public DaoSession getDaoSession() {
        LogUtil.i(TAG, "getDaoSession_____________________");
        if (daoMaster == null) {
            daoMaster = getDaoMaster();
            LogUtil.i(TAG, "getDaoSession: daoMaster == null");
        }
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
            LogUtil.i(TAG, "getDaoSession: daoSession == null");
        }
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), DATABASE_NAME, null);
        db = openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        return daoMaster;
    }

}
