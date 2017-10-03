package com.example.nanni.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nanni.myapplication.apiutils.APIclient;
import com.example.nanni.myapplication.apiutils.ApiInterface;
import com.example.nanni.myapplication.apiutils.OrderBean;
import com.example.nanni.myapplication.models.UpdateOrderStatus;
import com.example.nanni.myapplication.util.DBpreviousOrders;
import com.example.nanni.myapplication.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeliveredButton extends Fragment {
    Button mbt_Deliver;
    List<OrderBean> deliveryDetailsList;
    DBpreviousOrders dBpreviousOrders;
    Retrofit apiService ;
    ApiInterface serviceCall ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_delivered_button,container,false);

        apiService = APIclient.getClient();
        serviceCall = apiService.create(ApiInterface.class);
        dBpreviousOrders = new DBpreviousOrders(getActivity());
        mbt_Deliver=(Button)view.findViewById(R.id.bt_deliver);
        deliveryDetailsList=new ArrayList<>();

        Bundle bundle=getArguments();

        if(bundle!=null){
            String orderNum=  bundle.getString("OrderNum");
            String orderName=bundle.getString("OrderName");
            String orderDAte=bundle.getString("OrderPhoneNumber");
            OrderBean orderBean=new OrderBean();
            orderBean.setOrderNumber(orderNum);
            orderBean.setOrderName(orderName);
            orderBean.setOrderDate(orderDAte);
            deliveryDetailsList.add(orderBean);

        }

        mbt_Deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dBpreviousOrders.insertData(deliveryDetailsList);
                updateDeliveredList(deliveryDetailsList);
               getFragmentManager().beginTransaction().replace(R.id.content_frame,new PreviousOrdersActivity()).commit();
            }
        });


        return view;
    }








    private void updateDeliveredList(List<OrderBean> list) {
        UpdateOrderStatus bean = new UpdateOrderStatus(list.get(0).getOrderNumber(),
                Utils.ORDER_DELIVERED);
        Call<Object> updatedeliveredProducts = serviceCall.updateOrderStatus(bean);
        updatedeliveredProducts.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }



}
