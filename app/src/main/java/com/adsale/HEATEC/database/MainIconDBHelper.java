package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.clsMainIcon;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainIconDBHelper extends DatabaseHelper {
	public static final String TAG = "MainIconDBHelper";

	public static final String DBTableBame = "MainIcon";
	public static final String strBaseSQL = "SELECT * FROM MainIcon WHERE 1=1";

	public MainIconDBHelper(Context context) {
		super(context);
	}

	public List<clsMainIcon> getMainIconList() {
		List<clsMainIcon> ocolMainIcons = new ArrayList<clsMainIcon>();
		String strSql = strBaseSQL + " AND [IsHidden] = 0 and [Google_TJ] not like '%-%' order by [SEQ] desc";
		ocolMainIcons = Query(strSql);
		return ocolMainIcons;
	}
	
	public List<clsMainIcon> getPadMainIconList() {
		List<clsMainIcon> ocolMainIcons = new ArrayList<clsMainIcon>();
		String strSql = strBaseSQL + " AND [IsHidden] = 0   order by [SEQ] desc";
		ocolMainIcons = Query(strSql);
		return ocolMainIcons;
	}

	public List<clsMainIcon> getDownMainIconList() {
		List<clsMainIcon> ocolMainIcons = new ArrayList<clsMainIcon>();
		String strSql = strBaseSQL + " AND [IsDown] = 0 and [CType] = 1 order by [SEQ] desc";
		ocolMainIcons = Query(strSql);
		return ocolMainIcons;
	}

	public boolean downloaded(String pIconID) {
		boolean blnResult = false;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String strSql = "Update MainIcon set IsDown=1  where IconID = '" + pIconID + "'";
			db.execSQL(strSql);
			db.close();
			blnResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blnResult;
	}

	public clsMainIcon getMainIcon(String IconID) {
		clsMainIcon oclsMainIcon = null;
		List<clsMainIcon> ocolWebContents = null;
		String strSql = strBaseSQL;
		strSql += " AND IconID ='" + IconID + "'";
		ocolWebContents = Query(strSql);
		if (ocolWebContents != null && ocolWebContents.size() > 0) {
			oclsMainIcon = ocolWebContents.get(0);
		}
		return oclsMainIcon;
	}

	public List<clsMainIcon> Query(String pSQL) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<clsMainIcon> ocolMainIcon = new ArrayList<clsMainIcon>();
		try {
			Cursor cursor = db.rawQuery(pSQL, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					ocolMainIcon.add(new clsMainIcon(cursor));
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

		return ocolMainIcon;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strID = SoapParseUtils.GetValue(soapObject, "IconID");

				cursor = db.rawQuery(strBaseSQL + " and IconID='" + strID + "'", null);
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
				FillMainIconBySoapObject(soapObject, cv);
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
				strID = SoapParseUtils.GetValue(soapObject, "IconID");
				int cType = SoapParseUtils.GetIntValue(soapObject, "CType", 0);
				String strUpdateDateTime1 = SoapParseUtils.GetValue(soapObject, "ZipDateTime");
				String strUpdateDateTime2 = cursor.getString(cursor.getColumnIndex("ZipDateTime"));
				if (!strUpdateDateTime1.equals(strUpdateDateTime2) && cType == 1) {
					cv.put("IsDown", 0);
				}
				FillMainIconBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "IconID=?", new String[] { strID }) > 0;
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
				db.execSQL("delete from " + DBTableBame + " where IconID ='" + id + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillMainIconBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("IconID", SoapParseUtils.GetValue(soapObject, "IconID"));
		cv.put("Icon", SoapParseUtils.GetValue(soapObject, "Icon"));
		cv.put("SEQ", SoapParseUtils.GetIntValue(soapObject, "SEQ", 0));
		cv.put("CType", SoapParseUtils.GetValue(soapObject, "CType"));
		cv.put("CFile", SoapParseUtils.GetValue(soapObject, "CFile"));
		cv.put("TitleTW", SoapParseUtils.GetValue(soapObject, "TitleTW"));
		cv.put("TitleCN", SoapParseUtils.GetValue(soapObject, "TitleCN"));
		cv.put("TitleEN", SoapParseUtils.GetValue(soapObject, "TitleEN"));
		cv.put("ZipDateTime", SoapParseUtils.GetValue(soapObject, "ZipDateTime"));	 
		cv.put("CreateDateTime", SoapParseUtils.GetValue(soapObject, "CreateDateTime"));
		cv.put("UpdateDateTime", SoapParseUtils.GetValue(soapObject, "UpdateDateTime"));
		cv.put("RecordTimeStamp", SoapParseUtils.GetValue(soapObject, "RecordTimeStamp"));
		cv.put("BaiDu_TJ", SoapParseUtils.GetValue(soapObject, "BaiDu_TJ"));
		cv.put("Google_TJ", SoapParseUtils.GetValue(soapObject, "Google_TJ"));
		cv.put("IsHidden", SoapParseUtils.GetValue(soapObject, "IsHidden").equals("true")?1:0);
	}
}
