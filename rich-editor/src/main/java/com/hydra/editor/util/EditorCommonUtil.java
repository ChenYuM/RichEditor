package com.hydra.editor.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

/**
 * 编辑通用工具
 * @Author Hydra
 * @Date 2022/10/13 14:19
 */
public class EditorCommonUtil {

    /**
     * 获取控件的高度
     */
    public static int getViewMeasureHeight(Context context, View view) {
        //获取像素密度
        float mDensity = context.getResources().getDisplayMetrics().density;
        //获取布局的高度
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return (int) (mDensity * height + 0.5);
    }

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

}
