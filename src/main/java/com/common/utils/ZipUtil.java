package com.common.utils;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {

	public static void zipFile(final String srcPath, final String zipFileName) throws ZipException {
		// 生成的压缩文件
		ZipFile zipFile = new ZipFile(zipFileName);
		ZipParameters parameters = new ZipParameters();
		// 压缩方式
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		// 压缩级别
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setEncryptFiles(false);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		// 压缩方式
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		// 压缩级别
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		// 要打包的文件夹
		File currentFile = new File(srcPath);
		File[] fs = currentFile.listFiles();
		// 遍历test文件夹下所有的文件、文件夹
		for (File f : fs) {
			if (f.isDirectory()) {
				zipFile.addFolder(f.getPath(), parameters);
			} else {
				zipFile.addFile(f, parameters);
			}
		}
	}

	public static void unzip(final String zipFileName, final String desFilePath) throws ZipException {
		ZipFile zipFile = new ZipFile(zipFileName);

		zipFile.extractAll(desFilePath);
	}

	public static void unzip(final File file, final String desFilePath) throws ZipException {

		ZipFile zipFile = new ZipFile(file);

		zipFile.extractAll(desFilePath);
	}

}
