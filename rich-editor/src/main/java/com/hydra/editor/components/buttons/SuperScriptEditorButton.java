package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.components.EditorButton;

/**
 * 文字上标按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class SuperScriptEditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.action_superscript;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_superscript;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_superscript_;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        if(this.isActive()){
            this.editor.setSuperscript();
        }else{
            this.editor.insertBlank();
        }
    }
}
