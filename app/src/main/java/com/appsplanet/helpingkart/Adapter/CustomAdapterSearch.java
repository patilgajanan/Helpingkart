package com.appsplanet.helpingkart.Adapter;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appsplanet.helpingkart.R;

import java.util.ArrayList;

public class CustomAdapterSearch extends BaseAdapter {

    Context context;
    ArrayList<Search> search_service;
    LayoutInflater inflater;

    public CustomAdapterSearch(Context context, ArrayList<Search> search_service) {
        this.context = context;
        this.search_service = search_service;
    }

    @Override
    public int getCount() {
        return search_service.size();
    }

    @Override
    public Object getItem(int i) {
        return search_service.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_search_item, parent, false);
        }
        TextView nameTxt = (TextView) convertView.findViewById(R.id.tv_search_adapter);
        nameTxt.setText(search_service.get(position).getName());
        final int pos = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, search_service.get(pos).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
