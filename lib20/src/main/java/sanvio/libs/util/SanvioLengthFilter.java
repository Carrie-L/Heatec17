package sanvio.libs.util;

import android.content.Context;
import android.text.InputFilter.LengthFilter;
import android.text.Spanned;
import android.widget.Toast;

public class SanvioLengthFilter extends LengthFilter {
	private int mMaxlength;
	private int mErrorResID;
	private Context mContext;

	public SanvioLengthFilter(int arg0, int Maxlength, int errorResID,
			Context context) {
		super(arg0);
		mMaxlength = Maxlength;
		mContext = context;
		mErrorResID = errorResID;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		int destLen = getCharacterNum(dest.toString());
		int sourceLen = getCharacterNum(source.toString());
		if (destLen + sourceLen > mMaxlength) {
			Toast.makeText(mContext, mErrorResID, Toast.LENGTH_SHORT).show();
			return "";
		}
		return source;
	}

	/**
	 * @description ?��?�?��字符串�?字符个数（�??�中?��?，�?个中?��?2个�?符�?
	 */
	public static int getCharacterNum(final String content) {
		if (null == content || "".equals(content)) {
			return 0;
		} else {
			return (content.length() + getChineseNum(content));
		}
	}

	public static int getChineseNum(String s) {
		int num = 0;
		char[] myChar = s.toCharArray();
		for (int i = 0; i < myChar.length; i++) {
			if ((char) (byte) myChar[i] != myChar[i]) {
				num++;
			}
		}
		return num;
	}

}
