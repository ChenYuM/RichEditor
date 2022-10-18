package com.hydra.editor.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.hydra.editor.R;
import com.hydra.editor.common.EditorConstant;
import com.hydra.editor.components.buttons.*;
import com.hydra.editor.components.group.FontEditorButtonGroup;
import com.hydra.editor.components.group.HButtonGroup;
import com.hydra.editor.components.group.TextColorEditorButtonGroup;
import com.hydra.editor.view.EditorView;

import java.util.HashMap;
import java.util.Map;

/**
 * 编辑器按钮的管理器
 * @Author Hydra
 * @Date 2022/10/12 15:14
 */
public class EditorButtonManager {

    /**是否正在执行动画*/
    private boolean isAnimating = false;

    /**二级菜单控件的高度*/
    private final int menuViewHeight;

    /**二级菜单的View*/
    private final LinearLayout menuPanelView;

    /**按钮组的id*/
    private static final Map<String, int[]> buttonGroupIds = new HashMap<>();
    static{
        //按钮的组-居左、居中、居右
        buttonGroupIds.put(EditorConstant.BUTTON_GROUP_ALIGN,
                new int[]{ R.id.button_align_left, R.id.button_align_right, R.id.button_align_center });
        //按钮的组-有序、无序列表
        buttonGroupIds.put(EditorConstant.BUTTON_GROUP_LIST,
                new int[]{ R.id.button_list_ul, R.id.button_list_ol });
        //按钮的组-用于切换显示列表的二级菜单
        buttonGroupIds.put(EditorConstant.BUTTON_GROUP_MENU_ACTIVE,
                new int[]{ R.id.button_font, R.id.button_text_color, R.id.button_h });
        //按钮的组-h1---h6的组
        buttonGroupIds.put(EditorConstant.BUTTON_GROUP_H,
                new int[]{ R.id.button_h1, R.id.button_h2, R.id.button_h3, R.id.button_h4, R.id.button_h5, R.id.button_h6 });
    }

    /**所有按钮的列表*/
    private final Map<Integer, EditorButton> editorButtons = new HashMap<>();

    public EditorButtonManager(Context context, View contentView, EditorView mEditor){
        //获取菜单容器并计算高度
        menuPanelView = contentView.findViewById(R.id.ll_menu_panel);
        //获取高度
        menuViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.menu_panel_height);

        //初始化按钮
        this.initEditorButtons(contentView, mEditor);
    }

    /**初始化按钮*/
    private void initEditorButtons(View contentView, EditorView mEditor){
        //按钮组按钮
        this.addEditorButton(new FontEditorButtonGroup());
        this.addEditorButton(new TextColorEditorButtonGroup());
        this.addEditorButton(new HButtonGroup());
        //添加按钮
        this.addEditorButton(new BoldEditorButton());
        this.addEditorButton(new ListOLEditorButton());
        this.addEditorButton(new ListULEditorButton());
        this.addEditorButton(new ItalicEditorButton());
        this.addEditorButton(new UnderlineEditorButton());
        this.addEditorButton(new IndentEditorButton());
        this.addEditorButton(new OutdentEditorButton());
        this.addEditorButton(new BlockquoteEditorButton());
        this.addEditorButton(new StrikeThroughEditorButton());
        this.addEditorButton(new SuperScriptEditorButton());
        this.addEditorButton(new SubScriptEditorButton());
        this.addEditorButton(new AlignLeftEditorButton());
        this.addEditorButton(new AlignRightEditorButton());
        this.addEditorButton(new AlignCenterEditorButton());
        this.addEditorButton(new H1EditorButton());
        this.addEditorButton(new H2EditorButton());
        this.addEditorButton(new H3EditorButton());
        this.addEditorButton(new H4EditorButton());
        this.addEditorButton(new H5EditorButton());
        this.addEditorButton(new H6EditorButton());
        this.addEditorButton(new UndoEditorButton());
        this.addEditorButton(new RedoEditorButton());
        //遍历，初始化
        editorButtons.values()
                .forEach(item->{
                    item.initView(contentView, mEditor);
                });
    }

    /**添加按钮到列表里面*/
    private void addEditorButton(EditorButton button){
        //设置管理器
        button.setManager(this);
        //添加到数组里面
        editorButtons.put(button.getId(), button);
    }

    /**根据id获取目标编辑按钮*/
    private EditorButton getEditorButton(int id){
        return editorButtons.get(id);
    }

    /**取消激活所有按钮*/
    public void inactiveEditorButtonByGroup(String group, int curId){
        if(group == null){
            return;
        }
        int[] ids = buttonGroupIds.get(group);
        if(ids == null || ids.length == 0){
            return;
        }
        for (int id : ids) {
            if(id == curId){
                continue;
            }
            EditorButton button = getEditorButton(id);
            if(button != null){
                button.inactive();
            }
        }
    }

    /**打开菜单容器*/
    public void openMenuPanel(View targetView){
        //隐藏里面所有的view，展示当前菜单的view
        this.hideChildrenView(menuPanelView);
        targetView.setVisibility(View.VISIBLE);
        //如果是隐藏的才需要展开动画
        if (menuPanelView.getVisibility() == View.GONE) {
            //打开动画
            this.animateOpen(menuPanelView);
        }
    }

    /**关闭菜单容器*/
    public void closeMenuPanel(){
        if (menuPanelView.getVisibility() != View.GONE) {
            //关闭动画
            this.animateClose(menuPanelView);
        }
    }

    private void hideChildrenView(ViewGroup view){
        int childCount = view.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = view.getChildAt(i);
            child.setVisibility(View.GONE);
        }
    }

    /**
     * 开启动画
     * @param view 开启动画的view
     */
    public void animateOpen(LinearLayout view) {
        //如果动画正在执行,直接return,相当于点击无效了,不会出现当快速点击时,
        if (isAnimating) return;
        isAnimating = true;
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, menuViewHeight);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        animator.start();
    }

    /**
     * 关闭动画
     * @param view 关闭动画的view
     */
    public void animateClose(LinearLayout view) {
        //如果动画正在执行,直接return,相当于点击无效了,不会出现当快速点击时,
        if (isAnimating) return;
        isAnimating = true;
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        animator.start();
    }


    /**
     * 创建动画
     * @param view  开启和关闭动画的view
     * @param start view的高度
     * @param end   view的高度
     * @return ValueAnimator对象
     */
    private ValueAnimator createDropAnimator(View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = value;
            view.setLayoutParams(layoutParams);
        });
        return animator;
    }

}
