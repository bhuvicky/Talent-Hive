package com.bhuvanesh.talenthive.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager implements DBQuery {

    private static final String DATABASE_NAME = "Talent Hive DB";
    private static final int DATABASE_VERSION = 1;
    private SLDBHelper dbHelper;
    private SQLiteDatabase mSqldb;

    public DBManager(Context context) {
        dbHelper = SLDBHelper.getHelper(context);
    }

    public void connect() {
        if (mSqldb == null) {
            mSqldb = dbHelper.getWritableDatabase();
        }
    }

    public SQLiteDatabase getDBInstance() {
        if (mSqldb == null) {
            mSqldb = dbHelper.getWritableDatabase();
        }
        return mSqldb;
    }

    public int replace(String tableName, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = getDBInstance();
        sqLiteDatabase.beginTransaction();
        int rowCount = -1;
        try {
            sqLiteDatabase.replace(tableName, null, values);
        } finally {
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            rowCount = values.size();
        }
        return rowCount;
    }

    public Cursor select(String query) {
        return select(query, null);
    }

    public Cursor select(String query, String[] selectionArgs) {
        return getDBInstance().rawQuery(query, selectionArgs);
    }

    private static class SLDBHelper extends SQLiteOpenHelper {

        private static SLDBHelper dbHelper;

        private SLDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public static synchronized SLDBHelper getHelper(Context context) {
            if (dbHelper == null) {
                dbHelper = new SLDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
                System.out.println("db helper null");
            } else {
                System.out.println("db helper not null");
            }
            return dbHelper;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_PROFILE_DETAILS);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (!db.isReadOnly()) {
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
