package com.example.nanni.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.nanni.myapplication.adapters.SideMenuAdapter;

public class NavigationDrawerActivity extends AppCompatActivity {

    ListView mSideMenuList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        mSideMenuList=(ListView)findViewById(R.id.left_drawer);

        setAdapter();
    }
    public void setAdapter(){

        SideMenuAdapter sideMenuAdapter=new SideMenuAdapter();
        mSideMenuList.setAdapter(sideMenuAdapter);
    }
}
