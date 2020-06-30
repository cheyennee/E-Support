package com.example.e_support.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.CopyOnWriteArrayList;

public class PrefUtils {
    private static final String SHARE_PREFS_NAME = "itcast";
    public static void putString (Context context,String key,String value){
        SharedPreferences spf = context.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        spf.edit().putString(key,value).commit();
    }
    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences spf =context.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        spf.edit().putBoolean(key, value).commit();
    }
    public static void putInt(Context context,String key,int value){
        SharedPreferences spf = context.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        spf.edit().putInt(key, value).commit();
    }
    public static String getString (Context context,String key,String defaultValue){
        SharedPreferences spf = context.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        return spf.getString(key,defaultValue);
    }
    public static boolean getBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences spf = context.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        return spf.getBoolean(key,defaultValue);
    }
    public static int getInt(Context context,String key,int defaultValue){
        SharedPreferences spf = context.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        return spf.getInt(key,defaultValue);
    }
}
