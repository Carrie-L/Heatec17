package sanvio.libs.webserver;

import java.util.Iterator;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import sanvio.libs.dbclass.clsParameterList;
import sanvio.libs.util.NoServerException;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


public abstract class WebServerHelper {
	protected static final String TAG = "WebserviceHelper";

	protected Context mcontext;

	private static final String NAMESPACE = "http://tempuri.org/";

	protected static String USER_WEBSERVICEURL = "";
	public static String VERIFY_USER = "";
	public static String VERIFY_PSD = "";

	public static final String ELEMENT_HEAD = "RoxvellWebserviceHeader";
	public static final String ELEMENT_USER = "UserName";
	public static final String ELEMENT_PSD = "Password";

	public WebServerHelper(Context context) {
		mcontext = context;
		if (TextUtils.isEmpty(USER_WEBSERVICEURL)) {
			throw new IllegalStateException("USER_WEBSERVICEURL is Empty");
		}
		if (TextUtils.isEmpty(VERIFY_USER)) {
			throw new IllegalStateException("VERIFY_USER is Empty");
		}
		if (TextUtils.isEmpty(VERIFY_PSD)) {
			throw new IllegalStateException("VERIFY_PSD is Empty");
		}
	}

	/**
	 * 必须设置
	 * 
	 * @param url
	 *            WebServer路径
	 * @param user
	 *            WebServer验证用户
	 * @param psd
	 *            WebServer验证密码
	 */
	public static void setOptions(String url, String user, String psd) {
		USER_WEBSERVICEURL = url;
		VERIFY_USER = user;
		VERIFY_PSD = psd;
	}

	protected class clsEnvelopeList {
		private String mUserClassName;
		private Class<?> mUserClassType;

		public clsEnvelopeList(String pUserClassName, Class<?> pUserClassType) {
			mUserClassName = pUserClassName;
			mUserClassType = pUserClassType;
		}

		/**
		 * @return the mUserClassName
		 */
		public String getUserClassName() {
			return mUserClassName;
		}

		/**
		 * @param mUserClassName
		 *            the mUserClassName to set
		 */
		public void setUserClassName(String mUserClassName) {
			this.mUserClassName = mUserClassName;
		}

		/**
		 * @return the mUserClassType
		 */
		public Class<?> getUserClassType() {
			return mUserClassType;
		}

		/**
		 * @param mUserClassType
		 *            the mUserClassType to set
		 */
		public void setUserClassType(Class<?> mUserClassType) {
			this.mUserClassType = mUserClassType;
		}
	}

	protected static SoapObject InvokeMethod(String URL, String MethodName, List<clsParameterList> oList) throws NoServerException {
		return InvokeMethod(URL, MethodName, oList, null);
	}

	protected static SoapObject InvokeMethod(String URL, String MethodName, List<clsParameterList> oParameterLists,
			List<clsEnvelopeList> poEnvelopeLists) throws NoServerException {
		SoapObject request = GetSoapObject(MethodName);
		if (oParameterLists != null) {
			for (Iterator<clsParameterList> iterator = oParameterLists.iterator(); iterator.hasNext();) {
				clsParameterList oparameterList = iterator.next();
				PropertyInfo oPropertyInfo = new PropertyInfo();
				oPropertyInfo.setName(oparameterList.getParameterName());
				oPropertyInfo.setValue(oparameterList.getParameterValue());
				if (oparameterList.getIsCustomerObj()) {
					oPropertyInfo.setType(oparameterList.getParameterValue().getClass());
				}
				request.addProperty(oPropertyInfo);
			}
		}

	//	Log.v(TAG, "request:" + request.toString());

		// if (URL.equals(Configure.USER_WEBSERVICEURL)) {
		// VERIFY_USER = "youchok";
		// } else {
		// VERIFY_USER = VERIFY_USER_VALUE;
		// }

		SoapSerializationEnvelope envelope = GetEnvelope(request);

		if (poEnvelopeLists != null) {
			for (clsEnvelopeList oclsEnvelopeList : poEnvelopeLists) {
				envelope.addMapping(NAMESPACE, oclsEnvelopeList.getUserClassName(), oclsEnvelopeList.getUserClassType());
			}
		}

		Marshal dateMarshal = new MarshalDate();
		dateMarshal.register(envelope);

		Marshal floatMarshal = new MarshalFloat();
		floatMarshal.register(envelope);

		Marshal DoubleMarshal = new MarshalDouble();
		DoubleMarshal.register(envelope);

		return MakeCall(URL, envelope, NAMESPACE, MethodName);
	}

	protected static SoapObject MakeCall(String URL, SoapSerializationEnvelope Envelope, String NAMESPACE, String METHOD_NAME)
			throws NoServerException {
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;
		try {
			androidHttpTransport.call(NAMESPACE + METHOD_NAME, Envelope);
	//		 Log.i(TAG, "requestDump:" + androidHttpTransport.requestDump);
		//	 Log.i(TAG, "responseDump:" + androidHttpTransport.responseDump);
			if (Envelope.getResponse() != null) {
		//		Log.i(TAG, METHOD_NAME + ">>>>>" + Envelope.bodyIn.toString());
				return (SoapObject) Envelope.bodyIn;
			}
		} catch (Exception e) {
//			 Log.i(TAG, "requestDump:" + androidHttpTransport.requestDump);
//			 Log.i(TAG, "responseDump:" + androidHttpTransport.responseDump);
			Log.e(TAG, "MakeCall catch Except!Error:" + e.getMessage());
			if (e.getMessage().indexOf("failed to connect") != -1) {
				throw new NoServerException();
			}
			e.printStackTrace();
		}
		return null;
	}

	protected static SoapObject GetSoapObject(String MethodName) {
		return new SoapObject(NAMESPACE, MethodName);
	}

	protected static SoapSerializationEnvelope GetEnvelope(SoapObject Soap) {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.headerOut = new Element[] { buildAuthHeader() };
		envelope.dotNet = true;
		envelope.setOutputSoapObject(Soap);
		return envelope;
	}

	protected static Element buildAuthHeader() {
		Element h = new Element().createElement(NAMESPACE, ELEMENT_HEAD);
		Element username = new Element().createElement(NAMESPACE, ELEMENT_USER);
		username.addChild(Node.TEXT, VERIFY_USER);
		h.addChild(Node.ELEMENT, username);
		Element pass = new Element().createElement(NAMESPACE, ELEMENT_PSD);
		pass.addChild(Node.TEXT, VERIFY_PSD);
		h.addChild(Node.ELEMENT, pass);
		return h;
	}

}
