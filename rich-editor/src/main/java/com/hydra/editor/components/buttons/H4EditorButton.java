package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.common.EditorInputType;
import com.hydra.editor.components.EditorButton;

/**
 * 字体大小按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class H4EditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_h4;
    }

    /**所在的组*/
    @Override
    public String getGroup() {
        return EditorConstant.BUTTON_GROUP_H;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_h4;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_h4_;
    }

    /**
     * 控件的枚举类型
     */
    @Override
    public EditorInputType getEditorInputType() {
        return EditorInputType.H3;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        this.editor.setFontSize(3);
    }
}
