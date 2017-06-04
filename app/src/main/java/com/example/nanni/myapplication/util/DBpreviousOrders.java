package com.example.nanni.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanni...!!! on 5/27/2017.
 */

public class DBpreviousOrders extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "DELIVERYBOYS_PREVIOUS_ORDERS";
    public static final String DATABASE_NAME = "DELIVERY_BOYS";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_ORDER_NUMBER = "orderNum";
    public static final String KEY_ORDER_NAME = "orderName";
    public static final String KEY_ORDERDATE = "orderDate";

Context context;
    public DBpreviousOrders(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PREVIOUS_ORDERS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ORDER_NUMBER + " INTEGER PRIMARY KEY," + KEY_ORDER_NAME + " TEXT,"
                + KEY_ORDERDATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_PREVIOUS_ORDERS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  boolean insertData(List<String> list) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            cv.put(KEY_ORDER_NUMBER, list.get(i));
            cv.put(KEY_ORDER_NAME,list.get(i));
            cv.put(KEY_ORDERDATE,list.get(i));
            sqdb.insert(TABLE_NAME, null, cv);
            Log.d("inserted into", list.get(i));
        }
return true;
    }

    public List<String> getData() {
        List<String> wishes = new ArrayList<String>();
        SQLiteDatabase sqdb = this.getReadableDatabase();
        Cursor cur = sqdb.rawQuery("select * from " + TABLE_NAME, null);
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            wishes.add(cur.getString(cur.getColumnIndex(KEY_ORDER_NUMBER)));
            wishes.add(cur.getString(cur.getColumnIndex(KEY_ORDER_NAME)));
            wishes.add(cur.getString(cur.getColumnIndex(KEY_ORDERDATE)));
            cur.moveToNext();

        }
        Log.d("Wishes size", "" + wishes.size());

        return wishes;
    }
    public void updateData(int id) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ORDER_NUMBER, id);
        sqdb.update(TABLE_NAME, values, "id=" + id, new String[]{String.valueOf(id)});
    }

    }