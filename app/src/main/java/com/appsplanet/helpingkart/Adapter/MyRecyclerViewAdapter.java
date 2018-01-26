package com.appsplanet.helpingkart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsplanet.helpingkart.Activity.DrawerFragments.Booking.BookingAdapter;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Booking.BookingItem;
import com.appsplanet.helpingkart.Class.NotificationItem;
import com.appsplanet.helpingkart.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements Filterable {

    private List<NotificationItem> homeItems;
    private Context context;
    ArrayList<NotificationItem> filterList;
    CustomFilterForBooking filter;

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilterForBooking();
        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pb_home_item;
        public TextView tv_title_notificaton, tv_message_notification, tv_date_notification;

        public MyViewHolder(View view) {
            super(view);

            //pb_home_item = (ProgressBar) view.findViewById(R.id.pb_home_item);
            tv_title_notificaton = (TextView) view.findViewById(R.id.tv_title_notificaton);
            tv_message_notification = (TextView) view.findViewById(R.id.tv_message_notification);
            tv_date_notification = (TextView) view.findViewById(R.id.tv_date_notification);

        }
    }


    public MyRecyclerViewAdapter(Context context, ArrayList<NotificationItem> homeItems) {
        this.context = context;
        this.homeItems = homeItems;
        this.filterList = homeItems;

    }

    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_cardview_item, parent, false);
        return new MyRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewAdapter.MyViewHolder holder, int position) {
        NotificationItem homeItem = homeItems.get(position);
        holder.tv_title_notificaton.setText(homeItem.getStr_title_notification());
        holder.tv_message_notification.setText(homeItem.getStr_message_notification());
        holder.tv_date_notification.setText(homeItem.getStr_time_notification());


    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    class CustomFilterForBooking extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                //CONSTARINT TO UPPER
                constraint = constraint.toString().toUpperCase();
                ArrayList<NotificationItem> filters = new ArrayList<NotificationItem>();
                //get specific items
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getStr_title_notification().toUpperCase().contains(constraint)) {
                        NotificationItem p = new NotificationItem(filterList.get(i).getStr_title_notification(), filterList.get(i).getStr_message_notification(), filterList.get(i).getStr_time_notification());
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
            homeItems = (ArrayList<NotificationItem>) results.values;
            notifyDataSetChanged();
        }
    }
}