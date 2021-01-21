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

	public static ArrayList<String> getStringList(String value){
		return getSListFromString(",", value);
	}

	public static ArrayList<Integer> getIntegerList(String value){
		return getIListFromString(",", value);
	}

	public static String strListToString(Collection<String> collection){
		return getStringFromSList(",", collection);
	}

	public static String intListToString(Collection<Integer> collection){
		return getStringFromIList(",", collection);
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
		String regExp = "^[1][0-9]{10}$";
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

	public static String getBasUrl(String url) {
		String head = "";
		int index = url.indexOf("://");
		if (index != -1) {
			head = url.substring(0, index + 3);
			url = url.substring(index + 3);
		}
		index = url.indexOf("/");
		if (index != -1) {
			url = url.substring(0, index + 1);
		}
		return head + url;
	}

	public static boolean getBoolean(Integer integer){
		return (integer != null && integer == 1) ? true : false;
	}

	public static Integer getInteger(boolean status){
		return status ? 1 : 0;
	}

	/**
	 * 验证身份证号
	 * */
	public static boolean isIDCard(String IDNumber) {
		if (IDNumber == null || "".equals(IDNumber)) {
			return false;
		}
		// 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
		String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
				"(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		//假设18位身份证号码:41000119910101123X  410001 19910101 123X
		//^开头
		//[1-9] 第一位1-9中的一个      4
		//\\d{5} 五位数字           10001（前六位省市县地区）
		//(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
		//\\d{2}                    91（年份）
		//((0[1-9])|(10|11|12))     01（月份）
		//(([0-2][1-9])|10|20|30|31)01（日期）
		//\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
		//[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
		//$结尾

		//假设15位身份证号码:410001910101123  410001 910101 123
		//^开头
		//[1-9] 第一位1-9中的一个      4
		//\\d{5} 五位数字           10001（前六位省市县地区）
		//\\d{2}                    91（年份）
		//((0[1-9])|(10|11|12))     01（月份）
		//(([0-2][1-9])|10|20|30|31)01（日期）
		//\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
		//$结尾


		boolean matches = IDNumber.matches(regularExpression);

		//判断第18位校验值
		if (matches) {

			if (IDNumber.length() == 18) {
				try {
					char[] charArray = IDNumber.toCharArray();
					//前十七位加权因子
					int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
					//这是除以11后，可能产生的11位余数对应的验证码
					String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
					int sum = 0;
					for (int i = 0; i < idCardWi.length; i++) {
						int current = Integer.parseInt(String.valueOf(charArray[i]));
						int count = current * idCardWi[i];
						sum += count;
					}
					char idCardLast = charArray[17];
					int idCardMod = sum % 11;
					if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
						return true;
					} else {
						return false;
					}

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

		}
		return matches;
	}

	public static void main(String[] args) {
		boolean mobileNO = isMobileNO("19612345678");
		System.out.println(mobileNO);
	}

}
