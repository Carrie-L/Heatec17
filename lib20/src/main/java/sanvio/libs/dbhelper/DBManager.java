package sanvio.libs.dbhelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sanvio.libs.util.FileUtils;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DBManager {
	public static final String TAG = "DBManager";
	private static final int BUFFER_SIZE = 400000;

	public static final String DB_NAME = DatabaseHelper.DB_NAME; // 保存的数据库文件名

	public static String PACKAGE_NAME = "";

	public static String DB_PATH = "";// 在手机里存放数据库的位置

	private SQLiteDatabase database;
	private Context context;

	public DBManager(Context context) {
		this.context = context;
		PACKAGE_NAME = context.getPackageName();
		DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases";

		// PackageInfo info;
		// try {
		// info =
		// context.getPackageManager().getPackageInfo(context.getPackageName(),
		// 0);
		// // 当前应用的版本名称
		// String versionName = info.versionName;
		// // 当前版本的版本号
		// int versionCode = info.versionCode;
		// // 当前版本的包名
		// String packageNames = info.packageName;
		// } catch (NameNotFoundException e) {
		// e.printStackTrace();
		// }
		Log.v(TAG, "PACKAGE_NAME: " + PACKAGE_NAME);
		Log.v(TAG, "DB_NAME: " + DB_NAME);
		Log.v(TAG, "DB_PATH: " + DB_PATH);
	}

	public void openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}

	private SQLiteDatabase openDatabase(String dbfile) {
		Log.v(TAG, "Copy db file path:" + dbfile);
		try {
			File dir = new File(DB_PATH);
			if (!dir.exists())
				dir.mkdir();
			if (!(new File(dbfile).exists())) {
				// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				InputStream is = this.context.getResources().openRawResource(DatabaseHelper.DB_RAW_ID); // 欲导入的数据库
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				Log.v(TAG, "Copy db file Success ！");
			}
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
			return db;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void copyDBToSdcard(Context c) {
		try {
			File dir = new File(DB_PATH);
			if (!dir.exists())
				dir.mkdir();
			String dbfile = c.getDir("Heatec",Context.MODE_PRIVATE) + "/DB_copy.db";
			File copyFile = new File(DB_PATH + "/" + DB_NAME);
			File targetFile = new File(dbfile);
			if (copyFile.exists()) {
				if ((targetFile.exists())) {
					targetFile.delete();
				}
				InputStream is = new FileInputStream(copyFile);
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			Log.e(TAG, "Copy DB To Sdcard:" + dbfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeDatabase() {
		if (this.database != null)
			this.database.close();
	}

	public static Boolean copyOthenDB(String pDbName, String strSdCardDBPath) {
		try {
			File dir = new File(DB_PATH);
			if (!dir.exists())
				dir.mkdir();
			String dbfile = DB_PATH + "/" + pDbName;
			File copyFile = new File(strSdCardDBPath);
			File targetFile = new File(dbfile);
			if (copyFile.exists()) {
				if ((targetFile.exists())) {
					targetFile.delete();
				}
				InputStream is = new FileInputStream(copyFile);
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				Log.v(TAG, "Copy " + pDbName + " Success ！");
				Log.v(TAG, "Db from :" + strSdCardDBPath);
				copyFile.delete();
				return true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void checkDBVersion(float pCurrentDBVersion, int DBRawID) {
		String dbfile = DB_PATH + "/" + DB_NAME;
		Log.v("DBManager", "Copy db file path:" + dbfile);
		File dir = new File(DB_PATH);
		if (!dir.exists()) {
			dir.mkdir();
			copyDB(dbfile, DBRawID);
		} else {
			if (!(new File(dbfile).exists())) {
				// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				copyDB(dbfile, DBRawID);
			} else {
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
				boolean dbversionIsExit = TableIsExist(db, "dbversion");
				if (!dbversionIsExit) { // 如果dbversion表不存在，刪除DB。重新copyDB
					UpdateDBVersion(dbfile, DBRawID);
				} else { // 如果dbversion表存在，当前version与更新version比较
					float dbVersion = getDBVersion(db);
					if (dbVersion < pCurrentDBVersion) { // 旧版本小于新版本，必须更新
						UpdateDBVersion(dbfile, DBRawID);
					}
				}
				db.close();
			}
		}
		this.database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);

	}

	public boolean TableIsExist(SQLiteDatabase db, String tabName) {
		boolean result = false;
		if (tabName != null) {
			Cursor cursor = null;
			try {
				String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
				cursor = db.rawQuery(sql, null);
				if (cursor.moveToNext()) {
					int count = cursor.getInt(0);
					if (count > 0) {
						result = true;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private void copyDB(String dbfile, int DBRawID) {
		try {
			InputStream is = this.context.getResources().openRawResource(DBRawID); // 欲导入的数据库
			FileOutputStream fos = new FileOutputStream(dbfile);
			byte[] buffer = new byte[BUFFER_SIZE];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
			Log.v("DBManager", "Copy db file Success ！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void UpdateDBVersion(String dbfile, int DBRawID) {
		boolean bDeleteDatabase = context.deleteDatabase(DB_NAME);
		if (bDeleteDatabase) {
			copyDB(dbfile, DBRawID);
		}
	}

	private float getDBVersion(SQLiteDatabase db) {
		float dbVersion = 2.0f;
		String strSql = "SELECT tableversion FROM dbversion";
		Cursor cursor = db.rawQuery(strSql, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				dbVersion = cursor.getFloat(cursor.getColumnIndex("tableversion"));
			}
		}
		cursor.close();
		return dbVersion;
	}


}