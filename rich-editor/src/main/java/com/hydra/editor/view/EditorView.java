package com.hydra.editor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.hydra.editor.common.EditorInputType;
import com.hydra.editor.listener.*;
import com.hydra.editor.util.EditorBitmapUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 富文本编辑实现类
 * Created by ZQiong on 2018/3/22.
 */

public class EditorView extends WebView {

    private static final String SETUP_HTML = "file:///android_asset/editor/editor.html";
    private static final String SCHEME_STATE = "re-state://";
    private static final String SCHEME_CALLBACK = "re-callback://";
    private static final String SCHEME_FOCUS_CALLBACK = "re-focus-callback://";
    private boolean isReady = false;
    private String mContents;
    private OnEditorFocusListener mEditorFocusListener;
    private OnTextChangeListener mTextChangeListener;
    private OnDecorationStateListener mDecorationStateListener;
    private AfterInitialLoadListener mLoadListener;

    public EditorView(Context context) {
        this(context, null);
    }

    public EditorView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public EditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        getSettings().setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(createWebviewClient());
        loadUrl(SETUP_HTML);

        applyAttributes(context, attrs);
    }

    protected EditorWebViewClient createWebviewClient() {
        return new EditorWebViewClient();
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        mTextChangeListener = listener;
    }

    public void setOnEditorFocusListener(OnEditorFocusListener listener) {
        mEditorFocusListener = listener;
    }

    public void setOnDecorationChangeListener(OnDecorationStateListener listener) {
        mDecorationStateListener = listener;
    }

    public void setOnInitialLoadListener(AfterInitialLoadListener listener) {
        mLoadListener = listener;
    }

    /**文本改变的回调*/
    private void callback(String url) {
        mContents = url.replaceFirst(SCHEME_CALLBACK, "");
        if (mTextChangeListener != null) {
            mTextChangeListener.onTextChange(mContents);
        }
    }

    /**聚焦的回调*/
    private void focusCallback(String url) {
        if (mEditorFocusListener != null) {
            mEditorFocusListener.onFocus(mContents);
        }
    }

    private String lastState = null;
    private void stateCheck(String url) {
        String state = url.replaceFirst(SCHEME_STATE, "").toUpperCase(Locale.ENGLISH);
        //防止重复执行
        if(state.equals(lastState)){
            return;
        }
        lastState = state;
        String[] array = state.split(",");
        //匹配枚举中的值，获取到当前的状态改变，进行数据处理
        List<EditorInputType> editorInputTypes = new ArrayList<>();
        for(String item : array){
            for (EditorInputType editorInputType : EditorInputType.values()) {
                if (editorInputType.name().equalsIgnoreCase(item)) {
                    editorInputTypes.add(editorInputType);
                    break;
                }
            }
        }

        if (mDecorationStateListener != null) {
            mDecorationStateListener.onStateChangeListener(state, editorInputTypes);
        }
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        if(attrs == null){
            return;
        }
        final int[] attrsArray = new int[]{
                android.R.attr.gravity
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);

        int gravity = ta.getInt(0, View.NO_ID);
        switch (gravity) {
            case Gravity.LEFT:
                exec("javascript:RE.setTextAlign(\"left\")");
                break;
            case Gravity.RIGHT:
                exec("javascript:RE.setTextAlign(\"right\")");
                break;
            case Gravity.TOP:
                exec("javascript:RE.setVerticalAlign(\"top\")");
                break;
            case Gravity.BOTTOM:
                exec("javascript:RE.setVerticalAlign(\"bottom\")");
                break;
            case Gravity.CENTER_VERTICAL:
                exec("javascript:RE.setVerticalAlign(\"middle\")");
                break;
            case Gravity.CENTER_HORIZONTAL:
                exec("javascript:RE.setTextAlign(\"center\")");
                break;
            case Gravity.CENTER:
                exec("javascript:RE.setVerticalAlign(\"middle\")");
                exec("javascript:RE.setTextAlign(\"center\")");
                break;
        }

        ta.recycle();
    }

    public void setHtml(String contents) {
        if (contents == null) {
            contents = "";
        }
        try {
            exec("javascript:RE.setHtml('" + URLEncoder.encode(contents, "UTF-8") + "');");
        } catch (UnsupportedEncodingException e) {
            Log.e("Error", e.getMessage(), e);
        }
        mContents = contents;
    }

    public void getHtml(ValueCallback<String> resultCallback) {
        exec("javascript:RE.getHtml();", resultCallback);
    }

    public void setEditorFontColor(int color) {
        String hex = convertHexColorString(color);
        exec("javascript:RE.setBaseTextColor('" + hex + "');");
    }

    public void setEditorFontSize(int px) {
        exec("javascript:RE.setBaseFontSize('" + px + "px');");
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        exec("javascript:RE.setPadding('" + left + "px', '" + top + "px', '" + right + "px', '" + bottom
                + "px');");
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        // still not support RTL.
        setPadding(start, top, end, bottom);
    }

    public void setEditorBackgroundColor(int color) {
        setBackgroundColor(color);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundResource(int resid) {
        Bitmap bitmap = EditorBitmapUtils.decodeResource(getContext(), resid);
        String base64 = EditorBitmapUtils.toBase64(bitmap);
        bitmap.recycle();

        exec("javascript:RE.setBackgroundImage('url(data:image/png;base64," + base64 + ")');");
    }

    @Override
    public void setBackground(Drawable background) {
        Bitmap bitmap = EditorBitmapUtils.toBitmap(background);
        String base64 = EditorBitmapUtils.toBase64(bitmap);
        bitmap.recycle();

        exec("javascript:RE.setBackgroundImage('url(data:image/png;base64," + base64 + ")');");
    }

    public void setBackground(String url) {
        exec("javascript:RE.setBackgroundImage('url(" + url + ")');");
    }

    public void setEditorWidth(int px) {
        exec("javascript:RE.setWidth('" + px + "px');");
    }

    public void setEditorHeight(int px) {
        exec("javascript:RE.setHeight('" + px + "px');");
    }

    public void setPlaceholder(String placeholder) {
        exec("javascript:RE.setPlaceholder('" + placeholder + "');");
    }

    public void setInputEnabled(Boolean inputEnabled) {
        exec("javascript:RE.setInputEnabled(" + inputEnabled + ")");
    }

    public void loadCSS(String cssFile) {
        String jsCSSImport = "(function() {" +
                "    var head  = document.getElementsByTagName(\"head\")[0];" +
                "    var link  = document.createElement(\"link\");" +
                "    link.rel  = \"stylesheet\";" +
                "    link.type = \"text/css\";" +
                "    link.href = \"" + cssFile + "\";" +
                "    link.media = \"all\";" +
                "    head.appendChild(link);" +
                "}) ();";
        exec("javascript:" + jsCSSImport + "");
    }

    public void undo() {
        exec("javascript:RE.undo();");
    }

    public void redo() {
        exec("javascript:RE.redo();");
    }

    public void setBold() {
        exec("javascript:RE.setBold();");
    }

    public void setItalic() {
        exec("javascript:RE.setItalic();");
    }

    public void setSubscript() {
        exec("javascript:RE.setSubscript();");
    }

    public void setSuperscript() {
        exec("javascript:RE.setSuperscript();");
    }

    public void setStrikeThrough() {
        exec("javascript:RE.setStrikeThrough();");
    }

    public void setUnderline() {
        exec("javascript:RE.setUnderline();");
    }

    public void setTextColor(int color) {
        String hex = convertHexColorString(color);
        exec("javascript:RE.setTextColor('" + hex + "');");
    }

    public void setTextBackgroundColor(int color) {
        String hex = convertHexColorString(color);
        exec("javascript:RE.setTextBackgroundColor('" + hex + "');");
    }

    public void setFontSize(int fontSize) {
        exec("javascript:RE.setFontSize('" + fontSize + "');");
    }

    public void removeFormat() {
        exec("javascript:RE.removeFormat();");
    }

    public void setHeading(int heading) {
        exec("javascript:RE.setHeading('" + heading + "');");
    }

    public void setIndent() {
        exec("javascript:RE.setIndent();");
    }

    public void setOutdent() {
        exec("javascript:RE.setOutdent();");
    }

    public void setAlignLeft() {
        exec("javascript:RE.setJustifyLeft();");
    }

    public void setAlignCenter() {
        exec("javascript:RE.setJustifyCenter();");
    }

    public void setAlignRight() {
        exec("javascript:RE.setJustifyRight();");
    }

    public void setBlockquote() {
        exec("javascript:RE.setBlockquote();");
    }

    public void setBullets() {
        exec("javascript:RE.setBullets();");
    }

    public void setNumbers() {
        exec("javascript:RE.setNumbers();");
    }

    public void insertImage(String url, String alt) {
        exec("javascript:RE.insertImage('" + url + "', '" + alt + "');");
    }

    public void insertLink(String href, String title) {
        exec("javascript:RE.insertLink('" + href + "', '" + title + "');");
    }

    public void insertBlank() {
        exec("javascript:RE.insertBlank();");
    }

    public void insertBlankLine() {
        exec("javascript:RE.insertBlankLine();");
    }

    public void insertTodo() {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.setTodo('" + EditorBitmapUtils.getCurrentTime() + "');");
    }

    public void focusEditor() {
        requestFocus();
        exec("javascript:RE.focus();");
    }

    public void clearFocusEditor() {
        exec("javascript:RE.blurFocus();");
    }

    private String convertHexColorString(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    protected void exec(String trigger) {
        exec(trigger, null);
    }

    protected void exec(String trigger, ValueCallback<String> resultCallback) {
        if (isReady) {
            load(trigger, resultCallback);
        } else {
            postDelayed(() -> exec(trigger, resultCallback), 100);
        }
    }

    private void load(String trigger, ValueCallback<String> resultCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript(trigger, value -> {
                if(resultCallback != null){
                    try {
                        //去掉前后双引号
                        value = value.substring(1, value.length()-1);
                        //url解码
                        value = URLDecoder.decode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Log.e("???", e.getMessage(), e);
                    }
                    resultCallback.onReceiveValue(value);
                }
            });
        }
    }

    protected class EditorWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            isReady = url.equalsIgnoreCase(SETUP_HTML);
            if (mLoadListener != null) {
                mLoadListener.onAfterInitialLoad(isReady);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String decodeUrl;
            try {
                decodeUrl = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.e("EditorView", e.getMessage(), e);
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.indexOf(url, SCHEME_CALLBACK) == 0) {
                callback(decodeUrl);
                return true;
            } else if (TextUtils.indexOf(url, SCHEME_FOCUS_CALLBACK) == 0) {
                focusCallback(decodeUrl);
                return true;
            } else if (TextUtils.indexOf(url, SCHEME_STATE) == 0) {
                stateCheck(decodeUrl);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
