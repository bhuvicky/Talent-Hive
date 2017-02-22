package com.bhuvanesh.talenthive.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DBManager implements DBQuery {

    private static final String DATABASE_NAME = "Talent Hive";
    private static final int DATABASE_VERSION = 1;
    private SLDBHelper dbHelper;
    private SQLiteDatabase mSqldb;

    public DBManager(Context context) {
        dbHelper = SLDBHelper.getHelper(context);
    }

//     Open the database
    public void connect() {
        if (mSqldb == null) {
            mSqldb = dbHelper.getWritableDatabase();
        }
    }

//    It returns the current database object.
    public SQLiteDatabase getDBInstance() {
        if (mSqldb == null) {
            mSqldb = dbHelper.getWritableDatabase();
        }
        return mSqldb;
    }

//    Query the table for the number of rows in the table.
    public long getTableRowCount(String tableName) {
        return DatabaseUtils.queryNumEntries(getDBInstance(), tableName);
    }

//    Prints the contents of a Cursor to System.out.
    public void printCursorInLog(Cursor cursor) {
        DatabaseUtils.dumpCursor(cursor);
    }

    //inserts a new seperate record if not exists, otherwise it will update an existing record
    public long replace(String tableName, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = getDBInstance();
        sqLiteDatabase.beginTransaction();
        long rowId = -1;
        try {
            //if successful, returns id of the row what we sent
            //if unsuccessful, return -1
            rowId = sqLiteDatabase.replace(tableName, null, values);
        } finally {
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }
        return rowId;
    }

    public long replaceBulk(String tableName, List<ContentValues> valuesList) {
        long rowId = -1;
        SQLiteDatabase sqLiteDatabase = getDBInstance();
        sqLiteDatabase.beginTransaction();
        try {
            for (ContentValues values : valuesList) {
                rowId = sqLiteDatabase.replace(tableName, null, values);
                System.out.println("replacing bulk: " + rowId);
            }
        } finally {
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }
        return rowId;
    }

    public Cursor select(String query) {
        return select(query, null);
    }

    public Cursor select(String query, String[] selectionArgs) {
        return getDBInstance().rawQuery(query, selectionArgs);
    }

    public void executeSQLQuery(String query) {
        getDBInstance().execSQL(query);
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
            db.execSQL(CREATE_TABLE_STORY);
            db.execSQL(CREATE_TABLE_STORY_FEED);
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
