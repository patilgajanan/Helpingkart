package com.appsplanet.helpingkart.Activity.DrawerFragments.Booking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.HomeAdapter;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.HomeItem;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFoodActivity;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewServiceActivity;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewVehicleActivity;
import com.appsplanet.helpingkart.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> implements Filterable {

    private List<BookingItem> homeItems;
    private Context context;
    ArrayList<BookingItem> filterList;
    CustomFilterForBooking filter;

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilterForBooking();
        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        //btn_complete_booking_item,btn_cancel_booking_item,btn_view_booking_item

        public ProgressBar pb_home_item;
        public TextView tv_item_date;
        public TextView tv_item_type, tv_item_booking_cost, tv_item_booking_status;
        public Button btn_complete_booking_item, btn_cancel_booking_item, btn_view_booking_item;

        public MyViewHolder(View view) {
            super(view);

            pb_home_item = (ProgressBar) view.findViewById(R.id.pb_home_item);
            tv_item_date = (TextView) view.findViewById(R.id.tv_item_booking_date);
            tv_item_type = (TextView) view.findViewById(R.id.tv_item_booking_type);
            tv_item_booking_cost = (TextView) view.findViewById(R.id.tv_item_booking_cost);
            tv_item_booking_status = (TextView) view.findViewById(R.id.tv_item_booking_status);


            btn_complete_booking_item = (Button) view.findViewById(R.id.btn_complete_booking_item);
            btn_cancel_booking_item = (Button) view.findViewById(R.id.btn_cancel_booking_item);
            btn_view_booking_item = (Button) view.findViewById(R.id.btn_view_booking_item);


        }
    }


    public BookingAdapter(Context context, ArrayList<BookingItem> homeItems) {
        this.context = context;
        this.homeItems = homeItems;
        this.filterList = homeItems;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BookingItem homeItem = homeItems.get(position);
        holder.tv_item_type.setText(homeItem.getBooking_type());
        holder.tv_item_date.setText(homeItem.getBooking_date());
        //holder.tv_item_booking_status.setText(homeItem.getBooking_status());
        //holder.tv_item_booking_cost.setText(homeItem.getBooking_cost());
        holder.btn_view_booking_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

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
                ArrayList<BookingItem> filters = new ArrayList<BookingItem>();
                //get specific items
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getBooking_type().toUpperCase().contains(constraint)) {
                        BookingItem p = new BookingItem(filterList.get(i).getBooking_type(), filterList.get(i).getBooking_date());
                        //filterList.get(i).getBooking_cost(), filterList.get(i).getBooking_status()
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
            homeItems = (ArrayList<BookingItem>) results.values;
            notifyDataSetChanged();
        }
    }


    public void booking_status() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertLayout = inflater.inflate(R.layout.dialog_booking_completed, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        TextView tv_dialog_you_have_points, tv_dialog_total_payment_money;
        Button btn_booking_completed, btn_booking_point_redeem;

        tv_dialog_you_have_points = alertLayout.findViewById(R.id.tv_dialog_you_have_points);
        tv_dialog_total_payment_money = alertLayout.findViewById(R.id.tv_dialog_total_payment_money);
        btn_booking_completed = alertLayout.findViewById(R.id.btn_booking_completed);
        btn_booking_point_redeem = alertLayout.findViewById(R.id.btn_booking_point_redeem);


        alert.setCancelable(false);


        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void view_booking() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertLayout = inflater.inflate(R.layout.dialog_booking_completed, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "Cancel clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        AlertDialog dialog = alert.create();
        dialog.show();
    }


//tv_item_booking_cost,tv_item_booking_status,ll_tv_booking_type,tv_item_booking_type
//tv_item_booking_date,iv_service_name_booking_item,ll_main_booking_item

    //btn_complete_booking_item,btn_cancel_booking_item,btn_view_booking_item


}