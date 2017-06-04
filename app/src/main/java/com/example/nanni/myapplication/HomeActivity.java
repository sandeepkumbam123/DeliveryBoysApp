package com.example.nanni.myapplication;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements LocationListener {
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
    String sourceAdress = "17.445727,78.381755";
    String destinationAdress1 = "17.447070,78.374155";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String[] orderNum = {"1", "2", "3", "4", "5", "6"};
    String[] orderName = {"Chicken", "Bone Less Chicken", "Freash Meat", "Chicken", "Eggs", "Special chicken"};
    String[] orderDate = {"02/06/2017", "03/06/2017", "04/06/2017", "05/06/2017", "06/06/2107", "07/06/2017"};
    int positionClicked=0;
    String[] destinationAdress={"17.485232,78.394104","17.447070,78.374155","17.4868949, 78.3907834","17.485232," +
            "78.394104","17.447070,78.374155","17.4868949, 78.3907834"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mOrdersList = (ListView) findViewById(R.id.lv_OrdersList);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setAdapter();

        getMyLocationPermission();
    }

    private void trackLocation(String sourceAdress) {

        mapsActivity = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + sourceAdress + "&daddr="
                        + destinationAdress[positionClicked]));
        startActivityForResult(mapsActivity, 507);

    }

    public void setAdapter() {
        OrdersListAdapter ordersListAdapter = new OrdersListAdapter();
        mOrdersList.setAdapter(ordersListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 507:
                Intent i=new Intent(getApplicationContext(),DeliveredButton.class);
                Bundle bundle=new Bundle();
                bundle.putString("OrderNum",orderNum[positionClicked]);
                bundle.putString("OrderName",orderName[positionClicked]);
                bundle.putString("OrderDate",orderDate[positionClicked]);
                startActivity(i,bundle);

        }
    }

    private void getMyLocationPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
               // getLocation();
            }
        }

    }

    private void getLocation() {
        isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            showSettingsAlert();
        } else {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionGranted = false;
                ActivityCompat.requestPermissions(this,
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
                            Toast.makeText(getApplicationContext(), "Lat" + latitude + "  " + "Lon" + longitude, Toast.LENGTH_LONG).show();
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
//sendlattitude longitude
                                Toast.makeText(getApplicationContext(), "Lat" + latitude + "  " + "Lon" + longitude, Toast.LENGTH_LONG).show();
                                trackLocation(latitude + "," + longitude);
                            }


                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                    // permission was granted, yay! Do the

                } else {

                    Toast.makeText(getApplicationContext(), "Please enable location permissions to App", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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

        if (location.getLatitude() == 0 && location.getLongitude() == 0) {
            finishActivity(507);
        }

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



        @Override
        public int getCount() {
            return 6;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView mTV_OrderNum;
            TextView mTV_OrderName;
            TextView mTV_OrederDate;
            Button mBT_Pick;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_view, parent, false);
            }

            mTV_OrderNum = (TextView) findViewById(R.id.tv_orderNum);
            mTV_OrderName = (TextView) findViewById(R.id.tv_orderName);
            mTV_OrederDate = (TextView) findViewById(R.id.tv_deliveryTime);
            mTV_OrderNum.setText(orderNum[position]);
            mTV_OrderName.setText(orderName[position]);
            mTV_OrederDate.setText(orderDate[position]);
            mBT_Pick = (Button) findViewById(R.id.bt_pick);
            mBT_Pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionClicked=position;
                    getLocation();
                }
            });
            return convertView;
        }
    }

}
