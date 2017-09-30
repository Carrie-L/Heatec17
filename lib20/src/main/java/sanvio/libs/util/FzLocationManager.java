package sanvio.libs.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.google.code.microlog4android.config.PropertyConfigurator;

/**
 * @author why
 */
public class FzLocationManager {
	private final String TAG = "FzLocationManager";
	private Context mContext;
	private LocationManager gpsLocationManager;
	private LocationManager networkLocationManager;
	private final int MINTIME = 60000;
	private final int MININSTANCE = 0;
	private Location lastLocation = null;
	private LocationCallBack mCallback;
	private Boolean gps_enabled = false, network_enabled = false;
	private MyLocationListener NetworkListener, GPSListener;
	private static final Logger logger = LoggerFactory.getLogger();
	private Boolean mIsLocation = false;

	public void init(Context c, LocationCallBack callback) {
		mContext = c;
		mCallback = callback;
		PropertyConfigurator.getConfigurator(mContext).configure();
		NetworkListener = new MyLocationListener(LocationManager.NETWORK_PROVIDER);
		GPSListener = new MyLocationListener(LocationManager.GPS_PROVIDER);
	}

	public void StartLocaion() {
		LocationManager lm;

		lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}
		networkLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		networkLocationManager.removeUpdates(NetworkListener);
		networkLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINTIME, MININSTANCE, NetworkListener);

		gpsLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		gpsLocationManager.removeUpdates(GPSListener);
		// gpsLocationManager.addGpsStatusListener(gpsListener);
		gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINTIME, MININSTANCE, GPSListener);
		// if(!gps_enabled && !network_enabled){
		// Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		// mContext.startActivity(intent);
		// }else {
		if (network_enabled) {
			lastLocation = networkLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		if (gps_enabled) {
			// Settings.Secure.setLocationProviderEnabled(mContext.getContentResolver(),
			// "gps", true);
			lastLocation = gpsLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		mIsLocation = true;
		// }
	}

	private void updateLocation(Location location) {
		lastLocation = location;
		mCallback.onCurrentLocation(location);
	}

	// private GpsStatus.Listener gpsListener = new GpsStatus.Listener(){
	// //GPS?嗆??????嗉圻??
	// @Override
	// public void onGpsStatusChanged(int event) {
	// //?瑕?敶??嗆?
	// GpsStatus gpsstatus=gpsLocationManager.getGpsStatus(null);
	// switch(event){
	// //蝚砌?甈∪?雿??隞?
	// case GpsStatus.GPS_EVENT_FIRST_FIX:
	// break;
	// //撘??摰???隞?
	// case GpsStatus.GPS_EVENT_STARTED:
	// break;
	// //??GPS?急??嗆?鈭辣
	// case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
	// logger.log(Level.DEBUG, "GPS_EVENT_SATELLITE_STATUS");
	// Iterable<android.location.GpsSatellite> allSatellites =
	// gpsstatus.getSatellites();
	// int count = 0;
	// for (android.location.GpsSatellite gpsSatellite : allSatellites) {
	// count++;
	// }
	// logger.log(Level.DEBUG,"Satellite Count:" + count);
	// break;
	// //?迫摰?鈭辣
	// case GpsStatus.GPS_EVENT_STOPPED:
	// Log.d("Location", "GPS_EVENT_STOPPED");
	// break;
	// }
	// }
	// };
	//
	private class MyLocationListener implements LocationListener {
		private String mProvider;

		@Override
		public void onLocationChanged(Location location) {

			logger.log(Level.DEBUG, "Provider:" + mProvider);
			logger.log(Level.DEBUG, "onLocationChanged");
			logger.log(Level.DEBUG, "Latitude:" + location.getLatitude());
			logger.log(Level.DEBUG, "Longitude:" + location.getLongitude());
			if (mProvider.equals(LocationManager.GPS_PROVIDER)) {
				updateLocation(location);
			} else if (mProvider.equals(LocationManager.NETWORK_PROVIDER) && !gps_enabled) {
				updateLocation(location);
			}

		}

		public MyLocationListener(String mProvider) {
			super();
			this.mProvider = mProvider;
		}

		@Override
		public void onProviderDisabled(String provider) {
			logger.log(Level.DEBUG, "onProviderDisabled:" + provider);
			if (provider.equals(LocationManager.GPS_PROVIDER)) {
				gps_enabled = false;
			} else {
				network_enabled = false;
			}

		}

		@Override
		public void onProviderEnabled(String provider) {
			logger.log(Level.DEBUG, "onProviderEnabled:" + provider);
			if (provider.equals(LocationManager.GPS_PROVIDER)) {
				gps_enabled = true;
			} else {
				network_enabled = true;
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			case LocationProvider.TEMPORARILY_UNAVAILABLE:

				logger.log(Level.DEBUG, "onStatusChanged:" + provider + " Your location is temporarily unavailable");
				break;
			case LocationProvider.OUT_OF_SERVICE:

				logger.log(Level.DEBUG, "onStatusChanged:" + provider + " Your location is now unavailable");

				break;
			case LocationProvider.AVAILABLE:
				logger.log(Level.DEBUG, "onStatusChanged:" + provider + " Your location is now available");
				break;
			}
		}

	}

	public Location getMyLocation() {
		return lastLocation;
	}

	public interface LocationCallBack {
		/**
		 * 敶?雿蔭
		 * 
		 * @param location
		 */
		void onCurrentLocation(Location location);
	}

	public void destoryLocationManager() {
		Log.d(TAG, "destoryLocationManager");
		gpsLocationManager.removeUpdates(GPSListener);
		networkLocationManager.removeUpdates(NetworkListener);

	}

	/**
	 * @return the isLocation
	 */
	public Boolean getIsLocation() {
		return mIsLocation;
	}

	public Location getLastKnownLocation() {
		Location location = gpsLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location == null)
			location = networkLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		return location;
	}

}