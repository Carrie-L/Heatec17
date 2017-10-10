package com.adsale.HEATEC.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.adsale.HEATEC.R;
import com.adsale.HEATEC.base.BaseActivity;
import com.adsale.HEATEC.base.BaseFragmentActivity;
import com.adsale.HEATEC.dao.WebContent;
import com.adsale.HEATEC.databinding.ActivitySubscribeBinding;
import com.adsale.HEATEC.fragment.SubscribeFragment;
import com.adsale.HEATEC.util.SystemMethod;
import com.adsale.HEATEC.util.network.Configure;
import com.adsale.HEATEC.util.network.NetworkClient;
import com.adsale.HEATEC.view.TitleView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import sanvio.libs.util.DialogUtils;

import static com.adsale.HEATEC.R.id.txtCompany;
import static com.adsale.HEATEC.R.id.txtEmail;
import static com.adsale.HEATEC.R.id.txtName;

public class SubscribeActivity extends BaseActivity {
    public static String TAG = "SubscribeActivity";
    public final ObservableField<String> company = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    private ActivitySubscribeBinding binding;
    private int language;

    @Override
    protected void initView() {
        binding = ActivitySubscribeBinding.inflate(getLayoutInflater(), mBaseFrameLayout, true);
        binding.setAty(this);
    }

    @Override
    protected void initData() {
        language = SystemMethod.getCurLanguage(getApplicationContext());
    }

    public void onSend() {
        track();
        if (!checkEditText()) {
            return;
        }

        NetworkClient client = SystemMethod.setupRetrofit(NetworkClient.class, "");
        client.onSubscribe(getLangType(), getFormBody())
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Response<ResponseBody>>() {
            @Override
            public void accept(Response<ResponseBody> response) throws Exception {

            }
        });

    }

    private FormBody getFormBody() {
        return new FormBody.Builder()
                .add("CompName", company.get())
                .add("EName", name.get())
                .add("Email", email.get())
                .add("rad", getLangCode())
                .build();
    }

    private String getLangCode() {
        return language == 0 ? "950" : language == 1 ? "1252" : "936";
    }

    private String getLangType() {
        return language == 0 ? "trad" : language == 1 ? "eng" : "simp";
    }


    private void track() {
//		SystemMethod.trackViewLog(mContext, 422, "SE", "", "");
//		SystemMethod.setStatEvent(mContext, "SubscribeE", "SE", mLanguage);
    }

    public void onReset() {
        company.set("");
        name.set("");
        email.set("");
    }

    public void onPrivacy() {
        Intent intent = new Intent(this, WebContentActivity.class);
        startActivity(intent);
    }

    private boolean checkEditText() {
        if (company.get().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.register_msg013, Toast.LENGTH_LONG).show();
            binding.etCompany.requestFocus();
            return false;
        }

        if (name.get().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.register_msg001, Toast.LENGTH_LONG).show();
            binding.etName.requestFocus();
            return false;
        }

        String check = "[a-zA-Z.\u4e00-\u9fa5\\s]+";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email.get());
        if (!matcher.matches()) {
            DialogUtils.showAlertDialog(SubscribeActivity.this, (R.string.register_msg002), null);
            binding.etEmail.requestFocus();
            return false;
        }

        if (email.get().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.register_msg003, Toast.LENGTH_LONG).show();
            binding.etEmail.requestFocus();
            return false;
        }

        check = "[\\w\\-.]+@([\\w-]+\\.)+[a-z]{2,3}";
        regex = Pattern.compile(check);
        matcher = regex.matcher(email.get());
        if (!matcher.matches()) {
            DialogUtils.showAlertDialog(SubscribeActivity.this, (R.string.register_msg004), null);
            binding.etEmail.requestFocus();
            return false;
        }

        return true;
    }


}
