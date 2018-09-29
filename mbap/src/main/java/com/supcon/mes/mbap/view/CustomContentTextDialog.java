package com.supcon.mes.mbap.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.annotation.Bind;
import com.app.annotation.BindByTag;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.ScreenUtil;

/**
 * Created by Xushiyun on 2018/6/4.
 * Email:ciruy_victory@gmail.com
 */

public class CustomContentTextDialog extends Dialog {

    public static void showContent(Context context, String content){
        if(!TextUtils.isEmpty(content))
            new CustomContentTextDialog(context, content).show();
    }

    private String contentText;

    CustomScrollView scrollView;

    TextView contentTextView;


    public void setContentText(String contentText) {
        this.contentText = contentText;
        contentTextView.setText(contentText);
    }

    public CustomContentTextDialog(@NonNull Context context, String contentText) {
        this(context, R.style.contentDialog, contentText);
    }

    public CustomContentTextDialog(@NonNull Context context, int themeResId, String contentText) {
        super(context, R.style.contentDialog);
        this.contentText = contentText;
        initView();

        initListener();
    }

    private void initListener() {
        contentTextView.setOnClickListener(v -> CustomContentTextDialog.this.dismiss());
    }

    protected Object layout() {
        return R.layout.custom_content_text_dialog;
    }


    private void initView() {
        getWindow().setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画

        final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = null;
        if (layout() instanceof View) {
            view = (View) layout();
        }
        else if (layout() instanceof Integer)
            view = layoutInflater.inflate((Integer) layout(), null);
        else {
        }
        setContentView(view);
        contentTextView = view.findViewById(R.id.contentTextView);
        scrollView = view.findViewById(R.id.scrollView);

        contentTextView.setMinHeight(ScreenUtil.getScreenHeight(getContext())-ScreenUtil.getStatusHeight(getContext()));

        if (!TextUtils.isEmpty(contentText))
            contentTextView.setText(contentText);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }



}
