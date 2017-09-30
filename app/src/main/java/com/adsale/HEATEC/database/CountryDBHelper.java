package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.ICategory;
import com.adsale.HEATEC.database.model.clsCountry;
import com.adsale.HEATEC.database.model.clsMainIcon;
import com.adsale.HEATEC.database.model.clsSection;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CountryDBHelper extends DatabaseHelper {
	public static final String TAG = "CountryDBHelper";
	public static final String DBTableBame = "Country";
	public static final String strBaseSQL = "SELECT * FROM Country WHERE 1=1";

	public CountryDBHelper(Context context) {
		super(context);
	}

	public List<ICategory> getCountryList(int planguage) {
		List<ICategory> Countrys = null;
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			Countrys = getCountryList(planguage, db);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return Countrys;
	}

	public List<ICategory> getCountryList(int planguage, SQLiteDatabase db) {
		List<ICategory> Countrys = new ArrayList<ICategory>();
		String strSql = strBaseSQL;
		String strCondition = " and CountryID in (select DISTINCT CountryID from Exhibitor) ";
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
				Countrys.add(new clsCountry(cursor));
			}
		}
		cursor.close();
		return Countrys;
	}




	public boolean Delete(String id, SQLiteDatabase db) {
		boolean result = true;
		try {
			if (id != null) {
				db.execSQL("delete from " + DBTableBame + " where CountryID ='" + id + "'");

			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}


	public List<clsSection> getIndexArray(int planguage) {
		List<clsSection> IndexArray = new ArrayList<clsSection>();
		int intSort;
		String strSql = "", strFieldName = "";
		String strCondition = "", strOrder = "";
		SQLiteDatabase db = this.getReadableDatabase();
		strCondition = "";
		if (planguage == 0) {
			strSql = "Select SortTW from Country ";
			strOrder = "group by SortTW order by cast(SortTW as INT)";
			strFieldName = "SortTW";
		} else if (planguage == 2) {
			strSql = "Select SortCN from Country ";
			strOrder = "group by SortCN order by SortCN ";
			strFieldName = "SortCN";
		} else if (planguage == 1) {
			strSql = "Select SortEN from Country ";
			strOrder = "group by SortEN order by SortEN";
			strFieldName = "SortEN";
		}
		strCondition = "Where CountryID in (select DISTINCT CountryID from Exhibitor) ";
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
