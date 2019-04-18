package com.andy.fast.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * SharedPreferences 工具类
 */
public class SPUtil {

    //  ----------------------------------------------String 操作------------------------------------------------------------

    public static String getString(Context context, String spname, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spname, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(Context context, String spName, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    //  ----------------------------------------------int 操作------------------------------------------------------------
    public static int getInt(Context context, String spName, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void setInt(Context context, String spName, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    //  ----------------------------------------------boolean 操作------------------------------------------------------------

    public static boolean getBoolean(Context context, String spName, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String spName, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    //  ----------------------------------------------long 操作------------------------------------------------------------
    public static long getLong(Context context, String spName, String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static void setLong(Context context, String spName, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }


    // ---------------------------------------------- Set 操作-----------------------------------------------------------
    public static Set<String> getSet(Context context, String spName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        Set<String> set = sp.getStringSet(key, null);
        if (set == null) {
            setSet(context, spName, key, new HashSet<String>());
        }
        return sp.getStringSet(key, null);
    }

    public static void addSetItem(Context context, String spName, String key, String item) {
        Set<String> set = getSet(context, spName, key);
        set.add(item);
        setSet(context, spName, key, set);
    }

    public static void setSet(Context context, String spName, String key, Set<String> set) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(key, set);
        edit.commit();
    }

    public static boolean hasThisItem(Context context, String spName, String key, String item) {
        Set<String> set = getSet(context, spName, key);
        return set.contains(item);
    }

    // ---------------------------------------------- 删除该SP内的所有数据--------------------------------------------------
    public static void ClearAll(Context context, String spName) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }

    public static void remove(Context context, String spName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }
    
}