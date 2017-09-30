package gcmhelper;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;


public abstract class GCMHelper {
	public static final String TAG = "GCMTools";
//	public final String SENDER_ID = "274089126550";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_SAVE_BACKEND = "isSaveToBackend";
	private static final String PROPERTY_GCM_SETTING = "GCMSetting";
	protected  Context mContext;
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	public boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) mContext,PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				// finish();
			}
			return false;
		}
		return true;
	}

	public GCMHelper(Context context) {
		super();
		this.mContext = context;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public  String getRegistrationId() {
		final SharedPreferences prefs = getGCMPreferences();
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,Integer.MIN_VALUE);
		int currentVersion = getAppVersion();
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}
	
	public  void CheckIsSaveToBack(final String regId) {
		final SharedPreferences prefs = getGCMPreferences();
		boolean IsSaveToBack = prefs.getBoolean(PROPERTY_SAVE_BACKEND, false);
		if (IsSaveToBack==false) {
			new AsyncTask<String, String, Boolean>(){
				@Override
				protected Boolean doInBackground(String... params) {
					// TODO Auto-generated method stub
					return sendRegistrationIdToBackend(regId);
				}

				@Override
				protected void onPostExecute(Boolean result) {
					// TODO Auto-generated method stub
					if (result) {
						SharedPreferences.Editor editor = prefs.edit();
					    editor.putBoolean(PROPERTY_SAVE_BACKEND, true);
					    editor.commit();
					}
				}
				
				}.execute();
			
		}
		
	}
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private  int getAppVersion() {
		try {
			PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private  SharedPreferences getGCMPreferences() {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return mContext.getSharedPreferences(PROPERTY_GCM_SETTING,Context.MODE_PRIVATE);
	
	}
	
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */


	public void registerInBackground() {
		new RegisterAsyncTask().execute();
	}

	public class RegisterAsyncTask extends AsyncTask<String, String, String>{
		private String regid;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			GoogleCloudMessaging gcm = null;
			String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(mContext);
                }
                regid = gcm.register(getSenderID());
                msg = "Device registered, registration ID=" + regid;
                Log.v(TAG, msg);
                // You should send the registration ID to your server over HTTP,
                // so it can use GCM/HTTP or CCS to send messages to your app.
                // The request to your server should be authenticated if your app
                // is using accounts.
                boolean blnIsSave= sendRegistrationIdToBackend(regid);

                // For this demo: we don't need to send it because the device
                // will send upstream messages to a server that echo back the
                // message using the 'from' address in the message.

                // Persist the regID - no need to register again.
                storeRegistrationId(regid,blnIsSave);
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
                // If there is an error, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off.
            }
            return msg;
		}
		
	} 
	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	 * or CCS to send messages to your app. Not needed for this demo since the
	 * device sends upstream messages to a server that echoes back the message
	 * using the 'from' address in the message.
	 * @return 
	 */
//	private boolean sendRegistrationIdToBackend(String regId) {
//	    // Your implementation here.
//		MasterWSHelper oMasterWSHelper=new MasterWSHelper(mContext);
//		return oMasterWSHelper.RegAndroidDTToDataBase(regId);
//	}
	public abstract boolean sendRegistrationIdToBackend(String regId);
	public abstract String getSenderID();
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param mContext application's mContext.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(String regId,boolean isSaveToBackend) {
	    final SharedPreferences prefs = getGCMPreferences();
	    int appVersion = getAppVersion();
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.putBoolean(PROPERTY_SAVE_BACKEND, isSaveToBackend);
	    editor.commit();
	}
}
