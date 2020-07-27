package com.common.utils;


public class DigitalConvertOne implements DigitalConvertInterface {
	private static final String[] str = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	//private static final String[] arr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public String convert(int num) {
		String result = "";
		
		String numStr = String.valueOf(num);
		int length=numStr.length();
		for (int i = 0; i < numStr.length(); i++) {
			if(i==0&&length==2&&numStr.substring(0,1).equals("1")){
				result+="十";
			}else if(i==0&&length==2&&!numStr.substring(0,1).equals("1")){
				result += str[Integer.parseInt(String.valueOf(numStr.charAt(i)))];
				result+="十";
			}else if(!numStr.substring(length-1,length).equals("0")){
				result += str[Integer.parseInt(String.valueOf(numStr.charAt(i)))];
			}
			
			
			
			
			}
		
		return result+"";
	}
}
