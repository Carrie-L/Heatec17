package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.clsMapFloor;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MapFloorDBHelper extends DatabaseHelper {
	public static final String TAG = "MapFloorDBHelper";
	public static final String DBTableBame = "MapFloor";
	public static final String strBaseSQL = "SELECT * FROM MapFloor WHERE 1=1";

	public MapFloorDBHelper(Context context) {
		super(context);
	}

	public List<clsMapFloor> getMapFloorList(String parentID, int type) {
		List<clsMapFloor> MapFloors = null;
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			MapFloors = getMapFloorList(db, parentID, type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return MapFloors;
	}

	public String getParentID(String ID) {
		String oParentID = "";
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			String strSql = String.format(strBaseSQL + " and [MapFloorID]='%s' ", ID);
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor.moveToFirst()) {
				oParentID = (new clsMapFloor(cursor)).getParentID();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return oParentID;
	}

	public List<clsMapFloor> getMapFloorList(SQLiteDatabase db, String parentID, int type) {
		List<clsMapFloor> MapFloors = new ArrayList<clsMapFloor>();
		String strSql = strBaseSQL;
		if (parentID != null) {
			strSql = String.format(strSql + " and [ParentID]='%s' ", parentID);
		}
		if (type > 0) {
			strSql = String.format(strSql + " and [Type]=%s ", type);
		}
		strSql += " order by SEQ ;";
		Cursor cursor = db.rawQuery(strSql, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				MapFloors.add(new clsMapFloor(cursor));
			}
		}
		cursor.close();
		return MapFloors;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strID = SoapParseUtils.GetValue(soapObject, "MapFloorID");

				cursor = db.rawQuery(strBaseSQL + " and MapFloorID='" + strID + "'", null);
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
				FillMapFloorBySoapObject(soapObject, cv);
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
				strID = SoapParseUtils.GetValue(soapObject, "MapFloorID");
				FillMapFloorBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "MapFloorID=?", new String[] { strID }) > 0;
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
				db.execSQL("delete from " + DBTableBame + " where MapFloorID ='" + id + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillMapFloorBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("MapFloorID", SoapParseUtils.GetValue(soapObject, "MapFloorID"));
		cv.put("ParentID", SoapParseUtils.GetValue(soapObject, "ParentID"));
		cv.put("Type", SoapParseUtils.GetIntValue(soapObject, "Type", 0));
		cv.put("NameTW", SoapParseUtils.GetValue(soapObject, "NameTW"));
		cv.put("NameCN", SoapParseUtils.GetValue(soapObject, "NameCN"));
		cv.put("NameEN", SoapParseUtils.GetValue(soapObject, "NameEN"));
		cv.put("SEQ", SoapParseUtils.GetIntValue(soapObject, "SEQ", 0));
		cv.put("CreateDateTime", SoapParseUtils.GetValue(soapObject, "CreateDateTime"));
		cv.put("UpdateDateTime", SoapParseUtils.GetValue(soapObject, "UpdateDateTime"));
		cv.put("RecordTimeStamp", SoapParseUtils.GetValue(soapObject, "RecordTimeStamp"));
	}

	public String check() {
		String oParentID = "";
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			String strSql = "select MapFloorID  from MapFloor Where ParentID='' AND Type=1 ";
			strSql += "   AND (select count(*) from MapFloor Where ParentID='')=1";
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor.moveToFirst()) {
				oParentID = cursor.getString(0);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return oParentID == null ? "" : oParentID;
	}

}
