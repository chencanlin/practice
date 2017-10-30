package com.org.ccl.practicetwo.database.sqliteopenhelper;

/**
 * Created by ccl on 2017/10/24.
 */

public class Book {
    public String name;
    public String author;
    public float price;
    public int pages;
    public int id;

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", pages=" + pages +
                ", id=" + id +
                '}';
    }
}
