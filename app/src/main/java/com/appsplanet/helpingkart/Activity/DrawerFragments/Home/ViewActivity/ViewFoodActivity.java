package com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFood.ViewFoodAdapter;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFood.ViewFoodItem;
import com.appsplanet.helpingkart.Activity.LoginActivity;
import com.appsplanet.helpingkart.Activity.MainActivity;
import com.appsplanet.helpingkart.Activity.OtpActivity;
import com.appsplanet.helpingkart.Activity.ProfileActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.Class.Functions;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.DataBase.DatabaseHandler;
import com.appsplanet.helpingkart.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewFoodActivity extends AppCompatActivity {

    private Toolbar toolbar_view_food;
    private RecyclerView recycler_view_food;
    private ViewFoodAdapter mAdapter;
    private Button btn_order_now_view_food;
    private ProgressBar pb_order_now_view_food;
    private CoordinatorLayout coordinatorLayout_view_food;
    private String cb_order_everyday;
    private CheckBox cb_food_menu;
    private DatabaseHandler dbhandler;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ViewFoodActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        dbhandler = new DatabaseHandler(this);
        toolbar_view_food = (Toolbar) findViewById(R.id.toolbar_view_food);
        setSupportActionBar(toolbar_view_food);
        getSupportActionBar().setTitle("Food");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar_view_food.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cb_food_menu = (CheckBox) findViewById(R.id.cb_food_menu);
        coordinatorLayout_view_food = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_view_food);
        recycler_view_food = (RecyclerView) findViewById(R.id.recycler_view_food);
        pb_order_now_view_food = (ProgressBar) findViewById(R.id.pb_order_now_view_food);
        btn_order_now_view_food = (Button) findViewById(R.id.btn_order_now_view_food);
        recycler_view_food.setNestedScrollingEnabled(false);
        mAdapter = new ViewFoodAdapter(ViewFoodActivity.this, getFood());
        recycler_view_food.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ViewFoodActivity.this, 1);
        recycler_view_food.setLayoutManager(layoutManager);
        recycler_view_food.setAdapter(mAdapter);
        //getFood();
        btn_order_now_view_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAllRecords();
                //food_booking();
            }
        });
    }


    public void bookingDone() {
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.dialog_booking_done, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ViewFoodActivity.this).setView(v).show();
        alertDialog.setTitle(SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_NAME));
        alertDialog.setView(v);

        final Button btn_go_back_booking_done = (Button) v.findViewById(R.id.btn_go_back_booking_done);
        btn_go_back_booking_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                finish();
            }
        });
        alertDialog.show();
    }

    public List<ViewFoodItem> getFood() {
        final List<ViewFoodItem> viewFoodItems = new ArrayList<>();
        btnVisiblity(false);
        AndroidNetworking.post(Config.getFoodMenu)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("breakfast");
                            viewFoodItems.add(new ViewFoodItem("", "Breakfast", "", "", "", ""));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String item_price = jsonObject.getString("price");
                                viewFoodItems.add(new ViewFoodItem("", "Breakfast", name, item_price, "1", "0"));
                            }
                            JSONArray array_lunch = response.getJSONArray("lunch");
                            viewFoodItems.add(new ViewFoodItem("", "Lunch", "", "", "", ""));
                            for (int j = 0; j < array_lunch.length(); j++) {
                                JSONObject json = array_lunch.getJSONObject(j);
                                String name_item_lunch = json.getString("name");
                                String item_price_lunch = json.getString("price");
                                viewFoodItems.add(new ViewFoodItem("", "Lunch", name_item_lunch, item_price_lunch, "1", "0"));
                            }
                            JSONArray array_dinner = response.getJSONArray("dinner");
                            viewFoodItems.add(new ViewFoodItem("", "Dinner", "", "", "", ""));
                            for (int k = 0; k < array_dinner.length(); k++) {
                                JSONObject json_obj_dinner = array_dinner.getJSONObject(k);
                                String name_item_dinner = json_obj_dinner.getString("name");
                                String item_price_dinner = json_obj_dinner.getString("price");
                                viewFoodItems.add(new ViewFoodItem("", "Dinner", name_item_dinner, item_price_dinner, "1", "0"));
                            }


                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            }
                            btnVisiblity(true);
                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_view_food, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_view_food, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(coordinatorLayout_view_food, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);
                    }
                });
        return viewFoodItems;
    }

    public void btnVisiblity(boolean status) {
        if (status) {
            btn_order_now_view_food.setVisibility(View.VISIBLE);
            pb_order_now_view_food.setVisibility(View.GONE);
        } else {
            btn_order_now_view_food.setVisibility(View.INVISIBLE);
            pb_order_now_view_food.setVisibility(View.VISIBLE);
        }
    }

    public void food_booking(final String mobile, String category_food, String food_item, String item_price, String food_quantity, int total_price, String repeat_order) {
        btnVisiblity(false);
        AndroidNetworking.post(Config.getFoodBooking)
                .addBodyParameter("user_mobile", mobile)
                .addBodyParameter("category", category_food)
                .addBodyParameter("item", food_item)
                .addBodyParameter("price", item_price)
                .addBodyParameter("quantity", food_quantity)
                .addBodyParameter("total_price", String.valueOf(total_price))

                .addBodyParameter("repeat_order", repeat_order)

                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error") && response.getString("error").equals("false")) {
                                bookingDone();

                                //SplashScreenActivity.sharedPreferencesDatabase.addData(Config.DB_REGISTER_MOBILE, mobile);
                            } else if (response.has("error") && response.getString("error").equals("true")) {
                                Snackbar.make(coordinatorLayout_view_food, response.getString("message").toString(), Snackbar.LENGTH_LONG).show();
                            }
                            btnVisiblity(true);
                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout_view_food, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            btnVisiblity(true);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Snackbar.make(coordinatorLayout_view_food, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(coordinatorLayout_view_food, error.getErrorDetail(), Snackbar.LENGTH_SHORT).show();
                        }
                        btnVisiblity(true);
                    }
                });
    }

    public void getAllRecords() {
        Cursor c = null;
        SQLiteDatabase db = dbhandler.getReadableDatabase();
        String sql = "SELECT * FROM " + dbhandler.TABLE_NAME_FOOD;
        c = db.rawQuery(sql, null);
        if (c != null) {
            while (c.moveToNext()) {
                if (cb_food_menu.isChecked()) {
                    cb_order_everyday = "1";
                } else {
                    cb_order_everyday = "0";
                }
                String item_category = c.getString(c.getColumnIndex("itemcategory"));
                String item_name = c.getString(c.getColumnIndex("itemname"));
                String item_price = c.getString(c.getColumnIndex("itemprice"));
                String item_quantity = c.getString(c.getColumnIndex("itemquantity"));
                String user_mobile = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE);
                int Total_price = Integer.parseInt(item_price) * Integer.parseInt(item_quantity);
                food_booking(user_mobile, item_category, item_name, item_price, item_quantity, Total_price, cb_order_everyday);

            }
            c.close();
        }
    }


    public void detailsDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ViewFoodActivity.this).create();
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
