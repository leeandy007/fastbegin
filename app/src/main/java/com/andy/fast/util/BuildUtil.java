package com.andy.fast.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/** 系统硬件相关工具类 */
public class BuildUtil {

	/** 主板信息 */
	public static String getBoard(){
		return Build.BOARD;
	}
	
	/** 主系统启动程序版本号 */
	public static String getBootloader(){
		return Build.BOOTLOADER;
	}
	
	/** 系统定制商 */
	public static String getBrand(){
		return Build.BRAND;
	}
	
	/** cpu指令集 */
	public static String getCpu_abi(){
		return Build.CPU_ABI;
	}
	
	/** cpu指令集2 */
	public static String getCpu_abi2(){
		return Build.CPU_ABI2;
	}
	
	/** 设备参数 */
	public static String getDevice(){
		return Build.DEVICE;
	}
	
	/** 显示屏参数 */
	public static String getDisplay(){
		return Build.DISPLAY;
	}
	
	/** 无线电固件版本（基带版本） */
	public static String getRadio(){
		return Build.getRadioVersion();
	}
	
	/** 硬件识别码 */
	public static String getFingerprint(){
		return Build.FINGERPRINT;
	}
	
	/** 硬件名称 */
	public static String getHardware(){
		return Build.HARDWARE;
	}
	
	/** HOST */
	public static String getHost(){
		return Build.HOST;
	}
	
	/** ID修订版本列表 */
	public static String getId(){
		return Build.ID;
	}
	
	/** 硬件制造商 */
	public static String getManufacturer(){
		return Build.MANUFACTURER;
	}
	
	/** 手机型号 */
	public static String getPhoneModel(){
		return Build.MODEL;
	}
	
	/** 硬件序列号 */
	public static String getSerial(){
		return Build.SERIAL;
	}
	
	/** 手机制造商 */
	public static String getProduct(){
		return Build.PRODUCT;
	}
	
	/** 描述Build的标签 */
	public static String getTags(){
		return Build.TAGS;
	}
	
	/** TIME */
	public static long getTime(){
		return Build.TIME;
	}
	
	/** builder类型 */
	public static String getType(){
		return Build.TYPE;
	}
	
	/** USER */
	public static String getUser(){
		return Build.USER;
	}
	
	/** 当前开发代号 */
	public static String getVersion_codename(){
		return Build.VERSION.CODENAME;
	}
	
	/** 源码控制版本号 */
	public static String getVersion_incremental(){
		return Build.VERSION.INCREMENTAL;
	}
	
	/** Android版本 */
	public static String getAndroidVersionName(){
		return Build.VERSION.RELEASE;
	}
	
	/** Android版本号 */
	public static int getAndroidVersionCode(){
		return Build.VERSION.SDK_INT;
	}
	
	/** 
	 * APP版本名
	 * @param context
	 */
	public static String getAppVersionName(Context context){
		String versionName = "";
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionName = info.versionName;
		} catch (Exception e) {
		}
		return versionName;
	}
	
	/** 
	 * APP版本号
	 * @param context
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	/**
	 * 获取屏幕分辨率
	 * @param context
	 */
	public static String getScreenSize(Context context){
		String mScreenSize = "";
		try {
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics mDisplayMetrics = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
			int W = mDisplayMetrics.widthPixels;
			int H = mDisplayMetrics.heightPixels;
			mScreenSize = W + " * " + H;
		} catch (Exception e) {
			
		}
		return mScreenSize;
	}
	
	/**
	 * 获取手机的MAC地址
	 * @param context
	 */
	public static String getMacAddress(Context context){
		String mMac = "";
        try {
        	WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			mMac = manager.getConnectionInfo().getMacAddress();
		} catch (Exception e) {
			
		}
		return mMac;
    }
	
	/** 
	 * 获取IMSI 
	 * @param context
	 */
	public static String getIMSI(Context context){
		String mIMSI = "";
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			mIMSI = tm.getSubscriberId();
		} catch (Exception e) {
			
		}
		return mIMSI;
	}
	
	/** 
	 * 获取IMEI
	 * @param context
	 */
	public static String getIMEI(Context context){
		String mIMEI = "";
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			mIMEI = tm.getDeviceId();
		} catch (Exception e) {
			
		}
		return mIMEI;
	}
	
	/**
	 * 获取SIM卡的序列号
	 * @param context
	 */
	public static String getSimSerialNumber(Context context){
		String mSimSerialNumber = "";
        try {
        	TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        	mSimSerialNumber = tm.getSimSerialNumber(); 
		} catch (Exception e) {
			
		}
		return mSimSerialNumber;
    }
	
	/**
	 * 获取手机号码
	 * @param context
	 */
	public static String getPhoneNumber(Context context){
		String mPhoneNumber = "";
        try {
        	TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        	mPhoneNumber = tm.getLine1Number(); 
		} catch (Exception e) {
			
		}
		return mPhoneNumber;
    }
	
	
	
}
