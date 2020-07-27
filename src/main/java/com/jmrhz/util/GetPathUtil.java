package com.jmrhz.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetPathUtil {
	
	public static String getPath() {
		String path = System.getProperty("user.dir");
		File savefile = new File(path + File.separator + "uploadFiles");
		if (!savefile.exists()) {
			savefile.mkdir();
		}
		return savefile.getPath();
	}

	public static String getModulesPath() {
		String path = System.getProperty("user.dir");
		File savefile = new File(path + File.separator + "uploadFiles" + File.separator + "modules");
		if (!savefile.exists()) {
			savefile.mkdir();
		}
		return savefile.getPath();
	}

	public static void copyFatherToChild(Object father, Object child) {
		if (!(child.getClass().getSuperclass() == father.getClass())) {
			System.err.println("child不是father的子类");
		}
		Class fatherClass = father.getClass();
		Field ff[] = fatherClass.getDeclaredFields();
		for (int i = 0; i < ff.length; i++) {
			Field f = ff[i];// 取出每一个属性，如deleteDate
			f.setAccessible(true);
			Class type = f.getType();
			boolean isStatic = Modifier.isStatic(f.getModifiers());
			if (isStatic) {
				continue;
			}
			try {
				Method m = fatherClass.getMethod("get" + upperHeadChar(f.getName()));// 方法getDeleteDate
				m.setAccessible(true);
				Object obj = m.invoke(father);// 取出属性值
				f.set(child, obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 首字母大写，in:deleteDate，out:DeleteDate
	 */
	private static String upperHeadChar(String in) {
		String head = in.substring(0, 1);
		String out = head.toUpperCase() + in.substring(1, in.length());
		return out;
	}

	/**
	 * 获取应用绝对路径
	 * 
	 * @return
	 */
	public static String realPath() {
		String osName = System.getProperty("os.name").toLowerCase();
		String path = GetPathUtil.class.getResource("/").getPath();
		// 根据不同操作系统类型选择不同的数据获取方法
		if (osName.startsWith("windows")) {
			path = path.replace("file://", "");
		} else if (osName.startsWith("linux")) {
			path = path.replace("file:", "");
		} else {
			// 其他服务器类型

		}
		return path;
	}

	public static void main(String[] args) {
		System.out.println(getPath());
		System.out.println(getModulesPath());
	}
}
