package com.adsale.HEATEC.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.util.SystemMethod;

/**
 * to mainActivity
 * @author orz
 *
 */
public class AlarmBlankActivity extends AppCompatActivity {
	private Context mContext;
	private String oDeviceType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blank);
		
		SystemMethod.setBooleanSharedPreferences(mContext, "firstLaunched", false);
		oDeviceType = SystemMethod.getSharedPreferences(mContext, "DeviceType");
		Intent i ;
		if(oDeviceType.equals("Phone")){
			 
			i= new Intent(AlarmBlankActivity.this, MainActivity.class);
		}else{ 
			i= new Intent(AlarmBlankActivity.this, PadMainActivity.class);

		}
		startActivity(i);
	}
}
