package com.hydra.editor.listener;

import com.hydra.editor.common.EditorInputType;

import java.util.List;

/**
 * 选中状态改变的监听器
 * @Author Hydra
 * @Date 2022/10/8 14:25
 */
public interface OnDecorationStateListener {
    void onStateChangeListener(String text, List<EditorInputType> editorInputTypes);
}