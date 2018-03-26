package com.appsplanet.helpingkart.Activity.DrawerFragments.Home;

/**
 * Created by surajk44437 on 8/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFoodActivity;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewServiceActivity;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewVehicleActivity;
import com.appsplanet.helpingkart.Activity.SplashScreenActivity;
import com.appsplanet.helpingkart.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements Filterable {

    private List<HomeItem> homeItems;
    private Context context;
    ArrayList<HomeItem> filterList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_home_item;
        public ProgressBar pb_home_item;
        public ImageView iv_home_item;
        public TextView tv_home_item;

        public MyViewHolder(View view) {
            super(view);
            ll_home_item = (LinearLayout) view.findViewById(R.id.ll_home_item);
            pb_home_item = (ProgressBar) view.findViewById(R.id.pb_home_item);
            iv_home_item = (ImageView) view.findViewById(R.id.iv_home_item);
            tv_home_item = (TextView) view.findViewById(R.id.tv_home_item);

        }
    }


    public HomeAdapter(Context context, ArrayList<HomeItem> homeItems) {
        this.context = context;
        this.homeItems = homeItems;
        this.filterList = homeItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final HomeItem homeItem = homeItems.get(position);
        holder.tv_home_item.setText(homeItem.getTitle());

        if (!TextUtils.isEmpty(homeItem.getImg())) {
            Picasso.with(context).load(homeItem.getImg()).into(holder.iv_home_item, new Callback() {
                @Override
                public void onSuccess() {
                    holder.pb_home_item.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    holder.pb_home_item.setVisibility(View.GONE);
                }
            });
        }
        if (TextUtils.equals(homeItem.getTitle(), "Car Booking")) {
            holder.ll_home_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SplashScreenActivity.sharedPreferencesDatabase.addData("Servicename", homeItem.getTitle());

                    SplashScreenActivity.sharedPreferencesDatabase.addData("serviceimg", homeItem.getImg());
                    Intent i = new Intent(context, ViewVehicleActivity.class);
                    context.startActivity(i);
                }
            });
        } else if (TextUtils.equals(homeItem.getTitle(), "Food")) {
            holder.ll_home_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SplashScreenActivity.sharedPreferencesDatabase.addData("Servicename", homeItem.getTitle());
                    SplashScreenActivity.sharedPreferencesDatabase.addData("serviceimg", homeItem.getImg());
                    Intent i = new Intent(context, ViewFoodActivity.class);
                    context.startActivity(i);
                }
            });
        } else {
            holder.ll_home_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SplashScreenActivity.sharedPreferencesDatabase.addData("serviceimg", homeItem.getImg());
                    SplashScreenActivity.sharedPreferencesDatabase.addData("Servicename", homeItem.getTitle());
                    Intent i = new Intent(context, ViewServiceActivity.class);
                    context.startActivity(i);
                }
            });
        }

    }

    CustomFilter filter;

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                //CONSTARINT TO UPPER
                constraint = constraint.toString().toUpperCase();
                ArrayList<HomeItem> filters = new ArrayList<HomeItem>();
                //get specific items
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                        HomeItem p = new HomeItem(filterList.get(i).getId(), filterList.get(i).getImg(), filterList.get(i).getTitle());
                        filters.add(p);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            homeItems = (ArrayList<HomeItem>) results.values;
            notifyDataSetChanged();
        }
    }

}
