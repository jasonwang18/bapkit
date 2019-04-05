package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.TodoBean;

/**
 * Created by wangshizhan on 2018/1/17.
 * Email:wangshizhan@supcon.com
 */

public class CustomTodoView extends BaseRelativeLayout {

    ImageView customTodoIc;
    TextView customTodoText;
    TextView customTodoTime;
    TextView customTodoStatus;

    int mTextSize, iconRes;
    String mStatus, mTime, mText;
    private TodoBean mTodoBean;

    public CustomTodoView(Context context) {
        super(context);
    }

    public CustomTodoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_todo;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customTodoText.setTypeface(newFont);
            customTodoTime.setTypeface(newFont);
            customTodoStatus.setTypeface(newFont);
        }
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTodoView);
            mTextSize   = array.getInt(R.styleable.CustomTodoView_text_size, 0);
            iconRes     = array.getResourceId(R.styleable.CustomTodoView_icon_res, 0);
            mStatus     = array.getString(R.styleable.CustomTodoView_todo_status);
            mText       = array.getString(R.styleable.CustomTodoView_todo_text);
            mTime       = array.getString(R.styleable.CustomTodoView_todo_time);
            array.recycle();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customTodoIc = findViewById(R.id.customTodoIc);
        customTodoText = findViewById(R.id.customTodoText);
        customTodoTime = findViewById(R.id.customTodoTime);
        customTodoStatus = findViewById(R.id.customTodoStatus);

        if(!TextUtils.isEmpty(mText)){
            customTodoText.setText(mText);
        }

        if(!TextUtils.isEmpty(mStatus)){
            customTodoStatus.setText(mStatus);
        }

        if(!TextUtils.isEmpty(mTime)){
            customTodoTime.setText(mTime);
        }

        if(mTextSize!=0){
            customTodoText.setTextSize(mTextSize);
            customTodoTime.setTextSize(mTextSize);
        }

        if(iconRes!=0){
            customTodoIc.setImageResource(iconRes);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        rootView.setOnClickListener(v -> onChildViewClick(v, 0, mTodoBean));
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void setData(TodoBean todoBean){
        this.mTodoBean = todoBean;
        customTodoText.setText(todoBean.text);
        customTodoIc.setImageResource(todoBean.iconRes);
        customTodoTime.setText(todoBean.time);
        customTodoStatus.setText(todoBean.status);
    }

}
