package com.adsale.HEATEC.database;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.adsale.HEATEC.database.model.clsNewsLink;

import sanvio.libs.dbhelper.DatabaseHelper;
import sanvio.libs.util.SoapParseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class NewsLinkDBHelper extends DatabaseHelper {
	public static final String TAG = "NewsLinkDBHelper";

	public static final String DBTableBame = "NewsLink";
	public static final String strBaseSQL = "SELECT * FROM NewsLink WHERE 1=1";

	public NewsLinkDBHelper(Context context) {
		super(context);
	}

	public List<clsNewsLink> getNewsLinkList(String pNewsID) {
		List<clsNewsLink> ocolNewsLinks = new ArrayList<clsNewsLink>();
		String strSql = strBaseSQL;
		if (!TextUtils.isEmpty(pNewsID)) {
			strSql += " and NewsID='" + pNewsID + "'";
		}
		strSql += " order by SEQ ";
		ocolNewsLinks = Query(strSql);
		return ocolNewsLinks;
	}

	public List<clsNewsLink> getNewsImageList(String pNewsID) {
		List<clsNewsLink> ocolNewsLinks = new ArrayList<clsNewsLink>();
		String strSql = strBaseSQL;
		if (!TextUtils.isEmpty(pNewsID)) {
			strSql += " and NewsID='" + pNewsID + "' and Photo <>''";
		}
		strSql += " order by SEQ ";
		ocolNewsLinks = Query(strSql);
		return ocolNewsLinks;
	}

	public clsNewsLink getNewsLink(String pLinkID) {
		clsNewsLink oclsNewsLink = new clsNewsLink();
		List<clsNewsLink> ocolNewsLinks = null;
		String strSql = strBaseSQL;
		strSql += String.format(" AND pLinkID ='%s' ", pLinkID);
		ocolNewsLinks = Query(strSql);
		if (ocolNewsLinks != null && ocolNewsLinks.size() > 0) {
			oclsNewsLink = ocolNewsLinks.get(0);
		}
		return oclsNewsLink;
	}

	public List<clsNewsLink> Query(String pSQL) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<clsNewsLink> ocolNewsLink = new ArrayList<clsNewsLink>();
		try {
			Cursor cursor = db.rawQuery(pSQL, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					ocolNewsLink.add(new clsNewsLink(cursor));
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

		return ocolNewsLink;
	}

	public boolean modifyBySoapObject(SoapObject soapObject, SQLiteDatabase db) {
		Cursor cursor = null;
		Boolean result = true, IsDelete = false;

		String strID;
		if (soapObject != null) {
			try {
				IsDelete = Boolean.valueOf(SoapParseUtils.GetValue(soapObject, "IsDelete"));
				strID = SoapParseUtils.GetValue(soapObject, "LinkID");

				cursor = db.rawQuery(strBaseSQL + " and LinkID='" + strID + "'", null);
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
				FillNewsLinkBySoapObject(soapObject, cv);
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
				strID = SoapParseUtils.GetValue(soapObject, "LinkID");
				FillNewsLinkBySoapObject(soapObject, cv);
				result = db.update(DBTableBame, cv, "LinkID=?", new String[] { strID }) > 0;
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
				db.execSQL("delete from " + DBTableBame + " where LinkID ='" + id + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void FillNewsLinkBySoapObject(SoapObject soapObject, ContentValues cv) {
		cv.put("LinkID", SoapParseUtils.GetValue(soapObject, "LinkID"));
		cv.put("NewsID", SoapParseUtils.GetValue(soapObject, "NewsID"));
		cv.put("Photo", SoapParseUtils.GetValue(soapObject, "Photo"));
		cv.put("Title", SoapParseUtils.GetValue(soapObject, "Title"));
		cv.put("Link", SoapParseUtils.GetValue(soapObject, "Link"));
		cv.put("SEQ", SoapParseUtils.GetValue(soapObject, "SEQ"));
		cv.put("CreateDateTime", SoapParseUtils.GetValue(soapObject, "CreateDateTime"));
		cv.put("UpdateDateTime", SoapParseUtils.GetValue(soapObject, "UpdateDateTime"));
		cv.put("RecordTimeStamp", SoapParseUtils.GetValue(soapObject, "RecordTimeStamp"));
	}
}
