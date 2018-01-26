package com.appsplanet.helpingkart.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.appsplanet.helpingkart.Activity.AppIntroduction.AppIntroductionActivity;
import com.appsplanet.helpingkart.Class.Functions;
import com.appsplanet.helpingkart.Class.SharedPreferencesDatabase;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashScreenActivity extends AppCompatActivity {

    public static SharedPreferencesDatabase sharedPreferencesDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = SplashScreenActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        sharedPreferencesDatabase = new SharedPreferencesDatabase(SplashScreenActivity.this);
        sharedPreferencesDatabase.createDatabase();

        sharedPreferencesDatabase.addData(Config.LOGIN_DEVICE_TOKEN, FirebaseInstanceId.getInstance().getToken());
//getIntent().getExtras().toSt
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            if (bundle2string(b).contains("google")) {
                startActivity(new Intent(SplashScreenActivity.this, NotificationActivity.class));
                finish();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.equals(sharedPreferencesDatabase.getData(Config.DB_LOGIN), Config.DB_LOGIN_DONE)) {
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashScreenActivity.this, AppIntroductionActivity.class));
                            finish();
                        }
                    }
                }, 3000);
            }
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.equals(sharedPreferencesDatabase.getData(Config.DB_LOGIN), Config.DB_LOGIN_DONE)) {
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, AppIntroductionActivity.class));
                        finish();
                    }
                }
            }, 3000);
        }

    }

    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }
}
