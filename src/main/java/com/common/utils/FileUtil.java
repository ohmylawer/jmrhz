package com.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.config.YMLConfig;

@Component
public class FileUtil {

	public static String readFile(final File file) throws IOException {

		// InputStream is = null;
		//
		// try{
		//
		// is = new FileInputStream( file );
		//
		// byte[] buf = new byte[1024];
		// StringBuffer buffer = new StringBuffer("");
		//
		// while( IOUtils.toBufferedInputStream(is).read(buf) > 0) {
		//
		// buffer.append( new String(buf,"UTF-8"));
		// }
		//
		// return buffer.toString();
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }finally {
		//
		// IOUtils.closeQuietly(is);
		// }
		//
		// return "";

		return FileUtils.readFileToString(file, "UTF-8");

	}

	public static void writeFile(final String filePath, String fileName, final String data) throws IOException {

		fileName = (fileName.contains(".")) ? fileName : fileName + ".json";

		FileUtils.write(new File(filePath + File.separator + fileName), data, "UTF-8");

	}

	public static File createDic(final String file) {

		File path = new File(file);
		if (!path.exists()) {

			path.mkdir();
		}

		return path;
	}

	public static JSONArray stringToJSONArray(final String json) {

		if (CommonUtil.isEmpty(json)) {

			return null;
		}

		return JSONObject.parseArray(json);
	}

	/**
	 * 导入文件
	 * 
	 * @param importingFile
	 *            待导入文件
	 * @param fileInfo
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> importFile(final File importingFile, final String[] fileNames) throws Exception {

		Map<String, String> resultMap = null;

		if (CommonUtil.isEmpty(importingFile)) {

			return null;
		}

		boolean hasFileType = false;
		for (final String fileType : Constants.IMPORT_FILE_TYPES) {

			if (fileType.equals(FileUtil.getFileType(importingFile))) {

				hasFileType = true;
				break;
			}
		}

		if (!hasFileType) {

			return null;
		}

		if (Constants.IMPORT_FILE_TYPE_ZIP.equals(FileUtil.getFileType(importingFile))) {

			resultMap = new HashMap<String, String>();

			for (String fileName : fileNames) {

				String importResult = FileUtil.importZipFile(importingFile, fileName);
				if (CommonUtil.isEmpty(importResult)) {

					continue;
				} else if ("3".equals(importResult)) {

					continue;
				}

				resultMap.put(fileName, importResult);
			}

		}

		return resultMap;
	}

	/**
	 * 导入文件
	 * 
	 * @param importingFile
	 *            待导入文件
	 * @param fileInfo
	 * @return
	 * @throws Exception
	 */
	public static String importFile(final File importingFile) throws Exception {

		if (CommonUtil.isEmpty(importingFile)) {

			return "0";
		}

		boolean hasFileType = false;
		for (final String fileType : Constants.IMPORT_FILE_TYPES) {

			if (fileType.equals(FileUtil.getFileType(importingFile))) {

				hasFileType = true;
				break;
			}
		}

		if (!hasFileType) {

			return "2";
		}

		if (Constants.IMPORT_FILE_TYPE_ZIP.equals(FileUtil.getFileType(importingFile))) {

			String importResult = FileUtil.importZipFile(importingFile, null);
			if (CommonUtil.isEmpty(importResult)) {

				return "0";
			} else if ("3".equals(importResult)) {

				return "3";
			} else {

				return importResult;
			}
		} else {
			// 暂未实现导入Excel文件。
		}

		return "1";
	}

	public static String importZipFile(final File importingFile, final String fileName) throws Exception {

		// 文件解压
		final String desFilePath = CommonUtil.getUploadPath() + File.separator
				+ Calendar.getInstance().getTimeInMillis();

		File unzipDic = FileUtil.createDic(desFilePath);

		ZipUtil.unzip(importingFile, desFilePath);

		// 文件校验
		File fileInfo = new File(desFilePath + File.separator + Constants.IMPORT_FILEINFO_CHECKFILE_NAME);

		// 文件读入
		JSONObject fileInfoJSON = FileUtil.fileToJSON(fileInfo);
		short validateResult = FileUtil.validateFileInfo(fileInfoJSON);
		if (1 != validateResult) {
			// 删除解压缩的临时文件
			org.apache.commons.io.FileUtils.deleteDirectory(unzipDic);

			return "3";
		}
		// 文件解密
		File dataFile = new File(desFilePath + File.separator
				+ ((CommonUtil.isEmpty(fileName)) ? Constants.IMPORT_FILEINFO_NAME : fileName));
		JSONArray dataFileJSON = FileUtil.fileToJSONs(dataFile);

		// 删除解压缩的临时文件
		org.apache.commons.io.FileUtils.deleteDirectory(unzipDic);

		if (1 == fileInfoJSON.getIntValue(Constants.IMPORT_FILEINFO_ENCRYPTION)) {

			DesUtils des = new DesUtils("maintenanceplan2");

			return des.decrypt(des.encrypt(dataFileJSON.toJSONString()));
		} else {

			return dataFileJSON.toJSONString();
		}
	}

	public static Map<String, Object> exportFile(final Map<String, List> exportFileAndData, final String toUnitCode,
			final long fromUnitId, final String equTypeCodes, final String exportFileName) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 1);
		result.put("msg", "数据导出成功！");

		// 验证数据
		if (CommonUtil.isEmpty(exportFileAndData)) {

			result.put("status", 0);
			result.put("msg", "待导出的数据不能为空！");

			return result;
		}

		JSONObject fileInfo = new JSONObject();
		fileInfo.put(Constants.IMPORT_FILEINFO_TOUNIT, toUnitCode);
		fileInfo.put(Constants.IMPORT_FILEINFO_EQU, equTypeCodes);
		fileInfo.put(Constants.IMPORT_FILEINFO_FROMUNIT, fromUnitId);
		fileInfo.put(Constants.IMPORT_FILEINFO_ENCRYPTION, getEncryption());

		short validate = FileUtil.validateFileInfo(fileInfo);
		if (1 != validate) {

			result.put("status", validate);
			result.put("msg", "文件发送信息不完整，请确认后重新导出文件！");

			return result;
		}

		final String filePath = CommonUtil.getDownloadPath() + File.separator
				+ Calendar.getInstance().getTimeInMillis();
		FileUtil.createDic(filePath);

		// 写入校验用文件。
		org.apache.commons.io.FileUtils.write(
				new File(filePath + File.separator + Constants.IMPORT_FILEINFO_CHECKFILE_NAME), fileInfo.toJSONString(),
				"UTF-8");

		Map<String, Object> exportedFileMap = FileUtil.doExportFile(filePath, exportFileAndData, exportFileName);
		result.put("fileName", exportedFileMap.get("IMPORT_FILEINFO_NAME").toString());
		return result;
	}

	private static int getEncryption() {
		return 0;
	}

	public static File createUploadFile(final MultipartFile file) throws Exception {

		if (CommonUtil.isEmpty(file)) {

			return null;
		}

		File uploadFile = new File(CommonUtil.getUploadPath() + File.separator + file.getOriginalFilename());
		if (uploadFile.exists()) {

			uploadFile.delete();
		}

		file.transferTo(uploadFile);
		return uploadFile;
	}

	private static Map<String, Object> doExportFile(final String filePath, final Map<String, List> exportFileAndData,
			final String exportFileName) throws Exception {

		final boolean isEncryption = (getEncryption() == 1);
		final DesUtils des = isEncryption ? new DesUtils("maintenanceplan2") : null;
		final String zipFileName = exportFileName
				+ CommonUtil.dateToStr(Calendar.getInstance().getTime(), "yyyy年MM月dd日HH时mm分") + "文件.zip";

		final Map<String, Integer> exportResult = new HashMap<String, Integer>(exportFileAndData.size());

		final File path = FileUtil.createDic(filePath);

		// 采用多线程方式写文件。
		final Iterator<String> fileNameIt = exportFileAndData.keySet().iterator();
		String fileName = null;

		while (fileNameIt.hasNext()) {

			fileName = fileNameIt.next();

			if (isEncryption) {

				FileUtil.writeFile(filePath, fileName,
						des.encrypt(JSONArray.toJSONString(exportFileAndData.get(fileName))));

			} else {
				FileUtil.writeFile(filePath, fileName, JSONArray.toJSONString(exportFileAndData.get(fileName)));
			}

			exportResult.put(fileName, 1);
		}

		ZipUtil.zipFile(filePath, CommonUtil.getDownloadPath() + File.separator + zipFileName);

		org.apache.commons.io.FileUtils.deleteDirectory(path);

		final Map<String, Object> result = new HashMap<String, Object>();
		result.put("IMPORT_FILEINFO_NAME", zipFileName);
		result.put("", exportResult);

		return result;
	}

	private static JSONObject fileToJSON(final File file) throws IOException {

		return JSONObject.parseObject(FileUtil.readFile(file));
	}

	private static JSONArray fileToJSONs(final File file) throws IOException {

		return JSONArray.parseArray(FileUtil.readFile(file));
	}

	/**
	 * * @param fileInfo 文件信息，用于扩展接口。具体包括： 1、fromUnit 文件来源，A单位a部门 2、toUnit
	 * 文件地址，B单位b部门 3、planYear 计划年度，如2019 4、planNumber 计划名称，如正常计划、调整计划等 5、ZB类别
	 * 多个类别以逗号分隔
	 * 
	 * @param fileInfo
	 * @return
	 * @throws Exception
	 */
	private static short validateFileInfo(final JSONObject fileInfo) throws Exception {
		if (null == fileInfo) {

			return 3;
		}

		// if ( 0 == CommonUtil.getCurrentLoginUser().getOrgId() ) {
		//
		// return 3;
		// }

		if (CommonUtil.isEmpty(fileInfo.get(Constants.IMPORT_FILEINFO_TOUNIT))) {

			return 3;
		}

		final String equType = fileInfo.getString(Constants.IMPORT_FILEINFO_EQU);
		if (!CommonUtil.isEmpty(equType)) {

			final String[] equTypes = equType.split(",");
			for (String sEquType : equTypes) {

			}
		}

		if (CommonUtil.isEmpty(fileInfo.get(Constants.IMPORT_FILEINFO_ENCRYPTION))) {

			return 3;
		}

		return 1;
	}

	public static String getFileType(final File file) {

		final int docIndex = file.getName().indexOf(".");

		return (docIndex > 0) ? file.getName().substring(docIndex + 1) : "未知文件类型";
	}

	@Autowired
	private static YMLConfig ymlConfig;

	// public static void main(String[] args) {
	//
	// File file = new
	// File("D:\\wud\\maintenanceplan\\Maintenanceplan2\\src\\main\\resources\\static\\templates\\admin\\data\\menu_cc.json");
	//
	//
	// System.out.println( JSONArray.parse(FileUtil.readFile(file)));
	// }
}
