package com.appsplanet.helpingkart.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appsplanet.helpingkart.Activity.DrawerFragments.Booking.BookingFragment;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.HomeFragment;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Mypoints.MyPoints;
import com.appsplanet.helpingkart.Activity.DrawerFragments.ReferalFragment;
import com.appsplanet.helpingkart.Adapter.CustomAdapterSearch;
import com.appsplanet.helpingkart.Adapter.Search;

import com.appsplanet.helpingkart.Class.CircleImageView;
import com.appsplanet.helpingkart.Class.Functions;
import com.appsplanet.helpingkart.Class.NotificationUtils;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.DataBase.DatabaseHandler;
import com.appsplanet.helpingkart.R;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static FloatingSearchView floating_search_view;
    public static int REQUEST_PHONE_CALL = 552;
    ArrayList<Search> searchitem = new ArrayList<>();
    String string;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ImageView iv_notification_app_bar_main, iv_wallet_app_bar_main;
    private FloatingActionButton fab_support_call;
    private ArrayList<String> results = new ArrayList<String>();
    private Spinner spinner;
    private CircleImageView imageView;
    private TextView tv_name_nav_header, tv_phone_nav_header, tv_location_selected, app_name_nav_bar, tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MainActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }

        app_name_nav_bar = (TextView) findViewById(R.id.app_name_nav_bar);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_location_selected = findViewById(R.id.tv_location_set);

        fab_support_call = (FloatingActionButton) findViewById(R.id.fab_support_call);
        //tv_notification_count = (TextView) findViewById(R.id.tv_notification_count);

        fab_support_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_PHONE_CALL);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8237272827"));
                    startActivity(intent);
                }

            }
        });

       /* mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                }
            }
        };*/

        displayFirebaseRegId();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        iv_notification_app_bar_main = (ImageView)

                findViewById(R.id.iv_notification_app_bar_main);

        floating_search_view = (FloatingSearchView)

                findViewById(R.id.floating_search_view);

        iv_wallet_app_bar_main = (ImageView)

                findViewById(R.id.iv_wallet_app_bar_main);
        floating_search_view.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener()

        {
            @Override
            public void onFocus() {
                toolbar.setVisibility(View.GONE);
                HomeFragment.relativeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFocusCleared() {
                toolbar.setVisibility(View.VISIBLE);
                HomeFragment.relativeLayout.setVisibility(View.VISIBLE);
            }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_hamburger, getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View header = navigationView.getHeaderView(0);
        tv_name_nav_header = (TextView) header.findViewById(R.id.tv_name_nav_header);
        tv_phone_nav_header = (TextView) header.findViewById(R.id.tv_phone_nav_header);
        final LinearLayout ll_nav_header = (LinearLayout) header.findViewById(R.id.ll_nav_header);
        imageView = (CircleImageView) header.findViewById(R.id.imageView);

        iv_notification_app_bar_main.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                try {
                    Class<?> s = Class.forName("com.appsplanet.helpingkart.Activity.NotificationActivity");
                    startActivity(new Intent(MainActivity.this, s));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        iv_wallet_app_bar_main.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
            }
        });

        addFragment(new HomeFragment(), getString(R.string.app_name_main));

        ll_nav_header.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            //super.onBackPressed();
            Functions.exitApp(MainActivity.this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            addFragment(new HomeFragment(), getString(R.string.app_name_main));
        } else if (id == R.id.nav_booking) {

            addFragment(new BookingFragment(), "BOOKING HISTORY");
        } else if (id == R.id.nav_points) {
            addFragment(new MyPoints(), "My Points");
        } else if (id == R.id.nav_logout) {
            SplashScreenActivity.sharedPreferencesDatabase.removeData();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void addFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(title);
    }

    private void displayFirebaseRegId() {
        String token = SplashScreenActivity.sharedPreferencesDatabase.getData("token");

    }

    @Override
    protected void onResume() {
        super.onResume();
        String profile_address = SplashScreenActivity.sharedPreferencesDatabase.getData("updated_address");
        tv_name_nav_header.setText(SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_NAME));
        tv_phone_nav_header.setText(SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE));
        String img = SplashScreenActivity.sharedPreferencesDatabase.getData("profile_img");
        if (!TextUtils.isEmpty(img)) {
            Picasso.with(MainActivity.this).load(img).into(imageView);
        }

        if (!TextUtils.isEmpty(profile_address)) {
            tv_location_selected.setVisibility(View.VISIBLE);
            tv_location_selected.setText(profile_address);
            tv_location.setVisibility(View.VISIBLE);
            app_name_nav_bar.setVisibility(View.GONE);
        } else {
            tv_location.setVisibility(View.GONE);
            tv_location_selected.setVisibility(View.GONE);
            app_name_nav_bar.setVisibility(View.VISIBLE);

        }
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}