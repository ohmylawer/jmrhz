package com.common.utils;


public class DigitalConvertTwo implements DigitalConvertInterface {
	private static final String[] str = {"〇", "（一）", "（二）", "（三）", "（四）", "（五）", "（六）", "（七）", "（八）", "（九）","（十）","（十一）","（十二）"};
	//private static final String[] arr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public String convert(int num) {
		
		return str[num];
	}
}
