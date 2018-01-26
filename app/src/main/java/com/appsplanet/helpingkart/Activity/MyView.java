package com.appsplanet.helpingkart.Activity;

import android.widget.TextView;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.content.res.Resources;

public class MyView extends android.support.v7.widget.AppCompatTextView {

    private static final int MAX_INDENT = 300;
    private static final String TAG = MyView.class.getSimpleName();

    public MyView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        float indent = getIndent(getX());
        //Part of the magic happens here too
        canvas.translate(indent, 0);
        super.onDraw(canvas);
        canvas.restore();
    }

    public float getIndent(float distance) {
        float x_vertex = MAX_INDENT;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float y_vertex = displayMetrics.heightPixels / 2 / displayMetrics.density;
        double a = (0 - x_vertex) / (Math.pow((0 - y_vertex), 2));
        float indent = (float) (a * Math.pow((distance -x_vertex ), 2) + y_vertex);
        return indent;
    }
}