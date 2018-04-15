package com.lava.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * 下载工具类 （向客户端写回文件流支持断点续传）
 * 
 * @author Binjie.Qian
 * 
 */
public class DownLoadUtil {

	private static Logger log = Logger.getLogger(DownLoadUtil.class);

	/**
	 * 下载
	 * 
	 * @param file 文件
	 * @param fileName 文件名
	 * @param request
	 * @param response
	 */
	public static void download(File file, String fileName,
			HttpServletRequest request, HttpServletResponse response) {

		ServletOutputStream out = null;
		BufferedOutputStream bufferOut = null;
		InputStream inputStream = null;
		try {
			if (file == null || !file.exists()) {
				log.error("dwonload file is not exise downPath="
						+ file.getAbsolutePath());
				return;
			}
			
			log.debug("下载文件路径：" + file.getAbsolutePath());

			long fSize = file.length();

			if (fileName.endsWith(".txt")) {
				response.setContentType("text/plain");
			} else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
				response.setContentType("application/vnd.ms-excel");
			} else {
				response.setContentType("application/ostet-stream");
			}

			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Length", String.valueOf(fSize));
			response.setHeader("Content-Disposition", "attachment; filename="
					+ encodingFileName(request, fileName));
			long pos = 0;
			if (null != request.getHeader("Range")) {
				// 断点续传
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				try {
					pos = Long.parseLong(request.getHeader("Range")
							.replaceAll("bytes=", "").replaceAll("-", ""));
				} catch (NumberFormatException e) {
					log.error(request.getHeader("Range") + " is not Number!");
					pos = 0;
				}
			}
			out = response.getOutputStream();
			bufferOut = new BufferedOutputStream(out);
			inputStream = new FileInputStream(file);
			String contentRange = new StringBuffer("bytes ")
					.append(new Long(pos).toString()).append("-")
					.append(new Long(fSize - 1).toString()).append("/")
					.append(new Long(fSize).toString()).toString();
			response.setHeader("Content-Range", contentRange);
			log.debug("Content-Range " + contentRange);
			inputStream.skip(pos);
			byte[] buffer = new byte[5 * 1024];
			int length = 0;
			while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
				bufferOut.write(buffer, 0, length);
			}
		} catch (Exception e) {
			log.error("downloadRebootLog down exception", e);
		} finally {
			try {
				if (bufferOut != null) {
					bufferOut.flush();
					bufferOut.close();
				}
			} catch (Exception e) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
			try {
				if (inputStream != null) {
					out.close();
				}

			} catch (Exception e) {
			}
		}
	}

	/**
	 * 文件名转码
	 * 
	 * @param fileName
	 * @return
	 */
	private static String encodingFileName(HttpServletRequest request,
			String fileName) {
		String returnFileName = "";
		try {
			String agent = (String) request.getHeader("USER-AGENT");
			if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
				returnFileName = new String(fileName.getBytes("GB2312"),
						"ISO8859-1");
			} else {

				returnFileName = URLEncoder.encode(fileName, "UTF-8")
						.replaceAll("\\+", "%20").replaceAll("%28", "\\(")
						.replaceAll("%29", "\\)").replaceAll("%3B", ";")
						.replaceAll("%40", "@").replaceAll("%23", "\\#")
						.replaceAll("%26", "\\&");
				if (returnFileName.length() > 150) {
					returnFileName = new String(fileName.getBytes("GB2312"),
							"ISO8859-1");
					returnFileName = StringUtils.replace(returnFileName, " ",
							"%20");
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Don't support this encoding ...", e);
		}
		return returnFileName;
	}
}
