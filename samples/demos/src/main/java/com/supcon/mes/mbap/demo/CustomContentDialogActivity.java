package com.supcon.mes.mbap.demo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.listener.OnDoubleClickListener;
import com.supcon.mes.mbap.view.CustomContentTextDialog;
import com.supcon.mes.mbap.view.CustomExpandableTextView;

public class CustomContentDialogActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_content_dialog);

        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
//        CustomContentTextDialog customContentTextDialog = new CustomContentTextDialog(this, "Hello world!!");
        CustomExpandableTextView button = findViewById(R.id.btn);
//        button.enableContentDialog();
//        button.disableContentDialog();
//        button.enableSingleClickMode();

//        button.setOnTouchListener(null);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                customContentTextDialog.setContentText(button.getText().toString());
////                customContentTextDialog.show();
//                ToastUtils.show(getApplicationContext(), "点击了组件");
//            }
//        });

    }
}
