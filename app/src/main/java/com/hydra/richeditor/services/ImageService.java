package com.hydra.richeditor.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.hydra.richeditor.utils.FileUtil;
import com.hydra.richeditor.utils.ToastUtils;

import java.io.File;
import java.io.IOException;

/**
 * 图片操作的服务类
 * @Author Hydra
 * @Date 2022/10/18 13:58
 */
public class ImageService {

    /**上下文对象*/
    private final Activity context;

    /**弹出框的选项*/
    private static final String[] items = new String[]{"选择本地图片", "拍照"};

    /** 请求码  */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;

    /** 临时图片文件名称  */
    private static final String IMAGE_FILE_NAME = "image.jpg";

    /**用户拍照的头像，后面会删除*/
    private File takePhotoImageFile = null;

    public ImageService(Activity context){
        this.context = context;
    }

    /**弹出选择窗口，拍照或者选择本地图片*/
    public void showChooseImageDialog(){
        new AlertDialog.Builder(context)
                .setTitle("设置头像")
                .setItems(items, (dialog, which) -> {
                    switch (which){
                        case 0:
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            String[] mimeTypes = new String[]{"image/*"};
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            context.startActivityForResult(intent, IMAGE_REQUEST_CODE);
                            break;
                        case 1:
                            try {
                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                String state = Environment.getExternalStorageState();
                                if (Environment.MEDIA_MOUNTED.equals(state)) {
                                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                    takePhotoImageFile = new File(path, IMAGE_FILE_NAME);

                                    if(!takePhotoImageFile.exists()){
                                        takePhotoImageFile.createNewFile();
                                    }
                                    Uri uri = FileUtil.getFileUri(context, intentFromCapture, takePhotoImageFile);
                                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                }
                                context.startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                            } catch (Exception e) {
                                Log.e("RichEditor", e.getMessage(), e);
                                ToastUtils.toastLong(e.getMessage());
                            }
                            break;
                    }
                }).setNegativeButton("取消", (dialog, which) -> {
                })
                .show();
    }


    /**Activity选择图片、拍照后的回调*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    if (data != null) {
                        Uri uri = data.getData();
                        parseImage(uri);
                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, IMAGE_FILE_NAME);
                        Uri uri = Uri.fromFile(tempFile);
                        parseImage(uri);
                    } else {
                        ToastUtils.toast("未找到存储卡，无法存储照片！");
                    }
                    break;
            }
        }
    }


    private void parseImage(Uri uri){
        try {
            Bitmap bitmap = getBitmapFromUri(uri);
            if(this.imageParseListener != null){
                this.imageParseListener.onImageParse(bitmap);
            }
        } catch (Exception e) {
            Log.e("[Android]", "目录为：" + uri);
            Log.e("[Android]", e.getMessage(), e);
            ToastUtils.toast(e.getMessage());
        }
    }


    /**读取uri所在的图片*/
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
    }

    /**图片解析后的回调*/
    private ImageParseListener imageParseListener;
    public void setImageParseListener(ImageParseListener imageParseListener) {
        this.imageParseListener = imageParseListener;
    }

    /**图片解析后的回调*/
    public interface ImageParseListener{
        void onImageParse(Bitmap bitmap);
    }

}
