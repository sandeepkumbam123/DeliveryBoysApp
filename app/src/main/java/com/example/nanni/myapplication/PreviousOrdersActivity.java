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

import com.example.nanni.myapplication.util.DBpreviousOrders;

import java.util.ArrayList;
import java.util.List;

public class PreviousOrdersActivity extends AppCompatActivity {

    ListView mPreviousOrderList;
    DBpreviousOrders dBpreviousOrders;
    List<String> ordersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_orders);
        ordersList=new ArrayList<>();
        mPreviousOrderList=(ListView)findViewById(R.id.lv_previousOrders);
        mPreviousOrderList.setAdapter(new OrdersListAdapter());
        ordersList= dBpreviousOrders.getData();
    }


    public class OrdersListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ordersList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return ordersList.size();
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
            mBT_Pick=(Button)findViewById(R.id.bt_pick);

            mBT_Pick.setVisibility(View.GONE);

           // mTV_OrderNum.setText(ordersList.get());

            return convertView;
        }
    }

}
