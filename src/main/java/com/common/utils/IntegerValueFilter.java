package com.common.utils;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 
    * @ClassName: IntegerValueFilter
    * @Description: TODO(阿里Fastjson将整型数据转为JSON时，会有精度损失。在过滤器中对整型数据按字符串进行转换，避免精度损失)
    * @author tony
    * @date 2018年10月31日
    *
 */
public class IntegerValueFilter implements ValueFilter {

	@Override
	public Object process(Object object, String name, Object value) {
		
		if(null != value && value instanceof Long) {
			
			return value.toString();
		}
		return value;
	}

}


