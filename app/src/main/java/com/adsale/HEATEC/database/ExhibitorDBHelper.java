package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.clsExhibitor;
import com.adsale.HEATEC.database.model.clsIndustry;
import com.adsale.HEATEC.database.model.clsSection;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExhibitorDBHelper extends DatabaseHelper {
	public static final String TAG = "ExhibitorDBHelper";

	public static final String DBTableBame = "Exhibitor";
	public static final String strBaseSQL = "SELECT * FROM Exhibitor WHERE 1=1";

	public ExhibitorDBHelper(Context context) {
		super(context);
	}

	// public List<clsExhibitor> getExhibitorList(int planguage) {
	// List<clsExhibitor> Exhibitors = null;
	// SQLiteDatabase db = this.getReadableDatabase();
	// Exhibitors = getExhibitorList(planguage, db);
	// db.close();
	// return Exhibitors;
	// }
	//
	// public List<clsExhibitor> getExhibitorList(int planguage, SQLiteDatabase
	// db) {
	// List<clsExhibitor> Exhibitors = new ArrayList<clsExhibitor>();
	// String strSql = strBaseSQL;
	// if (planguage == 0) {
	// strSql += " order by cast(SortTW as INT) ";
	// } else if (planguage == 2) {
	// strSql += " order by SortCN ";
	// } else if (planguage == 1) {
	// strSql += " order by SortEN ";
	// }
	// Cursor cursor = db.rawQuery(strSql, null);
	// if (cursor != null && cursor.getCount() > 0) {
	// while (cursor.moveToNext()) {
	// Exhibitors.add(new clsExhibitor(cursor));
	// }
	// }
	// cursor.close();
	// return Exhibitors;
	// }
	public List<clsExhibitor> SearchExhibitorList(int planguage, String pIndustryID, String pCountryID, String pFloor, boolean IsFavourite) {
		List<clsExhibitor> ocolExhibitors = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String strSql = strBaseSQL;
		if (pIndustryID != null && !pIndustryID.equals("")) {
			strSql += " and CompanyID in (SELECT CompanyID FROM ExhibitorIndustryDtl WHERE IndustryID ='" + pIndustryID + "' GROUP BY CompanyID)";

		}

		if (pCountryID != null && !pCountryID.equals("")) {
			strSql += " and CountryID='" + pCountryID + "'";
		}

		if (pFloor != null && !pFloor.equals("")) {
			strSql += " and Floor='" + pFloor + "'";
		}

		if (IsFavourite) {
			strSql += " and IsFavourite =1 ";
		}

		if (planguage == 0) {
			strSql += " order by cast(SortTW as INT) ";
		} else if (planguage == 2) {
			strSql += " order by SortCN ";
		} else if (planguage == 1) {
			strSql += " order by SortEN ";
		}

		try {
			Cursor cursor = db.rawQuery(strSql, null);
			ocolExhibitors = new ArrayList<clsExhibitor>();
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					ocolExhibitors.add(new clsExhibitor(cursor));
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
		return ocolExhibitors;
	}

	public clsExhibitor getExhibitor(String pCompanyID) {
		clsExhibitor Exhibitor = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String strSql = strBaseSQL + " and [CompanyID] = '" + pCompanyID + "'";
		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor.moveToFirst()) {
				Exhibitor = new clsExhibitor(cursor);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return Exhibitor;
	}

	public List<clsIndustry> getExhibitorIndustryList(String pCompanyID) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<clsIndustry> list = new ArrayList<clsIndustry>();
		String strSql = "SELECT [ExhibitorIndustryDtl].*,  [Industry].* FROM [ExhibitorIndustryDtl]"
				+ " INNER JOIN [Industry] ON [ExhibitorIndustryDtl].[IndustryID] =   [Industry].[IndustryID] " + "WHERE [ExhibitorIndustryDtl].[CompanyID] = '"
				+ pCompanyID + "'";

		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					list.add(new clsIndustry(cursor));
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
		return list;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strID = SoapParseUtils.GetValue(soapObject, "CompanyID");

				cursor = db.rawQuery(strBaseSQL + " and CompanyID='" + strID + "'", null);
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
				FillExhibitorBySoapObject(soapObject, cv);
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
				strID = SoapParseUtils.GetValue(soapObject, "CompanyID");
				FillExhibitorBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "CompanyID=?", new String[] { strID }) > 0;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public boolean clearFavourite() {
		boolean result = false;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("update " + DBTableBame + " set IsFavourite=0");
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}

		return result;
	}

	public boolean Delete(String id, SQLiteDatabase db) {
		boolean result = true;
		try {
			if (id != null) {
				db.execSQL("delete from " + DBTableBame + " where CompanyID ='" + id + "'");

			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillExhibitorBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("CompanyID", SoapParseUtils.GetValue(soapObject, "CompanyID"));
		cv.put("CompanyNameTW", SoapParseUtils.GetValue(soapObject, "CompanyNameTW"));
		cv.put("DescriptionTW", SoapParseUtils.GetValue(soapObject, "DescriptionTW"));
		cv.put("AddressTW", SoapParseUtils.GetValue(soapObject, "AddressTW"));
		cv.put("SortTW", SoapParseUtils.GetValue(soapObject, "SortTW"));
		cv.put("CompanyNameCN", SoapParseUtils.GetValue(soapObject, "CompanyNameCN"));
		cv.put("DescriptionCN", SoapParseUtils.GetValue(soapObject, "DescriptionCN"));
		cv.put("AddressCN", SoapParseUtils.GetValue(soapObject, "AddressCN"));
		cv.put("SortCN", SoapParseUtils.GetValue(soapObject, "SortCN"));
		cv.put("CompanyNameEN", SoapParseUtils.GetValue(soapObject, "CompanyNameEN"));
		cv.put("DescriptionEN", SoapParseUtils.GetValue(soapObject, "DescriptionEN"));
		cv.put("AddressEN", SoapParseUtils.GetValue(soapObject, "AddressEN"));
		cv.put("SortEN", SoapParseUtils.GetValue(soapObject, "SortEN"));
		cv.put("ExhibitorNO", SoapParseUtils.GetValue(soapObject, "ExhibitorNO"));
		cv.put("CountryID", SoapParseUtils.GetValue(soapObject, "CountryID"));
		cv.put("Logo", SoapParseUtils.GetValue(soapObject, "Logo"));
		cv.put("Tel", SoapParseUtils.GetValue(soapObject, "Tel"));
		cv.put("Tel1", SoapParseUtils.GetValue(soapObject, "Tel1"));
		cv.put("Fax", SoapParseUtils.GetValue(soapObject, "Fax"));
		cv.put("Email", SoapParseUtils.GetValue(soapObject, "Email"));
		cv.put("Website", SoapParseUtils.GetValue(soapObject, "Website"));
		cv.put("Longitude", SoapParseUtils.GetValue(soapObject, "Longitude"));
		cv.put("Latitude", SoapParseUtils.GetValue(soapObject, "Latitude"));
		cv.put("Location_X", SoapParseUtils.GetValue(soapObject, "Location_X"));
		cv.put("Location_Y", SoapParseUtils.GetValue(soapObject, "Location_Y"));
		cv.put("Location_W", SoapParseUtils.GetValue(soapObject, "Location_W"));
		cv.put("Location_H", SoapParseUtils.GetValue(soapObject, "Location_H"));
		cv.put("SEQ", SoapParseUtils.GetValue(soapObject, "SEQ"));
		cv.put("CreateDateTime", SoapParseUtils.GetValue(soapObject, "CreateDateTime"));
		cv.put("UpdateDateTime", SoapParseUtils.GetValue(soapObject, "UpdateDateTime"));
		cv.put("RecordTimeStamp", SoapParseUtils.GetValue(soapObject, "RecordTimeStamp"));
		cv.put("ContactTW", SoapParseUtils.GetValue(soapObject, "ContactTW"));
		cv.put("TitleTW", SoapParseUtils.GetValue(soapObject, "TitleTW"));
		cv.put("ContactCN", SoapParseUtils.GetValue(soapObject, "ContactCN"));
		cv.put("TitleCN", SoapParseUtils.GetValue(soapObject, "TitleCN"));
		cv.put("ContactEN", SoapParseUtils.GetValue(soapObject, "ContactEN"));
		cv.put("TitleEN", SoapParseUtils.GetValue(soapObject, "TitleEN"));
		cv.put("IsFavourite", SoapParseUtils.GetValue(soapObject, "IsFavourite"));
		cv.put("Note", SoapParseUtils.GetValue(soapObject, "Note"));
		cv.put("Floor", SoapParseUtils.GetValue(soapObject, "Floor"));

	}

	public List<clsSection> getIndexArray(int planguage, String pIndustryID, String pCountryID, String pFloor, boolean IsFavourite) {
		List<clsSection> IndexArray = new ArrayList<clsSection>();
		int intSort;
		String strSql, strOrder, strCondition = "", strFieldName = "";

		SQLiteDatabase db = this.getReadableDatabase();
		if (planguage == 0) {
			strSql = "Select SortTW from Exhibitor";
			strOrder = " group by SortTW order by cast(SortTW as INT)";
			// strSql =
			// "Select SortTW from Exhibitor group by SortTW order by cast(SortTW as INT) ";
			strFieldName = "SortTW";
		} else if (planguage == 2) {
			strSql = "Select SortCN from Exhibitor";
			strOrder = " group by SortCN order by SortCN";
			strFieldName = "SortCN";
		} else if (planguage == 1) {
			strSql = "Select SortEN from Exhibitor";
			strOrder = " group by SortEN order by SortEN ";
			strFieldName = "SortEN";
		} else {
			return null;
		}

		if (pIndustryID != null && !pIndustryID.equals("")) {
			strCondition = " CompanyID in (SELECT CompanyID FROM ExhibitorIndustryDtl WHERE IndustryID = '" + pIndustryID + "' GROUP BY CompanyID)";

		}
		if (pCountryID != null && !pCountryID.equals("")) {
			strCondition += (strCondition.equals("") ? "" : " and ") + " CountryID='" + pCountryID + "'";
		}

		if (pFloor != null && !pFloor.equals("")) {
			strCondition += (strCondition.equals("") ? "" : " and ") + " Floor='" + pFloor + "'";
		}

		if (IsFavourite) {
			strCondition += " IsFavourite =1 ";
		}

		strSql = strSql + (strCondition.equals("") ? "" : " where " + strCondition) + strOrder;

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

	public boolean Update(clsExhibitor pExhibitor) {
		boolean result = false;
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			ContentValues cv = new ContentValues();
			FillExhibitor(pExhibitor, cv);
			result = db.update(DBTableBame, cv, "CompanyID=?", new String[] { pExhibitor.getCompanyID() }) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return result;
	}

	private void FillExhibitor(clsExhibitor pExhibitor, ContentValues cv) {
		cv.put("IsFavourite", pExhibitor.getIsFavourite());
		cv.put("Note", pExhibitor.getNote());
	}
}
