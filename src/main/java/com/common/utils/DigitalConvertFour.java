package com.common.utils;


public class DigitalConvertFour implements DigitalConvertInterface {
//	private static final String[] str = {"(〇)", "(一)", "(二)", "(三)", "(四)", "(五)", "(六)", "(七)", "(八)", "(九)"};
	//private static final String[] arr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public String convert(int num) {
		String result = "(";
		
		String numStr = String.valueOf(num);
		for (int i = 0; i < numStr.length(); i++) {
			result += String.valueOf(numStr.charAt(i));
		}
		
		return result + ")";
	}
}
