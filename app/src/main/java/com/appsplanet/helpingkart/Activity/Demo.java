package com.appsplanet.helpingkart.Activity;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.appsplanet.helpingkart.Adapter.ScrollAdapter;
import com.appsplanet.helpingkart.Class.HalfCircleListView;
import com.appsplanet.helpingkart.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new HalfCircleListView(this);
        List<String> listOfStrings = new ArrayList<String>();
        listOfStrings.add("a");
        listOfStrings.add("a");
        listOfStrings.add("b");
        listOfStrings.add("c");
        listOfStrings.add("d");
        listOfStrings.add("e");
        listOfStrings.add("f");
        listOfStrings.add("g");

        listOfStrings.add("a");
        listOfStrings.add("a");
        listOfStrings.add("b");
        listOfStrings.add("c");
        listOfStrings.add("d");
        listOfStrings.add("e");
        listOfStrings.add("f");
        listOfStrings.add("g");

        listOfStrings.add("a");
        listOfStrings.add("a");
        listOfStrings.add("b");
        listOfStrings.add("c");
        listOfStrings.add("d");
        listOfStrings.add("e");
        listOfStrings.add("f");
        listOfStrings.add("g");

        listOfStrings.add("a");
        listOfStrings.add("a");
        listOfStrings.add("b");
        listOfStrings.add("c");
        listOfStrings.add("d");
        listOfStrings.add("e");
        listOfStrings.add("f");
        listOfStrings.add("g");

        listOfStrings.add("a");
        listOfStrings.add("a");
        listOfStrings.add("b");
        listOfStrings.add("c");
        listOfStrings.add("d");
        listOfStrings.add("e");
        listOfStrings.add("f");
        listOfStrings.add("g");
        listView.setAdapter(new ScrollAdapter(listOfStrings));
        setContentView(listView);
    }
}