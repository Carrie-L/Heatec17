package com.adsale.HEATEC.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;

import org.apache.http.NameValuePair;

import sanvio.libs.util.DialogUtils;
import sanvio.libs.util.NetworkUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.activity.WebContentActivity;
import com.adsale.HEATEC.base.BaseFragment;
import com.adsale.HEATEC.database.WebContentDBHelper;
import com.adsale.HEATEC.database.model.clsMainIcon;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.util.network.Configure;
import com.baidu.mobstat.StatService;

public class SubscribeFragment extends BaseFragment {
	public static final String TAG = "SubscribeFragment";
	public static final String EVENTID = "SubscribeF";
	private EditText txtName, txtEmail, txtCompany;
	private TextView txtPrivacy;
	private TextView txt30, txt31, txt32, txt33;
	private Button btnSend, btnReset;
	private int mLanguage = 0;
	private Context mContext;
	private View mBaseView;
	private String mTitle;
	private Dialog mProgressDialog;
	private String strMsg;

	@Override
	public View initView(LayoutInflater inflater) {
		mBaseView = inflater.inflate(R.layout.activity_subscribe, null);
		mContext = getActivity();

		findView();

		return mBaseView;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		setupView();
	}

	private void findView() {
		txtName = (EditText) mBaseView.findViewById(R.id.txtName);
		txtEmail = (EditText) mBaseView.findViewById(R.id.txtEmail);
		txtCompany = (EditText) mBaseView.findViewById(R.id.txtCompany);
		txtPrivacy = (TextView) mBaseView.findViewById(R.id.txtPrivacy);
		txt30 = (TextView) mBaseView.findViewById(R.id.txt30);
		txt31 = (TextView) mBaseView.findViewById(R.id.txt31);
		txt32 = (TextView) mBaseView.findViewById(R.id.txt32);
		txt33 = (TextView) mBaseView.findViewById(R.id.txt33);
		btnSend = (Button) mBaseView.findViewById(R.id.btnSend);
		btnReset = (Button) mBaseView.findViewById(R.id.btnReset);
	}

	private void setupView() {
		mLanguage = SystemMethod.getCurLanguage(mContext);

		InputFilter[] CompanyFilters = { new sanvio.libs.util.SanvioLengthFilter(0, 100, R.string.Input_Outlimit_Msg, mContext) };
		txtCompany.setFilters(CompanyFilters);

		InputFilter[] NameFilters = { new sanvio.libs.util.SanvioLengthFilter(0, 50, R.string.Input_Outlimit_Msg, mContext) };
		txtName.setFilters(NameFilters);

		InputFilter[] EmailFilters = { new sanvio.libs.util.SanvioLengthFilter(0, 50, R.string.Input_Outlimit_Msg, mContext) };
		txtEmail.setFilters(EmailFilters);

		btnReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ResetForm();
			}
		});
		btnSend.setOnClickListener(SendOnClickListener);
		// txtName.setText("hongli");
		// txtEmail.setText("li.hong@roxvell.com");
		// txtCompany.setText("Sanvio");
		txtPrivacy.setOnClickListener(mPrivacyClickListener);

		WebContentDBHelper oWebContentDBHelper = new WebContentDBHelper(mContext);
		txt30.setText(oWebContentDBHelper.getContent(30, mLanguage));
		txt31.setText(oWebContentDBHelper.getContent(31, mLanguage));
		txt32.setText(oWebContentDBHelper.getContent(32, mLanguage));
		txt33.setText(oWebContentDBHelper.getContent(33, mLanguage));
		txtPrivacy.setText(oWebContentDBHelper.getContent(34, mLanguage));

	}

	private OnClickListener mPrivacyClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// <item>私隱政策聲明</item>
			clsMainIcon oclsMainIcon;
			oclsMainIcon = new clsMainIcon();
			oclsMainIcon.setIconID(99 + "");
			oclsMainIcon.setTitle(mLanguage, mContext.getString(R.string.subscribe_str005));
			Intent intent = new Intent(mContext, WebContentActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("clsMainIcon", oclsMainIcon);
			intent.putExtra("Title", mContext.getString(R.string.subscribe_str005));
			startActivity(intent);
			if (SystemMethod.getSharedPreferences(mContext, "DeviceType").equals("Pad")) {
				((Activity) mContext).overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
			}
		}
	};

	private void ResetForm() {
		// SystemMethod.trackLogJson(mContext, 100, "event", "ResetSubscribe",
		// null);
		txtName.setText("");
		txtEmail.setText("");
		txtCompany.setText("");
	}

	private OnClickListener SendOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (NetworkUtils.getNetWorkState(mContext) == 3) {
				DialogUtils.showAlertDialog(mContext, R.string.NoNetwok_Msg, null);
				return;
			}

			// SystemMethod.trackLogJson(mContext, 100, "event",
			// "SendSubscribe", null);
			SystemMethod.trackViewLog(mContext, 303, "Event", "SendSubscribe", "0", null);

			String strCompany = txtCompany.getText().toString();
			if (strCompany == null || strCompany.trim().equals("")) {
				DialogUtils.showAlertDialog(mContext, R.string.register_msg013, null);
				txtCompany.requestFocus();
				return;
			}

			String strName = txtName.getText().toString();
			if (strName == null || strName.trim().equals("")) {
				DialogUtils.showAlertDialog(mContext, R.string.register_msg001, null);
				txtName.requestFocus();
				return;
			}

			String check = "";
			Pattern regex;
			check = "[a-zA-Z.\u4e00-\u9fa5\\s]+";
			regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(strName);
			if (!matcher.matches()) {
				DialogUtils.showAlertDialog(mContext, (R.string.register_msg002), null);
				txtName.requestFocus();
				return;
			}

			String strEmail = txtEmail.getText().toString();
			if (strEmail == null || strEmail.trim().equals("")) {
				DialogUtils.showAlertDialog(mContext, R.string.register_msg003, null);
				txtEmail.requestFocus();
				return;
			}

			check = "^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+$";
			regex = Pattern.compile(check);
			matcher = regex.matcher(strEmail);
			if (!matcher.matches()) {
				DialogUtils.showAlertDialog(mContext, (R.string.register_msg004), null);
				txtEmail.requestFocus();
				return;
			}
			new AnsySubscribe().execute(strCompany, strName, strEmail);
		}
	};

	private class AnsySubscribe extends AsyncTask<String, String, Boolean> {

		@Override
		protected void onPreExecute() {
			mProgressDialog = DialogUtils.CreateProgressDialog(mContext, R.string.Registering_MSg);
			mProgressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			String strResponse = "";
			Boolean blnResult = false;
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("CompName", arg0[0]));
			// pairs.add(new BasicNameValuePair("EName", arg0[1]));
			// pairs.add(new BasicNameValuePair("Email", arg0[2]));
			// pairs.add(new BasicNameValuePair("WAct", "9105"));

			String strURL = "";
			String langCode = "";
			switch (mLanguage) {
			case 1:
				strURL = Configure.Subscribe_EN_URL;
				langCode = "1252";
				break;
			case 2:
				strURL = Configure.Subscribe_CN_URL;
				langCode = "936";
				break;
			default:
				strURL = Configure.Subscribe_TW_URL;
				langCode = "950";
				break;
			}

			FormBody formBody = new FormBody.Builder()
            .add("CompName", arg0[0])
            .add("EName", arg0[1])
            .add("Email", arg0[2])
            .add("rad", langCode)
            .build();
			
			LogUtil.i(TAG,"arg0[0]="+arg0[0]);
			LogUtil.i(TAG,"arg0[1]="+arg0[1]);
			LogUtil.i(TAG,"arg0[2]="+arg0[2]);
			LogUtil.i(TAG,"langCode="+langCode);

			// TODO: 2017/9/25
//			strResponse=NetworkHelper.post(strURL, formBody);

			LogUtil.v(TAG, strResponse);

			if (strResponse != null && !strResponse.equals("")) {
				// Document oDocument = Jsoup.parse(strResponse);
				// Element oElement = oDocument.getElementById("SubSuccess");
				// if (oElement != null) {
				// strMsg = oElement.html();
				// blnResult = true;
				// }

				if (strResponse.contains("callSuccess()")) {
					strMsg = mContext.getString(R.string.thx_yd);
					/*
					 * if(mLanguage==0){ strMsg=""; }
					 */
					blnResult = true;
					LogUtil.d(TAG, "strMsg:" + strMsg);
				}
			}

			return blnResult;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			StatService.onEvent(mContext, "SubscribeEmail", "pass", 1);
			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				mProgressDialog.cancel();
				if(!result){
					Toast.makeText(mContext, mContext.getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
				}
			}
			if (result) {
				ResetForm();
				DialogUtils.showAlertDialog(mContext, strMsg, null);
			}

		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}

	}

}
