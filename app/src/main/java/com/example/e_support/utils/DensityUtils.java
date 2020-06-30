package com.example.e_support.utils;

import android.content.Context;

public class DensityUtils {
    public static int dipToPx(float dp, Context ctx){
        float density = ctx.getResources().getDisplayMetrics().density;
        int px = (int)(dp * density + 0.5f);
        return px;
    }
    public static float pxToDip(int px,Context ctx){
        float density = ctx.getResources().getDisplayMetrics().density;
        float dp = px / density ;
        return dp;
    }
}
