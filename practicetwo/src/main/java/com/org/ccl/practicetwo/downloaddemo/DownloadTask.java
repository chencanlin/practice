package com.org.ccl.practicetwo.downloaddemo;

import android.os.AsyncTask;
import android.os.Environment;

import com.org.ccl.practicetwo.downloaddemo.interf.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ccl on 2017/10/27.
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {


    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSED = 2;
    private static final int TYPE_CANCELED = 3;
    private final DownloadListener mDownloadListener;


    private int lastProgress;
    private boolean isCanceled = false;
    private boolean isPaused = false;

    public DownloadTask(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        File file;
        // 记录已经下载的长度
        long downloadedLength = 0;
        String downloadUrl = params[0];
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        file = new File(directory, fileName);
        if (file.exists()) {
            downloadedLength = file.length();
        }
        try {
            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (downloadedLength == contentLength) {
                return TYPE_SUCCESS;
            }
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                ResponseBody body = response.body();
                if(body != null) {
                    inputStream = body.byteStream();
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    // 跳到已经下载过的地方
                    randomAccessFile.seek(downloadedLength);
                    byte[] bytes = new byte[1024];
                    int total = 0;
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        if (isCanceled) {
                            return TYPE_CANCELED;
                        } else if (isPaused) {
                            return TYPE_PAUSED;
                        } else {
                            total += len;
                            randomAccessFile.write(bytes, 0, len);
                            int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                            publishProgress(progress);
                        }
                    }
                    body.close();
                    return TYPE_SUCCESS;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                if (isCanceled && file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                mDownloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                mDownloadListener.onFailed();
                break;
            case TYPE_CANCELED:
                mDownloadListener.onCanceled();
                break;
            case TYPE_PAUSED:
                mDownloadListener.onPaused();
                break;
        }
    }

    public void pausedDownload(){
        isPaused = true;
    }

    public void canceledDownload(){
        isCanceled = true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Integer progress = values[0];
        if(progress >= lastProgress){
            mDownloadListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                long contentLength = body.contentLength();
                response.close();
                return contentLength;
            }
        }
        return 0;
    }
}
