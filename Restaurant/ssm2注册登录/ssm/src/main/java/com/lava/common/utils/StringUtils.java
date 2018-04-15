package com.lava.common.utils;

/**
 * 字符串常用操作
 * @author Shengle.Liao
 *
 */
public class StringUtils{
	
	/**
	 * 私有无参构造
	 */
    private StringUtils() {
    }

	/**
     * 判断字符串是否为null或者空字符串
     * 
     * @param 		str 被测试的字符串
     * @return 		boolean 如果为null或者空字符串，返回true，否则返回false
	 */
	public static boolean isEmpty(Object str) {
		return org.springframework.util.StringUtils.isEmpty(str);
	}

	/**
	 * 判断字符串是否是有效数字
	 *
	 * @param 		str 被测试的字符串
	 * @return		boolean 如果是有效数字，返回true，否则返回false
	 *
	 */
    public static boolean isNumeric(String str) {
    	 if (isEmpty(str)) {
             return false;
         }
         final int size = str.length();
         for (int i = 0; i < size; i++) {
             if (Character.isDigit(str.charAt(i)) == false) {
                 return false;
             }
         }
         return true;
    }

}
