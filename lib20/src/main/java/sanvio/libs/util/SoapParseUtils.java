package sanvio.libs.util;

import org.ksoap2.serialization.SoapObject;

public class SoapParseUtils {

	public static String GetValue(SoapObject soapChilds, String PropertyName) {
		String strValue = "";
		if (soapChilds.hasProperty(PropertyName)) {
			strValue = soapChilds.getProperty(PropertyName).toString();
			if (strValue != null && strValue.equals("anyType{}"))
				strValue = "";
			else if (strValue != null && strValue.equals("(null)"))
				strValue = "";
		}
		return strValue;
	}

	public static int GetIntValue(SoapObject soapChilds, String PropertyName, int Default) {
		String strValue = "";
		int intValue = Default;
		if (soapChilds.hasProperty(PropertyName)) {
			try {
				strValue = soapChilds.getProperty(PropertyName).toString();
				if (strValue != null && strValue.equals("anyType{}"))
					intValue = Default;
				else if (strValue != null && strValue.equals("(null)"))
					intValue = Default;
				else {
					intValue = Integer.valueOf(strValue);
				}
			} catch (Exception e) {
				intValue = Default;
			}
		}
		return intValue;
	}

	public static float GetFloatValue(SoapObject soapChilds, String PropertyName, float Default) {
		String strValue = "";
		float oValue = Default;
		if (soapChilds.hasProperty(PropertyName)) {
			try {
				strValue = soapChilds.getProperty(PropertyName).toString();
				if (strValue != null && strValue.equals("anyType{}"))
					oValue = Default;
				else if (strValue != null && strValue.equals("(null)"))
					oValue = Default;
				else {
					oValue = Float.valueOf(strValue);
				}
			} catch (Exception e) {
				oValue = Default;
			}
		}
		return oValue;
	}
}
