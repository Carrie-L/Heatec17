package com.adsale.HEATEC.util.network;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import android.content.SharedPreferences;

import com.adsale.HEATEC.App;
import com.adsale.HEATEC.R;

public class NetworkHelper {
	public static final String TAG = "NetworkHelper";

	private static final MediaType MEDIA_TYPE_XML = MediaType.parse("text/xml; charset=UTF-8");
	static final String GetMaster = "wsCLE15.asmx?op=getMaster";
	static final String HEADER_SOAP_ACTION_MASTER = "SOAPAction:http://tempuri.org/getMaster";
	static final String DOWN_WEBCONTENT_URL = "WebContent/{fileName}";

	public static RequestBody getMasterRequestBody() {
		return RequestBody.create(MEDIA_TYPE_XML, getMasterSoapBody());
	}

	private static String getMasterSoapBody() {
		SharedPreferences sp = App.mSP_updateTime;
		return new StringBuilder().append(getSoapHeader())
				.append("<soap:Body>\n")
				.append("    <getMaster xmlns=\"http://tempuri.org/\">\n")
				.append("      <pExhibitorUpdateDateTime>").append(sp.getString("EXHIBITOR", "")).append("</pExhibitorUpdateDateTime>\n")
				.append("      <pIndustryDtlUpdateDateTime>").append(sp.getString("EXHIBITOR_INDUSTRY_DTL", "")).append("</pIndustryDtlUpdateDateTime>\n")
				.append("      <pNewsUpdateDateTime>").append(sp.getString("NEWS", "")).append("</pNewsUpdateDateTime>\n")
				.append("      <pNewsLinkUpdateDateTime>").append(sp.getString("NEWS_LINK", "")).append("</pNewsLinkUpdateDateTime>\n")
				.append("      <pWebContentDateTime>").append(sp.getString("WEB_CONTENT", "")).append("</pWebContentDateTime>\n")
				.append("      <pIndustryDateTime>").append(sp.getString("INDUSTRY", "")).append("</pIndustryDateTime>\n")
				.append("      <pMainIconDateTime>").append(sp.getString("MAIN_ICON", "")).append("</pMainIconDateTime>")
				.append("      <pFloorDateTime>").append(sp.getString("FLOOR", "")).append("</pFloorDateTime>\n")
				.append("      <pMapFloorDateTime>").append(sp.getString("MAP_FLOOR", "")).append("</pMapFloorDateTime>\n")
				.append("    </getMaster>\n")
				.append("  </soap:Body>\n")
				.append("</soap:Envelope>").toString();
	}

	private static String getSoapHeader() {
		return new StringBuffer().append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n")
				.append("  <soap:Header>\n")
				.append("    <RoxvellWebserviceHeader xmlns=\"http://tempuri.org/\">\n")
				.append("      <UserName>CLE15</UserName>\n")
				.append("      <Password>pass</Password>\n")
				.append("    </RoxvellWebserviceHeader>\n")
				.append("  </soap:Header>\n").toString();
	}

}
