package com.example.nanni.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanni.myapplication.apiutils.OrderBean;
import com.example.nanni.myapplication.util.DBpreviousOrders;

import java.util.ArrayList;
import java.util.List;

public class PreviousOrdersActivity extends Fragment implements OrderInfoListener {

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
        mPreviousOrderList.setAdapter(new OrdersListAdapter(this));
        return view;
    }



    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.order_details, null);
        final TextView orderName = (TextView) dialogView.findViewById(R.id.tv_orderName);
        final TextView orderAddress = (TextView) dialogView.findViewById(R.id.tv_order_address);
        final TextView orderId = (TextView) dialogView.findViewById(R.id.tv_orderId);
        final TextView orderType = (TextView) dialogView.findViewById(R.id.tv_order_type);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.bt_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    @Override
    public void onClick(int position) {
        showDialog();
        Toast.makeText(getActivity(), "Details of the Order will be soon available on a dialog", Toast.LENGTH_SHORT).show();
    }


    public class OrdersListAdapter extends BaseAdapter {
        OrderInfoListener listener;

        public OrdersListAdapter(OrderInfoListener listener) {
         this.listener = listener;
        }

        @Override
        public int getCount() {
            return ordersList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
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

            mTV_OrderNum.setText(ordersList.get(position).getOrderNumber());
            mTV_OrderName.setText(ordersList.get(position).getOrderName());
            mTV_OrederDate.setText(ordersList.get(position).getOrderDate());

            mTV_OrderNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });

            return convertView;
        }



    }


}
