package br.com.bemypet.bemypet.controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Cassio on 13/08/16.
 */
public class ManagerPreferences {

    static String namePref = "br.com.bemypet.bemypet";

    public static void saveBoolean(Context ctx, String key, boolean value){

        SharedPreferences preferences = ctx.getSharedPreferences(namePref, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.apply();

    }

    public static void saveString(Context ctx, String key, String value){
        SharedPreferences preferences = ctx.getSharedPreferences(namePref, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void saveInt(Context ctx, String key, Integer value){
        SharedPreferences preferences = ctx.getSharedPreferences(namePref, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context ctx, String key){
        SharedPreferences preferences = ctx.getSharedPreferences(namePref, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static String getString(Context ctx, String key){
        SharedPreferences preferences = ctx.getSharedPreferences(namePref, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static Integer getInt(Context ctx, String key){
        SharedPreferences preferences = ctx.getSharedPreferences(namePref, Context.MODE_PRIVATE);
        return  preferences.getInt(key, 0);
    }


}
