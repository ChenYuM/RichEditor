package com.hydra.editor.components;

import android.view.View;
import com.hydra.editor.R;
import com.hydra.editor.view.EditorView;

/**
 * 编辑器的按钮
 * @Author Hydra
 * @Date 2022/10/12 15:14
 */
public abstract class EditorButtonGroup extends EditorButton {

    /**容器view*/
    protected View panelView;

    /**容器的id*/
    public abstract int getPanelId();

    @Override
    public void initView(View contentView, EditorView mEditor) {
        super.initView(contentView, mEditor);
        //获取字体相关的view容器
        this.panelView = contentView.findViewById(this.getPanelId());
    }

    /**
     * 点击事件触发
     */
    @Override
    public void onEditorButtonClick() {
        EditorButtonManager manager = getManager();
        if(this.isActive()){
            manager.openMenuPanel(this.panelView);
        }else{
            manager.closeMenuPanel();
        }
    }

}
