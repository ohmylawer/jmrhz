package com.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CommonUtil {

	public static HttpServletRequest getRequest() {

		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static HttpServletResponse getResponse() {

		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	public static boolean isEmpty(final String value) {

		return StringUtils.isEmpty(value);
	}

	public static boolean isEmpty(final Object value) {

		return (null == value);
	}

	public static boolean isCollectionEmpty(final Collection collection) {

		if (!CommonUtil.isEmpty(collection)) {

			return collection.isEmpty();
		}

		return true;
	}

	public static boolean isEmpty(final Object[] value) {

		return (null == value) || (value.length == 0);
	}

	public static String dateToStr(final Date date) {

		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String dateToStr(final Date date, final String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: genarateID 生成ID
	 * @Description: TODO(基于Twitter的Snowflake算法，生成符合时间趋势的ID.)
	 * @return long 返回长整型ID
	 */
	public static long genarateID() {

		return idWorker.nextId();
	}

	public static long[] stringArray2Long(final String[] strArray) throws Exception {

		try {

			if (CommonUtil.isEmpty(strArray) || (strArray.length == 0)) {

				return null;
			}

			List<Long> longList = new ArrayList<Long>();

			for (int i = 0; i < strArray.length; i++) {

				if (CommonUtil.isEmpty(strArray[i])) {

					continue;
				}

				BigInteger bi = new BigInteger(strArray[i].trim());

				longList.add(bi.longValue());
			}

			if (CommonUtil.isCollectionEmpty(longList)) {

				return null;
			}

			int iCount = 0;

			long[] longArray = new long[longList.size()];

			for (Long longValue : longList) {

				longArray[iCount++] = longValue;
			}

			return longArray;

		} catch (Exception e) {

			throw new Exception("将字符串数组转换为长整型数据出错！");
		}

	}

	public static List<Long> long2List(final long[] data) {

		if (CommonUtil.isEmpty(data) || (data.length == 0)) {

			return null;
		}

		List<Long> lists = new ArrayList<Long>();

		for (int i = 0; i < data.length; i++) {

			lists.add(data[i]);
		}

		return lists;
	}

	public static String MD5(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			return toHex(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String MD5(final String s, final boolean encryptOK) {
		if (encryptOK) {

			return MD5(s);
		}

		return s;
	}

	private static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}

	public static void sendJsonData(final HttpServletResponse response, final String data) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}

	public static void sendJsonData(final String data) throws Exception {

		sendJsonData(CommonUtil.getResponse(), data);
	}

	public static void success(final HttpServletResponse response, final String data) throws Exception {

		success(response, data, "操作完成！");
	}

	public static void success(final String data) throws Exception {

		success(CommonUtil.getResponse(), data);
	}

	public static void success(final HttpServletResponse response, final String data, final String successMsg)
			throws Exception {
		JSONObject errorInfo = new JSONObject();
		errorInfo.put("status", true);
		errorInfo.put("code", 200);
		errorInfo.put("message", successMsg);
		errorInfo.put("data", data);
		sendJsonData(response, errorInfo.toJSONString());
	}

	public static void error(HttpServletResponse response, String errorMsg) throws Exception {

		response.sendError(404, errorMsg);

		sendJsonData(response, errorMsg);
	}

	/**
	 * 获取当前jar包所在系统中的目录
	 */
	private static File getRuntimePath() {

		ApplicationHome home = new ApplicationHome(CommonUtil.class);

		return home.getSource().getParentFile();

	}

	public static File getReportPath() {

		String reportPath = getRuntimePath() + File.separator + "report";

		FileUtil.createDic(reportPath);

		return new File(reportPath);
	}

	public static File getDownloadPath() {

		String downloadPath = getRuntimePath() + File.separator + "download";

		FileUtil.createDic(downloadPath);

		return new File(downloadPath);
	}

	public static String getDBUrl() {

		FileUtil.createDic(getRuntimePath() + File.separator + "db");

		return getRuntimePath() + File.separator + "db";
	}

	public static File getUploadPath() {

		String uploadPath = getRuntimePath() + File.separator + "upload";

		File uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {

			uploadFile.mkdir();
		}

		return uploadFile;
	}

	public static String getCodeByLevel(int level, int num) {

		String strNum = String.valueOf(num);

		StringBuffer codeBuf = new StringBuffer();

		for (int i = 0; i < (level - strNum.length()); i++) {

			codeBuf.append("0");
		}

		codeBuf.append(strNum);
		return codeBuf.toString();
	}

	private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
}
