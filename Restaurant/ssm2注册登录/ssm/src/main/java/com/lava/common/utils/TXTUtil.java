package com.lava.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * TXT����
 * @author Lei.Hu
 *
 */
public class TXTUtil{
	
	private static final Logger logger = Logger
			.getLogger(TXTUtil.class);
	/**
	 * ����·��
	 */
	private String path;
	
	/**
	 * ��ѯ����
	 */
	private String queryParam;
	
	public static final String TXT = ".txt";
	
	/**
	 * 
	 * @param getQueryParam ��ѯ����
	 * @param path  ·��
	 */
	public TXTUtil(String queryParam,String path){
		this.queryParam=queryParam;
		this.path=path;
	}
	
	/**
	 * дtxt�ļ�
	 * @param list
	 * @param request
	 * @return
	 */
	public String writeTxt(List<String> list, HttpServletRequest request){
		// ��ÿ�ʼʱ��
		long start = System.currentTimeMillis();
		
		try {
			File desk = new File(path);
			if (!desk.exists()) {
				desk.mkdirs();
			}
			// �������֣���ѯ����+ʱ���
			String fileName = getQueryParam() + start + TXT;
			// �����txt��·��
			logger.info(fileName);
			
			String filePath = path + fileName;
			File file=new File(filePath);
			
			String lineTxt = null;
			StringBuilder sb = new StringBuilder();// �����ַ���������
			if(list!=null&&list.size()!=0){
				for (int i = 0; i < list.size(); i++) {
					lineTxt=list.get(i);
					sb.append(lineTxt);
					sb.append("\r\n");
				}
			}
			
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
			out.write(sb.toString());
			out.flush();
			out.close();   

			long end = System.currentTimeMillis();
			logger.info("----��ɸò������õ�ʱ����:" + (end - start) + " ����");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("export txt exception ", e);
		}
		
		return request.getContextPath() + "/report/download?id=" + start
				+ "&downPath=";
	}
	
	public String getQueryParam() {
		return queryParam == null || queryParam.equals("") ? "" : queryParam ;
	}
}
