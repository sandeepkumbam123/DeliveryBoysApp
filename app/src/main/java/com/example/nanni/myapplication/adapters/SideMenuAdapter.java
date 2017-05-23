package com.example.nanni.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nanni.myapplication.R;

/**
 * Created by nanni...!!! on 5/20/2017.
 */

public class SideMenuAdapter extends BaseAdapter {
    String[] sideMenuItems={"Home","Previous Orders","My Account","Logout","V 1.0.0"};
    @Override
    public int getCount() {
        return sideMenuItems.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView mSideMenuText;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_side_menu_items,parent,false);
            mSideMenuText=(TextView)convertView.findViewById(R.id.sideMenuText);
            mSideMenuText.setText(sideMenuItems[position]);
        }
        return convertView;
    }
}
