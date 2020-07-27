package com.common.utils;

public class CodeUtil {
	
	public static CodeUtil getInstance() {
		
		return instance;
	}

	public String generateMaxCode( final int level, final String parentCode, final String curMaxCode, final int length ) {
		
		String maxCode = "";
		
		if( ((null == parentCode) || ("".equals(parentCode))) && (1 == level) && ( null == curMaxCode || "".equals(curMaxCode)) ) {			
				
				return fixCode("1",length);
		}
		
		if( parentCode.length() != (level-1)*length ) {			
			
			return null;
		}
		
		if( (null == curMaxCode) || ("".equals(curMaxCode)) ) {
			
			maxCode = parentCode.substring(0, (level-1)*length) + fixCode("1",length);
		}else {
		
			maxCode = parentCode.substring(0, (level-1)*length)+fixCode(String.valueOf((Integer.parseInt(curMaxCode.substring( (level-1)*length, level*length)) + 1)) , length);
		}
		
		
		
		return maxCode;
	}
	
	public String fixCode( final String code, final int length) {
		
		String maxCode = "";
		
		for( int i = 0 ; i < (length -  code.length()) ; i++ ) {
			
			maxCode+="0";
		}
		
		return maxCode+code;
	}
	
	private CodeUtil() {};
	
	private final static CodeUtil instance = new CodeUtil();
}
