package com.appsplanet.helpingkart.Activity.DrawerFragments.Mypoints;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsplanet.helpingkart.Activity.MainActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;

import java.util.ArrayList;

public class MyPoints extends Fragment {
    ArrayList stock_list;
    private TextView tv_referral_code, tv_my_wallet_points;
    private String s_referral_code, s_my_points;
    private ImageView iv_share_facebook, iv_share_whatsapp, iv_share_twitter, iv_share_sms;
    private Toolbar toolbar_my_points;

    public MyPoints() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_points, container, false);

        MainActivity.floating_search_view.setVisibility(View.GONE);
        iv_share_facebook = (ImageView) rootView.findViewById(R.id.iv_share_facebook);
        iv_share_twitter = (ImageView) rootView.findViewById(R.id.iv_share_twitter);
        iv_share_sms = (ImageView) rootView.findViewById(R.id.iv_share_sms);
        iv_share_whatsapp = (ImageView) rootView.findViewById(R.id.iv_share_whatsapp);

        tv_my_wallet_points = (TextView) rootView.findViewById(R.id.tv_my_wallet_points);
        s_my_points = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REFERAL_POINTS);
        if (s_my_points.equals("")) {
            s_my_points = "";
        } else {
            tv_my_wallet_points.setText(s_my_points);
        }
        tv_referral_code = (TextView) rootView.findViewById(R.id.tv_referral_code);
        s_referral_code = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REFERAL_CODE);
        tv_referral_code.setText(s_referral_code);
        iv_share_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAppWhatsapp();
            }
        });
        iv_share_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareappSms();
            }
        });
        iv_share_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApptwitter();
            }
        });
        iv_share_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAppFacebook();
            }
        });

        return rootView;
    }

    public void shareAppWhatsapp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey Use this app for to get your personal assistant and use this my refer code:" + s_referral_code);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    public void shareAppFacebook() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey Use this app for to get your personal assistant and use this my refer code:" + s_referral_code);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.facebook.katana");
        startActivity(sendIntent);
    }

    public void shareappSms() {
        String smsBody = "Hey Use this app for to get your personal assistant and use this my refer code:" + s_referral_code;
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");

        startActivity(sendIntent);
    }

    public void shareApptwitter() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey Use this app for to get your personal assistant and use this my refer code:" + s_referral_code);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.twitter.android");
        startActivity(sendIntent);
    }


}
