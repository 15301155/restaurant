package com.lava.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * TXT工具
 * @author Lei.Hu
 *
 */
public class TXTUtil{
	
	private static final Logger logger = Logger
			.getLogger(TXTUtil.class);
	/**
	 * 导出路径
	 */
	private String path;
	
	/**
	 * 查询参数
	 */
	private String queryParam;
	
	public static final String TXT = ".txt";
	
	/**
	 * 
	 * @param getQueryParam 查询条件
	 * @param path  路径
	 */
	public TXTUtil(String queryParam,String path){
		this.queryParam=queryParam;
		this.path=path;
	}
	
	/**
	 * 写txt文件
	 * @param list
	 * @param request
	 * @return
	 */
	public String writeTxt(List<String> list, HttpServletRequest request){
		// 获得开始时间
		long start = System.currentTimeMillis();
		
		try {
			File desk = new File(path);
			if (!desk.exists()) {
				desk.mkdirs();
			}
			// 导出名字：查询参数+时间戳
			String fileName = getQueryParam() + start + TXT;
			// 输出的txt的路径
			logger.info(fileName);
			
			String filePath = path + fileName;
			File file=new File(filePath);
			
			String lineTxt = null;
			StringBuilder sb = new StringBuilder();// 创建字符串构建器
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
			logger.info("----完成该操作共用的时间是:" + (end - start) + " 毫秒");
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
