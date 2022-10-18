package com.hydra.editor.main;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.hydra.editor.view.EditorView;

/**
 * 预览
 */
public class RichEditorPreview extends LinearLayout implements View.OnClickListener {

    /**文本编辑器*/
    private EditorView mEditor;

    public RichEditorPreview(Context context) {
        this(context, null);
    }

    public RichEditorPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichEditorPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * 初始化View
     */
    private void initView() {
        initEditor();
    }

    /**
     * 初始化文本编辑器
     */
    private void initEditor() {
        mEditor = new EditorView(getContext());
        //输入框显示字体的大小
        mEditor.setEditorFontSize(18);
        //输入框背景设置
        mEditor.setEditorBackgroundColor(Color.WHITE);

        //输入提示文本-支持html
        mEditor.setPlaceholder("暂无内容");
        //是否允许输入
        mEditor.setInputEnabled(false);

        //添加到当前的view里面
        this.addView(mEditor);
    }

    /**设置html内容*/
    public void setHtmlContent(String content){
        mEditor.setHtml(content);
    }

    /**输入框文本padding*/
    public void setPadding(int left, int top, int right, int bottom){
        mEditor.setPadding(left, top, right, bottom);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
    }
}
