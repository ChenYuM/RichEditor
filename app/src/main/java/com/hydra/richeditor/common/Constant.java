package com.hydra.richeditor.common;

import android.os.Environment;
import android.util.Log;


/**
 * 常量
 * Created by  on 2022/05/20.
 */
public class Constant {

	/**裁剪图片的临时文件名称*/
	String CROPPED_IMAGE_NAME = "HydraCropImage";

    private String getSDCardPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
    public static String NovelPath = "com/hydra/novel";
    //日志路径
    public static String LOG_PATH = "$SDCardPath/$NovelPath/logs"; // SD卡中的根目录下的目录
    /**用户图片位置*/
    public static String ImagesPath = "$SDCardPath/$NovelPath/images";
    /**更新包下载位置*/
    public static String ApkPath = "$SDCardPath/$NovelPath/apk";
    /**字体包下载位置*/
    public static String TypefacePath = "$SDCardPath/$NovelPath/typeface";


}
