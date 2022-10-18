package com.hydra.richeditor.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import com.hydra.editor.main.RichEditor;
import com.hydra.editor.util.EditorBitmapUtils;
import com.hydra.richeditor.R;
import com.hydra.richeditor.services.ImageService;
import com.hydra.richeditor.utils.ToastUtils;
import com.mingyuers.permission.PermissionAnywhere;
import com.mingyuers.permission.PermissionCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RichEditor richEditor;

    /**图片服务*/
    private ImageService imageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("富文本编辑器");

        //请求权限
        requirePermission();

        imageService = new ImageService(this);
        imageService.setImageParseListener(bitmap -> {
            //裁剪bitmap
            int targetWidth = 300;
            if(bitmap.getWidth() > targetWidth){
                bitmap = EditorBitmapUtils.imageCompress(bitmap, targetWidth);
            }
            // 将Bitmap转换成字符串
            String data = EditorBitmapUtils.toBase64(bitmap);

            //这里插入一个base64的字符串，正常也可以将图片传到服务器后插入url
            richEditor.insertImage("data:image/png;base64,"+data);
        });

        initView();

    }

    /**请求权限*/
    private void requirePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                //写文件权限
                String[] permissions = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                //获取权限
                PermissionAnywhere.requestPermission(permissions, (grantedPermissions, deniedPermissions, alwaysDeniedPermissions) -> {
                });
            } catch (Exception e) {
                Log.e("RichEditor", e.getMessage(), e);
                ToastUtils.toast(e.getMessage());
            }
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        richEditor = findViewById(R.id.re_main_rich_editor);
        //预览
        richEditor.setOnEditorPreviewListener(text -> {
            Bundle params = new Bundle();
            params.putString("html", text);
            Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
            intent.putExtras(params);
            startActivity(intent);
        });
        //图片选择
        richEditor.setOnImageSelectListener(() -> {
            imageService.showChooseImageDialog();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageService.onActivityResult(requestCode, resultCode, data);
    }
}