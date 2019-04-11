package com.andy.fast.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断Object是否为null
	 *
	 * @param obj
	 * @return null true
	 */
	public static boolean isEmpty(Object obj) {
		if (null != obj) {
			return false;
		}
		return true;
	}

	/**
	 * 判断Integer是否为null
	 *
	 * @param i
	 * @return null true
	 */
	public static boolean isEmpty(Integer i) {
		if (null != i && i != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为null
	 *
	 * @param str
	 * @return null true
	 */
	public static boolean isEmpty(String str) {
		if (null != str && !str.trim().equals("") && !str.equals("null")) {
			return false;
		}
		return true;
	}

	/**
	 * 判断list是否为null
	 *
	 * @param list
	 * @return null true
	 */
	public static boolean isEmpty(List<?> list) {
		if (null != list && list.size() != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断数组是否为null
	 *
	 * @param objects
	 * @return null true
	 */
	public static boolean isEmpty(Object[] objects) {
		if (null != objects && objects.length != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断list是否为null
	 *
	 * @param map
	 * @return null true
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		if (null != map && !map.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 返回空值
	 * */
	public static String isEmptyToString(String str) {
		String s = (isEmpty(str) ? "" : str.trim());
		return s;
	}

	/**
	 * 返回空值
	 * */
	public static String isEmptyToString(Object obj) {
		String s = (isEmpty(obj) ? "" : String.valueOf(obj));
		return s;
	}

	public static String isEmptyToString(String str, String defaultValue) {
		String s = (isEmpty(str) ? defaultValue : str.trim());
		return s;
	}

	public static String isNoEmptyToString(String str, String defaultValue) {
		String s = (isEmpty(str) ? "" : defaultValue);
		return s;
	}

	public static String[] split(String str, String split){
		String[] string = null;
		if(!StringUtil.isEmpty(str)){
			string = str.split(split);
		}
		return string;
	}

	/**
	 * 拆分String,以xx号分隔，并封装成 ArrayList<String> 不使用超类List，以减少内存开销
	 * @param split 分隔符
	 * @param value 字符串
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getSListFromString(String split, String value) {
		ArrayList<String> list = new ArrayList<>();
		if (!isEmpty(value)) {
			String[] values = value.split(split);
			for (String string : values) {
				list.add(string);
			}
			return list;
		}
		return list;
	}

	/**
	 * 拆分String,以xx号分隔，并封装成 ArrayList<Integer> 不使用超类List，以减少内存开销
	 * @param split 分隔符
	 * @param value 字符串
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> getIListFromString(String split, String value) {
		ArrayList<Integer> list = new ArrayList<>();
		if (!isEmpty(value)) {
			String[] values = value.split(split);
			for (String s : values) {
				list.add(Integer.valueOf(s));
			}
			return list;
		}
		return list;
	}


	/**
	 * 拆分Collection<String>,以xx号分隔，并封装成制定分隔符的String
	 * @param split 分隔符
	 * @param collection 集合
	 * @return 字符串
	 */
	public static String getStringFromSList(String split, Collection<String> collection) {
		String result = "";
		if (collection != null && !collection.isEmpty()) {
			Iterator<String> it = collection.iterator();
			while (it.hasNext()) {
				String s = it.next();
				result = result + split + s;
			}
			result = result.substring(1);
		}
		return result;
	}

	/**
	 * 拆分Collection<Integer>,以xx号分隔，并封装成制定分隔符的String
	 * @param split 分隔符
	 * @param collection 集合
	 * @return 字符串
	 */
	public static String getStringFromIList(String split, Collection<Integer> collection) {
		String result = "";
		if (collection != null && !collection.isEmpty()) {
			Iterator<Integer> it = collection.iterator();
			while (it.hasNext()) {
				String s = String.valueOf(it.next());
				result = result + split + s;
			}
			result = result.substring(1);
		}
		return result;
	}

	/**
	 * 验证手机号码
	 * */
	public static boolean isMobileNO(String mobiles) {
		String regExp = "^[1]([3][0-9]{1}|[4][0-9]{1}|[5][0-9]{1}|[7][0-9]{1}|[8][0-9]{1})[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断特殊字符
	 * @param pInput
	 * @return
	 */
	public static boolean isSpecialChar(String pInput){
		if(pInput == null){
			return false;
		}
		String regEx = "[a-zA-Z0-9]+";
		return pInput.matches(regEx);
	}



	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0)
			return false;
		return emailer.matcher(email).matches();
	}

}
