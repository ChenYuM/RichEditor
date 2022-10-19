package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.common.EditorInputType;
import com.hydra.editor.components.EditorButton;

/**
 * 双引号按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class BlockquoteEditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.action_blockquote;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_blockquote;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_blockquote_;
    }
    /**
     * 控件的枚举类型
     */
    @Override
    public EditorInputType getEditorInputType() {
        return EditorInputType.BLOCKQUOTE;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        if(this.isActive()){
            this.editor.setBlockquote();
        }else{
            this.editor.insertBlankLine();
        }
    }
}
