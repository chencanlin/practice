package com.org.ccl.practicetwo.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.org.ccl.practicetwo.database.litepal.Book;

import org.litepal.tablemanager.Connector;

/**
 * Created by ccl on 2017/10/25.
 */

public class ProviderTest extends ContentProvider {
    private static final String TAG = "ProviderTest";

    private static final int TABLE_ONE_DIR = 0;
    private static final int TABLE_ONE_ITEM = 1;
    private static final int TABLE_TWO_DIR = 2;
    private static final int TABLE_TWO_ITEM = 3;


    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.org.ccl.practicetwo.contentprovider", "table1", TABLE_ONE_DIR);
        uriMatcher.addURI("com.org.ccl.practicetwo.contentprovider", "table1/#", TABLE_ONE_ITEM);
        uriMatcher.addURI("com.org.ccl.practicetwo.contentprovider", "table2", TABLE_TWO_DIR);
        uriMatcher.addURI("com.org.ccl.practicetwo.contentprovider", "table2/#", TABLE_TWO_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TABLE_ONE_DIR:
                // TODO 查询table1中所有数据
                Log.i(TAG, "TABLE_ONE_DIR");
                Book book = new Book();
                book.setId(1);
                book.setName("疾风回旋曲");
                book.setAuthor("东野圭吾");
                book.setPages(600);
                book.setPrice(1000f);
                book.save();
                SQLiteDatabase writableDatabase = Connector.getWritableDatabase();
                return writableDatabase.query("Book",null,null,null,null,null,null,null);
            case TABLE_ONE_ITEM:
                // TODO 查询table1中单条数据
                Log.i(TAG, "TABLE_ONE_ITEM");
                SQLiteDatabase writableDatabase1 = Connector.getWritableDatabase();
                String id = uri.getPathSegments().get(1);
                return writableDatabase1.query("Book", null, "id=?", new String[]{id}, null, null, null);
            case TABLE_TWO_DIR:
                // TODO 查询table2中所有数据
                Log.i(TAG, "TABLE_TWO_DIR");
                break;
            case TABLE_TWO_ITEM:
                // TODO 查询table2中单条数据
                Log.i(TAG, "TABLE_TWO_ITEM");
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE_ONE_DIR:
                return "vnd.android.cursor.dir/vnd.com.org.ccl.practicetwo.contentprovider.table1";
            case TABLE_ONE_ITEM:
                return "vnd.android.cursor.item/vnd.com.org.ccl.practicetwo.contentprovider.table1";
            case TABLE_TWO_DIR:
                return "vnd.android.cursor.dir/vnd.com.org.ccl.practicetwo.contentprovider.table2";
            case TABLE_TWO_ITEM:
                return "vnd.android.cursor.item/vnd.com.org.ccl.practicetwo.contentprovider.table2";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
