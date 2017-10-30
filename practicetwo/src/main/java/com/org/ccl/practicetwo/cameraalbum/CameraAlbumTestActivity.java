package com.org.ccl.practicetwo.cameraalbum;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by ccl on 2017/10/25.
 */

public class CameraAlbumTestActivity extends Activity {

    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 2;
    private ImageView mIVImage;
    private Uri fileUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_test);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        // 拍照存储在指定的路径
        findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getExternalCacheDir(), "output_image.jpg");
                if(!file.isFile() || !file.exists()){
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    fileUri = FileProvider.getUriForFile(CameraAlbumTestActivity.this, "com.org.ccl.practicetwo.cameraalbum.fileprovider", file);
                }else{
                    fileUri = Uri.fromFile(file);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
            }
        });
        mIVImage = findViewById(R.id.iv_image);

        // 从相册里面选择照片
        findViewById(R.id.tv_choose_from_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK){
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mIVImage.setImageBitmap(bitmap);
        }else if(requestCode == REQUEST_CODE_CHOOSE_PHOTO && resultCode == RESULT_OK){
            // 判断手机系统版本号是否大于4.4(19)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                handleImageOnKitKat(data);
            }else{
                handleImageBeforeKitKat(data);
            }
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            // 如果是document 类型的Uri 则通过document id处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equalsIgnoreCase(uri.getAuthority())){
                // 解析出数字格式的id
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if("com.android.providers.downloads.documents".equalsIgnoreCase(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是content类型的Uri 则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            // 如果是file类型的Uri，则直接获取路径
            imagePath = uri.getPath();
        }
        // 根据图片路径显示图片
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        // 通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mIVImage.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
