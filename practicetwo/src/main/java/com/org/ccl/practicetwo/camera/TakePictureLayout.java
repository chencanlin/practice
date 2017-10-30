package com.org.ccl.practicetwo.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ccl on 2017/7/19.
 */

public class TakePictureLayout extends FrameLayout implements View.OnClickListener {

    private static final int PADDING_LEFT_RIGHT = 240;
    private static final int PADDING_TOP_BOTTOM = 150;

    private int mPaddingLeftRight;
    private int mPaddingTopBottom;

    private Camera mCamera;
    private View mRightButtonLayout;
    private TextView mTvCancel;
    private TextView mTvDetermine;
    private TextView mTvTakePhoto;

    private File mCaptureFile;
    private TranslucentMaskLayerView mTranslucentMaskLayerView;

    public TakePictureLayout(@NonNull Context context) {
        this(context, null);
    }

    public TakePictureLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TakePictureLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        boolean flag = checkCameraHardware(context);
        if (!flag) {
            Toast.makeText(context, "不支持拍照", Toast.LENGTH_SHORT).show();
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TakePictureLayout);
        mPaddingLeftRight = typedArray.getDimensionPixelSize(R.styleable.TakePictureLayout_padding_left_right, PADDING_LEFT_RIGHT);
        mPaddingTopBottom = typedArray.getDimensionPixelSize(R.styleable.TakePictureLayout_padding_top_bottom, PADDING_TOP_BOTTOM);
        typedArray.recycle();
        init();
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void init() {
        mCamera = getCameraInstance();
        CameraPreview cameraPreview = new CameraPreview(getContext(), mCamera);
        addView(cameraPreview);
        mTranslucentMaskLayerView = new TranslucentMaskLayerView(getContext());
        addView(mTranslucentMaskLayerView);
        mRightButtonLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_right_button, this, false);
        addView(mRightButtonLayout);
        mTvDetermine = mRightButtonLayout.findViewById(R.id.tv_determine);
        mTvCancel = mRightButtonLayout.findViewById(R.id.tv_cancel);
        mTvTakePhoto = mRightButtonLayout.findViewById(R.id.tv_take_photo);
        mTvDetermine.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mTvTakePhoto.setOnClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mRightButtonLayout != null) {
            mRightButtonLayout.layout(getMeasuredWidth() - mPaddingLeftRight + (mPaddingLeftRight - mRightButtonLayout.getMeasuredWidth()) / 2,
                    getMeasuredHeight() / 2 - mRightButtonLayout.getMeasuredHeight() / 2,
                    getMeasuredWidth() - (mPaddingLeftRight - mRightButtonLayout.getMeasuredWidth()) / 2,
                    getMeasuredHeight() / 2 + mRightButtonLayout.getMeasuredHeight() / 2);
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                mCaptureFile = pictureFile;
                setCameraStatus(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void setCameraStatus(boolean inThePreview) {
        mTvTakePhoto.setVisibility(inThePreview ? View.VISIBLE : View.GONE);
        mTvDetermine.setVisibility(inThePreview ? View.GONE : View.VISIBLE);
        mTvCancel.setVisibility(inThePreview ? View.GONE : View.VISIBLE);
    }

    private File getOutputMediaFile() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_UNMOUNTED)) {
            Toast.makeText(getContext(), "请插入sd卡", Toast.LENGTH_SHORT).show();
            return null;
        }
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "/com.zuihuibao/capture/");
        if (!mediaStorageDir.exists() || mediaStorageDir.isFile()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".png");

        return mediaFile;
    }

    private void takePicture() {
        if (mCamera != null) {
            mCamera.takePicture(null, null, mPicture);
        }
    }

    public void startPreView() {
        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    private void releaseSource(){
        releaseCamera();
        mTranslucentMaskLayerView.releaseBitmap();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        int inSampleSize;
        inSampleSize = (int) Math.max(((float) options.outWidth / reqWidth), ((float) options.outHeight / reqHeight));
        if (inSampleSize<1){
            inSampleSize = 1;
        }
        return inSampleSize;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_determine:
                Toast.makeText(getContext(), mCaptureFile.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("path", mCaptureFile.getAbsolutePath());
                byte[] bytes = decodeBitmap();
                if(bytes != null){
                    intent.putExtra("bitmap", bytes);
                }
                ((Activity) getContext()).setResult(Activity.RESULT_OK,intent);
                ((Activity) getContext()).finish();
                releaseSource();
                break;
            case R.id.tv_cancel:
                if(mCaptureFile!= null && mCaptureFile.exists() && mCaptureFile.isFile()) {
                    boolean delete = mCaptureFile.delete();
                    if(delete){
                        setCameraStatus(true);
                        mCaptureFile = null;
                        startPreView();
                    }
                }
                break;
            case R.id.tv_take_photo:
                takePicture();
                break;
        }
    }

    private byte[] decodeBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCaptureFile.getAbsolutePath(),options);
        int i = calculateInSampleSize(options, 400, 600);
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        Bitmap bitmap = BitmapFactory.decodeFile(mCaptureFile.getAbsolutePath(), options);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        bitmap.recycle();
        bitmap = null;
        if(compress){
            return byteArrayOutputStream.toByteArray();
        }else{
            return null;
        }
    }
}
