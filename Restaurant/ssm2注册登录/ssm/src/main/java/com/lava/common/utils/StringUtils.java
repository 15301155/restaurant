package com.lava.common.utils;

/**
 * �ַ������ò���
 * @author Shengle.Liao
 *
 */
public class StringUtils{
	
	/**
	 * ˽���޲ι���
	 */
    private StringUtils() {
    }

	/**
     * �ж��ַ����Ƿ�Ϊnull���߿��ַ���
     * 
     * @param 		str �����Ե��ַ���
     * @return 		boolean ���Ϊnull���߿��ַ���������true�����򷵻�false
	 */
	public static boolean isEmpty(Object str) {
		return org.springframework.util.StringUtils.isEmpty(str);
	}

	/**
	 * �ж��ַ����Ƿ�����Ч����
	 *
	 * @param 		str �����Ե��ַ���
	 * @return		boolean �������Ч���֣�����true�����򷵻�false
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
