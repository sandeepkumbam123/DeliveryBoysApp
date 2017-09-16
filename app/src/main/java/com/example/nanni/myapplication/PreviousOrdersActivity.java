package com.example.nanni.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nanni.myapplication.apiutils.OrderBean;
import com.example.nanni.myapplication.util.DBpreviousOrders;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrdersActivity extends Fragment  {

    ListView mPreviousOrderList;
    DBpreviousOrders dBpreviousOrders;
    List<OrderBean> ordersList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_previous_orders,container,false);

        ordersList=new ArrayList<>();
        dBpreviousOrders=new DBpreviousOrders(getActivity());
        ordersList= dBpreviousOrders.getData();


        mPreviousOrderList=(ListView)view.findViewById(R.id.lv_previousOrders);
        mPreviousOrderList.setAdapter(new OrdersListAdapter());
        return view;
    }


        public class OrdersListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ordersList.size();
        }

        @Override
        public Object getItem(int position) {
            return position ;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView mTV_OrderNum;
            TextView mTV_OrderName;
            TextView mTV_OrederDate;
            Button mBT_Pick;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_view, parent, false);
            }
            mTV_OrderNum=(TextView)convertView.findViewById(R.id.tv_orderNum);
            mTV_OrderName=(TextView)convertView.findViewById(R.id.tv_orderName);
            mTV_OrederDate=(TextView)convertView.findViewById(R.id.tv_deliveryTime);
            mBT_Pick=(Button)convertView.findViewById(R.id.bt_pick);

            mBT_Pick.setVisibility(View.GONE);

            mTV_OrderNum.setText("OrderNumber : "+ordersList.get(position).getOrderNumber());
            mTV_OrderName.setText(ordersList.get(position).getOrderName());
            mTV_OrederDate.setText(ordersList.get(position).getOrderDate());


            return convertView;
        }



    }


}
