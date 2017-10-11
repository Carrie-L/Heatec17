package com.adsale.HEATEC.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.adsale.HEATEC.R;


/**
 * Created by Carrie on 2017/10/11.
 */

public class DialogUtil {
    /**
     * 只有一個“確定/OK”按鈕
     */
    public static void showConfirmAlertDialog(Context context, String msg) {
        new AlertDialog.Builder(context).setMessage(msg).setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 只有一個“取消/Cancel”按鈕
     */
    public static void showCancelAlertDialog(Context context, String msg) {
        new AlertDialog.Builder(context).setMessage(msg).setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 確定|取消 兩個按鈕
     */
    public static void showAlertDialog(Context context, int msgRes, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setMessage(context.getString(msgRes))
                .setPositiveButton(R.string.ok, listener)
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 確定|取消 兩個按鈕
     */
    public static void showAlertDialog(Context context, int msgRes, int okRes, int cancelRes, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setMessage(context.getString(msgRes))
                .setPositiveButton(okRes, listener)
                .setNegativeButton(context.getString(cancelRes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 圓形progress bar dialog
     */
    public static ProgressDialog createProgressDialog(Context pContext, int ResourceID) {
        ProgressDialog oProgressDialog;
        oProgressDialog = new ProgressDialog(pContext);
        oProgressDialog.setCancelable(false);
        oProgressDialog.setCanceledOnTouchOutside(false);
        oProgressDialog.setMessage(pContext.getString(ResourceID));
        return oProgressDialog;
    }

}
