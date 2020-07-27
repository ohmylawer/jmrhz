package com.jmrhz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PicUtil {
	public static void copPic(String from, String to) throws IOException {
		File file1 = new File(from);
		File file2 = new File(to);
		byte[] b = new byte[(int) file1.length()];
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(file1);
			out = new FileOutputStream(file2);
			while (in.read(b) != -1) {
				out.write(b);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			out.flush();
			in.close();
			out.close();
		}
	}
}
