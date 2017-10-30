package com.org.ccl.practicetwo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.org.ccl.practicetwo", appContext.getPackageName());
    }

    @Test
    public void testGetBitmap(){
        Context context = InstrumentationRegistry.getTargetContext();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image);
        assertTrue(bitmap != null);
    }
}
