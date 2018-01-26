package com.appsplanet.helpingkart.Activity.DrawerFragments.Booking;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Activity.MainActivity;
import com.appsplanet.helpingkart.Activity.ProfileActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.R;
import com.arlib.floatingsearchview.FloatingSearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class BookingFragment extends Fragment {
    private RecyclerView recyclerView_booking;
    private ProgressBar pb_booking;
    private TextView tv_error_booking;
    private BookingAdapter bookingAdapter;
    private String user_mobile;

    public BookingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_booking, container, false);

        user_mobile = SplashScreenActivity.sharedPreferencesDatabase.getData(Config.DB_REGISTER_MOBILE);

        recyclerView_booking = (RecyclerView) rootView.findViewById(R.id.recyclerView_booking);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        // getOrderHistory();
        MainActivity.floating_search_view.setVisibility(View.VISIBLE);
        pb_booking = (ProgressBar) rootView.findViewById(R.id.pb_booking);
        recyclerView_booking.setNestedScrollingEnabled(false);
        recyclerView_booking.setHasFixedSize(true);
        tv_error_booking = (TextView) rootView.findViewById(R.id.tv_error_booking);
        bookingAdapter = new BookingAdapter(getActivity(), getOrderHistory());
        recyclerView_booking.setLayoutManager(mLayoutManager);
        recyclerView_booking.setAdapter(bookingAdapter);
        //btnVisiblity(false);
        MainActivity.floating_search_view.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                bookingAdapter.getFilter().filter(newQuery);
                //vf_banner_home.setVisibility(View.GONE);

            }
        });
        return rootView;
    }

    public ArrayList<BookingItem> getDatas() {
        ArrayList<BookingItem> bookingItems = new ArrayList<>();
/*
        bookingItems.add(new BookingItem("VEHICLE & MECHANICS", "08.20 AM", "2-10-2017"));
        bookingItems.add(new BookingItem("MARRIAGE & EVENTS", "05.20 PM", "05-10-2017"));
        bookingItems.add(new BookingItem("CARPENTER", "05.20 PM", "05-10-2017"));

        bookingItems.add(new BookingItem("VEHICLE & MECHANICS", "08.20 AM", "2-10-2017"));
        bookingItems.add(new BookingItem("MARRIAGE & EVENTS", "05.20 PM", "05-10-2017"));
        bookingItems.add(new BookingItem("CARPENTER", "05.20 PM", "05-10-2017"));

        bookingItems.add(new BookingItem("VEHICLE & MECHANICS", "08.20 AM", "2-10-2017"));
        bookingItems.add(new BookingItem("MARRIAGE & EVENTS", "05.20 PM", "05-10-2017"));
        bookingItems.add(new BookingItem("CARPENTER", "05.20 PM", "05-10-2017"));

        bookingItems.add(new BookingItem("VEHICLE & MECHANICS", "08.20 AM", "2-10-2017"));
        bookingItems.add(new BookingItem("MARRIAGE & EVENTS", "05.20 PM", "05-10-2017"));
        bookingItems.add(new BookingItem("CARPENTER", "05.20 PM", "05-10-2017"));*/

        if (bookingAdapter != null) {
            bookingAdapter.notifyDataSetChanged();
        }
        return bookingItems;
    }


    public ArrayList<BookingItem> getOrderHistory() {
        btnVisiblity(false);
        final ArrayList<BookingItem> viewBookingItems = new ArrayList<>();
        AndroidNetworking.post(Config.getOrderHistory)
                .addBodyParameter("mobile", user_mobile)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Functions.getJsonArrayFunction(response, viewBookingItems, "time", "created_date", BookingItem.class);
                        try {

                            Iterator<String> keys = response.keys();
                            while (keys.hasNext()) {
                                String str_Name = keys.next();
                                JSONArray jsonArray = response.getJSONArray(str_Name);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    if (str_Name.equals("carBooking")) {
                                        String order_date = jsonObject.getString("created_date");
                                        //String order_cost = jsonObject.getString("order_cost");
                                        /*String order_status = jsonObject.getString("status");
                                        if (order_status == null) {
                                            order_status = "Active";
                                        }*/
                                        viewBookingItems.add(new BookingItem(str_Name, order_date));
                                    } else if (str_Name.equals("vehicleMechanicBooking")) {
                                        //String order_cost = jsonObject.getString("order_cost");

                                        String order_status = jsonObject.getString("status");
                                     /*   if (order_status == null) {
                                            order_status = "Active";
                                        }*/
                                        String order_date = jsonObject.getString("created_date");
                                        viewBookingItems.add(new BookingItem(str_Name, order_date));
                                    } else {
                                        //String order_cost = jsonObject.getString("order_cost");
                                        String order_status = jsonObject.getString("status");
                                       /* if (order_status == null) {
                                            order_status = "Active";
                                        }*/
                                        String order_date = jsonObject.getString("created_date");
                                        viewBookingItems.add(new BookingItem(str_Name, order_date));
                                    }

                                }
                                if (bookingAdapter != null) {
                                    bookingAdapter.notifyDataSetChanged();
                                }

                            }


                            btnVisiblity(true);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            btnVisiblity(true);


                        }

                    }


                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                    }

                });
        return viewBookingItems;
    }

    public void btnVisiblity(boolean status) {
        if (status) {

            pb_booking.setVisibility(View.GONE);
        } else {

            pb_booking.setVisibility(View.VISIBLE);
        }
    }

}
