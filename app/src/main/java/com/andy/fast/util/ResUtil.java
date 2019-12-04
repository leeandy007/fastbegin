package com.andy.fast.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResUtil {

    public static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }

    public static Drawable getDrawable(Context context, int resId){
        return context.getResources().getDrawable(resId);
    }

    public static int getColor(Context context, int resId){
        return context.getResources().getColor(resId);
    }

    public static SpannableString transferColor(Context context, int color, String text, String keyword){
        SpannableString spannableString = new SpannableString(text);
        //关键字匹配
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(new SpannableString(text.toLowerCase()));
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new ForegroundColorSpan(getColor(context, color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static String transferColor(Context context, int color, String text){
        return "<font color=\"#"+Color2String(context, color)+"\">" + text + "</font>";
    }

    private static String Color2String(Context context, int color) {
        String hexString = Integer.toHexString(ResUtil.getColor(context, color)).replaceFirst("ff", "");
        return hexString;
    }

}
