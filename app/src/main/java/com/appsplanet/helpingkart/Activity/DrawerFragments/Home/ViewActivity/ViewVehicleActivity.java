package com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Activity.LoginActivity;
import com.appsplanet.helpingkart.Activity.MainActivity;
import com.appsplanet.helpingkart.Activity.ProfileActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.Adapter.ResourceAdapter;
import com.appsplanet.helpingkart.Class.Functions;
import com.appsplanet.helpingkart.Class.ResourceItem;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar_view_vehicle;
    private CoordinatorLayout coordinatorLayout_vehicle;
    private EditText et_from_to_go_view_vehicle, et_where_to_go_view_vehicle, et_from_date_view_vehicle, et_to_date_view_vehicle, et_time_view_vehicle, et_no_of_persons_view_vehicle;
    private Button btn_order_now_view_vehicle;
    private ProgressBar pb_order_now_view_vehicle;
    private RadioGroup radio_group_cat_type_view_vehicle;
    private Spinner spinner_from_to_go_view_vehicle;
    private String s_from_to_go = "", service_image, service_name;
    private ImageView iv_car_preference;
    private TextView tv_resource_not_found_vehicle;
    private ResourceAdapter resourceAdapter;
    private RecyclerView recyclerView_view_resource_vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ViewVehicleActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        service_image = SplashScreenActivity.sharedPreferencesDatabase.getData("serviceimg");

        recyclerView_view_resource_vehicle = findViewById(R.id.recyclerView_view_resource_vehicle);

        tv_resource_not_found_vehicle = findViewById(R.id.tv_resource_not_found_vehicle);
        iv_car_preference = (ImageView) findViewById(R.id.iv_car_preference);
        Picasso.with(ViewVehicleActivity.this).load(service_image).into(iv_car_preference);
        coordinatorLayout_vehicle = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_vehicle);
        toolbar_view_vehicle = (Toolbar) findViewById(R.id.toolbar_view_vehicle);
        et_from_to_go_view_vehicle = (EditText) findViewById(R.id.et_from_to_go_view_vehicle);
        et_where_to_go_view_vehicle = (EditText) findViewById(R.id.et_where_to_go_view_vehicle);
        et_from_date_view_vehicle = (EditText) findViewById(R.id.et_from_date_view_vehicle);
        et_to_date_view_vehicle = (EditText) findViewById(R.id.et_to_date_view_vehicle);
        et_time_view_vehicle = (EditText) findViewById(R.id.et_time_view_vehicle);
        et_no_of_persons_view_vehicle = (EditText) findViewById(R.id.et_no_of_persons_view_vehicle);
        btn_order_now_view_vehicle = (Button) findViewById(R.id.btn_order_now_view_vehicle);
        pb_order_now_view_vehicle = (ProgressBar) findViewById(R.id.pb_order_now_view_vehicle);
        radio_group_cat_type_view_vehicle = (RadioGroup) findViewById(R.id.radio_group_cat_type_view_vehicle);

        setSupportActionBar(toolbar_view_vehicle);
        getSupportActionBar().setTitle(getString(R.string.app_name_main));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar_view_vehicle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        et_from_date_view_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.setDatePicker(ViewVehicleActivity.this, et_from_date_view_vehicle);
            }
        });
        et_to_date_view_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.setDatePicker(ViewVehicleActivity.this, et_to_date_view_vehicle);
            }
        });
        et_time_view_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions.setTimePicker(ViewVehicleActivity.this, et_time_view_vehicle);
            }
        });

        service_name = SplashScreenActivity.sharedPreferencesDatabase.getData("Servicename");
        btn_order_now_view_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE);
                String s_from_to_go_view_vehicle = et_from_to_go_view_vehicle.getText().toString();
                String s_et_where_to_go_view_vehicle = et_where_to_go_view_vehicle.getText().toString();
                String s_et_from_date_view_vehicle = et_from_date_view_vehicle.getText().toString();
                String s_et_to_date_view_vehicle = et_to_date_view_vehicle.getText().toString();
                String s_et_time_view_vehicle = et_time_view_vehicle.getText().toString();
                String s_et_no_of_persons_view_vehicle = et_no_of_persons_view_vehicle.getText().toString();
                int selectedId = radio_group_cat_type_view_vehicle.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if (TextUtils.isEmpty(s_from_to_go_view_vehicle)) {
                    Toast.makeText(ViewVehicleActivity.this, "Please select FROM TO GO", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_where_to_go_view_vehicle)) {

                    Snackbar.make(coordinatorLayout_vehicle, "Location can not be blank", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_from_date_view_vehicle)) {

                    Snackbar.make(coordinatorLayout_vehicle, "Date can not be blank", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_to_date_view_vehicle)) {

                    Snackbar.make(coordinatorLayout_vehicle, "To date can not be blank", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_time_view_vehicle)) {
                    Snackbar.make(coordinatorLayout_vehicle, "Time can not be blank", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_et_no_of_persons_view_vehicle)) {
                    Snackbar.make(coordinatorLayout_vehicle, "No of person can not be blank", Snackbar.LENGTH_SHORT).show();
                } else if (radio_group_cat_type_view_vehicle.getCheckedRadioButtonId() == -1) {
                    Snackbar.make(coordinatorLayout_vehicle, "Car type can not be blank", Snackbar.LENGTH_SHORT).show();
                } else {
                    String car_type = radioButton.getText().toString();
                    setBooking(mobile, s_from_to_go_view_vehicle, s_et_where_to_go_view_vehicle, s_et_from_date_view_vehicle, s_et_to_date_view_vehicle, s_et_time_view_vehicle, s_et_no_of_persons_view_vehicle, car_type);


                }

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewVehicleActivity.this);
        resourceAdapter = new ResourceAdapter(ViewVehicleActivity.this, getresource(service_name));
        recyclerView_view_resource_vehicle.setLayoutManager(layoutManager);
        recyclerView_view_resource_vehicle.setAdapter(resourceAdapter);
        recyclerView_view_resource_vehicle.setNestedScrollingEnabled(false);
    }

    public void setBooking(final String mobile, String from_address, final String where_to_go, final String from_date, final String to_date, final String time, final String noofperson, final String car_type) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getCarBooking)
                .addBodyParameter("user_mobile", mobile)
                .addBodyParameter("from_address", from_address)
                .addBodyParameter("destination", where_to_go)
                .addBodyParameter("from_date", from_date)
                .addBodyParameter("to_date", to_date)
                .addBodyParameter("time", time)
                .addBodyParameter("noofperson", noofperson)
                .addBodyParameter("car_type", car_type)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error") && response.getString("error").equals("false")) {
                                detailsDialog(ViewVehicleActivity.this, "", response.getString("message"));
                            } else if (response.has("error") && response.getString("error").equals("true")) {
                                Snackbar.make(coordinatorLayout_vehicle, response.getString("message").toString(), Snackbar.LENGTH_LONG).show();
                            }
                            btnVisiblity(true);
                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_vehicle, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_vehicle, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(coordinatorLayout_vehicle, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
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
                            Snackbar.make(coordinatorLayout_vehicle, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            //btnVisiblity(true);
                            tv_resource_not_found_vehicle.setVisibility(View.VISIBLE);
                            tv_resource_not_found_vehicle.setText("No Resource Found");
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_vehicle, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {

                            Snackbar.make(coordinatorLayout_vehicle, "Sorry No resource Available", Snackbar.LENGTH_SHORT).show();
                            tv_resource_not_found_vehicle.setVisibility(View.VISIBLE);
                            tv_resource_not_found_vehicle.setText("No Resource Found");


                        }
                        //btnVisiblity(true);
                    }
                });
        return homeItems;
    }

    public void btnVisiblity(boolean status) {
        if (status) {
            btn_order_now_view_vehicle.setVisibility(View.VISIBLE);
            pb_order_now_view_vehicle.setVisibility(View.GONE);
        } else {
            btn_order_now_view_vehicle.setVisibility(View.INVISIBLE);
            pb_order_now_view_vehicle.setVisibility(View.VISIBLE);
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
}
