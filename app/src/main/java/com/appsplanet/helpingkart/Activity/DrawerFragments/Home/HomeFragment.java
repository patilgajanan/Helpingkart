package com.appsplanet.helpingkart.Activity.DrawerFragments.Home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFood.ViewFoodItem;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFoodActivity;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewVehicleActivity;
import com.appsplanet.helpingkart.Activity.LoginActivity;
import com.appsplanet.helpingkart.Activity.MainActivity;
import com.appsplanet.helpingkart.Activity.OtpActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.Activity.WalletActivity;
import com.appsplanet.helpingkart.Class.Functions;
import com.appsplanet.helpingkart.Config;
import com.appsplanet.helpingkart.DataBase.DatabaseHandler;
import com.appsplanet.helpingkart.R;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class HomeFragment extends Fragment {

    public static RelativeLayout relativeLayout;
    private ImageView vf_1_home, vf_2_home, vf_3_home, dot_1, dot_2, dot_3;
    private RecyclerView recycler_view_home;
    private HomeAdapter mAdapter;
    private SliderLayout slider_home;
    private ProgressBar pb_home;
    private ProgressBar pb_service_home_fragment;
    private TextView tv_set_day;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl_layout);
        //final ViewFlipper vf_banner_home = (ViewFlipper) rootView.findViewById(R.id.vf_banner_home);
        pb_service_home_fragment = (ProgressBar) rootView.findViewById(R.id.pb_service_home_fragment);
        pb_home = (ProgressBar) rootView.findViewById(R.id.pb_home);
        slider_home = (SliderLayout) rootView.findViewById(R.id.slider_home);
        tv_set_day = (TextView) rootView.findViewById(R.id.tv_set_day);

        setDay(tv_set_day);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        MainActivity.floating_search_view.setVisibility(View.VISIBLE);
        recycler_view_home = (RecyclerView) rootView.findViewById(R.id.recycler_view_home);
        getServiceCategory();
      /*  FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getFragmentManager().getBackStackEntryCount() == 0)
                    Functions.exitApp(getActivity());
            }
        });*/
        recycler_view_home.setNestedScrollingEnabled(false);
        mAdapter = new HomeAdapter(getActivity(), getServiceCategory());
        recycler_view_home.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recycler_view_home.setLayoutManager(layoutManager);
        recycler_view_home.setAdapter(mAdapter);
        getBannerHome();
        MainActivity.floating_search_view.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                mAdapter.getFilter().filter(newQuery);
                //vf_banner_home.setVisibility(View.GONE);

            }
        });
        return rootView;
    }

    public void getBannerHome() {
        AndroidNetworking.post(Config.getHomeBanner)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pbVisiblity(true);
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String image_item = jsonObject.getString("image_url");
                                String imgurl = "http://helpingcart.com/need-hlp/";
                                String combinedImage = imgurl + image_item;
                                HashMap<String, String> url_maps = new HashMap<String, String>();
                                url_maps.put("1", combinedImage);
                                for (String name : url_maps.keySet()) {
                                    DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
                                    defaultSliderView
                                            .image(String.valueOf(url_maps.get(name)))
                                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                                @Override
                                                public void onSliderClick(BaseSliderView slider) {

                                                }
                                            });
                                    slider_home.addSlider(defaultSliderView);
                                }
                                pbVisiblity(true);
                            }


                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            //   btnVisiblity(true);

                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                        pbVisiblity(true);
                    }

                });

    }

    public ArrayList<HomeItem> getServiceCategory() {

        final ArrayList<HomeItem> homeItems = new ArrayList<>();
        //      btnVisiblity(false);
        AndroidNetworking.post(Config.getCategory)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("categories");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                String id_item = c.getString("id");
                                String name_item = c.getString("name");
                                String image_item = c.getString("image_url");
                                String imgurl = "http://helpingcart.com/need-hlp/";
                                String combinedImage = imgurl + image_item;
                                homeItems.add(new HomeItem(id_item, combinedImage, name_item));

                                if (mAdapter != null) {
                                    mAdapter.notifyDataSetChanged();
                                }
                                btnVisiblity(true);

                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            //   btnVisiblity(true);

                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        if (TextUtils.equals(error.getErrorDetail(), "connectionError")) {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                        btnVisiblity(true);
                    }

                });
        return homeItems;
    }

    public void pbVisiblity(boolean status) {
        if (status) {

            pb_home.setVisibility(View.GONE);
        } else {
            pb_home.setVisibility(View.VISIBLE);

        }
    }

    public void btnVisiblity(boolean status) {
        if (status) {

            pb_service_home_fragment.setVisibility(View.GONE);
        } else {
            pb_service_home_fragment.setVisibility(View.VISIBLE);

        }
    }

    public List<HomeItem> getData() {
        List<HomeItem> homeItems = new ArrayList<>();

        homeItems.add(new HomeItem("1", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_taxi.png?alt=media&token=c64fd150-2c42-472f-8bf3-a1e82a422db0", "VEHICLE & MECHANICS"));
        homeItems.add(new HomeItem("2", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_cards.png?alt=media&token=0c7f4432-d617-4536-9217-ce07d299ff9b", "MARRIAGE & EVENTS"));
        homeItems.add(new HomeItem("3", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_bed.png?alt=media&token=8e801936-6467-482e-9168-21b3399bdadc", "CARPENTER"));

        homeItems.add(new HomeItem("4", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_plumbing.png?alt=media&token=5dfa2c88-1924-4215-8e83-96bfa5823c53", "PLUMBER"));
        homeItems.add(new HomeItem("5", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_settings.png?alt=media&token=83a9b291-a412-42cc-9982-da7311b1b12f", "MECHANICS"));
        homeItems.add(new HomeItem("6", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_worker.png?alt=media&token=b0de863a-45d1-4144-a2a5-97bd1d56be77", "LABOUR"));

        homeItems.add(new HomeItem("7", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_taxi.png?alt=media&token=c64fd150-2c42-472f-8bf3-a1e82a422db0", "CAR BOOKING"));
        homeItems.add(new HomeItem("8", "https://firebasestorage.googleapis.com/v0/b/fir-ex-43a12.appspot.com/o/icon_food.png?alt=media&token=e4dfb71f-07eb-48a0-90a8-94bd04770906", "FOOD"));

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        return homeItems;
    }

    public void setDay(TextView textView) {

        Calendar c = Calendar.getInstance();
        String name = SplashScreenActivity.sharedPreferencesDatabase.getData("register_name");

        String str_good_morning = "Good Morning";
        String str_good_aftrnoon = "Good Afternoon";
        String str_good_evening = "Good Evening";

        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            textView.setText(str_good_morning + "" + name);
            //textView.setText("Good Morning");

        } else if (timeOfDay >= 12 && timeOfDay < 16) {

            textView.setText(str_good_aftrnoon + " " + name);
        } else if (timeOfDay >= 16 && timeOfDay < 21) {

            textView.setText(str_good_evening + " " + name);
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            textView.setText(str_good_evening + " " + name);
        }
    }

}
