package com.adsale.HEATEC.util;

import java.util.Random;

import com.adsale.HEATEC.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;

public class NotificationHelper {
	public static final String FILTER_ACTION = "com.youchok.DISPLAY_MESSAGE";
	public static final String EXTRA_MESSAGE = "message";

	public static void generateNotification(Context context, Intent postIntent, Class<?> pStartActivity) {
		String message = postIntent.getStringExtra("message");
		String APPName = context.getString(R.string.app_name);

		Intent intent = new Intent(FILTER_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);

		Intent notificationIntent = new Intent(context, pStartActivity);
		notificationIntent.putExtra("ShowMessage", true);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		int requestID = (int) System.currentTimeMillis();
		PendingIntent oContentIntent = PendingIntent.getActivity(context, requestID, notificationIntent, 0);

		Builder oBuilder = new Builder(context);
		oBuilder.setSmallIcon(R.drawable.ic_launcher);
		oBuilder.setWhen(System.currentTimeMillis());
		oBuilder.setContentIntent(oContentIntent);

		if (android.os.Build.VERSION.SDK_INT >= 16) {
			oBuilder.setContentTitle(APPName);
			oBuilder.setContentText(message);
			oBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
		} else {
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.view_notice);
			rv.setTextViewText(R.id.txtMsg, message);
			rv.setTextViewText(R.id.txtTitle, context.getString(R.string.app_name));
			oBuilder.setContent(rv);
		}

		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		oBuilder.setSound(uri);
		Notification notification = oBuilder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(new Random().nextInt(), notification);
	}

}
