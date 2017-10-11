package com.adsale.HEATEC;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.LoadingActivity;
import com.adsale.HEATEC.dao.ScheduleInfo;
import com.adsale.HEATEC.database.ScheduleInfoDBHelper;
import com.adsale.HEATEC.database.model.clsScheduleInfo;

public class AlarmReceiver extends BroadcastReceiver {

	public static final String ACTION = "com.adsale.HEATEC..AlarmReceiverNOTIFICATION_ALARM";
	// com.adsale.HEATEC..AlarmReceiverNOTIFICATION_ALARM
	ScheduleInfoDBHelper mDbHelper;

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent pIntent) {

		if (pIntent.getAction().equalsIgnoreCase(ACTION)
				&& pIntent.hasExtra("ScheduleID")) {
			mDbHelper = new ScheduleInfoDBHelper(context);
			ScheduleInfo oclClsScheduleInfo = mDbHelper.getSchedule(pIntent
					.getIntExtra("ScheduleID", 0));
			if (oclClsScheduleInfo == null)
				return;

			String message = oclClsScheduleInfo.getTitle() + "\r\n"
					+ oclClsScheduleInfo.getLocation() + "\r\n"
					+ oclClsScheduleInfo.getStartTime();

			NotificationManager mNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			
			String APPName = context.getString(R.string.app_name);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
					new Intent(context, LoadingActivity.class), 0);

			NotificationCompat.Builder oBuilder = new NotificationCompat.Builder(
					context)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle(APPName)
					.setStyle(
							new NotificationCompat.BigTextStyle()
									.bigText(message));
			Uri uri = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			oBuilder.setSound(uri);
			oBuilder.setContentText(message);
			oBuilder.setContentIntent(contentIntent);
			Notification notification = oBuilder.build();
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			mNotificationManager.notify(new Random().nextInt(), notification);
		}
	}

}