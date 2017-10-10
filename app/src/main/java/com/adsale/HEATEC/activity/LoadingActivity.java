package com.adsale.HEATEC.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Display;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.dao.MainIcon;
import com.adsale.HEATEC.dao.WebContent;
import com.adsale.HEATEC.data.LoadRepository;
import com.adsale.HEATEC.database.model.LoadUrl;
import com.adsale.HEATEC.databinding.ActivityLoadingBinding;
import com.adsale.HEATEC.util.ContentHandler;
import com.adsale.HEATEC.util.FileUtil;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.PermissionUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.util.network.Configure;
import com.adsale.HEATEC.util.network.NetworkClient;
import com.adsale.HEATEC.util.network.NetworkHelper;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.adsale.HEATEC.App.RootDir;
import static com.adsale.HEATEC.util.FileUtil.createFile;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = "LoadingActivity";
    private SharedPreferences sp_config;

    public final ObservableBoolean isShowLang = new ObservableBoolean(false);
    public final ObservableBoolean isShowPB = new ObservableBoolean(false);// progressBar
    private String oDeviceType;
    private NetworkClient mDownClient;
    private ArrayList<MainIcon> mainIcons;
    private ArrayList<WebContent> webContents;
    private Disposable mTxtDisposable, mWCDisposable;
    private String mWebContentDir;
    private String mMainIconDir;
    private LoadRepository mLoadRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // baidu... init
        setupBaiDu();
        // 谷歌统计跟踪
        ((App) getApplication()).sendTracker("LoadingActivity", "onCreate");

        ActivityLoadingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_loading);
        binding.setActivity(this);

        initData();
    }

    private void initData() {
        sp_config = getSharedPreferences("config", MODE_PRIVATE);
        boolean isFirstRunning = sp_config.getBoolean("isFirstRunning", true);
        sp_config.edit().putBoolean("isFirstGetMaster", isFirstRunning).apply();
        mLoadRepository = LoadRepository.getInstance(getApplicationContext());
        if (isFirstRunning) {
            isShowLang.set(true);
            getDeviceInfo();
        } else {
            oDeviceType = SystemMethod.getSharedPreferences(getApplicationContext(), "DeviceType");
            setRequestedOrientation();
            startDownload();
        }
    }

    public void selectLang(int language) {
        SystemMethod.switchLanguage(getApplicationContext(), language);
        startDownload();
    }

    private void startDownload() {
        if (!App.isNetworkAvailable) {
            return;
        }
        mDownClient = SystemMethod.setupRetrofit(NetworkClient.class, Configure.DOWNLOAD_PATH);
        isShowPB.set(true);
        mWebContentDir = RootDir.concat("WebContent/");
        mMainIconDir = RootDir.concat("MainIcon/");
        createFile(mWebContentDir);
        createFile(mMainIconDir);
        mainIcons = new ArrayList<>();
        webContents = new ArrayList<>();
        downInformation();
        downWebServiceData();
    }

    private void downInformation() {
        mDownClient.downTxt(Configure.FTP_INFORMATION)
                .map(new Function<Response<ResponseBody>, String>() {
                    @Override
                    public String apply(@NonNull Response<ResponseBody> responseBodyResponse) throws Exception {
                        long startTime = System.currentTimeMillis();
                        ResponseBody body = responseBodyResponse.body();
                        if (body != null) {
                            FileOutputStream fos = openFileOutput(Configure.FTP_INFORMATION, Context.MODE_PRIVATE);
                            fos.write(body.bytes());
                            body.close();
                            fos.close();
                        }
                        long endTime = System.currentTimeMillis();
                        LogUtil.i(TAG, "write txt spend time：" + Configure.FTP_INFORMATION + ", " + (endTime - startTime) + " ms");
                        return Configure.FTP_INFORMATION;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mTxtDisposable = d;
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i(TAG, "))))) down txt end, send broadcast");
                    }
                });
    }

    private void downWebServiceData() {
        mDownClient.getMaster(NetworkHelper.getMasterRequestBody())
                .map(new Function<Response<ResponseBody>, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Response<ResponseBody> responseBodyResponse) throws Exception {
                        return saxXml(responseBodyResponse);
                    }
                })
                .flatMap(new Function<Boolean, Observable<LoadUrl>>() {
                    @Override
                    public Observable<LoadUrl> apply(@NonNull Boolean aBoolean) throws Exception {
                        return Observable.fromIterable(processUrls());
                    }
                })
                .flatMap(new Function<LoadUrl, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> apply(@NonNull LoadUrl loadUrl) throws Exception {
                        if (loadUrl.urlName.endsWith("zip")) {
                            return downZip(loadUrl.urlName, loadUrl.dirName);
                        } else {
                            return downIconPic(loadUrl);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {

                    private Intent intent;
                    private long mStartTime, mEndTime;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mStartTime = System.currentTimeMillis();
                        mWCDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG, "getMaster -- onError=" + e.getMessage());
                        isShowPB.set(false);
                        if (oDeviceType.equals("Phone")) {
                            intent = new Intent(LoadingActivity.this, MainActivity.class);
                        } else {
                            intent = new Intent(LoadingActivity.this, PadMainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                        LogUtil.e(TAG, "))))) down wc error, send broadcast");
                    }

                    @Override
                    public void onComplete() {//只执行一次,在最后
                        mEndTime = System.currentTimeMillis();
                        isShowPB.set(false);
                        sp_config.edit().putBoolean("isFirstRunning", false).apply();
                        if (oDeviceType.equals("Phone")) {
                            intent = new Intent(LoadingActivity.this, MainActivity.class);
                        } else {
                            intent = new Intent(LoadingActivity.this, PadMainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                        LogUtil.i(TAG, "))))) down wc end, send broadcast");
                        LogUtil.i(TAG, "wc onComplete----spend time= " + (mEndTime - mStartTime) + "ms");// ms
                    }
                });
    }

    private Boolean saxXml(Response<ResponseBody> responseBodyResponse) throws IOException {
        ResponseBody body = responseBodyResponse.body();
        if (body != null) {
            ContentHandler contentHandler = ContentHandler.getInstance(mLoadRepository);
            contentHandler.parseXmlWithSAX(body.string());
            mainIcons = contentHandler.mMainIcons;
            webContents = contentHandler.mWebContents;
            body.close();
            return true;
        }
        return false;
    }

    /**
     * 将MainIcon \ WebContent 的 zip、icon 下载链接都整合到一起，便于用Retrofit+RxJava下载
     *
     * @return ArrayList
     */
    private ArrayList<LoadUrl> processUrls() {
        int iconSize = mainIcons.size();
        int wcSize = webContents.size();
        ArrayList<LoadUrl> urls = new ArrayList<>();
        LoadUrl loadUrl;
        MainIcon mainIcon;
        WebContent webContent;
        for (int i = 0; i < iconSize; i++) {
            mainIcon = mainIcons.get(i);
            loadUrl = new LoadUrl(mainIcon.getIcon(), mainIcon.getIconID());
            urls.add(loadUrl);
            if (mainIcon.getCFile() != null && !mainIcon.getCFile().trim().isEmpty()) {
                loadUrl = new LoadUrl(mainIcon.getCFile(), mainIcon.getIconID());
                urls.add(loadUrl);
            }
        }
        for (int i = 0; i < wcSize; i++) {
            webContent = webContents.get(i);
            if (webContent.getCType() == 1) {
                loadUrl = new LoadUrl(webContent.getCFile(), String.valueOf(webContent.getPageId()));
                urls.add(loadUrl);
            }
        }
        LogUtil.i(TAG, "urls=" + urls.size() + "," + urls.toString());
        return urls;
    }

    private Observable<Boolean> downIconPic(final LoadUrl entity) {
        return mDownClient.downWebContent(entity.urlName)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<ResponseBody>, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Response<ResponseBody> responseBodyResponse) throws Exception {
                        ResponseBody body = responseBodyResponse.body();
                        if (body != null) {
                            FileOutputStream fos = new FileOutputStream(new File(mMainIconDir + entity.urlName));
                            fos.write(body.bytes());
                            fos.close();
                            body.close();
                        }
                        return true;
                    }
                });
    }

    private Observable<Boolean> downZip(final String cFile, final String iconId) {
        return mDownClient.downWebContent(cFile)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<ResponseBody>, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Response<ResponseBody> responseBodyResponse) throws Exception {
                        ResponseBody body = responseBodyResponse.body();
                        if (body != null) {
                            FileUtil.unpackZip(cFile, body.byteStream(), mWebContentDir + iconId + "/");
                            body.close();
                        }
                        return true;
                    }
                });
    }

    /**
     * 设置横竖屏方向
     */
    private void setRequestedOrientation() {
        if (oDeviceType.equals("Phone")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横
        }
    }

    private void getDeviceInfo() {
        requestPermission();
        setupDeviceType();
        deviceSize();
    }

    private void deviceSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else {
            display.getSize(size); // correct for devices with hardware navigation buttons
        }
        int width = size.x;
        int height = size.y;
        LogUtil.i(TAG, "test 3rd 像素：width= " + width + ", height= " + height);

        if (oDeviceType != null && oDeviceType.equals("Pad") && (width < height)) {// 如果是平板，且宽度小于高度，则交换两者的值
            width = width ^ height;
            height = width ^ height;
            width = width ^ height;
            LogUtil.i(TAG, "交换屏幕宽高：width=" + width + ",height=" + height);
        }

        SharedPreferences sp = getSharedPreferences("ScreenSize", MODE_PRIVATE);
        sp.edit().putInt("ScreenWidth", width).putInt("ScreenHeight", height).apply();
    }

    private void setupDeviceType() {
        if (getResources().getBoolean(R.bool.isTablet)) {
            oDeviceType = "Pad";
        } else {
            oDeviceType = "Phone";
        }
        SystemMethod.setSharedPreferences(getApplicationContext(), "DeviceType", oDeviceType);
        setRequestedOrientation();
    }

    private void getDeviceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        sp_config.edit().putString("deviceId", tm.getDeviceId()).apply();
    }

    private void requestPermission() {
        //请求 phone_state 权限
        boolean hasRPSPermission = PermissionUtil.checkPermission(this, PermissionUtil.PERMISSION_READ_PHONE_STATE);
        if (!hasRPSPermission) {
            PermissionUtil.requestPermission(this, PermissionUtil.PERMISSION_READ_PHONE_STATE, PermissionUtil.PMS_CODE_READ_PHONE_STATE);
        } else {
            getDeviceId();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtil.PMS_CODE_READ_PHONE_STATE) {
            if (PermissionUtil.getGrantResults(grantResults)) {
                getDeviceId();
            } else {
                String uniqueID = UUID.randomUUID().toString();
                sp_config.edit().putString("deviceId", uniqueID).apply();
            }
        }
    }

    /**
     * // 百度统计相关配置
     */
    private void setupBaiDu() {

        // 设置渠道
        StatService.setAppChannel(this, "GoogleMarket", true);
        // 打开崩溃收集
        StatService.setOn(this, StatService.EXCEPTION_LOG);
        // 设置启动时日志发送延时的秒数
        StatService.setLogSenderDelayed(10);
        // 用于设置日志发送策略
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1,
                false);
        // 调试百度统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
        StatService.setDebugOn(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose(mTxtDisposable);
        dispose(mWCDisposable);
    }

    private void dispose(Disposable d) {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
    }
}




