package sanvio.libs.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.TextView;

public class DialogUtils {

	public static void showAskDialog(final Context mContext, int resID, OnClickListener pOKClickListener, OnClickListener pCancelClickListener) {
		AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
		ad.setMessage(mContext.getString(resID));
		ad.setPositiveButton("確定", pOKClickListener);
		ad.setNegativeButton("取消", pCancelClickListener);
		ad.create().show();
	}

	public static void showAskDialog(final Context mContext, String msg, OnClickListener pOKClickListener, OnClickListener pCancelClickListener) {
		AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
		ad.setMessage(msg);
		ad.setPositiveButton("確定", pOKClickListener);
		ad.setNegativeButton("取消", pCancelClickListener);
		ad.create().show();
	}

	public static void ConfirmExit(final Context mContext) {
		AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
		ad.setTitle("退出");
		ad.setMessage("請問您是否退出該應用?");
		ad.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int i) {
				((Activity) mContext).finish();
				final ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
				am.killBackgroundProcesses(mContext.getPackageName());
			}
		});
		ad.setNegativeButton("取消", null);
		ad.show();
	}

	public static ProgressDialog CreateProgressDialog(Context pContext, int ResourceID) {
		ProgressDialog oProgressDialog;
		oProgressDialog = new ProgressDialog(pContext);
		oProgressDialog.setCancelable(false);
		oProgressDialog.setCanceledOnTouchOutside(false);
		oProgressDialog.setMessage(pContext.getString(ResourceID));
		return oProgressDialog;

	}

	public static void showAlertDialog(Context context, String msg, final AlertCallback alertCallback) {
		new AlertDialog.Builder(context).setMessage(msg).setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (alertCallback != null)
					alertCallback.sure();
			}
		}).show();
	}

	public static void showAlertDialog(Context context, int msgID, final AlertCallback alertCallback) {
		String msg = context.getString(msgID);
		showAlertDialog(context, msg, alertCallback);
	}

	public interface AlertCallback {
		public void sure();
	}

	public static void showAlertDialog(Context context, int messageResID, int positiveResID, int negativeResID, OnClickListener positiveClickListener,
			OnClickListener negativeClickListener) {
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		ad.setMessage(messageResID);
		ad.setPositiveButton(positiveResID, positiveClickListener);
		if (negativeResID > 0) {
			ad.setNegativeButton(negativeResID, negativeClickListener);
		}
		ad.create().show();
	}

	public static void showAlertDialog(Context context, String message, int positiveResID, int negativeResID, OnClickListener positiveClickListener,
			OnClickListener negativeClickListener) {
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		ad.setMessage(message);
		ad.setPositiveButton(positiveResID, positiveClickListener);
		if (negativeResID > 0) {
			ad.setNegativeButton(negativeResID, negativeClickListener);
		}
		ad.create().show();
	}

	public static void showAlertDialog(Context context, int pTitleID, int messageResID, int positiveResID, int negativeResID,
			OnClickListener positiveClickListener, OnClickListener negativeClickListener) {
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		ad.setTitle(pTitleID);
		ad.setMessage(messageResID);
		ad.setPositiveButton(positiveResID, positiveClickListener);
		if (negativeResID > 0) {
			ad.setNegativeButton(negativeResID, negativeClickListener);
		}
		ad.create().show();
	}

	public static void showAlertDialog(Context context, int theme, int pTitleID, int messageResID, int positiveResID, int negativeResID,
			OnClickListener positiveClickListener, OnClickListener negativeClickListener) {
		AlertDialog.Builder ad;
		if (theme > 0) {
			ad = new AlertDialog.Builder(new ContextThemeWrapper(context, theme));
		} else {
			ad = new AlertDialog.Builder(context);
		}

		if (pTitleID != -1) {
			ad.setTitle(pTitleID);
		}
		TextView txtMsg = new TextView(context);
		int padding = DisplayUtils.dip2px(context, 10);
		int padding2 = DisplayUtils.dip2px(context, 15);
		txtMsg.setPadding(padding, padding2, padding, padding2);
		txtMsg.setGravity(Gravity.CENTER);
		txtMsg.setText(messageResID);
		txtMsg. setMovementMethod(ScrollingMovementMethod.getInstance()  );

		ad.setView(txtMsg);
		ad.setPositiveButton(positiveResID, positiveClickListener);

		if (negativeResID > 0) {
			ad.setNegativeButton(negativeResID, negativeClickListener);
		}

		ad.create().show();
	}

	public static void showAlertDialog(Context context, int theme, String pTitleID, String messageResID, int positiveResID, int negativeResID,
			OnClickListener positiveClickListener, OnClickListener negativeClickListener) {
		int textColor = 0;
		AlertDialog.Builder ad;
		if (theme > 0) {
			ad = new AlertDialog.Builder(new ContextThemeWrapper(context, theme));
			textColor = Color.BLACK;
		} else {
			ad = new AlertDialog.Builder(context);
			textColor = Color.WHITE;
		}

		if (!pTitleID.isEmpty()) {
			ad.setTitle(pTitleID);
		}
		TextView txtMsg = new TextView(context);
		int padding = DisplayUtils.dip2px(context, 10);
		int padding2 = DisplayUtils.dip2px(context, 15);
		txtMsg.setPadding(padding, padding2, padding, padding2);
		txtMsg.setGravity(Gravity.CENTER);
		txtMsg.setText(messageResID);
		txtMsg.setTextColor(textColor);
		txtMsg. setMovementMethod(ScrollingMovementMethod.getInstance()  );

		ad.setView(txtMsg);
		ad.setPositiveButton(positiveResID, positiveClickListener);

		if (negativeResID > 0) {
			ad.setNegativeButton(negativeResID, negativeClickListener);
		}

		ad.create().show();
	}

}
