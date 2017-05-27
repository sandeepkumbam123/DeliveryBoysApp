package com.example.nanni.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PreviousOrdersActivity extends AppCompatActivity {

    ListView mPreviousOrderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previousOrders);
        mPreviousOrderList=(ListView)findViewById(R.id.lv_previousOrders);

    }


    public class OrdersListAdapter extends BaseAdapter {
        ArrayList<String> ordersList = new ArrayList<>();

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView mTV_OrderNum;
            TextView mTV_OrderName;
            TextView mTV_OrederDate;
            Button mBT_Pick;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_view, parent, false);
            }
            mTV_OrderNum=(TextView)findViewById(R.id.tv_orderNum);
            mTV_OrderName=(TextView)findViewById(R.id.tv_orderName);
            mTV_OrederDate=(TextView)findViewById(R.id.tv_deliveryTime);
            mBT_Pick=(Button)findViewById(R.id.bt_pickNow);

            mBT_Pick.setVisibility(View.GONE);

            return convertView;
        }
    }

}
