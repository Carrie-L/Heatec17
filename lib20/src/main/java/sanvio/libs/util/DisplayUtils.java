package sanvio.libs.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class DisplayUtils {

	private final static String SCREEN_PARAMETERS = "Screen parameters";
	private final static String SCREEN_WIDTH = "Screen width";
	private final static String SCREEN_HEIGHT = "Screen height";
	private final static String NOTIFICATION_BAR_HEIGHT = "Notification bar height";

	public static double Scaling = 0;// 0.96

	private static int ScreenWidth = 0;
	private static int ScreenHeight = 0;
	private static int ActionBarHeight = 0;

	public static int MarginsTop = 0;
	public static int MarginsLeft = 0;

	public static int BasicWidth = 320;
	public static int BasicHeight = 480;

	public void setBasicSize(int w, int h) {
		BasicWidth = w;
		BasicHeight = h;
	}

	public static void getScreenPix(Context context) {
		if (ScreenWidth == 0)
			ScreenWidth = getScreenWidth(context);

		if (ScreenHeight == 0)
			ScreenHeight = getScreenHeight(context);

		if (ActionBarHeight == 0)
			ActionBarHeight = getActionBarHeight(context);

		if (ScreenWidth == 0 || ScreenHeight == 0) {
			final DisplayMetrics displayMetrics = new DisplayMetrics();
			// Calculate Window Size
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			windowManager.getDefaultDisplay().getMetrics(displayMetrics);
			ScreenWidth = displayMetrics.widthPixels;
			ScreenHeight = displayMetrics.heightPixels;

			if (ScreenHeight < ScreenWidth) {
				int i = ScreenHeight;
				ScreenHeight = ScreenWidth;
				ScreenWidth = i;
			}

			SharedPreferences pref = context.getSharedPreferences(SCREEN_PARAMETERS, Context.MODE_APPEND);
			Editor editor = pref.edit();
			editor.putInt(SCREEN_WIDTH, ScreenWidth);
			editor.putInt(SCREEN_HEIGHT, ScreenHeight);
			editor.commit();
		}
	}

	public static void calculateScaling(Context context) {
		getScreenPix(context);
		Scaling = BasicWidth / (double) ScreenWidth;
		if (getScaledValue(BasicHeight) > ScreenHeight) {
			Scaling = BasicHeight / (double) ScreenHeight;
			MarginsLeft = ScreenWidth - getScaledValue(BasicWidth);
			MarginsTop = 0;
		} else {
			MarginsTop = ScreenHeight - getScaledValue(BasicHeight);
			MarginsLeft = 0;
		}
	}

	public static int getScreenWidth(Context c) {
		if (ScreenWidth == 0) {
			SharedPreferences pref = c.getSharedPreferences(SCREEN_PARAMETERS, Context.MODE_APPEND);
			ScreenWidth = pref.getInt(SCREEN_WIDTH, 0);
		}
		return ScreenWidth;
	};

	public static int getScreenHeight(Context c) {
		if (ScreenHeight == 0) {
			SharedPreferences pref = c.getSharedPreferences(SCREEN_PARAMETERS, Context.MODE_APPEND);
			ScreenHeight = pref.getInt(SCREEN_HEIGHT, 0);
		}
		return ScreenHeight;
	};

	public static void setActionBarHeight(Context c, int height) {
		if (height != 0) {
			ActionBarHeight = height;
			SharedPreferences pref = c.getSharedPreferences(SCREEN_PARAMETERS, Context.MODE_APPEND);
			Editor editor = pref.edit();
			editor.putInt(SCREEN_WIDTH, ScreenWidth);
			editor.putInt(SCREEN_HEIGHT, ScreenHeight);
			editor.putInt(NOTIFICATION_BAR_HEIGHT, ActionBarHeight);
			editor.commit();
		}
	};

	public static int getActionBarHeight(Context c) {
		if (ActionBarHeight == 0) {
			SharedPreferences pref = c.getSharedPreferences(SCREEN_PARAMETERS, Context.MODE_APPEND);
			ActionBarHeight = pref.getInt(NOTIFICATION_BAR_HEIGHT, 0);
		}
		return ActionBarHeight;
	};

	public static int getScaledValue(int val) {
		return (int) Math.rint(val / Scaling);
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static void resetSize(View view, int widthSize, int heightSize, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
		if (view != null) {
			if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
				LinearLayout.LayoutParams layoutParams = null;
				layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
				if (layoutParams != null) {
					if (widthSize != 0) {
						layoutParams.width = getScaledValue(widthSize);
					}
					if (heightSize != 0) {
						layoutParams.height = getScaledValue(heightSize);
					}
					if (rightMargin != 0) {
						layoutParams.rightMargin = getScaledValue(rightMargin);
					}
					if (leftMargin != 0) {
						layoutParams.leftMargin = getScaledValue(leftMargin);
					}
					if (topMargin != 0) {
						layoutParams.topMargin = getScaledValue(topMargin);
					}
					if (bottomMargin != 0) {
						layoutParams.bottomMargin = getScaledValue(bottomMargin);
					}
				}
			} else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
				RelativeLayout.LayoutParams layoutParams = null;
				layoutParams = (LayoutParams) view.getLayoutParams();
				if (layoutParams != null) {
					if (widthSize != 0) {
						layoutParams.width = getScaledValue(widthSize);
					}
					if (heightSize != 0) {
						layoutParams.height = getScaledValue(heightSize);
					}
					if (rightMargin != 0) {
						layoutParams.rightMargin = getScaledValue(rightMargin);
					}
					if (leftMargin != 0) {
						layoutParams.leftMargin = getScaledValue(leftMargin);
					}
					if (topMargin != 0) {
						layoutParams.topMargin = getScaledValue(topMargin);
					}
					if (bottomMargin != 0) {
						layoutParams.bottomMargin = getScaledValue(bottomMargin);
					}
				}
			}
		}
	}
}
