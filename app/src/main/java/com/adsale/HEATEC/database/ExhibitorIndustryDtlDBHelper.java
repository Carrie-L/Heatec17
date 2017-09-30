package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.clsExhibitorIndustryDtl;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class ExhibitorIndustryDtlDBHelper extends DatabaseHelper {
	public static final String TAG = "ExhibitorIndustryDtlDBHelper";
	public static final String DBTableBame = "ExhibitorIndustryDtl";
	public static final String strBaseSQL = "SELECT * FROM ExhibitorIndustryDtl WHERE 1=1";

	public ExhibitorIndustryDtlDBHelper(Context context) {
		super(context);
	}

	public List<clsExhibitorIndustryDtl> getExhibitorIndustryDtlList(String pCompanyID) {
		List<clsExhibitorIndustryDtl> ExhibitorIndustryDtls = null;
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			ExhibitorIndustryDtls = getExhibitorIndustryDtlList(pCompanyID, db);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return ExhibitorIndustryDtls;
	}

	public List<clsExhibitorIndustryDtl> getExhibitorIndustryDtlList(String pCompanyID, SQLiteDatabase db) {
		List<clsExhibitorIndustryDtl> ExhibitorIndustryDtls = new ArrayList<clsExhibitorIndustryDtl>();

		String strSql = strBaseSQL + " and CompanyID='" + pCompanyID + "' ";
		Cursor cursor = db.rawQuery(strSql, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				ExhibitorIndustryDtls.add(new clsExhibitorIndustryDtl(cursor));
			}
		}
		cursor.close();
		return ExhibitorIndustryDtls;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strCompanyID, strIndustryID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strCompanyID = SoapParseUtils.GetValue(soapObject, "CompanyID");
				strIndustryID = SoapParseUtils.GetValue(soapObject, "IndustryID");

				String sql = strBaseSQL + " and CompanyID='" + strCompanyID + "' ";
				sql += " and IndustryID='" + strIndustryID + "' ";
				cursor = db.rawQuery(sql, null);
				if (cursor != null && !cursor.moveToFirst() && !IsDelete) {
					result = InsertBySoapObject(soapObject, db);
				} else if (cursor != null && cursor.moveToFirst()) {
					if (!IsDelete) {
						result = UpdateBySoapObject(soapObject, db);
					} else {
						Delete(strCompanyID, strIndustryID, db);
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
				FillExhibitorIndustryDtlBySoapObject(soapObject, cv);
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
		if (soapObject != null && db != null) {
			try {
				ContentValues cv = new ContentValues();
				String pCompanyID = SoapParseUtils.GetValue(soapObject, "CompanyID");
				String pIndustryID = SoapParseUtils.GetValue(soapObject, "IndustryID");
				FillExhibitorIndustryDtlBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "CompanyID ='" + pCompanyID + "' and IndustryID ='" + pIndustryID + "'", null) > 0;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public boolean Delete(String pCompanyID, String pIndustryID, SQLiteDatabase db) {
		boolean result = true;
		try {
			if (!TextUtils.isEmpty(pCompanyID) && !TextUtils.isEmpty(pIndustryID)) {
				db.execSQL("delete from " + DBTableBame + " where CompanyID ='" + pCompanyID + "' and IndustryID ='" + pIndustryID + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillExhibitorIndustryDtlBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("CompanyID", SoapParseUtils.GetValue(soapObject, "CompanyID"));
		cv.put("IndustryID", SoapParseUtils.GetValue(soapObject, "IndustryID"));
		cv.put("CreateDateTime", SoapParseUtils.GetValue(soapObject, "CreateDateTime"));
		cv.put("UpdateDateTime", SoapParseUtils.GetValue(soapObject, "UpdateDateTime"));
		cv.put("RecordTimeStamp", SoapParseUtils.GetValue(soapObject, "RecordTimeStamp"));
	}
}
