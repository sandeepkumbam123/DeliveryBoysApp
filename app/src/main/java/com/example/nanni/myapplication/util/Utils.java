package com.example.nanni.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nanni...!!! on 5/22/2017.
 */

public class Utils {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_ISCHECKED = "IsChecked";

    public static void saveUserNamePassword(Context context,String userName,String password)
    {

       context. getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, userName)
                .putString(PREF_PASSWORD, password)
                .commit();

    }

    public static void saveCheckButtonStatus(Context context,boolean isChecked){
        context. getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putBoolean(PREF_ISCHECKED, isChecked)
                .commit();

    }
    public static boolean getCheckButtonStatus(Context context){

        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        return pref.getBoolean(PREF_ISCHECKED,false);
    }
    public static String getUserNamePasssword(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        return pref.getString(PREF_USERNAME,"");
    }

    public static String getPrefPassword(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        return pref.getString(PREF_PASSWORD,"");
    }


}
