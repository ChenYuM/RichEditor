package com.hydra.editor.components;

import android.view.View;
import android.widget.ImageView;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.common.EditorInputType;
import com.hydra.editor.view.EditorView;

/**
 * 编辑器的按钮
 * @Author Hydra
 * @Date 2022/10/12 15:14
 */
public abstract class EditorButton implements View.OnClickListener {

    /**是否选中*/
    private boolean active;

    /**图标按钮*/
    protected ImageView view;

    /**容器view*/
    protected View contentView;

    /**编辑器*/
    protected EditorView editor;

    /**管理器*/
    protected EditorButtonManager manager;

    /**图标按钮的id*/
    public abstract int getId();

    /**默认的图片*/
    public abstract int getDefaultDrawable();

    /**选中时的图片*/
    public abstract int getActiveDrawable();

    /**点击事件触发*/
    public abstract void onEditorButtonClick();

    /**获取所在的组*/
    public String getGroup(){
        return null;
    }

    public boolean isActive() {
        return active;
    }

    /**按钮的id*/
    protected final ImageView getView(){
        return this.view;
    }

    /**
     * 控件的枚举类型
     * 用于遍历控件，设置是否激活
     */
    public EditorInputType getEditorInputType(){
        return null;
    }

    /**初始化设置容器*/
    public void initView(View contentView, EditorView mEditor){
        this.contentView = contentView;
        this.editor = mEditor;
        this.view = contentView.findViewById(this.getId());
        this.view.setOnClickListener(this);
    }

    /**获取按钮管理器*/
    public EditorButtonManager getManager() {
        return manager;
    }
    //设置按钮管理器
    public void setManager(EditorButtonManager manager) {
        this.manager = manager;
    }

    /**设置激活*/
    public final void active(){
        int activeDrawable = this.getActiveDrawable();
        if(EditorConstant.EDITOR_INVALID != activeDrawable){
            this.view.setImageResource(activeDrawable);
        }
        this.active = true;
    }

    /**取消激活*/
    public final void inactive(){
        int defaultDrawable = this.getDefaultDrawable();
        if(EditorConstant.EDITOR_INVALID != defaultDrawable){
            this.view.setImageResource(defaultDrawable);
        }
        this.active = false;
    }

    /**点击事件*/
    @Override
    public void onClick(View view){
        int id = this.getId();
        if(id == view.getId()){
            String group = this.getGroup();
            if(group != null){
                getManager().inactiveEditorButtonByGroup(group, id);
            }
            //选中时，设置
            if(this.active){
                this.inactive();
            }else{
                this.active();
            }
            this.onEditorButtonClick();
        }
    }

}
