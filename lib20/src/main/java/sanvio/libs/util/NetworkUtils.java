package sanvio.libs.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtils {
	/**	 
	 * @param context
	 * @return 3 not network 
	 */
	public static int getNetWorkState(Context context) {
		int state = -1;
		WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info;

		if (mWifiManager.isWifiEnabled() && ipAddress != 0) {

			if (connectivity == null) {
				state = 1;
			} else {
				info = connectivity.getActiveNetworkInfo();
				if (info == null) {
					state = 1;
				} else {
					if (info.isAvailable()) {
						state = 0;
					} else {
						state = 1;
					}
				}
			}
		} else {
			if (connectivity == null) {
				state = 3;
			} else {
				info = connectivity.getActiveNetworkInfo();
				if (info == null) {
					state = 3;
				} else {
					if (info.isAvailable()) {
						state = 2;
					} else {
						state = 3;
					}
				}
			}
		}
		return state;
	}
}
