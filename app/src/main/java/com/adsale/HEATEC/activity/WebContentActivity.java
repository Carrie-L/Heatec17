package com.adsale.HEATEC.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;
import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.util.Constant;
import com.adsale.HEATEC.util.LogUtil;
import com.adsale.HEATEC.util.SystemMethod;

import java.io.File;

import static com.adsale.HEATEC.App.isTablet;

/**
 * must intent data:
 * [Url] : already contains "WebContent" in code,so just send id. e.g.:【"99"】,内存中的文件夹名称需与asset目录下的一致，最后不需要[/]
 */
public class WebContentActivity extends BaseActivity {
    public final ObservableBoolean progressBar=new ObservableBoolean(true);
    private WebView webView;
    private String webUrl;
    private Intent mIntent;
    private int language;

    @Override
    protected void initView() {
        getLayoutInflater().inflate(R.layout.activity_webcontent, mBaseFrameLayout, true);
        webView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void initData() {
        final Intent intent = getIntent();
        webUrl = intent.getStringExtra(Constant.INTENT_WEB_CONTENT_URL);
        LogUtil.i(TAG, "webUrl=" + webUrl);
        language = SystemMethod.getCurLanguage(getApplicationContext());

        if (webUrl.toLowerCase().startsWith("http") || webUrl.toLowerCase().startsWith("web")) {
            loadWebUrl();
        } else {
            loadLocalHtml();
        }

        setWebViewClient();

    }

    private void loadLocalHtml() {
        webUrl = "WebContent/".concat(webUrl).concat("/").concat(getHtmName());
        LogUtil.i(TAG, "webUrl=" + webUrl);
        LogUtil.i(TAG, "App.RootDir=" + App.RootDir);
        if (new File(App.RootDir.concat(webUrl)).exists()) {
            loadDataHtml();
        } else {
            loadAssetHtml();
        }
    }

    private void loadDataHtml() {
        webView.loadUrl("file://".concat(App.RootDir).concat(webUrl));
    }

    private void loadAssetHtml() {
        webView.loadUrl("/android_asset/".concat(webUrl));
    }

    private void loadWebUrl() {
        LogUtil.i(TAG, "-- loadWebUrl --");
        webView.loadUrl(webUrl);
    }

    private String getHtmName() {
        return SystemMethod.getName(language, "TC.htm", "EN.htm", "SC.htm");
    }

    private String mUrl;

    private void setWebViewClient() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mUrl = url;
                LogUtil.i(TAG, "shouldOverrideUrlLoading: url= " + url);
                if (startsWith("web")) {
                    web();
                    return true;
                } else if (startsWith("http")) {
                    http();
                } else if (startsWith("mailto")) {
                    mailTo();
                    return true;
                } else if (startsWith("tel:")) {
                    callPhone();
                    return true;
                }

                intent();
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.set(false);
            }
        });
    }

    private boolean startsWith(String abc) {
        return mUrl.toLowerCase().startsWith(abc);
    }

    private void web() {
        mUrl = mUrl.replace("web://", "http://");
        LogUtil.i(TAG, "startsWith(\"web_————url=" + mUrl);
        Uri uri = Uri.parse(mUrl);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void http() {
        mIntent = new Intent(WebContentActivity.this, WebUrlActivity.class);
        mIntent.putExtra("WebUrl", mUrl);
//                    mIntent.putExtra("title", gTitle);
    }

    private void mailTo() {
        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.setData(Uri.parse(mUrl));
        mIntent.setType("plain/text");
        mIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mUrl.replaceFirst("mailto:", "").trim()});
        mIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        try {
            startActivity(mIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.exception_toast_email), Toast.LENGTH_SHORT).show();
        }
    }

    private void callPhone() {
        try {
            mIntent = new Intent(Intent.ACTION_DIAL);
            mIntent.setData(Uri.parse(mUrl));
            startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.exception_toast_phone), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void intent() {
        if (mIntent == null) {
            return;
        }
        LogUtil.e(TAG, "intent()");
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
        if (isTablet) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


}
