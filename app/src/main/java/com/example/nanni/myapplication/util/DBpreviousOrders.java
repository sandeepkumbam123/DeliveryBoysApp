package com.example.nanni.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nanni.myapplication.apiutils.OrderBean;

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
    private static DBpreviousOrders databaseInstance = null;
    Context context;

    public DBpreviousOrders(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBpreviousOrders getInstance() {
        return databaseInstance;
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

    public boolean insertData(List<OrderBean> list) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            cv.put(KEY_ORDER_NUMBER, list.get(i).getOrderNumber());
            cv.put(KEY_ORDER_NAME, list.get(i).getOrderName());
            cv.put(KEY_ORDERDATE, list.get(i).getOrderDate());
            sqdb.insert(TABLE_NAME, null, cv);
            Log.d("inserted into", list.get(i).getOrderDate());
        }
        return true;
    }

    public List<OrderBean> getData() {
        List<OrderBean> wishes = new ArrayList<>();

        SQLiteDatabase sqdb = this.getReadableDatabase();
        Cursor cur = sqdb.rawQuery("select * from " + TABLE_NAME, null);
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            OrderBean orderBean = new OrderBean();
            orderBean.setOrderDate(cur.getString(cur.getColumnIndex(KEY_ORDERDATE)));
            orderBean.setOrderName(cur.getString(cur.getColumnIndex(KEY_ORDER_NAME)));
            orderBean.setOrderNumber(cur.getString(cur.getColumnIndex(KEY_ORDER_NUMBER)));

            wishes.add(orderBean);
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