package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.components.EditorButton;

/**
 * 撤回按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class UndoEditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_undo;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_undo;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_undo;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        this.editor.undo();
    }
}
