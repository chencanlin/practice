package com.org.ccl.practicetwo.disklrucache;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.org.ccl.practicetwo.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ccl on 2017/8/24.
 */

public class DiskLruCacheDemo extends Activity {

    private static final String CACHE_DIR = "bitmap_obj";
    private static final String imgUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
    private ImageView mIv;
    private DiskLruCache mOpen;
    private TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup parent = findViewById(android.R.id.content);
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_disk_lru_cache_demo, parent, false);
        parent.addView(inflate);

        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File diskCacheDir = getDiskCacheDir(DiskLruCacheDemo.this, CACHE_DIR);
                int appVersion = getAppVersion(DiskLruCacheDemo.this);
                try {
                    mOpen = DiskLruCache.open(diskCacheDir, appVersion, 1, 1024 * 1024 * 10);
                    String key = hashKeyForDisk(imgUrl);
                    DiskLruCache.Editor edit = mOpen.edit(key);
                    if (edit != null) {
                        if (downloadUrlToStream(imgUrl, edit.newOutputStream(0))) {
                            edit.commit();
                        } else {
                            edit.abort();
                        }
                    }
                    mOpen.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIv = findViewById(R.id.iv);


        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOpen != null) {
                    try {
                        DiskLruCache.Snapshot snapshot = mOpen.get(hashKeyForDisk(imgUrl));
                        if (snapshot != null) {
                            InputStream inputStream = snapshot.getInputStream(0);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            mIv.setImageBitmap(bitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
                out = new BufferedOutputStream(outputStream, 8 * 1024);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                return true;
            }
            return false;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
