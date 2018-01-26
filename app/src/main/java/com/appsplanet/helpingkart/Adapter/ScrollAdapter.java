package com.appsplanet.helpingkart.Adapter;

import java.util.List;

import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.view.View;
import android.util.Log;

import com.appsplanet.helpingkart.Activity.MyView;

public class ScrollAdapter extends BaseAdapter {

    private List<String> scrollViews;
    private static final String TAG = ScrollAdapter.class.getSimpleName();

    public ScrollAdapter(List<String> scrollViews) {

        this.scrollViews = scrollViews;
    }

    @Override
    public int getCount() {
        return scrollViews.size();
    }

    @Override
    public String getItem(int i) {
        return scrollViews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = new MyView(viewGroup.getContext());
        }
        MyView currentView = (MyView) view;
        String itemViewType = getItem(i);
        Log.d(TAG, itemViewType);
        ((MyView) view).setText(itemViewType);
        return currentView;
    }
}