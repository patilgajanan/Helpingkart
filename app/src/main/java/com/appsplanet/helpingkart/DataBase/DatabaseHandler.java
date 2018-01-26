package com.appsplanet.helpingkart.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ServiceDb";    // Database Name
    private static final int DATABASE_Version = 1;    // Database Version

    ///////////////table for saving food order

    public static final String TABLE_NAME_FOOD = "foodorder";   // Table Name
    private static final String FID = "_id";     // Column I (Primary Key)
    private static final String ITEM_CATEGORY_NAME = "itemcategory";
    private static final String ITEM_NAME = "itemname";    //Column II
    private static final String ITEM_PRICE = "itemprice";
    private static final String ITEM_QUANTITY = "itemquantity";

    public static final String TABLE_NAME_NOTIFICATION = "notification";   // Table Name
    public static final String ITEM_MESSAGE = "message";   // Table Name

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_FOOD_TABLE = "CREATE TABLE foodorder ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "itemcategory TEXT, " +
                "itemname TEXT," + "itemprice INTEGER," + "itemquantity INTEGER)";
        Log.d("Table Created", CREATE_BOOK_FOOD_TABLE);

        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE notification(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "message TEXT)";

        db.execSQL(CREATE_NOTIFICATION_TABLE);

        db.execSQL(CREATE_BOOK_FOOD_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTIFICATION);
    }


    public void insertFoodorder(String item_category, String item_name, String item_price, String item_quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_CATEGORY_NAME, item_category);
        values.put(ITEM_NAME, item_name);
        values.put(ITEM_PRICE, item_price);
        values.put(ITEM_QUANTITY, item_quantity);
        db.insert(TABLE_NAME_FOOD, null, values);
        db.close();
    }


    public void insertnotification(String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_MESSAGE, message);
        db.insert(TABLE_NAME_NOTIFICATION, null, values);
        db.close();
    }
}
