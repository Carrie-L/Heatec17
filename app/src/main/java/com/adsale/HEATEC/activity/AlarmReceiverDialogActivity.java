package com.adsale.HEATEC.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.baidu.mobstat.StatService;

public class AlarmReceiverDialogActivity extends AppCompatActivity {
    private String alarmModule = "";
    private String companyID = "";
    private String notifiContent = "";
    private String notifiTitle = "";
    private Context mContext;

    private SimpleDateFormat sdf;
    private String dateTime;
    private String oDeviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        alarmModule = this.getIntent().getStringExtra("alarmModule");
        companyID = this.getIntent().getStringExtra("companyID");

        notifiTitle = this.getIntent().getStringExtra("notifiTitle");

        LogUtil.d("TAG", "notifiTitle---:" + notifiTitle);
        notifiContent = this.getIntent().getStringExtra("notifiContent");
        oDeviceType = SystemMethod.getSharedPreferences(mContext, "DeviceType");
        new AlertDialog.Builder(this)
                .setTitle(notifiTitle)
                .setMessage(notifiContent)
                .setNegativeButton(
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                .setPositiveButton(
                        R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (notifiContent == null) {
                                    notifiContent = "";
                                }
                                SystemMethod.trackPushLog(AlarmReceiverDialogActivity.this, 411, "LocalMsg", "", "", notifiContent);
                                Intent i;
                                if (oDeviceType.equals("Phone")) {
                                    i = new Intent(
                                            AlarmReceiverDialogActivity.this,
                                            MainActivity.class);
                                } else {
                                    i = new Intent(
                                            AlarmReceiverDialogActivity.this,
                                            PadMainActivity.class);
                                }
                                i.putExtra("alarmModule", alarmModule);
                                i.putExtra("isRunning", true);
                                startActivity(i);

                                dialog.dismiss();
                                finish();
                            }
                        }).show();
    }

}
