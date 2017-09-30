package com.adsale.HEATEC;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.adsale.HEATEC.activity.AlarmReceiverDialogActivity;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LocalAlarmReceiver extends BroadcastReceiver {
	private int nid=0;
	private String alarmModule=null;
	
	private SimpleDateFormat sdf;
	private String dateTime;
	@Override
    public void onReceive(Context context, Intent data){
		@SuppressWarnings("static-access")
		AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String componentName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        boolean isAppRunning = false;
        if (context.getPackageName().equals(componentName)) {
        	isAppRunning = true;
        }
        
		nid = data.getIntExtra("nid", 0);
		alarmModule = data.getStringExtra("alarmModule");
		if(alarmModule == null || alarmModule.trim().equals(""))
		{
			alarmModule = "";
		}

		if(!isAppRunning)
		{
			SystemMethod.setSharedPreferences(context, "alarmModule", alarmModule);
			SystemMethod.setSharedPreferences(context, "phoneAlarmModule", alarmModule);

			//用户未打开应用时采用通知的形式推送提醒用户
			NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification n = data.getParcelableExtra("n");
			nm.notify(nid, n);
			
			sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			dateTime=sdf.format(new Date());
			LogUtil.d("Splash", "-Splash--dateTime----"+dateTime);
			//:LocalNotification trackingName: LNotification_201506221425_tc_iOS 
//			SystemMethod.trackPushLog(context, 411, "LocalMsg", "", "", dateTime);
		}
		else
		{//用户打开应用时采用弹Dialog的形式推送提醒用户
			String notifiTitle = data.getStringExtra("notifiTitle");
			String notifiContent = data.getStringExtra("notifiContent");
			
			Intent i = new Intent(context, AlarmReceiverDialogActivity.class);
			i.putExtra("notifiTitle", notifiTitle);
			i.putExtra("notifiContent", notifiContent);
			i.putExtra("alarmModule", alarmModule);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, nid, data, PendingIntent.FLAG_UPDATE_CURRENT);
		am.cancel(pendingIntent);
		
		String alarmNid =SystemMethod.getSharedPreferences(context, "alarmNid");
		String[] alarmNidArr = alarmNid.split("####");
		alarmNid = "";
		for(int i = 0; i < alarmNidArr.length; i++)
		{
			if(!alarmNidArr[i].equals("") && Integer.parseInt(alarmNidArr[i]) != nid)
			{
				alarmNid = alarmNid + "####" + alarmNidArr[i];
			}
		}
		if (alarmNid.startsWith("####"))
		{
			alarmNid = alarmNid.substring(4);
		}
		SystemMethod.setSharedPreferences(context, "alarmNid",alarmNid);
	}
}
