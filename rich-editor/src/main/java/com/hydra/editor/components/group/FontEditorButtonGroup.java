package com.hydra.editor.components.group;

import android.view.View;
import com.hydra.editor.R;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.components.EditorButtonGroup;
import com.hydra.editor.components.EditorButtonManager;
import com.hydra.editor.view.EditorView;

/**
 * 显示操作字体按钮组的按钮
 * @Author Hydra
 * @Date 2022/10/12 16:04
 */
public class FontEditorButtonGroup extends EditorButtonGroup {

    /**
     * 图标按钮的id
     */
    @Override
    public int getId() {
        return R.id.button_font;
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
        return R.id.scroll_panel_font;
    }

    /**
     * 默认的图片
     */
    @Override
    public int getDefaultDrawable() {
        return R.mipmap.icon_editor_font;
    }

    /**
     * 选中时的图片
     */
    @Override
    public int getActiveDrawable() {
        return R.mipmap.icon_editor_font_;
    }

}
