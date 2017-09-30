package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import com.adsale.HEATEC.database.model.clsScheduleInfo;

import sanvio.libs.dbhelper.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class ScheduleInfoDBHelper extends DatabaseHelper {
	public static final String TAG = "ScheduleInfoDBHelper";

	public static final String DBTableBame = "ScheduleInfo";
	public static final String strBaseSQL = "SELECT * FROM ScheduleInfo WHERE 1=1";

	public ScheduleInfoDBHelper(Context context) {
		super(context);
	}

	public clsScheduleInfo getSchedule(int ID) {
		clsScheduleInfo oClsScheduleInfo = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String strSql = strBaseSQL;
		strSql += " and ScheduleID = " + ID;
		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor.moveToLast()) {

				oClsScheduleInfo = new clsScheduleInfo(cursor);

			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return oClsScheduleInfo;
	}

	public List<clsScheduleInfo> getScheduleInfoList(String pCompanyID) {
		List<clsScheduleInfo> ScheduleInfos = null;
		SQLiteDatabase db = this.getReadableDatabase();
		ScheduleInfos = getScheduleInfoList(pCompanyID, db);
		db.close();
		return ScheduleInfos;
	}

	public List<clsScheduleInfo> getScheduleInfoList(String pCompanyID, SQLiteDatabase db) {
		List<clsScheduleInfo> ScheduleInfos = new ArrayList<clsScheduleInfo>();
		String strSql = strBaseSQL;
		if (!TextUtils.isEmpty(pCompanyID)) {
			strSql += " and CompanyID = '" + pCompanyID + "'";
		}
		strSql += " ORDER BY StartTime";
		ScheduleInfos = Query(strSql);
		return ScheduleInfos;
	}

	public List<clsScheduleInfo> Query(String pSQL) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<clsScheduleInfo> ocolScheduleInfo = new ArrayList<clsScheduleInfo>();
		try {
			Cursor cursor = db.rawQuery(pSQL, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					ocolScheduleInfo.add(new clsScheduleInfo(cursor));
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

		return ocolScheduleInfo;
	}

	public boolean modify(clsScheduleInfo pClsScheduleInfo) {
		Cursor cursor = null;
		Boolean result = true;
		SQLiteDatabase db = getWritableDatabase();
		if (pClsScheduleInfo != null) {
			try {
				cursor = db.rawQuery(strBaseSQL + " and ScheduleID=" + pClsScheduleInfo.getScheduleID(), null);
				if (cursor != null && !cursor.moveToFirst()) {
					result = Insert(pClsScheduleInfo, db);
				} else if (cursor != null && cursor.moveToFirst()) {
					result = Update(pClsScheduleInfo, db);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			} finally {
				cursor.close();
			}
		}
		db.close();
		return result;
	}

	private boolean Insert(clsScheduleInfo pclsScheduleInfo, SQLiteDatabase db) {
		boolean result = false;
		if (pclsScheduleInfo != null && db != null) {
			try {
				ContentValues cv;
				cv = new ContentValues();
				cv.put("ScheduleID", pclsScheduleInfo.getScheduleID());
				FillScheduleInfo(pclsScheduleInfo, cv);
				result = db.insert(DBTableBame, null, cv) != -1;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	private boolean Update(clsScheduleInfo pClsScheduleInfo, SQLiteDatabase db) {
		boolean result = false;
		if (pClsScheduleInfo != null && db != null) {
			try {
				ContentValues cv = new ContentValues();
				FillScheduleInfo(pClsScheduleInfo, cv);
				result = db.update(DBTableBame, cv, "ScheduleID=?", new String[] { pClsScheduleInfo.getScheduleID() + "" }) > 0;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public boolean Delete(int id) {
		SQLiteDatabase db = getWritableDatabase();
		boolean result = Delete(id, db);
		db.close();
		return result;
	}

	public boolean Delete(int id, SQLiteDatabase db) {
		boolean result = true;
		try {
			if (id != -1) {
				db.execSQL("delete from " + DBTableBame + " where ScheduleID =" + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean chearAll() {
		boolean result = false;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from " + DBTableBame);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		return result;
	}

	private void FillScheduleInfo(clsScheduleInfo pClsScheduleInfo, ContentValues cv) {
		cv.put("Title", pClsScheduleInfo.getTitle());
		cv.put("Note", pClsScheduleInfo.getNote());
		cv.put("Location", pClsScheduleInfo.getLocation());
		cv.put("CompanyID", pClsScheduleInfo.getCompanyID());
		cv.put("StartTime", pClsScheduleInfo.getStartTime());
		cv.put("Length", pClsScheduleInfo.getLength());
		cv.put("Allday", pClsScheduleInfo.getAllday());
	}

	public Boolean check(String pCompanyID, String pStartTime) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(strBaseSQL + " and CompanyID='" + pCompanyID + "' and StartTime='" + pStartTime + "'", null);
		boolean result = cursor.moveToFirst();
		db.close();
		return result;

	}
}
