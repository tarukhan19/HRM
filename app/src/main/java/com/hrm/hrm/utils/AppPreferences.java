package com.hrm.hrm.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by Ishan Puranik on 08/05/2017.
 */
public class AppPreferences {
    public static final String PREFS_NAME = "MyPrefs";

    public static String savePreferences(Context context, String key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
        return key;
    }

    public static String loadPreferences(Context context, String key) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        String value = settings.getString(key, "");
        return value;

    }



/*

    public static String logout(Context context, String key, String value){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor=settings.edit();
        editor.putString(key,value);
        editor.clear();
        editor.commit();
        return key;
    }
*/


}
