package com.org.ccl.practicetwo.downloaddemo.interf;

/**
 * Created by ccl on 2017/10/27.
 */

public interface DownloadListener {


    void onProgress(int progress);


    void onSuccess();


    void onFailed();


    void onPaused();


    void onCanceled();

}
