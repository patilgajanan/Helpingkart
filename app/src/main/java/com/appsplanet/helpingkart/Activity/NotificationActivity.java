package com.appsplanet.helpingkart.Activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Booking.BookingItem;
import com.appsplanet.helpingkart.Adapter.MyRecyclerViewAdapter;
import com.appsplanet.helpingkart.Class.NotificationItem;
import com.appsplanet.helpingkart.Class.SharedPreferencesDatabase;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.DataBase.DatabaseHandler;
import com.appsplanet.helpingkart.R;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class NotificationActivity extends AppCompatActivity {
    private Toolbar toolbar_notifiation;
    private RecyclerView recycler_view_notification;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private DatabaseHandler databaseHandler;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String user_mobile;
    private RecyclerView.Adapter myRecyclerViewAdapter;
    private ProgressBar pb_notification_history;
    private CardView card_notification;
    private SharedPreferencesDatabase sharedPreferencesDatabase;
    private TextView tv_no_notification;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = NotificationActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        sharedPreferencesDatabase = new SharedPreferencesDatabase(NotificationActivity.this);
        sharedPreferencesDatabase.createDatabase();
        pb_notification_history = (ProgressBar) findViewById(R.id.pb_notification_history);
        tv_no_notification = (TextView) findViewById(R.id.tv_no_notification);
        card_notification = (CardView) findViewById(R.id.card_view_notification);
        user_mobile = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE);
        recycler_view_notification = (RecyclerView) findViewById(R.id.recycler_view_notification);
        toolbar_notifiation = (Toolbar) findViewById(R.id.toolbar_notifiation);
        setSupportActionBar(toolbar_notifiation);
        getSupportActionBar().setTitle("Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar_notifiation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationActivity.this, MainActivity.class));
                finish();
            }
        });
        databaseHandler = new DatabaseHandler(this);
        // mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(NotificationActivity.this, getNotificationHistory());
        recycler_view_notification.setLayoutManager(layoutManager);
        recycler_view_notification.setAdapter(myRecyclerViewAdapter);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    //getDataSet();
                }
            }
        };


    }

    public ArrayList<NotificationItem> getNotificationHistory() {

        final ArrayList<NotificationItem> homeItems = new ArrayList<>();
        //      btnVisiblity(false);
        AndroidNetworking.post(Config.getNotification)
                .setTag("test")
                .addBodyParameter("mobile", user_mobile)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int stored_id = 0;
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                String str_notification_title = jsonObject.getString("title");
                                String str_id = jsonObject.getString("id");
                                SplashScreenActivity.sharedPreferencesDatabase.addData("id", str_id);
                                String str_notification_message = jsonObject.getString("description");
                                String str_notification__date = jsonObject.getString("created_date");
                                homeItems.add(new NotificationItem(str_notification_title, str_notification_message, str_notification__date));

                            }

                            if (myRecyclerViewAdapter != null) {
                                myRecyclerViewAdapter.notifyDataSetChanged();
                            }


                            btnVisiblity(true);


                        } catch (JSONException e) {
                            Toast.makeText(NotificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Toast.makeText(NotificationActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            btnVisiblity(true);
                        } else {
                            Toast.makeText(NotificationActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            btnVisiblity(true);
                        }
                    }

                });
        return homeItems;
    }


   /* public ArrayList<NotificationItem> getNotificationHistory() {
        btnVisiblity(false);
        final ArrayList<NotificationItem> viewnotificationItems = new ArrayList<>();
        AndroidNetworking.post(Config.getNotification)
                .addBodyParameter("mobile", user_mobile)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Functions.getJsonArrayFunction(response, viewBookingItems, "time", "created_date", BookingItem.class);
                        try {
                            JSONArray json = new JSONArray(response.toString());
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject e = json.getJSONObject(i);
                                String str_notification_title = e.getString("title");
                                String str_notification_message = e.getString("description");
                                String str_notification__date = e.getString("created_date");
                                viewnotificationItems.add(new NotificationItem(str_notification_title, str_notification_message, str_notification__date));
                            }

                            if (myRecyclerViewAdapter != null) {
                                myRecyclerViewAdapter.notifyDataSetChanged();
                            }


                            btnVisiblity(true);
                        } catch (Exception e) {
                            Toast.makeText(NotificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }

                    }


                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Toast.makeText(NotificationActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(NotificationActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                    }

                });
        return viewnotificationItems;
    }*/

    public void btnVisiblity(boolean status) {
        if (status) {

            pb_notification_history.setVisibility(View.GONE);
            tv_no_notification.setVisibility(View.GONE);
        } else {
            tv_no_notification.setVisibility(View.VISIBLE);
            pb_notification_history.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });*/
    }

  /*  private ArrayList<NotificationItem> getDataSet() {
        ArrayList results = new ArrayList<NotificationItem>();
        String message_notification = SplashScreenActivity.sharedPreferencesDatabase.getData("message");
        //int msg = Integer.parseInt(message_notification);
        NotificationItem obj = new NotificationItem(message_notification, "12.00");
        results.add(message_notification, obj);
        return results;
    }*/

}
