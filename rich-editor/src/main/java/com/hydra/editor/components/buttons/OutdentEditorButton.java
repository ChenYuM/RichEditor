package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.components.EditorButton;

/**
 * 减少缩进按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class OutdentEditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_outdent;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_outdent;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_outdent;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        this.editor.setOutdent();
    }
}
