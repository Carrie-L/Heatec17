package sanvio.libs.util;

import java.text.DecimalFormat;

public class FormatUtils {
	
	public static int String2Int(String pString, int defut) {
		int intResult = defut;
		try {
			intResult = Integer.parseInt(pString);
		} catch (Exception e) {
		}
		return intResult;
	}

	public static float String2Float(String pString, float defut) {
		float result = defut;
		try {
			result = Float.parseFloat(pString);
		} catch (Exception e) {
		}
		return result;
	}

	public static double String2Double(String pString, double defut) {
		double result = defut;
		try {
			result = Double.parseDouble(pString);
		} catch (Exception e) {
		}
		return result;
	}

	public static float getFloatbyString(String value, float defaultValue) {
		try {
			return Float.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static int getIntegerbyString(String value, int defaultValue) {
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String getCurrency(Object value, String defaultValue) {
		try {
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String getPercent(Object value, String defaultValue) {
		try {
			DecimalFormat df = new java.text.DecimalFormat("#0%");
			return df.format(value);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}
}
