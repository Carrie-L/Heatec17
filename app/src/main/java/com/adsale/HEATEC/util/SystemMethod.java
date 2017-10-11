package com.adsale.HEATEC.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import sanvio.libs.util.FileUtils;
import sanvio.libs.util.HttpDownHelper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.database.model.LogJson;
import com.adsale.HEATEC.database.model.clsSection;
import com.adsale.HEATEC.database.model.ftpInformation;
import com.adsale.HEATEC.util.network.Configure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SystemMethod {

    public static final String CACHE_USER_ID_KEY = "UserId";
    public static final String CACHE_USER_NAME_KEY = "UserName";

    public static final String LOG_JSON_STR_KEY = "Heatec_logJsonArr.txt";

    public static final int PAGE_SIZE = 5;
    public static final String SP_CONFIG = " config";
    public static final String CUR_LANGUAGE = "CUR_LANGUAGE";

    public static ArrayList<LogJson> logJsonArr;
    public static String strLogJson;

    private static final String ACCOUNT_NAME = "Heatec@gmail.com";
    private static final String CALENDAR_DISPLAY_NAME = "Heatec2017";
    protected static final String TAG = "SystemMethod";

    public static boolean detectDateRange(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDatedata;
        Date endDatedata;
        try {
            startDatedata = sdf.parse(startDate);
            endDatedata = sdf.parse(endDate);

            if (new Date().after(startDatedata)
                    && new Date().before(endDatedata)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    // public static boolean RefreshSchedulePager = false;
    public static boolean PreRegClose(ftpInformation info) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date endDatedata;
        Date currentDatedata;
        try {

            currentDatedata = new Date();
            endDatedata = sdf.parse(info.getRegistration());
            LogUtil.d("TAG", "info.getRegistration():" + info.getRegistration());
            LogUtil.d("TAG", "new Date:" + sdf.format(currentDatedata));
            if (currentDatedata.after(endDatedata)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String GetTelNo(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String phonenumber = tm.getLine1Number();
        if (phonenumber == null) {
            phonenumber = "";
        }
        return phonenumber;

    }

    public static Date getBeiJingCurrentTime() {

        URL url;
        long ld;
        Date date = null;
        try {
            url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect(); // 发出连接
            ld = uc.getDate(); // 取得网站日期时间
            date = new Date(ld); // 转换为标准时间对象
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 取得资源对象
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public static void setSharedPreferences(Context context,
                                            String preferences_name, String value) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString("value", value);
        editor.commit();
    }

    public static void setVersionSharedPreferences(Context context,
                                                   String preferences_name, Integer value) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putInt("value", value);
        editor.commit();
    }

    public static void setBooleanSharedPreferences(Context context,
                                                   String preferences_name, Boolean value) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("value", value);
        editor.commit();
    }

    public static void clearSharedPreferences(Context context,
                                              String preferences_name) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static String getSharedPreferences(Context context,
                                              String preferences_name) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        return pref.getString("value", "1");
    }

    public static Integer getVersionSharedPreferences(Context context,
                                                      String preferences_name) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        return pref.getInt("value", -1);
    }

    public static boolean getBooleanSharedPreferences(Context context,
                                                      String preferences_name) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        return pref.getBoolean("value", false);
    }

    public static void clearCookies(Context mContext) {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
    }

    public static void hideSoftInput(Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity) mContext).getWindow()
                .getDecorView().getWindowToken(), 0);
    }

    public static int[] scallingPhoto(Context pContext, int photoWidth,
                                      int photoHeight, int maxWidth, int maxHeight) {
        int[] size = new int[2];
        int scaleWidth = 0;
        int scaleHeight = 0;
        float scaling = (float) (photoWidth * 1.00 / maxWidth);

        scaleWidth = maxWidth;
        scaleHeight = (int) Math.rint(photoHeight / scaling);
        if (scaleHeight > maxHeight) {
            scaling = (float) (photoHeight * 1.00 / maxHeight);
            scaleWidth = (int) Math.rint(photoWidth / scaling);
            scaleHeight = maxHeight;
        }
        size[0] = scaleWidth;
        size[1] = scaleHeight;

        return size;
    }

    /**
     * @param mContext
     * @param Language 0:ZhTw; 1:en;2:ZhCn;
     */
    public static void switchLanguage(Context mContext, int Language) {
        Locale locale = null;

        switch (Language) {
            case 1:
                locale = Locale.US;
                break;
            case 2:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            default:
                locale = Locale.TRADITIONAL_CHINESE;
                break;
        }

        SharedPreferences pref = mContext.getSharedPreferences(
                SP_CONFIG, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putInt(CUR_LANGUAGE, Language);
        editor.commit();
        Resources resources = mContext.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = locale;
        resources.updateConfiguration(config, dm);
    }

    public static void setLocaleLanguage(Context context) {
        if (SystemMethod.isPadDevice(context)) {

            int language = SystemMethod.getCurLanguage(context);
            LogUtil.e(TAG, "setLocaleLanguage: language=" + language + ",Locale= " + getLocale(context));
            SystemMethod.switchLanguage(context, language);
        }
    }

    public static String getLocale(Context context) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        return config.locale.getCountry();
    }

    /**
     * @param mContext 0:ZhTw; 1:en;2:ZhCn;
     */
    public static int getCurLanguage(Context mContext) {
        SharedPreferences pref = mContext.getSharedPreferences(
                SP_CONFIG, Context.MODE_APPEND);
        return pref.getInt(CUR_LANGUAGE, 0);
    }

    public static List<clsSection> ChangeIndexSEQ(List<clsSection> pSource) {
        if (pSource != null && pSource.size() > 0) {
            clsSection oclsSection = pSource.get(0);
            if (oclsSection.getLable().equals("#")) {
                pSource.add(pSource.size(), oclsSection);
                pSource.remove(0);
            }
        }
        return pSource;
    }

    public static boolean isPadDevice(Context c) {
        return !SystemMethod.getSharedPreferences(c, "DeviceType").equals(
                "Phone");
    }

    /**
     * todo rPermission
     *
     * @param mContext
     */
    public static void addToCalendar(Context mContext) {
        try {
            int calId = 0;
            Cursor userCursor = mContext.getContentResolver().query(
                    Calendars.CONTENT_URI, null, null, null, null);

            // 先获取用户日历账户，如果没有，则初始化添加账户
            if (userCursor.getCount() > 0) {
                while (userCursor.moveToNext()) {
                    if (userCursor.getString(
                            userCursor.getColumnIndex(Calendars.ACCOUNT_NAME))
                            .equals(ACCOUNT_NAME)) {
                        calId = userCursor.getInt(userCursor
                                .getColumnIndex("_id"));
                        break;
                    }
                }
            }
            if (calId == 0) {
                calId = initCalendars2(mContext);
            }

            System.out.println("calId=" + calId);

            ContentValues event, values;
            Calendar calendar;
            Uri newEvent;
            ContentResolver cr;
            int year = 2017;
            int month = 9;// 减1
            int startDay = 10;
            long start01, end01, id;

            for (int i = 0; i < 3; i++) {
                event = new ContentValues();
                event.put(Events.TITLE, mContext.getString(R.string.notes));
                event.put(Events.DESCRIPTION,
                        mContext.getString(R.string.calendar_description));
                event.put(Events.EVENT_LOCATION,
                        mContext.getString(R.string.calendar_location));
                event.put(Events.CALENDAR_ID, calId);

                calendar = Calendar.getInstance();
                calendar.set(year, month, startDay + i, 9, 30);
                start01 = calendar.getTime().getTime();
                calendar.set(year, month, startDay + i, 17, 00);// 11.30
                end01 = calendar.getTime().getTime();

                event.put(Events.DTSTART, start01);
                event.put(Events.DTEND, end01);
                event.put(Events.HAS_ALARM, 1);
                event.put(CalendarContract.EXTRA_EVENT_ALL_DAY, 0);
                event.put(Events.STATUS, 1);
                event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID()
                        .toString());

                event.put(Events.GUESTS_CAN_MODIFY, false);// 参与者能否修改事件
                // 2016/10/28

                newEvent = mContext.getContentResolver().insert(
                        Events.CONTENT_URI, event);
                id = Long.parseLong(newEvent.getLastPathSegment());
                values = new ContentValues();
                values.put(Reminders.EVENT_ID, id);
                values.put(Reminders.MINUTES, 1 * 60 * 24);// 设置为提前一天提醒
                values.put(Reminders.EVENT_ID, id);
                values.put(Reminders.METHOD, Reminders.METHOD_ALERT);

                cr = mContext.getContentResolver(); // 为刚才新添加的event添加reminder
                cr.insert(Reminders.CONTENT_URI, values); // 调用这个方法返回值是一个Uri
            }

            DialogUtil.showConfirmAlertDialog(mContext, mContext.getString(R.string.addToCalendar_success));

        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showConfirmAlertDialog(mContext, mContext.getString(R.string.addToCalendar_fail));
        }
    }

    // 添加账户
    private static void initCalendars(Context context) {

        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(Calendars.NAME, "yy");

        value.put(Calendars.ACCOUNT_NAME, ACCOUNT_NAME);
        value.put(Calendars.ACCOUNT_TYPE, "com.android.exchange");
        value.put(Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_DISPLAY_NAME);
        value.put(Calendars.VISIBLE, 1);
        value.put(Calendars.CALENDAR_COLOR, -9206951);
        value.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
        value.put(Calendars.SYNC_EVENTS, 1);
        value.put(Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(Calendars.OWNER_ACCOUNT, ACCOUNT_NAME);
        value.put(Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Calendars.CONTENT_URI;
        calendarUri = calendarUri
                .buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER,
                        "true")
                .appendQueryParameter(Calendars.ACCOUNT_NAME, ACCOUNT_NAME)
                .appendQueryParameter(Calendars.ACCOUNT_TYPE,
                        "com.android.exchange").build();

        context.getContentResolver().insert(calendarUri, value);
    }

    /**
     * 添加账户
     *
     * @param context
     */
    private static int initCalendars2(Context context) {// 插入成功
        int calId = 3;
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();

        value.put("_id", calId);

        value.put(Calendars.NAME, CALENDAR_DISPLAY_NAME);
        value.put(Calendars.ACCOUNT_NAME, ACCOUNT_NAME);
        value.put(Calendars.ACCOUNT_TYPE, "com.adsale.HEATEC");// "com.android.exchange"
        value.put(Calendars.CALENDAR_DISPLAY_NAME,
                context.getString(R.string.app_name));
        value.put(Calendars.VISIBLE, 1);
        value.put(Calendars.CALENDAR_COLOR, -9206951);
        value.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
        value.put(Calendars.SYNC_EVENTS, 1);
        value.put(Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(Calendars.OWNER_ACCOUNT, ACCOUNT_NAME);
        value.put(Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Calendars.CONTENT_URI;
        calendarUri = calendarUri
                .buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER,
                        "true")
                .appendQueryParameter(Calendars.ACCOUNT_NAME, ACCOUNT_NAME)
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, "com.adsale.HEATEC")
                .build();

        context.getContentResolver().insert(calendarUri, value);

        return calId;
    }


    private static void get(String url, RequestParams rp,
                            AsyncHttpResponseHandler responseHandler) {
        new AsyncHttpClient().get(url, rp, responseHandler);
    }

    /**
     * writeFileToSD
     *
     * @param text
     * @param fileName
     */
    public static void writeFileToSD(String text, String fileName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            LogUtil.d("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }
        try {
            File file = new File("/sdcard/" + fileName);

            if (!file.exists()) {
                LogUtil.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            byte[] buf = text.getBytes();
            stream.write(buf);
            stream.close();

        } catch (Exception e) {
            LogUtil.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

    /**
     * read sdcard data
     *
     * @param fileName
     * @return
     */
    public static String readFromSD(String fileName) {
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // 获得SD卡对应的存储目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                // 获取指定文件对应的输入流
                FileInputStream fis = new FileInputStream("/sdcard/" + fileName);
                // 将指定输入流包装成BufferReader
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        fis));
                StringBuilder sb = new StringBuilder("");
                String line = null;
                // 循环读取文件内容
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * App is ruuning?
     *
     * @param context
     * @return
     */
    public static Boolean isRuuning(Context context) {
        boolean isAppRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String componentName = activityManager.getRunningTasks(1).get(0).topActivity
                .getPackageName();
        if (context.getPackageName().equals(componentName)) {
            isAppRunning = true;
        }
        return isAppRunning;
    }

    /**
     * Logs function
     *
     * @param actionId
     * @param type
     * @param subType
     * @param comId    展商id
     */
    public static void trackLogJson(Context context, int actionId, String type,
                                    String subType, String comId) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        if (logJsonArr == null) {
            logJsonArr = new ArrayList<LogJson>();
        }
        if (strLogJson == null) {
            strLogJson = new String();
        }

        LogJson logJson = new LogJson();

        logJson.ActionID = actionId;
        logJson.AppName = "CDCETEX2016";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logJson.CreateDate = df.format(Calendar.getInstance().getTime());
        logJson.DeviceID = tm.getDeviceId();
        // logJson.DeviceID = "4DAC8AA7-4B3C-4E63-A7CE-7483960C7987";
        if (comId != null) {
            logJson.ActionGroup = 200;
            switch (SystemMethod.getCurLanguage(context)) {
                case 1:
                    logJson.LangID = 950;
                    logJson.TrackingName = "Ad_" + type + "_" + subType + "_"
                            + comId + "_" + "_en_Android";
                    break;
                case 2:
                    logJson.LangID = 936;
                    logJson.TrackingName = "Ad_" + type + "_" + subType + "_"
                            + comId + "_" + "_sc_Android";
                    break;
                default:
                    logJson.LangID = 1252;
                    logJson.TrackingName = "Ad_" + type + "_" + subType + "_"
                            + comId + "_" + "_tc_Android";
                    break;
            }
        } else {
            logJson.ActionGroup = 100;
            logJson.TrackingName = subType;
        }
        logJson.Location = "";
        // logJson.IsTest = true;
        logJson.IsTest = "false";
        logJson.VisitorID = "-1";
        logJson.Platform = "Android" + android.os.Build.VERSION.RELEASE;
        if (getBooleanSharedPreferences(context, "IsRegister")) {
            logJson.PreregID = getSharedPreferences(context, "PreregID");
        } else {
            logJson.PreregID = "-1";
        }
        logJson.SubType = subType;
        logJson.TimeZone = "+8";
        logJson.TrackingOS = "Android";
        logJson.Type = type;
        logJson.Year = 2016;

        logJsonArr.add(logJson);

        // writeFileToSD(new Gson().toJson(logJsonArr), LOG_JSON_STR_KEY);//
        // save
        // // log
        // // data
        // // to
        // // sdcard
        //
        // strLogJson = readFromSD(LOG_JSON_STR_KEY);

        strLogJson = new Gson().toJson(logJsonArr);
        LogUtil.d("trackLogJson", "wai---strLogJson:" + strLogJson);
        LogUtil.d("trackLogJson",
                "wai---context.getPackageName():" + context.getPackageName());

        if (logJsonArr.size() >= 5 || !isRuuning(context)) {
            postLog(Configure.LOGJSON, strLogJson,// new
                    // Gson().toJson(logJsonArr)
                    new AsyncHttpResponseHandler() {

                        @Override
                        @Deprecated
                        public void onFailure(int statusCode, Throwable error,
                                              String content) {
                            // TODO Auto-generated method stub
                            super.onFailure(statusCode, error, content);
                            // logJsonArr.clear();
                            LogUtil.d("trackLogJson", "onFailureStatusCode:"
                                    + statusCode);
                        }

                        @Override
                        @Deprecated
                        public void onSuccess(int statusCode, Header[] headers,
                                              String content) {
                            // TODO Auto-generated method stub
                            super.onSuccess(statusCode, headers, content);
                            LogUtil.d("trackLogJson", "onSuccess---StatusCode:"
                                    + statusCode);
                            LogUtil.d("trackLogJson", "onSuccess---content:"
                                    + content);
                            logJsonArr.clear();
                            logJsonArr = null;
                        }
                    });
        }

    }

    /**
     * 跟踪view
     *
     * @param context
     * @param actionId 100/200/300/400/....
     * @param type     page/Ad/Event/
     * @param subType  (page,Ad)(,M1)
     * @param location (page,Ad)(,0)
     * @param comId    companyId
     */
    public static void trackViewLog(Context context, int actionId, String type,
                                    String subType, String location, String comId) {
        if (logJsonArr == null) {
            logJsonArr = new ArrayList<LogJson>();
        }
        if (strLogJson == null) {
            strLogJson = new String();
        }

        LogJson logJson = new LogJson();

        logJson.ActionID = actionId;
        logJson.AppName = "CDCETEX2016";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logJson.CreateDate = df.format(Calendar.getInstance().getTime());
        logJson.DeviceID = getDeviceId(context);
        if (logJson.DeviceID == null) {
            // android pad
            logJson.DeviceID = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
        }
        // logJson.DeviceID = "4DAC8AA7-4B3C-4E63-A7CE-7483960C7987";
        // if (comId != null) {
        if (comId != null && !comId.equals("")) {
            comId = comId + "_";
        } else {
            comId = "";
        }
        String subTypeStr = "";
        if (subType != null && !subType.equals("")) {
            subTypeStr = subType + "_";
        } else {
            subType = "";
        }

        String locationStr = "";
        if (location != null && !location.equals("")) {
            locationStr = location + "_";
        } else {
            location = "";
        }
        if (type.equals("Page")) {
            logJson.SubType = "";
            logJson.ActionGroup = 100;
        } else if (type.equals("Ad")) {
            logJson.SubType = subType;
            logJson.ActionGroup = 200;
        } else if (type.equals("Event")) {
            logJson.SubType = subType;
            logJson.ActionGroup = 300;
        } else if (type.equals("Download")) {
            logJson.ActionGroup = 400;
            logJson.SubType = subType;
        } else {
            logJson.ActionGroup = 500;
            logJson.SubType = subType;
        }

        String typeStr = type;
        if (type.equals("Download")) {
            typeStr = "DL";
        }
        switch (SystemMethod.getCurLanguage(context)) {
            case 1:

                logJson.LangID = 1252;
                logJson.TrackingName = typeStr + "_" + subTypeStr + locationStr
                        + comId + "en_Android";
                break;
            case 2:
                logJson.LangID = 950;
                logJson.TrackingName = typeStr + "_" + subTypeStr + locationStr
                        + comId + "sc_Android";
                break;
            default:
                logJson.LangID = 936;
                logJson.TrackingName = typeStr + "_" + subTypeStr + locationStr
                        + comId + "tc_Android";
                break;
        }
        logJson.Location = location;
        // logJson.IsTest = true;
        logJson.IsTest = "false";
        logJson.VisitorID = "-1";
        logJson.Platform = "Android" + android.os.Build.VERSION.RELEASE;
        if (getBooleanSharedPreferences(context, "IsRegister")) {
            logJson.PreregID = getSharedPreferences(context, "PreregID");
        } else {
            logJson.PreregID = "-1";
        }

        logJson.TimeZone = "+8";
        logJson.TrackingOS = "Android";
        logJson.Type = type;
        logJson.Year = 2016;

        logJsonArr.add(logJson);

        // 目的是为了退出之后再打开app还是会记得推出之前的事件
        // writeFileToSD(new Gson().toJson(logJsonArr), LOG_JSON_STR_KEY);
        // strLogJson = readFromSD(LOG_JSON_STR_KEY);

        strLogJson = new Gson().toJson(logJsonArr);

        if (logJsonArr.size() >= 5) {
            postLog(Configure.LOGJSON, strLogJson,// new
                    // Gson().toJson(logJsonArr)
                    new AsyncHttpResponseHandler() {

                        @Override
                        @Deprecated
                        public void onFailure(int statusCode, Throwable error,
                                              String content) {
                            // TODO Auto-generated method stub
                            super.onFailure(statusCode, error, content);
                            // logJsonArr.clear();
                            LogUtil.d("trackLogJson", "onFailureStatusCode:"
                                    + statusCode);
                        }

                        @Override
                        @Deprecated
                        public void onSuccess(int statusCode, Header[] headers,
                                              String content) {
                            // TODO Auto-generated method stub
                            super.onSuccess(statusCode, headers, content);
                            LogUtil.d("trackLogJson", "onSuccess---StatusCode:"
                                    + statusCode);
                            LogUtil.d("trackLogJson", "onSuccess---content:"
                                    + content);
                            logJsonArr.clear();
                            // logJsonArr = null;
                        }
                    });
        }

    }

    public static void trackPushLog(Context context, int actionId, String type,
                                    String subType, String location, String pushContent) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        if (logJsonArr == null) {
            logJsonArr = new ArrayList<LogJson>();
        }
        if (strLogJson == null) {
            strLogJson = new String();
        }

        if (pushContent != null) {
            if (pushContent.length() > 5) {
                pushContent = pushContent.substring(0, 5);
            }
        } else {
            pushContent = "";
        }

        LogJson logJson = new LogJson();

        logJson.ActionID = actionId;
        logJson.AppName = "CDCETEX2016";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logJson.CreateDate = df.format(Calendar.getInstance().getTime());
        logJson.DeviceID = tm.getDeviceId();
        if (logJson.DeviceID == null) {
            // android pad
            logJson.DeviceID = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
        }
        logJson.ActionGroup = 400;
        switch (SystemMethod.getCurLanguage(context)) {
            case 1:

                logJson.LangID = 1252;
                logJson.TrackingName = type + "_" + pushContent + "_en_Android";
                break;
            case 2:
                logJson.LangID = 950;
                logJson.TrackingName = type + "_" + pushContent + "_tc_Android";

                break;
            default:
                logJson.LangID = 936;
                logJson.TrackingName = type + "_" + pushContent + "_sc_Android";
                break;
        }
        logJson.Location = "";
        logJson.IsTest = "false";
        logJson.VisitorID = "-1";
        logJson.Platform = "Android" + android.os.Build.VERSION.RELEASE;
        if (getBooleanSharedPreferences(context, "IsRegister")) {
            logJson.PreregID = getSharedPreferences(context, "PreregID");
        } else {
            logJson.PreregID = "-1";
        }
        logJson.SubType = subType;
        logJson.TimeZone = "+8";
        logJson.TrackingOS = "Android";
        logJson.Type = type;
        logJson.Year = 2016;
        logJsonArr.add(logJson);

        strLogJson = new Gson().toJson(logJsonArr);
        LogUtil.d("Splash", "wai---strLogJson:" + strLogJson);
        LogUtil.d("Splash",
                "wai---context.getPackageName():" + context.getPackageName());

        if (logJsonArr.size() >= 1) {
            postLog(Configure.LOGJSON, strLogJson,// new
                    // Gson().toJson(logJsonArr)
                    new AsyncHttpResponseHandler() {

                        @Override
                        @Deprecated
                        public void onFailure(int statusCode, Throwable error,
                                              String content) {
                            // TODO Auto-generated method stub
                            super.onFailure(statusCode, error, content);
                            // logJsonArr.clear();
                            LogUtil.d("Splash", "onFailureStatusCode:" + statusCode);
                        }

                        @Override
                        @Deprecated
                        public void onSuccess(int statusCode, Header[] headers,
                                              String content) {
                            // TODO Auto-generated method stub
                            super.onSuccess(statusCode, headers, content);
                            LogUtil.d("Splash", "onSuccess---StatusCode:"
                                    + statusCode);
                            LogUtil.d("Splash", "onSuccess---content:" + content);
                            logJsonArr.clear();
                            // logJsonArr = null;
                        }
                    });
        }

    }

    public static void postLog(String url, String JsonStr,
                               AsyncHttpResponseHandler responseHandler) {
        RequestParams rp = new RequestParams();
        rp.put("logArr", JsonStr);
        postUrl(url, rp, responseHandler);
    }

    public static void postUrl(String url, RequestParams rp,
                               AsyncHttpResponseHandler responseHandler) {
        new AsyncHttpClient().post(url, rp, responseHandler);
    }

    public static String getDeviceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
        return sp.getString("deviceId", "");
    }


    /**
     * writeArrayListToTxt
     *
     * @param logArr data
     * @param path   save path
     */
    public void writeArrayListToTxt(ArrayList<String> logArr, String path) {
        File LogArrFile = new File(path);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(LogArrFile));
            for (int i = 0; i < logArr.size(); i++) {
                bw.write(logArr.get(i) + "\n");
                bw.newLine();
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * writeArrayListTxtToSdCard
     *
     * @param logArr   data
     * @param fileName file name
     */
    public static void writeArrayListTxtToSdCard(ArrayList<String> logArr,
                                                 String fileName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            LogUtil.d("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }
        try {
            File LogArrFile = new File("/sdcard/" + fileName);

            if (!LogArrFile.exists()) {
                LogUtil.d("TestFile", "Create the file:" + fileName);
                LogArrFile.createNewFile();
            }
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(LogArrFile));
            for (int i = 0; i < logArr.size(); i++) {
                bw.write(logArr.get(i) + "\n");
                bw.newLine();
            }
            bw.close();

        } catch (Exception e) {
            LogUtil.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

    /**
     * xUtils框架: 下载zip 并解压
     *
     * @param url        下载地址
     * @param targetPath 文件存放的目标路径 /mnt/sdcard/com.adsale.ChinaPlas/Seminar/
     * @param zipPath    zip包的路径 , e.g.:
     *                   /mnt/sdcard/com.adsale.ChinaPlas/Seminar/seminar.zip
     */
    public static void downZip(String url, final String targetPath,
                               final String zipPath) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(url, zipPath, new RequestCallBack<File>() {
            private long startTime;
            private long endTime;
            private long durTime;
            private String name;

            @Override
            public void onStart() {
                super.onStart();
                name = zipPath.substring(zipPath.lastIndexOf("/") + 1);
                LogUtil.i(TAG, "文件名=" + name);
                startTime = System.currentTimeMillis();
            }

            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                LogUtil.i(TAG, name + "下载完成");
                if (HttpDownHelper.unZipFile(zipPath, targetPath)) {
                    FileUtils.deleteFile(zipPath);
                    endTime = System.currentTimeMillis();
                    durTime = endTime - startTime;
                    LogUtil.i(TAG, "下载" + name + "的时间为：" + durTime + "ms");
                }
            }

            public void onFailure(
                    com.lidroid.xutils.exception.HttpException arg0, String arg1) {
                LogUtil.i(TAG, name + "下载失败");
                LogUtil.e(TAG, arg1);
            }
        });
    }

    public static String getSharedPreferencesSelf(Context context,
                                                  String preferences_name, String key) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static void setSharedPreferencesSelf(Context context,
                                                String preferences_name, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(preferences_name,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getAssetContent(Context context, String file,
                                         String savePath) {
        String content = "";
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            content = new String(buffer);
            is.close();
            FileUtils.writeFileToSD(content, savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 字符串半角转换为全角
     * <p>
     * 说明：半角空格为32,全角空格为12288.
     * <p>
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input -- 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String halfToFull(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) // 半角空格
            {
                c[i] = (char) 12288;
                continue;
            }

            // 根据实际情况，过滤不需要转换的符号
            // if (c[i] == 46) //半角点号，不转换
            // continue;

            if (c[i] > 32 && c[i] < 127) // 其他符号都转换为全角
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 功能：字符串全角转换为半角
     * <p>
     * 说明：全角空格为12288，半角空格为32
     * <p>
     * 其他字符全角(65281-65374)与半角(33-126)的对应关系是：均相差65248
     *
     * @param input -- 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String fullToHalf(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) // 全角空格
            {
                c[i] = (char) 32;
                continue;
            }

            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":").replace("，", ",");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String getName(int language, String tc, String en, String sc) {
        return language == 0 ? tc : language == 1 ? en : sc;
    }

    public static void registerIn(Context context) {
        context.getSharedPreferences("IsRegister", 0).edit()
                .putBoolean("Register", true).apply();
    }

    public static void registerOut(Context context) {
        context.getSharedPreferences("IsRegister", 0).edit()
                .putBoolean("Register", false).apply();
    }

    public static boolean isRegister(Context context) {
        return context.getSharedPreferences("IsRegister", 0).getBoolean(
                "Register", false);
    }

    public static ftpInformation getInformation(Context context) {
        String str_information = "";
        if (new File(App.RootDir + "Information/"
                + "information.txt").exists()) {
            str_information = FileUtils.readFromSD(App.RootDir
                    + "Information/"
                    + "information.txt");
        } else {
            str_information = SystemMethod.getAssetContent(context,
                    "information.txt", App.RootDir
                            + "Information/" + "information.txt");
        }
        return new Gson().fromJson(str_information, ftpInformation.class);

    }

    public static void hideSoftKeyboard(Context context) {
        // InputMethodManager imm = (InputMethodManager)
        // context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(((Activity)
        // context).getWindow().getDecorView().getWindowToken(), 0);

        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    /**
     * retrofit的构造
     *
     * @param cls     e.g.:NetworkClient.class
     * @param baseUrl cannot be "" or null, baseUrl must end in /
     * @param <T>     e.g.:NetworkClient
     * @return T
     */
    public static <T> T setupRetrofit(Class<T> cls, String baseUrl) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.client(App.mOkHttpClient).build();
        return retrofit.create(cls);
    }


}
