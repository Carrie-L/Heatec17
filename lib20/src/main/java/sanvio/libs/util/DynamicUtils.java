package sanvio.libs.util;

import android.content.Context;

public class DynamicUtils {
	
	public static String FilePathRoot;
	
	public static double Scaling = 0;// 0.96

	public static int ScreenWidth = 0;
	public static int ScreenHeight = 0;
	public static int ActionBarHeight;

	public static int MarginsTop = 0;
	public static int MarginsLeft = 0;
	
	public static int BasicWidth = 640;
	public static int BasicHeight=760;
	
	
	public static void calculateScaling(Context context,int width,int height) {
		ScreenWidth=width;
		ScreenHeight=height;
		
		Scaling = BasicWidth / (double) ScreenWidth;
		if (getScaledValue(BasicHeight) > ScreenHeight) {
			Scaling = BasicHeight / (double) ScreenHeight;
			MarginsLeft = (ScreenWidth - getScaledValue(BasicWidth))/2;
			MarginsTop = 0;
		} else {
			MarginsTop = (ScreenHeight - getScaledValue(BasicHeight))/2;
			MarginsLeft = 0;
		}
	}
	
	public static int getScaledValue(int val) {
		return (int) Math.rint(val / Scaling);
	}
}
