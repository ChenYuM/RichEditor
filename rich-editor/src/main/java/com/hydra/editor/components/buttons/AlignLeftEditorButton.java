package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.components.EditorButton;

/**
 * 文本居左按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class AlignLeftEditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_align_left;
    }

    /**所在的组*/
    @Override
    public String getGroup() {
        return EditorConstant.BUTTON_GROUP_ALIGN;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_align_left;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_align_left_;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        this.editor.setAlignLeft();
    }
}
