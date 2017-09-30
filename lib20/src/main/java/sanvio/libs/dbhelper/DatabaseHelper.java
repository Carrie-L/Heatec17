package sanvio.libs.dbhelper;

import java.util.HashMap;
import java.util.Iterator;

import sanvio.libs.dbclass.clsUpdateDateTime;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	protected static final String TAG = "DatabaseHelper";

	protected static String DB_NAME = "";
	protected static int DB_RAW_ID = 0;

	/**
	 * 必须设置
	 * 
	 * @param dbName
	 * @param dbRawID
	 */
	public static void setOptions(String dbName, int dbRawID) {
		DB_NAME = dbName;
		DB_RAW_ID = dbRawID;
	}

	protected Context mContext;

	/*
	 * [UpdateDate]
	 */
	protected static final String TAB_UPDATEDATE = "UpdateDate";
	protected static final String UPDATEDATE_TYPE = "ModuleID";
	protected static final String UPDATEDATE_UPDATEDATE = "LastUpdateDate";

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		mContext = context;
		Log.i("myDebug", "DatabaseHelper(context, name, factory, version)");
	}

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		mContext = context;
		if (TextUtils.isEmpty(DB_NAME)) {
			throw new IllegalStateException("DB_NAME is Empty");
		}
		if (DB_RAW_ID == 0) {
			throw new IllegalStateException("DB_RAW_ID is Empty");
		}
		Log.i("myDebug", "DatabaseHelper(context)");
	}

	public Context getContext() {
		return mContext;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("myDebug", "onCreate(db)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i("myDebug", "onUpgrade(db,int,int)");
	}

	public String getUpdateDate(String pType) {
		String strUpdateDateTime = "";
		SQLiteDatabase db = this.getReadableDatabase();
		String strSql = "";
		strSql = "select " + UPDATEDATE_UPDATEDATE + "  from " + TAB_UPDATEDATE
				+ " where " + UPDATEDATE_TYPE + "='" + pType + "'";
		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor != null && cursor.moveToFirst()) {
				strUpdateDateTime = cursor.getString(0);
			} else {
				strUpdateDateTime = "";
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strUpdateDateTime == null ? "" : strUpdateDateTime;
	}

	public void getUpdateDate(
			HashMap<String, clsUpdateDateTime> ocolUpdateDateTimes) {
		if (ocolUpdateDateTimes.size() == 0) {
			return;
		}
		SQLiteDatabase db = this.getReadableDatabase();
		String strSql = "", strFilterString = "", StrType;
		clsUpdateDateTime oclsUpdateDateTime;

		for (Iterator<clsUpdateDateTime> iterator = ocolUpdateDateTimes
				.values().iterator(); iterator.hasNext();) {
			oclsUpdateDateTime = iterator.next();
			if (strFilterString == "") {
				strFilterString = "'" + oclsUpdateDateTime.getUpdateType()
						+ "'";
			} else {
				strFilterString = strFilterString + ",'"
						+ oclsUpdateDateTime.getUpdateType() + "'";
			}

		}
		strSql = "select *  from " + TAB_UPDATEDATE + " where ModuleID in ("
				+ strFilterString + ")";
		try {
			Cursor cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				StrType = cursor.getString(cursor.getColumnIndex("ModuleID"));
				oclsUpdateDateTime = ocolUpdateDateTimes.get(StrType);
				oclsUpdateDateTime.setUpdateDateTime(cursor.getString(cursor
						.getColumnIndex("LastUpdateDate")));
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public String getUpdateDate(String pTyep, SQLiteDatabase db) {
		String strUpdateDateTime = null;
		String strSql = null;
		if (pTyep.equals("") && db == null)
			return "";
		strSql = "select LastUpdateDate  from " + TAB_UPDATEDATE
				+ " where ModuleID='" + pTyep + "'";
		try {
			Cursor cursor = db.rawQuery(strSql, null);
			if (cursor != null && cursor.moveToFirst()) {
				strUpdateDateTime = cursor.getString(0);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strUpdateDateTime == null)
			strUpdateDateTime = "";
		Log.d("getUpdateDate", ">>>>>>Type=" + pTyep + "   Date="
				+ strUpdateDateTime);
		return strUpdateDateTime;
	}

	public Boolean setUpdateDate(String pTyep, String pUpdateDateTime) {
		boolean result = false;
		if (!pTyep.equals("") && !pUpdateDateTime.equals("")) {
			SQLiteDatabase db = this.getWritableDatabase();
			setUpdateDate(db, pTyep, pUpdateDateTime);
			db.close();
		}
		return result;
	}

	public Boolean setUpdateDate(SQLiteDatabase db, String pTyep,
			String pUpdateDateTime) {
		boolean result = false;
		if (!pTyep.equals("") && !pUpdateDateTime.equals("")) {
			ContentValues cv = new ContentValues();

			cv.put(UPDATEDATE_UPDATEDATE, pUpdateDateTime);
			String strSql = "";
			strSql = "select count(*) from " + TAB_UPDATEDATE + " where "
					+ UPDATEDATE_TYPE + "='" + pTyep + "'";
			Cursor cursor = db.rawQuery(strSql, null);
			cursor.moveToFirst();
			int intCount = cursor.getInt(0);
			if (intCount > 0) {
				if (db.update(TAB_UPDATEDATE, cv, UPDATEDATE_TYPE + "=? ",
						new String[] { pTyep }) > 0)
					result = true;
			} else {
				cv.put(UPDATEDATE_TYPE, pTyep);
				if (db.insert(TAB_UPDATEDATE, "", cv) > 0)
					result = true;
			}
			cursor.close();
		}
		return result;
	}
}
