package com.hydra.editor.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by ZQiong on 2018/3/22.
 */

public final class EditorBitmapUtils {

    private EditorBitmapUtils() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    public static String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    /**
     * 变换位图大小
     */
    public static Bitmap imageCompress(Bitmap bitmap, int width){
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        int height = (int)(width/(float)bitmapWidth * bitmapHeight);

        //根据缩放比例获取新图
        return imageCompress(bitmap, width, height);
    }

    /**变换位图大小*/
    public static Bitmap imageCompress(Bitmap bitmap, int width, int height){
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float scaleW = width / (float)bitmapWidth;
        float scaleH = height / (float)bitmapHeight;
        Matrix transformation = new Matrix();
        transformation.preScale(scaleW, scaleH);

        //根据缩放比例获取新图
        return Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, transformation, true);
    }

    public static Bitmap decodeResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
