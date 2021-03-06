package sanvio.libs.util;

import java.util.List;
import java.util.UUID;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class SystemUtil {
	/**
	 * Return version name.
	 * 
	 * @return (ex) 0.0.1 (if Fail) fail
	 */
	public static String versionName(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(),
					PackageManager.GET_META_DATA);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "fail";
		}
	}

	/**
	 * Return version code.
	 * 
	 * @return (ex) 1 (if Fail) -1
	 */
	public static int versionCode(Context context) {
		PackageInfo info;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(),
					PackageManager.GET_META_DATA);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Return phone (model) name.
	 * 
	 * @return HTC Desire, GT-P7510
	 */
	public static String modelName() {
		return Build.MODEL.toString();
	}

	/**
	 * Return phoneNumber
	 * 
	 * @return
	 */
	public static String phoneNumber(Context context) {
		TelephonyManager teleponyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return teleponyManager.getLine1Number();
	}

	/**
	 * <br>
	 * The inspection process is running at the top
	 * 
	 * @return true=foreground
	 */
	public static boolean isForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = activityManager.getRunningTasks(1);
		ComponentName cn = list.get(0).topActivity;
		String name = cn.getPackageName();
		return name.indexOf(context.getPackageName()) > -1;
	}

	/**
	 * Check the app is alive
	 * 
	 * @return true=alive
	 */
	public static boolean isAppAlive(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = activityManager.getRunningTasks(100);
		for (RunningTaskInfo task : list) {
			ComponentName cn = task.topActivity;
			String name = cn.getPackageName();
			if (name.indexOf(context.getPackageName()) > -1)
				return true;
		}
		return false;
	}

	/**
	 * Inspect the highest activity in the left
	 * 
	 * @param Activity
	 *            Name <br>
	 *            Chat.class.getName() = com.caffein.testActivity
	 * @return
	 */
	public static boolean isTopActivity(Context context,String name) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = activityManager.getRunningTasks(1);
		ComponentName cn = list.get(0).topActivity;
		return cn.getClassName().equals(name);
	}

	/**
	 * At the bottom of the stack check if the name of the database activity
	 * 
	 * @param Activity
	 *            Name
	 * @return
	 */
	public static boolean isBaseActivity(Context context,String name) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = activityManager.getRunningTasks(1);
		ComponentName cn = list.get(0).baseActivity;
		return cn.getClassName().equals(name);
	}

	/**
	 * Is Wi-Fi connected?
	 * 
	 * @return true=connected
	 */
	public static boolean isConnectedWiFi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
	}

	/**
	 * Is MobileNetwork connected?
	 * 
	 * @return true=connected
	 */
	public static boolean isConnectedMobileNetwork(Context context) {
		boolean kResult = false;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			kResult = connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).isConnected();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kResult;
	}

	/**
	 * Is any network connected?
	 * 
	 * @return true=connected
	 */
	public static boolean isConnectNetwork(Context context) {
		return isConnectedWiFi(context) || isConnectedMobileNetwork(context);
	}

	/**
	 * Return android_id<br />
	 * (If factory reset, android_id is changed)
	 * 
	 * @param context
	 * @return
	 */
	public static String androidId(Context context) {
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * Return debuggable value in menifest
	 * 
	 * @return true=debuggable
	 */
	public static boolean isDebuggable(Context context) {
		boolean kResult = false;
		if (context == null)
			kResult = false;
		else
			kResult = ((context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
		return kResult;
	}

	/**
	 * Return Sd-Card device that you have.
	 * 
	 * @return ture=have
	 */
	public static boolean isHasSDCard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * Vibrate
	 * 
	 * @param content
	 * @param msec
	 *            (time)
	 */
	public static void vibrate(Context context, int $msec) {
		try {
			Vibrator vibrator = (Vibrator) context
					.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate($msec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String GetDeviceID(Context context){
		String deviceId="";
		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		    final String tmDevice, tmSerial, androidId;
		    tmDevice = "" + tm.getDeviceId();
		    tmSerial = "" + tm.getSimSerialNumber();
		    androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		    deviceId = deviceUuid.toString();
		  
		}catch (Exception e) {
			e.printStackTrace();
		}
		return deviceId;
	}
	
	public static String getAndroidVersion() {
	    int sdkVersion = Build.VERSION.SDK_INT;
	    return "Android:" + sdkVersion ;
	}
}