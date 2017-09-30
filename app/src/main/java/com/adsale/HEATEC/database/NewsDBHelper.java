package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.clsNews;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsDBHelper extends DatabaseHelper {
	public static final String TAG = "NewsDBHelper";

	public static final String DBTableBame = "News";
	public static final String strBaseSQL = "SELECT * FROM News WHERE 1=1";

	public NewsDBHelper(Context context) {
		super(context);
	}

	public List<clsNews> getNewsList(int language) {
		List<clsNews> ocolNews = null;
		switch (language) {
		case 1:
			language = 3;
			break;
		case 2:
			language = 2;
			break;
		default:
			language = 1;
			break;
		}
		String strSql = strBaseSQL + " and LType=" + language + " order by  PublishDate desc";
		ocolNews = Query(strSql);
		return ocolNews;
	}

	public List<clsNews> getNewsList() {
		List<clsNews> ocolNews = null;
		String strSql = strBaseSQL + " order by  PublishDate desc";
		ocolNews = Query(strSql);
		return ocolNews;
	}

	public clsNews getNews(String NewsID) {
		clsNews oclsNews = null;
		List<clsNews> ocolNews = null;
		String strSql = strBaseSQL;
		strSql += String.format(" AND NewsID ='%s' ", NewsID);
		ocolNews = Query(strSql);
		if (ocolNews != null && ocolNews.size() > 0) {
			oclsNews = ocolNews.get(0);
		}
		return oclsNews;
	}

	public List<clsNews> Query(String pSQL) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<clsNews> ocolNews = new ArrayList<clsNews>();
		try {
			Cursor cursor = db.rawQuery(pSQL, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					ocolNews.add(new clsNews(cursor));
				}
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}

		return ocolNews;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strID = SoapParseUtils.GetValue(soapObject, "NewsID");

				cursor = db.rawQuery(strBaseSQL + " and NewsID='" + strID + "'", null);
				if (cursor != null && !cursor.moveToFirst() && !IsDelete) {
					result = InsertBySoapObject(soapObject, db);
				} else if (cursor != null && cursor.moveToFirst()) {
					if (!IsDelete) {
						result = UpdateBySoapObject(soapObject, db);
					} else {
						Delete(strID, db);
						result = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			} finally {
				cursor.close();
			}
		}
		return result;
	}

	private boolean InsertBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		boolean result = false;
		if (soapObject != null && db != null) {
			try {
				ContentValues cv;
				cv = new ContentValues();
				FillNewsBySoapObject(soapObject, cv);
				result = db.insert(DBTableBame, null, cv) != -1;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	private boolean UpdateBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		boolean result = false;
		String strID = "";
		if (soapObject != null && db != null) {
			try {
				ContentValues cv = new ContentValues();
				strID = SoapParseUtils.GetValue(soapObject, "NewsID");
				FillNewsBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "NewsID=?", new String[] { strID }) > 0;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public boolean Delete(String id, SQLiteDatabase db) {
		boolean result = true;
		try {
			if (id != null) {
				db.execSQL("delete from " + DBTableBame + " where NewsID ='" + id + "'");
				db.execSQL("delete from NewsLink where NewsID ='" + id + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillNewsBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("NewsID", SoapParseUtils.GetValue(soapObject, "NewsID"));
		cv.put("LType", SoapParseUtils.GetValue(soapObject, "LType"));
		cv.put("Logo", SoapParseUtils.GetValue(soapObject, "Logo"));
		cv.put("Title", SoapParseUtils.GetValue(soapObject, "Title"));
		cv.put("ShareLink", SoapParseUtils.GetValue(soapObject, "ShareLink"));
		cv.put("Description", SoapParseUtils.GetValue(soapObject, "Description"));
		cv.put("PublishDate", SoapParseUtils.GetValue(soapObject, "PublishDate"));
		cv.put("CreateDateTime", SoapParseUtils.GetValue(soapObject, "CreateDateTime"));
		cv.put("UpdateDateTime", SoapParseUtils.GetValue(soapObject, "UpdateDateTime"));
		cv.put("RecordTimeStamp", SoapParseUtils.GetValue(soapObject, "RecordTimeStamp"));
	}
}
