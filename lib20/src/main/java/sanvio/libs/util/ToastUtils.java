package sanvio.libs.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	public static void showNoServerException(final Context context) {
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(context, "連接服務器失敗，與我們聯繫。",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}

	public static void showNoServerException(final Context context,
			final int resID) {
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast
						.makeText(context, resID, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}

	public static void showToast(final Context context, final String text) {
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}

	public static void showToast(final Context context, final int text) {
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}
}
