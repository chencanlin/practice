// IMyAidlInterface.aidl
package com.org.ccl.practice.aidl;

// Declare any non-default types here with import statements
import com.org.ccl.practice.aidl.Book;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            Book getBook();
}
