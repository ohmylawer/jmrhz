package com.common.utils;


public class DigitalConvertSix implements DigitalConvertInterface {
	private static final String[] str = {"◎", "①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨"};
	public String convert(int num) {
		String result = "";
		
		String numStr = String.valueOf(num);
		for (int i = 0; i < numStr.length(); i++) {
			result += str[Integer.parseInt(String.valueOf(numStr.charAt(i)))];
		}
		
		return result;
	}
}
