package com.supcon.mes.mbap.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseDataLinearLayout;
import com.supcon.common.view.base.view.BaseDataRelativeLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.EmptyViewEntity;


/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomEmptyView extends BaseDataRelativeLayout<EmptyViewEntity> {

    protected ImageView emptyIcon;
    protected TextView emptyContent;
    protected Button emptyButton;

    public CustomEmptyView(Context context) {
        super(context);
    }

    public CustomEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            emptyContent.setTypeface(newFont);
            emptyButton.setTypeface(newFont);
        }
    }

    @Override
    public void update(EmptyViewEntity data) {
        if (data != null) {

            if (!TextUtils.isEmpty(data.buttonText)) {
                setEmptyButtonText(data.buttonText);
            }

            if (!TextUtils.isEmpty(data.contentText)) {
                setEmptyContent(data.contentText);
            }

            if (data.icon != 0) {
                setEmptyIcon(data.icon);
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_defaultemptyview;
    }

    @Override
    protected void initView() {
        super.initView();
        emptyIcon = findViewById(R.id.emptyIcon);
        emptyContent = findViewById(R.id.emptyContent);
        emptyButton = findViewById(R.id.emptyButton);
    }

    @Override
    protected void initListener() {
        super.initListener();
        emptyButton.setOnClickListener(v -> onChildViewClick(v, 0));
    }

    protected void setEmptyIcon(int resId) {
        if (emptyIcon != null) {
            emptyIcon.setImageResource(resId);
        }
    }

    public void setEmptyContent(String content) {
        if (emptyContent != null) {
            emptyContent.setText(content);
        }
    }

    public void setEmptyContent(int resId) {
        setEmptyContent(getContext().getString(resId));
    }

    public void setEmptyButtonText(String text) {
        if (emptyButton != null) {
            emptyButton.setVisibility(VISIBLE);
            emptyButton.setText(text);
        }

    }

    public void setEmptyButtonText(int resId) {
        setEmptyButtonText(getContext().getString(resId));
    }


}
