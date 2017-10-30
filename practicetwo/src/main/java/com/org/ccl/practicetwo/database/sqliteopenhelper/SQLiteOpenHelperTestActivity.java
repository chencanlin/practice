package com.org.ccl.practicetwo.database.sqliteopenhelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ccl on 2017/10/24.
 */

public class SQLiteOpenHelperTestActivity extends Activity implements View.OnClickListener {

    private static final String DB_NAME = "test_one.db";
    private static final String TABLE_NAME_BOOK = "Book";
    private static final String TABLE_NAME_CATEGORY = "Category";
    private static final String[] names = {"解忧杂货店", "白夜行", "幻夜", "嫌疑人x的附身"};
    private static final Float[] price = {39.5f, 49.5f, 59.5f, 69.5f};
    private static final int[] pages = {430, 530, 630, 730};
    private SQLiteOpenHelperTest mSQLiteOpenHelperTest;
    private Random mRandom;
    private List<Book> mBookList = new ArrayList<>();
    private int mVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_open_helper_test);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        mRandom = new Random();
        SharedPreferences db_version = getSharedPreferences("db_version", MODE_PRIVATE);
        mVersion = db_version.getInt("version", 1);
        mSQLiteOpenHelperTest = new SQLiteOpenHelperTest(this, DB_NAME, null, mVersion);
    }

    private void initView() {
        findViewById(R.id.tv_create_table_book).setOnClickListener(this);
        findViewById(R.id.tv_create_table_category).setOnClickListener(this);
        findViewById(R.id.tv_insert).setOnClickListener(this);
        findViewById(R.id.tv_delete).setOnClickListener(this);
        findViewById(R.id.tv_update).setOnClickListener(this);
        findViewById(R.id.tv_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase writableDatabase = mSQLiteOpenHelperTest.getWritableDatabase();
        switch (v.getId()) {
            case R.id.tv_create_table_book:

                break;
            case R.id.tv_create_table_category:
                mVersion++;
                mSQLiteOpenHelperTest = new SQLiteOpenHelperTest(this, DB_NAME, null, mVersion);
                mSQLiteOpenHelperTest.getWritableDatabase();
                SharedPreferences db_version = getSharedPreferences("db_version", MODE_PRIVATE);
                SharedPreferences.Editor edit = db_version.edit();
                edit.putInt("version", mVersion);
                edit.apply();
                Toast.makeText(this, mVersion + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_insert:
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", names[mRandom.nextInt(4)]);
                contentValues.put("author", "东野圭吾");
                contentValues.put("price", price[mRandom.nextInt(4)]);
                contentValues.put("pages", pages[mRandom.nextInt(4)]);
                writableDatabase.insert(TABLE_NAME_BOOK, null, contentValues);
                Cursor insertCursor = writableDatabase.query(TABLE_NAME_BOOK, null, null, null, null, null, null);
                String result = queryAllData(insertCursor);
                Toast.makeText(SQLiteOpenHelperTestActivity.this, TextUtils.isEmpty(result) ? "insert failed" : result, Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_delete:
                writableDatabase.delete(TABLE_NAME_BOOK, "name=?", new String[]{"幻夜"});
                Cursor deleteCursor = writableDatabase.query(TABLE_NAME_BOOK, null, null, null, null, null, null);
                String deleteResult = queryAllData(deleteCursor);
                Toast.makeText(this, TextUtils.isEmpty(deleteResult) ? "delete succeeded" : deleteResult, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_update:
                ContentValues updateContentValues = new ContentValues();
                updateContentValues.put("pages", "1024");
                updateContentValues.put("price", "1024");
                writableDatabase.update(TABLE_NAME_BOOK, updateContentValues, "name=?", new String[]{"幻夜"});
                Cursor updateCursor = writableDatabase.query(TABLE_NAME_BOOK, null, null, null, null, null, null);
                String updateResult = queryAllData(updateCursor);
                Toast.makeText(this, TextUtils.isEmpty(updateResult) ? "update failed" : updateResult, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_query:
                Cursor queryCursor = writableDatabase.query(TABLE_NAME_BOOK, null, null, null, null, null, "price desc");
                String queryResult = queryAllData(queryCursor);
                Toast.makeText(this, TextUtils.isEmpty(queryResult) ? "query failed" : queryResult, Toast.LENGTH_SHORT).show();
                break;
        }
        writableDatabase.close();
    }


    private String queryAllData(Cursor cursor) {
        String result = "";
        if (cursor == null) {
            return null;
        }
        mBookList.clear();
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                book.name = name;
                book.author = author;
                book.price = price;
                book.pages = pages;
                book.id = id;
                mBookList.add(book);
            } while (cursor.moveToNext());
            for (Book item : mBookList) {
                result += item.toString();
            }
        }
        return result;
    }
}
