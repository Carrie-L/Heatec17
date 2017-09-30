package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.clsWebContent;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WebContentDBHelper extends DatabaseHelper {
	public static final String TAG = "WebContentDBHelper";

	public static final String DBTableBame = "webContent";
	public static final String strBaseSQL = "SELECT * FROM webContent WHERE 1=1";

	public WebContentDBHelper(Context context) {
		super(context);
	}

	public List<clsWebContent> getWebContentList(int language) {
		List<clsWebContent> ocolWebContents = null;
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
		String strSql = strBaseSQL + " and LType=" + language + " order by  CreateDateTime desc";
		ocolWebContents = Query(strSql);
		return ocolWebContents;
	}

	public List<clsWebContent> Query(String pSQL) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<clsWebContent> ocolWebContents = new ArrayList<clsWebContent>();
		try {
			Cursor cursor = db.rawQuery(pSQL, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					ocolWebContents.add(new clsWebContent(cursor));
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

		return ocolWebContents;
	}

	public clsWebContent getWebContent(int PageId) {
		String strSql = strBaseSQL;
		List<clsWebContent> ocolWebContents = null;
		clsWebContent oclsWebContent = null;
		strSql += " AND PageId =" + PageId;
		ocolWebContents = Query(strSql);
		if (ocolWebContents != null && ocolWebContents.size() > 0) {
			oclsWebContent = ocolWebContents.get(0);
		}
		return oclsWebContent;
	}

	public String getContent(int PageId, int language) {
		String strContent = "";
		SQLiteDatabase db = getReadableDatabase();
		// 0:ZhTw; 1:en;2:ZhCn;
		String strSql = "SELECT %1$s FROM webContent WHERE   PageId = %2$s ";
		if (language == 1) {
			strSql = String.format(strSql, "ContentEN", PageId);
		}
		if (language == 2) {
			strSql = String.format(strSql, "ContentSC", PageId);
		} else {
			strSql = String.format(strSql, "ContentTC", PageId);
		}
		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor.moveToFirst()) {
				strContent = cursor.getString(0);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return strContent;
	}

	public List<clsWebContent> getDownWebContentList() {
		List<clsWebContent> ocolWebContents = null;
		String strSql = strBaseSQL + " AND [CType] = 1 AND [IsDown] = 0";
		ocolWebContents = Query(strSql);
		return ocolWebContents;
	}

	public boolean downloaded(int pPageId) {
		boolean blnResult = false;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String strSql = "Update webContent set IsDown=1  where PageId =" + pPageId;
			db.execSQL(strSql);
			db.close();
			blnResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blnResult;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strID = SoapParseUtils.GetValue(soapObject, "PageId");

				cursor = db.rawQuery(strBaseSQL + " and PageId='" + strID + "'", null);
				if (cursor != null && !cursor.moveToFirst() && !IsDelete) {
					result = InsertBySoapObject(soapObject, db);
				} else if (cursor != null && cursor.moveToFirst()) {
					if (!IsDelete) {
						result = UpdateBySoapObject(cursor, soapObject, db);
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
				cv.put("IsDown", 0);
				FillWebContentBySoapObject(soapObject, cv);
				result = db.insert(DBTableBame, null, cv) != -1;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	private boolean UpdateBySoapObject(Cursor cursor, SoapObject soapObject, SQLiteDatabase db) {
		boolean result = false;
		String strID = "";

		if (soapObject != null && db != null) {
			try {
				ContentValues cv = new ContentValues();
				strID = SoapParseUtils.GetValue(soapObject, "PageId");
				int cType = SoapParseUtils.GetIntValue(soapObject, "CType", 0);
				String strUpdateDateTime1 = SoapParseUtils.GetValue(soapObject, "ZipDateTime");
				String strUpdateDateTime2 = cursor.getString(cursor.getColumnIndex("ZipDateTime"));
				if (!strUpdateDateTime1.equals(strUpdateDateTime2) && cType == 1) {
					cv.put("IsDown", 0);
				}
				FillWebContentBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "PageId=?", new String[] { strID }) > 0;
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
				db.execSQL("delete from " + DBTableBame + " where PageId ='" + id + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillWebContentBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("PageId", SoapParseUtils.GetValue(soapObject, "PageId"));
		cv.put("CType", SoapParseUtils.GetValue(soapObject, "CType"));
		cv.put("CFile", SoapParseUtils.GetValue(soapObject, "CFile"));
		cv.put("TitleTW", SoapParseUtils.GetValue(soapObject, "TitleTW"));
		cv.put("TitleCN", SoapParseUtils.GetValue(soapObject, "TitleCN"));
		cv.put("TitleEN", SoapParseUtils.GetValue(soapObject, "TitleEN"));
		cv.put("ContentEN", SoapParseUtils.GetValue(soapObject, "ContentEN"));
		cv.put("ContentSC", SoapParseUtils.GetValue(soapObject, "ContentSC"));
		cv.put("ContentTC", SoapParseUtils.GetValue(soapObject, "ContentTC"));
		cv.put("ZipDateTime", SoapParseUtils.GetValue(soapObject, "ZipDateTime"));
		cv.put("CreateDateTime", SoapParseUtils.GetValue(soapObject, "CreateDateTime"));
		cv.put("UpdateDateTime", SoapParseUtils.GetValue(soapObject, "UpdateDateTime"));
		cv.put("RecordTimeStamp", SoapParseUtils.GetValue(soapObject, "RecordTimeStamp"));
	}
}
