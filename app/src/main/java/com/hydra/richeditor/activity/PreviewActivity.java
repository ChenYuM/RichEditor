package com.hydra.richeditor.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hydra.editor.main.RichEditorPreview;
import com.hydra.richeditor.R;

public class PreviewActivity extends AppCompatActivity {

    /**预览控件*/
    private RichEditorPreview richEditorPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getSupportActionBar().setTitle("预览");

        initView();
        initClickListener();
    }

    /**
     * 初始化View
     */
    private void initView() {
        Bundle params = getIntent().getExtras();
        String html = params.getString("html");
        richEditorPreview = findViewById(R.id.re_editor_preview);
        richEditorPreview.setHtmlContent(html);
    }

    private void initClickListener() {
    }

}
