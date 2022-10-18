package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.components.EditorButton;

/**
 * 加粗按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class BoldEditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_bold;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_bold;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_bold_;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        this.editor.setBold();
    }
}
