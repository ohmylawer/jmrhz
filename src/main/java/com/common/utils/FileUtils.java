package com.common.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;

/**
 * 文件工具类
 *
 */
public class FileUtils {
	
	private static String contentType = "application/x-msdownload";

	private static String enc = "utf-8";

    /**
     * 保存文件到本地
     *
     * @param path
     * @param filename
     */
    public static void storeFile(InputStream is, String path, String filename) {
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(path + File.separator + filename));
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
                bos.flush();
            }
            bos.close();
            bis.close();
            fos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String textValue(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void downloadFile(File file,String filename, HttpServletRequest request, HttpServletResponse response) {
        if (file != null) {
            try {
                if (file.exists()) {
                	if(filename == null || "".equals(filename)) {
        				filename = file.getName();
        			}
                	String os = request.getHeader("User-Agent");
        			if(null != os && os.toLowerCase().indexOf("win") != -1) {
        				filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
        			} else {
        				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        			}
        			
        			response.reset();
        			response.setContentType(contentType);
        			response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
        			
        			int fileLength = (int) file.length();
        			response.setContentLength(fileLength);
        			
        			if(fileLength != 0) {
        				InputStream inputStream = new FileInputStream(file);
        				byte[] buf = new byte[4096];
        				ServletOutputStream outputStream = response.getOutputStream();
        				int readLength;
        				while((readLength = inputStream.read(buf)) != -1) {
        					outputStream.write(buf, 0, readLength);
        				}
        				
        				inputStream.close();
        				outputStream.flush();
        				outputStream.close();
        			}
        			
        			response.flushBuffer();
                } else {
                    response.setContentType("text/html;charset=gb2312");
                    StringBuilder sb = new StringBuilder();
                    sb.append("<script type=\"text/javascript\">");
                    sb.append("alert(\"文件不存在！\")");
                    sb.append("</script>");
                    PrintWriter out = response.getWriter();
                    out.write(sb.toString());
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            downloadFileNotExist(response);
        }
    }

    public static void downloadFileNotExist(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"text/javascript\">");
        sb.append("alert(\"文件不存在！\")");
        sb.append("</script>");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(sb.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
