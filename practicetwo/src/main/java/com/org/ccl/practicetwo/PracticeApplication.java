package com.org.ccl.practicetwo;

import android.app.Application;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.litepal.LitePal;

/**
 * Created by ccl on 2017/10/18.
 */

public class PracticeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);

        Toast.makeText(this, "PracticeApplication", Toast.LENGTH_SHORT).show();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}
