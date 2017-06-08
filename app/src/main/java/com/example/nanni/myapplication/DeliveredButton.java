package com.example.nanni.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nanni.myapplication.apiutils.OrderBean;
import com.example.nanni.myapplication.util.DBpreviousOrders;

import java.util.ArrayList;
import java.util.List;

public class DeliveredButton extends Fragment {
Button mbt_Deliver;
    List<OrderBean> deliveryDetailsList;
    DBpreviousOrders dBpreviousOrders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_delivered_button,container,false);
        mbt_Deliver=(Button)view.findViewById(R.id.bt_deliver);
        deliveryDetailsList=new ArrayList<>();

        Bundle bundle=getArguments();

        if(bundle!=null){
            String orderNum=  bundle.getString("OrderNum");
            String orderName=bundle.getString("OrderName");
            String orderDAte=bundle.getString("OrderDate");
            OrderBean orderBean=new OrderBean();
            orderBean.setOrderNumber(orderNum);
            orderBean.setOrderName(orderName);
            orderBean.setOrderDate(orderDAte);
            deliveryDetailsList.add(orderBean);

        }

        mbt_Deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dBpreviousOrders.getInstance().insertData(deliveryDetailsList);
               getFragmentManager().beginTransaction().replace(R.id.content_frame,new PreviousOrdersActivity()).commit();
            }
        });


        return view;
    }



}
