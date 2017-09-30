package sanvio.libs.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

public class StringUtils {
	public static SpannableString strikethrough(String source) {

		SpannableString ss = new SpannableString(source);
		ss.setSpan(new StrikethroughSpan(), 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
}
