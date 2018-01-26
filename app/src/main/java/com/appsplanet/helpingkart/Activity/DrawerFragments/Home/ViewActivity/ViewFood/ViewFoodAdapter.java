package com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewFood;

/**
 * Created by surajk44437 on 8/12/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsplanet.helpingkart.DataBase.DatabaseHandler;
import com.appsplanet.helpingkart.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;

public class ViewFoodAdapter extends RecyclerView.Adapter<ViewFoodAdapter.MyViewHolder> {

    private List<ViewFoodItem> viewFoodItems;
    private Context context;
    ArrayList<String> list = new ArrayList<String>();
    private DatabaseHandler databaseHandler;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_food_category;
        public TextView tv_main_title_food_category, tv_title_food_category, tv_price_food_category,
                tv_minus_food_category, tv_quantity_food_category, tv_plus_food_category;
        public CheckBox checkbox_food_category;

        public MyViewHolder(View view) {
            super(view);
            databaseHandler = new DatabaseHandler(context);
            ll_food_category = (LinearLayout) view.findViewById(R.id.ll_food_category);
            tv_main_title_food_category = (TextView) view.findViewById(R.id.tv_main_title_food_category);
            tv_title_food_category = (TextView) view.findViewById(R.id.tv_title_food_category);
            tv_price_food_category = (TextView) view.findViewById(R.id.tv_price_food_category);
            tv_minus_food_category = (TextView) view.findViewById(R.id.tv_minus_food_category);
            tv_quantity_food_category = (TextView) view.findViewById(R.id.tv_quantity_food_category);
            tv_plus_food_category = (TextView) view.findViewById(R.id.tv_plus_food_category);
            checkbox_food_category = (CheckBox) view.findViewById(R.id.checkbox_food_category);

        }
    }


    public ViewFoodAdapter(Context context, List<ViewFoodItem> viewFoodItems) {
        this.context = context;
        this.viewFoodItems = viewFoodItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ViewFoodItem viewFoodItem = viewFoodItems.get(position);

        holder.tv_main_title_food_category.setText(viewFoodItem.getMain_title());
        holder.tv_title_food_category.setText(viewFoodItem.getTitle());
        holder.tv_price_food_category.setText(viewFoodItem.getPrice());
        holder.tv_quantity_food_category.setText(viewFoodItem.getQuantity());

        holder.tv_minus_food_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.valueOf(holder.tv_quantity_food_category.getText().toString());
                int result = value - 1;
                holder.tv_quantity_food_category.setText("" + result);
            }
        });
        holder.tv_plus_food_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.valueOf(holder.tv_quantity_food_category.getText().toString());
                int result = value + 1;
                holder.tv_quantity_food_category.setText("" + result);

            }
        });

        holder.checkbox_food_category.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (holder.checkbox_food_category.isChecked()) {
                    Toast.makeText(context, "Checkbox checkd", Toast.LENGTH_SHORT).show();
                    String maintitle_food = holder.tv_main_title_food_category.getText().toString();

                    String item_name_food = holder.tv_title_food_category.getText().toString();
                    String price_for_food = holder.tv_price_food_category.getText().toString();
                    String quantity_for_food = holder.tv_quantity_food_category.getText().toString();
                    databaseHandler.insertFoodorder(maintitle_food, item_name_food, price_for_food, quantity_for_food);
                }

            }


        });

        if (TextUtils.isEmpty(viewFoodItem.getId()) && TextUtils.isEmpty(viewFoodItem.getTitle())
                && TextUtils.isEmpty(viewFoodItem.getPrice()) && TextUtils.isEmpty(viewFoodItem.getQuantity())) {
            holder.tv_main_title_food_category.setVisibility(View.VISIBLE);
            holder.ll_food_category.setVisibility(View.GONE);
            holder.tv_main_title_food_category.setText(viewFoodItem.getMain_title());
        } else {
            holder.tv_main_title_food_category.setVisibility(View.GONE);
            holder.ll_food_category.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return viewFoodItems.size();
    }
}
