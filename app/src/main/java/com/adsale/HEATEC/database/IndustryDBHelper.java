package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.ICategory;
import com.adsale.HEATEC.database.model.clsIndustry;
import com.adsale.HEATEC.database.model.clsSection;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class IndustryDBHelper extends DatabaseHelper {
	public static final String TAG = "IndustryDBHelper";

	public static final String DBTableBame = "Industry";
	public static final String strBaseSQL = "SELECT * FROM Industry WHERE 1=1";

	public IndustryDBHelper(Context context) {
		super(context);
	}

	public List<ICategory> getIndustryList(int planguage) {
		List<ICategory> Industrys = null;
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			Industrys = getIndustryList(planguage, db);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return Industrys;
	}

	public List<ICategory> getIndustryList(int planguage, SQLiteDatabase db) {
		List<ICategory> Industrys = new ArrayList<ICategory>();
		String strSql = strBaseSQL;
		String strCondition = " and IndustryID in (select DISTINCT IndustryID from ExhibitorIndustryDtl where CompanyID in (select DISTINCT CompanyID from Exhibitor)) ";
		strSql += strCondition;
		if (planguage == 0) {
			strSql += " order by cast(SortTW as INT) ";
		} else if (planguage == 2) {
			strSql += " order by SortCN ";
		} else if (planguage == 1) {
			strSql += " order by SortEN ";
		}

		Cursor cursor = db.rawQuery(strSql, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Industrys.add(new clsIndustry(cursor));
			}
		}
		cursor.close();
		return Industrys;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strID = SoapParseUtils.GetValue(soapObject, "IndustryID");

				cursor = db.rawQuery(strBaseSQL + " and IndustryID='" + strID + "'", null);
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
				FillIndustryBySoapObject(soapObject, cv);
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
				strID = SoapParseUtils.GetValue(soapObject, "IndustryID");
				FillIndustryBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "IndustryID=?", new String[] { strID }) > 0;
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
				db.execSQL("delete from " + DBTableBame + " where IndustryID ='" + id + "'");

			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillIndustryBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("IndustryID", SoapParseUtils.GetValue(soapObject, "IndustryID"));
		cv.put("IndustryNameTW", SoapParseUtils.GetValue(soapObject, "IndustryNameTW"));
		cv.put("IndustryNameCN", SoapParseUtils.GetValue(soapObject, "IndustryNameCN"));
		cv.put("IndustryNameEN", SoapParseUtils.GetValue(soapObject, "IndustryNameEN"));
		cv.put("SortTW", SoapParseUtils.GetValue(soapObject, "SortTW"));
		cv.put("SortCN", SoapParseUtils.GetValue(soapObject, "SortCN"));
		cv.put("SortEN", SoapParseUtils.GetValue(soapObject, "SortEN"));

	}

	public List<clsSection> getIndexArray(int planguage) {
		List<clsSection> IndexArray = new ArrayList<clsSection>();
		int intSort;
		String strSql = "", strFieldName = "";
		String strCondition = "", strOrder = "";
		SQLiteDatabase db = this.getReadableDatabase();
		if (planguage == 0) {
			strSql = "Select SortTW from Industry ";
			strOrder = " group by SortTW order by cast(SortTW as INT)";
			strFieldName = "SortTW";
		} else if (planguage == 2) {
			strSql = "Select SortCN from Industry ";
			strOrder = "group by SortCN order by SortCN ";
			strFieldName = "SortCN";
		} else if (planguage == 1) {
			strSql = "Select SortEN from Industry  ";
			strOrder = "group by SortEN order by SortEN";
			strFieldName = "SortEN";
		}
		strCondition = "Where IndustryID in (select DISTINCT IndustryID from ExhibitorIndustryDtl  where CompanyID in (select DISTINCT CompanyID from Exhibitor)) ";
		strSql = strSql + strCondition + strOrder;

		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					intSort = cursor.getInt(cursor.getColumnIndex(strFieldName));
					String lable = cursor.getString(cursor.getColumnIndex(strFieldName));
					IndexArray.add(new clsSection(lable, intSort));
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
		return IndexArray;
	}
}
