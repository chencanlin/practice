package com.org.ccl.practicetwo.database.litepal;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;

import java.util.List;

/**
 * Created by ccl on 2017/10/24.
 */

public class LitePalTestActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal_test);
        init();
    }

    private void init() {
        initView();
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
        switch (v.getId()) {
            case R.id.tv_create_table_book:
                Book book = new Book();
                book.setId(1);
                book.setName("疾风回旋曲");
                book.setAuthor("东野圭吾");
                book.setPages(600);
                book.setPrice(1000f);
                book.save();
                List<Book> all = Book.findAll(Book.class);
                Toast.makeText(this, all == null || all.size() == 0 ? "insert failed" :all.get(0).toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_create_table_category:

                break;
            case R.id.tv_insert:
                break;
            case R.id.tv_delete:
                Book.deleteAll(Book.class, "pages=?", "600");
                break;
            case R.id.tv_update:
                Cursor query = getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null,null);
                while (query.moveToNext()){
                    String string = query.getString(0);
                }
                break;
            case R.id.tv_query:
                List<Book> books = Book.select("name", "price", "id").find(Book.class);
                String result = "";
                for (Book item: books) {
                    result+=item.toString();
                }
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
