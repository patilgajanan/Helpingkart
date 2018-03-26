package com.appsplanet.helpingkart.Class;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.appsplanet.helpingkart.Activity.DrawerFragments.Booking.BookingItem;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewServiceActivity;
import com.appsplanet.helpingkart.Activity.DrawerFragments.Home.ViewActivity.ViewVehicleActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Functions {
    DatePickerDialog datePickerDialog;

    public static void setDatePicker(Context context, final EditText et) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    public static void setTimePicker(Context context, final EditText et) {
        final TimePickerDialog timePickerDialog;
        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                int mHour = selectedHour;
                int mMin = selectedMinute;

                String AM_PM;
                if (selectedHour < 12) {
                    AM_PM = "AM";

                } else {
                    AM_PM = "PM";
                    mHour = mHour - 12;
                }
                et.setText(mHour + ":" + mMin + " " + AM_PM);

            }
        }, hour, minute, false);

        timePickerDialog.show();
    }

    public static void detailsDialog(final Context context, String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "  CLOSE  ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    public static void startNewActivity(Context context, Class<Activity> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    /*public static void getJsonArrayFunction(JSONObject response, List bList, String str1, String str2, Class<?> context) {
        try {
            Iterator<String> keys = response.keys();
            while (keys.hasNext()) {
                String str_Name = keys.next();
//String value = response.optString(str_Name);

                JSONArray jsonArray = response.getJSONArray(str_Name);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String time = jsonObject.getString(str1);
                    String created_date = jsonObject.getString(str2);

                    bList.add(new BookingItem(str_Name, created_date));
                }

            }
        } catch (Exception e) {

        }

    }

    public static void intentArraylist(Context context, Class<?> activity, ArrayList arrayList) {
        Intent intent = new Intent(context, activity);
        intent.putStringArrayListExtra("stock_list", arrayList);
        context.startActivity(intent);
    }
*/
    public static void exitApp(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to EXIT?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity) context).finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void setStatusBarColor(Context context, View statusBar, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = ((AppCompatActivity) context).getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setBackgroundColor(color);
        }
    }


}
