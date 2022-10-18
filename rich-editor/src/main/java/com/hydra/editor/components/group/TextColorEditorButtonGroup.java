package com.hydra.editor.components.group;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import com.hydra.editor.R;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.components.EditorButtonGroup;
import com.hydra.editor.view.ColorPickerView;
import com.hydra.editor.view.EditorView;

/**
 * 显示颜色选择器按钮组的按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class TextColorEditorButtonGroup extends EditorButtonGroup {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_text_color;
    }

    /**所在的组*/
    @Override
    public String getGroup() {
        return EditorConstant.BUTTON_GROUP_MENU_ACTIVE;
    }

    /**
     * 容器的id
     */
    @Override
    public int getPanelId() {
        return R.id.layout_panel_text_color;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return EditorConstant.EDITOR_INVALID;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return EditorConstant.EDITOR_INVALID;
    }

    /**初始化控件*/
    @Override
    public void initView(View contentView, EditorView mEditor) {
        super.initView(contentView, mEditor);
        this.initColorPicker();
    }

    /**
     * 初始化颜色选择器
     */
    private void initColorPicker() {
        ColorPickerView right = contentView.findViewById(R.id.cpv_main_color);
        right.setOnColorPickerChangeListener(new ColorPickerView.OnColorPickerChangeListener() {
            @Override
            public void onColorChanged(ColorPickerView picker, int color) {
                view.setImageDrawable(new ColorDrawable(color));
                editor.setTextColor(color);
            }

            @Override
            public void onStartTrackingTouch(ColorPickerView picker) {
            }

            @Override
            public void onStopTrackingTouch(ColorPickerView picker) {
            }
        });
    }

}
