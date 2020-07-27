package com.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class ExcelUtil {
	/**
	 * sheetName sheet名称 title 标题 values 内容 wb hssfworkbook 对象
	 */

	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {
		// 1.创建hssfworkBook 对应的一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}
		// 2.在workbook中添加一个sheet对应excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 3.在sheet中添加表头第0行
		HSSFRow row = sheet.createRow(0);
		// 4.创建单元格，并设置表头，设置表头剧中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);

		// 声明列对象
		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				// 将内容按顺序赋给对应的列对象
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		return wb;
	}

	public static void setResponseHeader(HttpServletResponse response, String fileName) throws Exception {
		try {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw e;
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
