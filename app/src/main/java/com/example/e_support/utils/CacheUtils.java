package com.example.e_support.utils;

import android.content.Context;

import com.example.e_support.fragment.ContentFragment;

public class CacheUtils {
    public static void setCache(Context context,String key,String json){
        PrefUtils.putString(context,key,json);
    }
    public static String getCache(Context context,String key){
        return PrefUtils.getString(context,key,null);
    }
}
