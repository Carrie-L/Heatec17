package com.adsale.HEATEC;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;


public class MyActivityManager {
	private static final String TAG = "MyActivityManager";
	public static List<Activity> activities=new ArrayList<Activity>();
	
	public static void add(Activity activity){
		activities.add(activity);
	}
	
	public static void finish(){
		for (Activity activity:activities) {
			activity.finish();
		}
	}
	
	
	
	
	

}