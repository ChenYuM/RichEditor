package com.hydra.editor.components.buttons;

import com.hydra.editor.R;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.common.EditorInputType;
import com.hydra.editor.components.EditorButton;

/**
 * 文本居左按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class AlignCenterEditorButton extends EditorButton {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_align_center;
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
        return R.mipmap.icon_editor_align_center;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_align_center_;
    }

    /**
     * 控件的枚举类型
     */
    @Override
    public EditorInputType getEditorInputType() {
        return EditorInputType.JUSTIFYCENTER;
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        if(this.isActive()){
            this.editor.setAlignCenter();
        }else{
            this.editor.setAlignLeft();
        }
    }
}
