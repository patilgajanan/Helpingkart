package com.appsplanet.helpingkart.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Class.SharedPreferencesDatabase;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout_login;
    private EditText et_mobile_login;
    private Button btn_register_login, btn_login;
    private ProgressBar pb_login;
    private String device_registration_id;
    private SharedPreferencesDatabase sharedPreferencesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = LoginActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        sharedPreferencesDatabase = new SharedPreferencesDatabase(LoginActivity.this);
        sharedPreferencesDatabase.createDatabase();
        coordinatorLayout_login = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_login);
        et_mobile_login = (EditText) findViewById(R.id.et_mobile_login);
        device_registration_id = sharedPreferencesDatabase.getData(Config.LOGIN_DEVICE_TOKEN);

        btn_register_login = (Button) findViewById(R.id.btn_register_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        pb_login = (ProgressBar) findViewById(R.id.pb_login);
        btn_register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_et_mobile_login = et_mobile_login.getText().toString();
                if (TextUtils.isEmpty(s_et_mobile_login)) {
                    Snackbar.make(coordinatorLayout_login, "Please enter Mobile", Snackbar.LENGTH_SHORT).show();
                } else if (s_et_mobile_login.length() < 10) {
                    Snackbar.make(coordinatorLayout_login, "Please enter Valid mobile", Snackbar.LENGTH_SHORT).show();

                } else {
                    getLogin(s_et_mobile_login);
                }
            }
        });
    }

    public void getLogin(final String mobile) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getLogin)
                .addBodyParameter("mobile", mobile)
                .addBodyParameter("device_registration_id", device_registration_id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error") && response.getString("error").equals("false")) {
                                startActivity(new Intent(LoginActivity.this, OtpActivity.class));
                                finish();
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_MOBILE, mobile);
                            } else if (response.has("error") && response.getString("error").equals("true")) {
                                Snackbar.make(coordinatorLayout_login, response.getString("message").toString(), Snackbar.LENGTH_LONG).show();
                            }
                            btnVisiblity(true);
                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_login, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_login, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(coordinatorLayout_login, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);

                    }
                });
    }

    public void btnVisiblity(boolean status) {
        if (status) {
            btn_login.setVisibility(View.VISIBLE);
            pb_login.setVisibility(View.GONE);
        } else {
            btn_login.setVisibility(View.INVISIBLE);
            pb_login.setVisibility(View.VISIBLE);
        }
    }
}
