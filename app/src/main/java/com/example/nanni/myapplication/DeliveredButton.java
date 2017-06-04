package com.example.nanni.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nanni.myapplication.util.DBpreviousOrders;

import java.util.ArrayList;
import java.util.List;

public class DeliveredButton extends AppCompatActivity {
Button mbt_Deliver;
    List<String> deliveryDetailsList;
    DBpreviousOrders dBpreviousOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_button);
        mbt_Deliver=(Button)findViewById(R.id.bt_deliver);
        deliveryDetailsList=new ArrayList<>();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
          String orderNum=  bundle.getString("OrderNum");
            String orderName=bundle.getString("OrderName");
            String orderDAte=bundle.getString("OrderDate");
            deliveryDetailsList.add(orderNum);
            deliveryDetailsList.add(orderName);
            deliveryDetailsList.add(orderDAte);
        }

        mbt_Deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dBpreviousOrders=new DBpreviousOrders(getApplicationContext());
                dBpreviousOrders.insertData(deliveryDetailsList);
                Intent intent=new Intent(getApplicationContext(),PreviousOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}
