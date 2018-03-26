package com.appsplanet.helpingkart.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout_otp;
    private TextView tv_mobile_otp, tv_resend_sms_otp;
    private EditText et_code_otp;
    private Button btn_next_otp;
    private String s_otp_registration;
    private ProgressBar pb_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        coordinatorLayout_otp = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_otp);
        tv_mobile_otp = (TextView) findViewById(R.id.tv_mobile_otp);
        tv_resend_sms_otp = (TextView) findViewById(R.id.tv_resend_sms_otp);
        et_code_otp = (EditText) findViewById(R.id.et_code_otp);
        btn_next_otp = (Button) findViewById(R.id.btn_next_otp);
        pb_otp = (ProgressBar) findViewById(R.id.pb_otp);
        s_otp_registration = et_code_otp.getText().toString();
        tv_mobile_otp.setText(SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE));
        tv_resend_sms_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                finish();
            }
        });

        btn_next_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_et_code_otp = et_code_otp.getText().toString();
                if (s_et_code_otp.equals("")) {
                    Snackbar.make(coordinatorLayout_otp, "Cannot be blank", Snackbar.LENGTH_SHORT).show();
                } else {
                    getOtp(s_et_code_otp);
                }

            }
        });
    }

    public void getOtp(String otp) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getOTP)
                .addBodyParameter("otp", otp)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error") && response.getString("error").equals("false")) {
                                String mobile = "";
                                String user_name = "";
                                String email = "";
                                String refered_code = "";
                                String array = response.getString("profile");
                                JSONObject parentObject = new JSONObject(array);
                                mobile = parentObject.getString("mobile");
                                user_name = parentObject.getString("name");
                                refered_code = parentObject.getString("referal_code");
                                String my_points = parentObject.getString("points");
                                //email = parentObject.getString("email");

                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_LOGIN, Config.DB_LOGIN_DONE);
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_LOGIN_MOBILE, mobile);
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_NAME, user_name);
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REFERAL_POINTS, my_points);
                                SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REFERAL_CODE, refered_code);
                                startActivity(new Intent(OtpActivity.this, MainActivity.class));
                                finish();
                            } else if (response.has("error") && response.getString("error").equals("true")) {
                                Snackbar.make(coordinatorLayout_otp, response.getString("message").toString(), Snackbar.LENGTH_LONG).show();
                            }
                            btnVisiblity(true);

                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_otp, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_otp, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(coordinatorLayout_otp, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);
                    }
                });
    }

    public void btnVisiblity(boolean status) {
        if (status) {
            btn_next_otp.setVisibility(View.VISIBLE);
            pb_otp.setVisibility(View.GONE);
        } else {
            btn_next_otp.setVisibility(View.INVISIBLE);
            pb_otp.setVisibility(View.VISIBLE);
        }
    }
}
