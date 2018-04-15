package com.lava.common.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title: ��ȡ����·�������乤����
 * </p>
 * <p>
 * Description: ��ȡ����·�������乤����
 * </p>
 */
public final class PathReflectTool {

	private static final Logger logger = Logger
			.getLogger(PathReflectTool.class);

	private PathReflectTool() {

	}

	/**
	 * ��ȡ����·��
	 * 
	 * @return String ���ع���·��
	 */
	public static String getRootPath() {
		String classPath = null;
		try {
			classPath = PathReflectTool.class.getResource("/").toURI()
					.getPath();
		} catch (URISyntaxException e) {
			classPath = "";
			logger.error("get rootPath exception", e);
		}
		String rootPath = "";

		// windows��
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("/WEB-INF/classes"));
		}

		// linux��
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}

		logger.debug("------------->" + rootPath);
		return rootPath;
	}

	/**
	 * ѭ������ת��, ��ȡ����� DeclaredField
	 * 
	 * @param object
	 *            : �������
	 * @param fieldName
	 *            : �����е�������
	 * @return �����е����Զ���
	 */

	public static Field getDeclaredField(Object object, String fieldName) {
		Field field = null;

		Class<?> clazz = object.getClass();

		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
			}
		}

		return null;
	}

	/**
	 * ��ȡʵ�����ֵ
	 * 
	 * @param obj
	 * @param paramName
	 *            ��������
	 * @return
	 */
	public static String getParamValue(Object obj, String paramName) {
		String result = "";
		try {
			Class<?> clazz = obj.getClass();
			PropertyDescriptor pd = new PropertyDescriptor(paramName, clazz);
			Method getMethod = pd.getReadMethod();// ���get����

			if (pd != null) {
				Object o = getMethod.invoke(obj);// ִ��get��������һ��Object
				result = getConvertValue(getDeclaredField(obj, paramName), o);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��������ʵ�岻ͬ�����ֶε�ֵ
	 * 
	 * @param field
	 * @param obj
	 * @param value
	 * @throws Exception
	 */
	public static void convertValue(Field field, Object obj, Object value) {
		field.setAccessible(true);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (null == value) {
				field.set(obj, value);
			} else if (field.getGenericType().toString()
					.equals("class java.lang.Integer")) {
				field.set(obj, Integer.parseInt(value.toString()));
			} else if (field.getGenericType().toString()
					.equals("class java.lang.Long")) {
				field.set(obj, Long.parseLong(value.toString()));
			} else if (field.getGenericType().toString().equals("boolean")) {
				field.set(obj, Boolean.parseBoolean(value.toString()));
			} else if (field.getGenericType().toString()
					.equals("class java.util.Date")) {
				field.set(obj, sim.parse(value.toString()));
			} else {
				field.set(obj, value);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * ��ȡ��Ӧ���͵�ת��ֵ
	 * 
	 * @param field
	 * @param obj
	 * @return
	 */
	public static String getConvertValue(Field field, Object obj) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String value = "";
		if (field.getGenericType().toString().equals("class java.util.Date")) {
			Date date = (Date) obj;
			if (date != null) {
				value = sim.format(date);
			}
		} else {
			value = obj==null? "" : obj.toString();
		}
		return value;
	}
}
