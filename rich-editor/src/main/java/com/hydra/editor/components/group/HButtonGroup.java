package com.hydra.editor.components.group;

import com.hydra.editor.R;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.components.EditorButtonGroup;

/**
 * 显示字体大小按钮组的按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class HButtonGroup extends EditorButtonGroup {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_h;
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
        return R.id.scroll_panel_h;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_h;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_h_;
    }

}
