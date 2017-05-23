package com.example.nanni.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nanni.myapplication.adapters.SideMenuAdapter;

public class NavigationDrawerActivity extends AppCompatActivity {

    ListView mSideMenuList;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarToggle;
    SideMenuAdapter sideMenuRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        mSideMenuList=(ListView)findViewById(R.id.left_drawer);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar();
        setAdapter();
        selectItem(0);
    }
    public void setAdapter(){

        SideMenuAdapter sideMenuAdapter=new SideMenuAdapter();
        mSideMenuList.setAdapter(sideMenuAdapter);
    }
    void setToolbar() {
        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        sideMenuRecyclerAdapter = new SideMenuAdapter();

        mSideMenuList.setAdapter(sideMenuRecyclerAdapter);
        mSideMenuList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(mActionBarToggle);
        setupDrawerToggle();
    }

    void setupToolbar() {
        //  mToolbarTitle.setText("Locations");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mActionBarToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mActionBarToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //view.setBackgroundColor(parent.getResources().getColor(R.color.colorPrimary));
          /*  for (int i = 0; i < mMenuList.getChildCount(); i++) {
                if (position == i) {
                    mMenuList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                } else {
                    mMenuList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
                }
*/
          mDrawerLayout.closeDrawers();
            selectItem(position);

        }
    }

    private void selectItem(int position) {
        Intent i=null;
        switch (position){
            case 0:
               i =new Intent(NavigationDrawerActivity.this,HomeActivity.class);
                break;
        }
        startActivity(i);
    }


}
