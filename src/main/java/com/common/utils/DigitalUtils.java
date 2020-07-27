package com.common.utils;

public class DigitalUtils {

	public static DigitalConvertInterface getDcObj(int hb) {
		DigitalConvertInterface dc = null;
		switch (hb) {
		case 1:
			dc = new DigitalConvertOne();
			break;
		case 2:
			dc = new DigitalConvertTwo();
			break;
		case 3:
			dc = new DigitalConvertThree();
			break;
		case 4:
			dc = new DigitalConvertFour();
			break;
		case 5:
			dc = new DigitalConvertFive();
			break;
		case 6:
			dc = new DigitalConvertSix();
			break;
		default:
			dc = new DigitalConvertThree();
			break;
		}
		return dc;
	}

	/**
	 * 按行标设置序号
	 * 
	 * @param hb
	 *            行标
	 * @param count
	 *            计划个数
	 * @return 序号值
	 */
	public static String getSerialNum(final int hb, final int count) {

		return DigitalUtils.getDcObj(hb).convert(count + 1);
	}

	// 设置编码
	public static String getCode(String fPlanCataLogCode) {

		int n = Integer.valueOf(fPlanCataLogCode);
		String index = String.valueOf(n + 1);
		int hb = index.length();
		if (fPlanCataLogCode.length() == 4) {
			switch (hb) {
			case 1:
				index = "000" + index;
				break;
			case 2:
				index = "00" + index;
				break;
			case 3:
				index = "0" + index;
				break;
			case 4:
				break;

			}
		}
		if(fPlanCataLogCode.length()==2) {
			switch (hb) {
			case 1:
				index = "0" + index;
				break;
			case 2:
				break;
			}
	
		}
		return index;
	}
}
