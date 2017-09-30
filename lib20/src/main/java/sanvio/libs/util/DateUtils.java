package sanvio.libs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

	public static String getToday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}

	public static String getToday(String pFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(pFormat);
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}

	@SuppressWarnings("deprecation")
	public static String dateAnalysis(String pDate, Context c) {
		String result = "";
		Date today, yesterday, date;
		String strToday = getToday();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(pDate);
			today = formatter.parse(strToday);
			yesterday = formatter.parse(strToday.substring(0, 4) + "-" + (today.getMonth() + 1) + "-" + (today.getDate() - 1));

			if (date.compareTo(today) == 0) {
				date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(pDate);
				String strDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH).format(date);
				if (strDate.endsWith("AM"))
					result = "上午" + strDate.substring(11, 16);
				else
					result = "下午" + strDate.substring(11, 16);
			} else if (date.compareTo(yesterday) == 0) {
				result = "昨天";
			} else {
				result = formatter.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			result = pDate.substring(0, 10);
		}
		return result;
	}

	@SuppressLint("NewApi")
	public static String FormatDate(String pFormat, String pstrDate) {
		String strResult = null;
		if (pstrDate.isEmpty()) {
			strResult = getToday(pFormat);
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(pFormat, Locale.getDefault());
			try {
				strResult = formatter.format(pstrDate);
			} catch (Exception e) {
				strResult = getToday(pFormat);
			}
		}

		return strResult;

	}

	public static String FormatDateByString(String pOldFormat, String pNewFormat, String pstrDate) {
		Date date = String2Date(pOldFormat, pstrDate);
		return FormatDate(pNewFormat, date);
	}

	public static Date String2Date(String pFormat, String pstrDate) {
		Date dResult = null;
		SimpleDateFormat formatter = new SimpleDateFormat(pFormat, Locale.getDefault());
		try {
			dResult = formatter.parse(pstrDate);
		} catch (Exception e) {
		}
		return dResult;

	}
	
	 public static Date String2Date(String pFormat, String strDate, TimeZone timezone) {
		Date dResult = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pFormat);
        sdf.setTimeZone(timezone);
        try { 
        	dResult = sdf.parse(strDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
       
        return dResult;
	  }
	 
	 
	public static String FormatDate(String pFormat, Date pDate) {
		String strResult = null;
		SimpleDateFormat formatter = new SimpleDateFormat(pFormat, Locale.getDefault());
		try {
			strResult = formatter.format(pDate);
		} catch (Exception e) {
			strResult = getToday(pFormat);
		}

		return strResult;

	}
	public static String FormatDate(String pFormat, Date pDate,TimeZone pTimeZone) {
		String strResult = null;
		SimpleDateFormat formatter = new SimpleDateFormat(pFormat);
		formatter.setTimeZone(pTimeZone);
		try {
			strResult = formatter.format(pDate);
		} catch (Exception e) {
			strResult = getToday(pFormat);
		}

		return strResult;

	}
	public static String FormatDate(String pFormat, Calendar calendar) {
		String strResult = "";
		SimpleDateFormat sdf = new SimpleDateFormat(pFormat);
		try {
			strResult = sdf.format(calendar.getTime());
		} catch (Exception e) {
		}
		return strResult;
	}

}
