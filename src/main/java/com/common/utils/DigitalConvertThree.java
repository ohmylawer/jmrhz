package com.common.utils;


public class DigitalConvertThree implements DigitalConvertInterface {
	public String convert(int num) {
		String result = "";
		
		String numStr = String.valueOf(num);
		for (int i = 0; i < numStr.length(); i++) {
			result += String.valueOf(numStr.charAt(i));
		}
		
		return result;
	}
}
