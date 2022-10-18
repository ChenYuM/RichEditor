package com.hydra.editor.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hydra.editor.R;
import com.hydra.editor.components.EditorButtonManager;
import com.hydra.editor.listener.OnEditorPreviewListener;
import com.hydra.editor.listener.OnImageSelectListener;
import com.hydra.editor.view.EditorView;

public class RichEditor extends LinearLayout implements View.OnClickListener {

    /**容器*/
    private View contentView;
    /**文本编辑器*/
    private EditorView mEditor;
    /**按钮组的容器*/
    private ViewGroup editorButtonGroup;
    /**按钮组的管理器，可以触发一些事件*/
    EditorButtonManager editorButtonManager;

    /**预览按钮*/
    private TextView mPreView;
    /**图片按钮*/
    private View mImage;

    public RichEditor(Context context) {
        this(context, null);
    }

    public RichEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initContentView();
        initView();
        initClickListener();
    }

    /**初始化容器*/
    private void initContentView(){
        this.contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_rich_editor, this);
    }


    /**
     * 初始化View
     */
    private void initView() {
        initEditor();
        initMenu();
    }

    /**
     * 初始化文本编辑器
     */
    private void initEditor() {
        mEditor = contentView.findViewById(R.id.re_main_editor);
        //mEditor.setEditorHeight(400);
        //输入框显示字体的大小
        mEditor.setEditorFontSize(18);
        //输入框显示字体的颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //输入框背景设置
        mEditor.setEditorBackgroundColor(Color.WHITE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //输入框文本padding
        mEditor.setPadding(10, 15, 10, 15);
        //输入提示文本-支持html
        mEditor.setPlaceholder("请输入编辑内容<br>&nbsp;·&nbsp;注意哦：发帖内容要跟该区主题相关<br/>&nbsp;·&nbsp;友好讨论，请勿骂人<br/>&nbsp;·&nbsp;优质话题会被置顶加精哦");
        //是否允许输入
        mEditor.setInputEnabled(true);
        //文本框聚焦事件
        mEditor.setOnEditorFocusListener(text->{
            //显示工具栏，这里的目的是因为一开始就显示工具栏的话用户操作是无效的，必须聚焦了才能改变编辑器的状态
            editorButtonGroup.setVisibility(View.VISIBLE);
        });
        //文本输入框监听事件
        mEditor.setOnTextChangeListener(text -> Log.d("mEditor", "html文本：" + text));

        //创建按钮管理器，暂时不需要对象的实力，只需要new就好了
        this.editorButtonManager = new EditorButtonManager(getContext(), contentView, mEditor);
    }

    /**
     * 初始化菜单按钮
     */
    private void initMenu() {
        //监听键盘用于隐藏按钮组
        editorButtonGroup = contentView.findViewById(R.id.layout_editor_button_group);

        mPreView = contentView.findViewById(R.id.tv_main_preview);
        mImage = contentView.findViewById(R.id.button_image);

    }

    private void initClickListener() {
        mPreView.setOnClickListener(this);
        mImage.setOnClickListener(this);
    }

    //预览的监听器
    private OnEditorPreviewListener mEditorPreviewListener;
    /**设置预览的监听器*/
    public void setOnEditorPreviewListener(OnEditorPreviewListener listener) {
        mEditorPreviewListener = listener;
    }

    //图片选择的监听器
    private OnImageSelectListener onImageSelectListener;
    /**设置图片选择的监听器*/
    public void setOnImageSelectListener(OnImageSelectListener listener) {
        onImageSelectListener = listener;
    }

    /**插入图片*/
    public void insertImage(String url){
        mEditor.insertImage(url, "dachshund");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_image) {//插入图片
            if(onImageSelectListener != null){
                onImageSelectListener.onImageSelect();
            }
        } else if (id == R.id.tv_main_preview) {
            //预览
            mEditor.getHtml(value->{
                Log.d("RichEditor", value);
                if(mEditorPreviewListener != null){
                    mEditorPreviewListener.onPreview(value);
                }
            });
        }
    }

}
