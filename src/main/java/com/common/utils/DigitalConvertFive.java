package com.common.utils;


public class DigitalConvertFive implements DigitalConvertInterface {
	// 将数字转换成 (数字) 如：(1111)
	public String convert(int num) {
		String result = "(";
		
		String numStr = String.valueOf(num);
		for (int i = 0; i < numStr.length(); i++) {
			result += String.valueOf(numStr.charAt(i));
		}
		
		return result;
	}
}
