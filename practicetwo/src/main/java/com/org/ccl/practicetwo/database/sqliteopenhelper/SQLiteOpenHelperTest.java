package com.org.ccl.practicetwo.database.sqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ccl on 2017/10/24.
 */

public class SQLiteOpenHelperTest extends SQLiteOpenHelper {





    private static final String SQL_CREATE_TABLE_BOOK = "create table Book ("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "price real,"
            + "author text,"
            + "pages integer)";

    private static final String SQL_CREATE_TABLE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement,"
            + "category_name,"
            + "category_mode)";
    private Context mContext;
    private Context mContext1;

    public SQLiteOpenHelperTest(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BOOK);
        db.execSQL(SQL_CREATE_TABLE_CATEGORY);
        Toast.makeText(mContext, "Create Succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
