package com.example.nanni.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanni.myapplication.apiutils.APIclient;
import com.example.nanni.myapplication.apiutils.ApiInterface;
import com.example.nanni.myapplication.apiutils.LoginResponse;
import com.example.nanni.myapplication.apiutils.OrderBean;
import com.example.nanni.myapplication.models.UpdateOrderStatus;
import com.example.nanni.myapplication.models.UserOrdersModel;
import com.example.nanni.myapplication.util.DBpreviousOrders;
import com.example.nanni.myapplication.util.Utils;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Fragment implements LocationListener ,OrderInfoListener{
    LocationManager mLocationManager;
    private boolean isGPSEnabled;
    Intent mapsActivity;
    private boolean isNetworkEnabled;
    private Location location;
    private double latitude;
    private double longitude;
    public boolean isLocationPermissionGranted;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    ListView mOrdersList;
    List<UserOrdersModel>  userOrdersList;
    ProgressDialog progressDialog;
    ApiInterface apiService;


    String destinationAdress1 = "17.447070,78.374155";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    int positionClicked=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home,container,false);
        apiService =
                APIclient.getClient().create(ApiInterface.class);
        mOrdersList = (ListView) view.findViewById(R.id.lv_OrdersList);

        getOrderDetails();


        mLocationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);



        return view;
    }

    @Override
    public void onClick(int position) {
        showDialog(position);
    }

    private void showDialog( int position) {

        UserOrdersModel userModel = userOrdersList.get(position);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.order_details, null);
        final TextView orderName = (TextView) dialogView.findViewById(R.id.tv_orderName);
        final TextView orderAddress = (TextView) dialogView.findViewById(R.id.tv_order_address);
        final TextView orderId = (TextView) dialogView.findViewById(R.id.tv_orderId);
        final TextView orderType = (TextView) dialogView.findViewById(R.id.tv_order_type);
        Button okayButton = (Button) dialogView.findViewById(R.id.bt_okay);
        orderName.setText(userModel.getCustomerName() +" , "+userModel.getCustomerPhoneNumber());
        orderAddress.setText(userModel.getOrderAddress()+" , "+ userModel.getOrderCity()+" , "+userModel.getOrderState()+" . ");
        orderId.setText(userModel.getmOrderId());
        orderType.setText(userModel.getOrderPaymentType());

        final AlertDialog.Builder dialog  = new AlertDialog.Builder(getActivity());
        dialog.setView(dialogView);

        final AlertDialog dialog1 = dialog.show();

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });



    }


    private void getOrderDetails() {

        progressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Getting Orders...", true);


        Call<List<UserOrdersModel>> userOrderDetails = apiService.getUserOrders();
        userOrderDetails.enqueue(new Callback<List<UserOrdersModel>>() {
            @Override
            public void onResponse(Call<List<UserOrdersModel>> call, Response<List<UserOrdersModel>> response) {
                int statusCode = response.code();
                progressDialog.dismiss();
                List<UserOrdersModel> ordersResponse = response.body();
                userOrdersList = ordersResponse;
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<UserOrdersModel>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("RESPONSE_Login", "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Internal error. Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void trackLocation(String sourceAdress) {

        mapsActivity = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + sourceAdress + "&daddr="
                        + destinationAdress1));
        startActivityForResult(mapsActivity, 507);

    }

    public void setAdapter() {
        OrdersListAdapter ordersListAdapter = new OrdersListAdapter(this,userOrdersList);
        mOrdersList.setAdapter(ordersListAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 507:
                DeliveredButton deliveredButton=new DeliveredButton();
                Bundle bundle=new Bundle();
                bundle.putString("OrderNum",userOrdersList.get(positionClicked).getmOrderId());
                bundle.putString("OrderName",userOrdersList.get(positionClicked).getCustomerName());
                bundle.putString("OrderPhoneNumber",userOrdersList.get(positionClicked).getCustomerPhoneNumber());
                deliveredButton.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.content_frame,deliveredButton).commit();
        }
    }



   /* private void getLocation() {
        isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            showSettingsAlert();
        } else {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionGranted = false;
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                isLocationPermissionGranted = true;
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, HomeActivity.this);
                    if (mLocationManager != null) {
                        location = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Toast.makeText(getActivity(), "Lat" + latitude + "  " + "Lon" + longitude, Toast.LENGTH_LONG).show();
                            //  sendCustomerNumberAPI(latitude, longitude);
                            trackLocation(latitude + "," + longitude);
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, HomeActivity.this);
                        if (mLocationManager != null) {
                            location = mLocationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                Toast.makeText(getActivity(), "Lat" + latitude + "  " + "Lon" + longitude, Toast.LENGTH_LONG).show();
                                trackLocation(latitude + "," + longitude);
                            }


                        }
                    }
                }
            }
        }
    }*/


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public class OrdersListAdapter extends BaseAdapter {
        OrderInfoListener listener;
        List<UserOrdersModel> userOrdersList;

        public OrdersListAdapter(OrderInfoListener listener ,List<UserOrdersModel>  userOrdersList) {
            this.listener = listener;
            this.userOrdersList = userOrdersList;
        }

        @Override
        public int getCount() {
            return userOrdersList.size();
        }

        @Override
        public UserOrdersModel getItem(int position) {
            return userOrdersList.get(position) ;
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

            final UserOrdersModel order = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_view, parent, false);
            }
            mTV_OrderNum=(TextView)convertView.findViewById(R.id.tv_orderNum);
            mTV_OrderName=(TextView)convertView.findViewById(R.id.tv_orderName);
            mTV_OrederDate=(TextView)convertView.findViewById(R.id.tv_deliveryTime);
            mBT_Pick=(Button)convertView.findViewById(R.id.bt_pick);

            mBT_Pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionClicked=position;
                    listener.onPickClick(position);

                }
            });

            mTV_OrderNum.setText("Order Number : "+order.getmOrderId());
            mTV_OrderName.setText(order.getCustomerName() +" , "+ order.getCustomerPhoneNumber());
            mTV_OrederDate.setText(order.getOrderAddress()+" , "+ order.getOrderCity()+" , "+order.getOrderState()+" . ");

            mTV_OrderNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });
            return convertView;
        }



    }

    @Override
    public void onPickClick(int position) {

        final UserOrdersModel order = userOrdersList.get(position);
        if(order.getOrderLongitude().isEmpty() || order.getOrderLatitude().isEmpty()){

            AlertDialog.Builder dialog  = new AlertDialog.Builder(getActivity());
            dialog.setTitle("Deliver Here");
            dialog.setMessage("Address :"+ order.getOrderAddress()+" , "+ order.getOrderCity()+" , "+order.getOrderState()+" . ");
            dialog.setPositiveButton("Delivered", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBpreviousOrders db = new DBpreviousOrders(getActivity());
                    List<OrderBean> deliveryDetailsList = new ArrayList<>();
                    OrderBean orderBean=new OrderBean();
                    orderBean.setOrderNumber(order.getmOrderId());
                    orderBean.setOrderName(order.getCustomerName());
                    orderBean.setOrderDate(order.getCustomerPhoneNumber());
                    deliveryDetailsList.add(orderBean);
                    db.insertData(deliveryDetailsList);
                    updateDeliveryDetails(deliveryDetailsList);
                    Toast.makeText(getActivity(), "Order has been delivered succesfully.", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();


        }
        else {

            trackLocation(order.getOrderLatitude()+","+order.getOrderLongitude());
        }
    }

    private void updateDeliveryDetails(List<OrderBean> list) {
        UpdateOrderStatus bean = new UpdateOrderStatus(list.get(0).getOrderNumber(),
                Utils.ORDER_PICKED);
        Call<Object> updatedeliveredProducts = apiService.updateOrderStatus(bean);
        updatedeliveredProducts.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(((LinkedTreeMap) response.body()).get("status").equals("failed")){
                    Toast.makeText(getActivity(), "Order hasn't deliverd .Please re-check to deliver", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Order has been delivered successfully", Toast.LENGTH_SHORT).show();
                    getOrderDetails();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }
}
