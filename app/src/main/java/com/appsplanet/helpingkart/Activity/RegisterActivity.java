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
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout_register;
    private TextView tv_terms_and_conditions_register, tv_i_have_acc_register;
    private Button btn_register;
    private EditText et_name_register, et_email_register, et_mobile_register, et_referral_code_register;
    private ProgressBar pb_register;
    private String device_registration_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = RegisterActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        coordinatorLayout_register = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_register);
        tv_terms_and_conditions_register = (TextView) findViewById(R.id.tv_terms_and_conditions_register);
        btn_register = (Button) findViewById(R.id.btn_register);
        et_name_register = (EditText) findViewById(R.id.et_name_register);
        et_referral_code_register = (EditText) findViewById(R.id.et_referral_code_register);
        et_email_register = (EditText) findViewById(R.id.et_email_register);
        et_mobile_register = (EditText) findViewById(R.id.et_mobile_register);
        pb_register = (ProgressBar) findViewById(R.id.pb_register);
        tv_i_have_acc_register = (TextView) findViewById(R.id.tv_i_have_acc_register);
        device_registration_id = FirebaseInstanceId.getInstance().getToken();
        tv_terms_and_conditions_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_i_have_acc_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_et_name_register = et_name_register.getText().toString();
                String s_et_email_register = et_email_register.getText().toString();
                String s_et_mobile_register = et_mobile_register.getText().toString();
                String s_et_referral_code_register = et_referral_code_register.getText().toString();

                if (TextUtils.isEmpty(s_et_name_register)) {

                    Snackbar.make(coordinatorLayout_register, "Please enter Name", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_email_register)) {
                    Snackbar.make(coordinatorLayout_register, "Please enter Email", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_mobile_register)) {
                    Snackbar.make(coordinatorLayout_register, "Please enter Mobile", Snackbar.LENGTH_SHORT).show();
                } else if (!s_et_email_register.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    Snackbar.make(coordinatorLayout_register, "Enter Valid Email", Snackbar.LENGTH_SHORT).show();
                } else if (s_et_mobile_register.length() < 10) {
                    Snackbar.make(coordinatorLayout_register, "Enter Valid Mobile no", Snackbar.LENGTH_SHORT).show();

                } else {
                    getRegisteration(s_et_name_register, s_et_email_register, s_et_mobile_register, s_et_referral_code_register, device_registration_id);
                }
            }
        });
    }

    public void getRegisteration(final String name, final String email, final String phone, final String referedcode, final String device_registration_id) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getRegistration)
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("mobile", phone)
                .addBodyParameter("code", referedcode)
                .addBodyParameter("device_registration_id", device_registration_id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error") && response.getString("error").equals("false")) {
                                startActivity(new Intent(RegisterActivity.this, OtpActivity.class));
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_NAME, name);
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_EMAIL, email);
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_MOBILE, phone);
                            } else if (response.has("error") && response.getString("error").equals("true")) {
                                Snackbar.make(coordinatorLayout_register, response.getString("message").toString(), Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(coordinatorLayout_register, "Something wrong", Snackbar.LENGTH_LONG).show();
                            }
                            btnVisiblity(true);

                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_register, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_register, "No Internet Connection", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(coordinatorLayout_register, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);
                    }
                });
    }

    public void btnVisiblity(boolean status) {
        if (status) {
            btn_register.setVisibility(View.VISIBLE);
            pb_register.setVisibility(View.GONE);
        } else {
            btn_register.setVisibility(View.INVISIBLE);
            pb_register.setVisibility(View.VISIBLE);
        }
    }
}
