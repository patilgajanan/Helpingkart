package com.appsplanet.helpingkart.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.appsplanet.helpingkart.Class.SharedPreferencesDatabase;
import com.appsplanet.helpingkart.Config;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        storeToken(refreshedToken);

        Intent registrationComplete = new Intent("Complete Registration");
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void storeToken(String token) {
        //saving the token on shared preferences
        SharedPreferencesDatabase sharedPreferencesDatabase = new SharedPreferencesDatabase(this);
        sharedPreferencesDatabase.createDatabase();
        sharedPreferencesDatabase.addData(Config.LOGIN_DEVICE_TOKEN, token);
        // SplashScreenActivity.sharedPreferencesDatabase.addData("token", token);
    }
}