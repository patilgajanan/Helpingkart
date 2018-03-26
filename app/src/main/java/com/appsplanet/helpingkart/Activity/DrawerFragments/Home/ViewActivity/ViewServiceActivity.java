package com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.HomeItem;
import com.appsplanet.helpingkart.Activity.MainActivity;
import com.appsplanet.helpingkart.Activity.NotificationActivity;
import com.appsplanet.helpingkart.Activity.ProfileActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.Adapter.MyRecyclerViewAdapter;
import com.appsplanet.helpingkart.Adapter.ResourceAdapter;
import com.appsplanet.helpingkart.Class.Functions;
import com.appsplanet.helpingkart.Class.NotificationItem;
import com.appsplanet.helpingkart.Class.ResourceItem;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.appsplanet.helpingkart.R.id.coordinatorLayout_login;

public class ViewServiceActivity extends AppCompatActivity {

    private Toolbar toolbar_view_service;
    private CoordinatorLayout coordinatorLayout_service;
    private EditText et_req_view_service, et_date_view_service, et_time_view_service, et_address_view_service;
    private Button btn_book_now_view_service;
    private ProgressBar pb_book_now_view_service, pb_iv_location_add;
    private String service_name, service_image;
    private ImageView iv_service_img, iv_location_add;
    private TextView tv_service_name;
    private AutoCompleteTextView ac_address_view_service;
    private int PLACE_PICKER_REQUEST = 1;
    private RecyclerView recyclerView_view_resource;
    private ResourceAdapter resourceAdapter;
    private TextView tv_resource_not_found;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);
        tv_service_name = (TextView) findViewById(R.id.tv_service_name);
        coordinatorLayout_service = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_service);
        toolbar_view_service = (Toolbar) findViewById(R.id.toolbar_view_service);
        pb_iv_location_add = (ProgressBar) findViewById(R.id.pb_iv_location_add_company);
        recyclerView_view_resource = findViewById(R.id.recyclerView_view_resource);
        tv_resource_not_found = findViewById(R.id.tv_resource_not_found);
        et_req_view_service = (EditText) findViewById(R.id.et_req_view_service);
        et_date_view_service = (EditText) findViewById(R.id.et_date_view_service);
        et_time_view_service = (EditText) findViewById(R.id.et_time_view_service);
        et_address_view_service = (EditText) findViewById(R.id.et_address_view_service);
        btn_book_now_view_service = (Button) findViewById(R.id.btn_book_now_view_service);
        pb_book_now_view_service = (ProgressBar) findViewById(R.id.pb_book_now_view_service);
        iv_service_img = (ImageView) findViewById(R.id.iv_service_img);
        // ac_address_view_service = (AutoCompleteTextView) findViewById(R.id.ac_address_view_service);
        iv_location_add = (ImageView) findViewById(R.id.iv_location_add);
        service_image = SplashScreenActivity.sharedPreferencesDatabase.getData("serviceimg");
        service_name = SplashScreenActivity.sharedPreferencesDatabase.getData("Servicename");
        //get Resources profile
        Picasso.with(ViewServiceActivity.this).load(service_image).into(iv_service_img);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ViewServiceActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        tv_service_name.setText(service_name);
        setSupportActionBar(toolbar_view_service);
        getSupportActionBar().setTitle(service_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar_view_service.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        et_date_view_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.setDatePicker(ViewServiceActivity.this, et_date_view_service);
            }
        });
        et_time_view_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.setTimePicker(ViewServiceActivity.this, et_time_view_service);
            }
        });
        btn_book_now_view_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE);
                String s_et_req_view_service = et_req_view_service.getText().toString();
                String s_et_date_view_service = et_date_view_service.getText().toString();
                String s_et_time_view_service = et_time_view_service.getText().toString();
                String s_et_address_view_service = et_address_view_service.getText().toString();
             /*   if (TextUtils.isEmpty(s_et_req_view_service)) {

                    Snackbar.make(coordinatorLayout_service, "Requirement can not be blank", Snackbar.LENGTH_SHORT).show();
                } else*/
                if (TextUtils.isEmpty(s_et_date_view_service)) {

                    Snackbar.make(coordinatorLayout_service, "Date can not be blank", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_time_view_service)) {

                    Snackbar.make(coordinatorLayout_service, "Time can not be blank", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_address_view_service)) {
                    Snackbar.make(coordinatorLayout_service, "Address can not be blank", Snackbar.LENGTH_SHORT).show();
                } else {

                    setBooking(mobile, s_et_req_view_service, s_et_date_view_service, s_et_time_view_service, s_et_address_view_service);
                }

            }
        });

        iv_location_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    pb_iv_location_add.setVisibility(View.VISIBLE);
                    iv_location_add.setVisibility(View.GONE);
                    startActivityForResult(builder.build(ViewServiceActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }

        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewServiceActivity.this);
        resourceAdapter = new ResourceAdapter(ViewServiceActivity.this, getresource(service_name));
        recyclerView_view_resource.setLayoutManager(layoutManager);
        recyclerView_view_resource.setAdapter(resourceAdapter);
        recyclerView_view_resource.setNestedScrollingEnabled(false);
    }

    public void setBooking(final String mobile, final String requirement, final String service_date, final String service_time, final String service_address) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getVehicleMechanic)
                .addBodyParameter("mobile", mobile)
                .addBodyParameter("requirement", requirement)
                .addBodyParameter("service_date", service_date)
                .addBodyParameter("service_time", service_time)
                .addBodyParameter("service_address", service_address)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error") && response.getString("error").equals("false")) {

                                detailsDialog(ViewServiceActivity.this, "", response.getString("message"));
                                //finish();
                            } else if (response.has("error") && response.getString("error").equals("true")) {
                                Snackbar.make(coordinatorLayout_service, response.getString("message").toString(), Snackbar.LENGTH_LONG).show();
                            }
                            btnVisiblity(true);
                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_service, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_service, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(coordinatorLayout_service, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);
                    }
                });
    }


    public ArrayList<ResourceItem> getresource(final String service_name) {
        //btnVisiblity(false);
        final ArrayList<ResourceItem> homeItems = new ArrayList<>();
        AndroidNetworking.post(Config.getResourceProfile)
                .addBodyParameter("category", service_name)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("first_name");
                                String lastname = jsonObject.getString("last_name");
                                String email = jsonObject.getString("email");
                                String service_type = jsonObject.getString("service_type");
                                String image = jsonObject.getString("image_url");
                                String mobile = jsonObject.getString("mobile");
                                String address = jsonObject.getString("address");
                                String imgurl = "http://helpingcart.com/need-hlp/";
                                String combinedImage = imgurl + image;

                                homeItems.add(new ResourceItem(combinedImage, name, lastname, email, service_type, mobile, address));
                                if (resourceAdapter != null) {
                                    resourceAdapter.notifyDataSetChanged();
                                }

                            }

                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_service, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            //btnVisiblity(true);
                            tv_resource_not_found.setVisibility(View.VISIBLE);
                            tv_resource_not_found.setText("No Resource Found");
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_service, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {

                            Snackbar.make(coordinatorLayout_service, "No Resource Found", Snackbar.LENGTH_SHORT).show();
                            tv_resource_not_found.setVisibility(View.VISIBLE);
                            tv_resource_not_found.setText("No Resource Found");


                        }
                        //btnVisiblity(true);
                    }
                });
        return homeItems;
    }


    public void btnVisiblity(boolean status) {
        if (status) {
            btn_book_now_view_service.setVisibility(View.VISIBLE);
            pb_book_now_view_service.setVisibility(View.GONE);
        } else {
            btn_book_now_view_service.setVisibility(View.INVISIBLE);
            pb_book_now_view_service.setVisibility(View.VISIBLE);
        }
    }

    public void detailsDialog(final Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "  CLOSE  ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                pb_iv_location_add.setVisibility(View.GONE);
                iv_location_add.setVisibility(View.VISIBLE);
                Place place = PlacePicker.getPlace(data, ViewServiceActivity.this);
                et_address_view_service.setText(place.getAddress());

            }
        }
    }
}
