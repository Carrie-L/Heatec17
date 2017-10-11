package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import com.adsale.HEATEC.dao.ScheduleInfo;

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

	public ScheduleInfo getSchedule(int ID) {
		ScheduleInfo oScheduleInfo = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String strSql = strBaseSQL;
		strSql += " and ScheduleID = " + ID;
		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor.moveToLast()) {

//				oScheduleInfo = new ScheduleInfo(cursor);

			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return oScheduleInfo;
	}

	public List<ScheduleInfo> getScheduleInfoList(String pCompanyID) {
		List<ScheduleInfo> ScheduleInfos = null;
		SQLiteDatabase db = this.getReadableDatabase();
		ScheduleInfos = getScheduleInfoList(pCompanyID, db);
		db.close();
		return ScheduleInfos;
	}

	public List<ScheduleInfo> getScheduleInfoList(String pCompanyID, SQLiteDatabase db) {
		List<ScheduleInfo> ScheduleInfos = new ArrayList<ScheduleInfo>();
		String strSql = strBaseSQL;
		if (!TextUtils.isEmpty(pCompanyID)) {
			strSql += " and CompanyID = '" + pCompanyID + "'";
		}
		strSql += " ORDER BY StartTime";
		ScheduleInfos = Query(strSql);
		return ScheduleInfos;
	}

	public List<ScheduleInfo> Query(String pSQL) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<ScheduleInfo> ocolScheduleInfo = new ArrayList<ScheduleInfo>();
		try {
			Cursor cursor = db.rawQuery(pSQL, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
//					ocolScheduleInfo.add(new ScheduleInfo(cursor));
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

	public boolean modify(ScheduleInfo pScheduleInfo) {
		Cursor cursor = null;
		Boolean result = true;
		SQLiteDatabase db = getWritableDatabase();
		if (pScheduleInfo != null) {
			try {
				cursor = db.rawQuery(strBaseSQL + " and ScheduleID=" + pScheduleInfo.getScheduleID(), null);
				if (cursor != null && !cursor.moveToFirst()) {
					result = Insert(pScheduleInfo, db);
				} else if (cursor != null && cursor.moveToFirst()) {
					result = Update(pScheduleInfo, db);
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

	private boolean Insert(ScheduleInfo pScheduleInfo, SQLiteDatabase db) {
		boolean result = false;
		if (pScheduleInfo != null && db != null) {
			try {
				ContentValues cv;
				cv = new ContentValues();
				cv.put("ScheduleID", pScheduleInfo.getScheduleID());
				FillScheduleInfo(pScheduleInfo, cv);
				result = db.insert(DBTableBame, null, cv) != -1;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	private boolean Update(ScheduleInfo pScheduleInfo, SQLiteDatabase db) {
		boolean result = false;
		if (pScheduleInfo != null && db != null) {
			try {
				ContentValues cv = new ContentValues();
				FillScheduleInfo(pScheduleInfo, cv);
				result = db.update(DBTableBame, cv, "ScheduleID=?", new String[] { pScheduleInfo.getScheduleID() + "" }) > 0;
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

	private void FillScheduleInfo(ScheduleInfo pScheduleInfo, ContentValues cv) {
		cv.put("Title", pScheduleInfo.getTitle());
		cv.put("Note", pScheduleInfo.getNote());
		cv.put("Location", pScheduleInfo.getLocation());
		cv.put("CompanyID", pScheduleInfo.getCompanyID());
		cv.put("StartTime", pScheduleInfo.getStartTime());
		cv.put("Length", pScheduleInfo.getLength());
//		cv.put("Allday", pScheduleInfo.getAllday());
	}

	public Boolean check(String pCompanyID, String pStartTime) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(strBaseSQL + " and CompanyID='" + pCompanyID + "' and StartTime='" + pStartTime + "'", null);
		boolean result = cursor.moveToFirst();
		db.close();
		return result;

	}
}
