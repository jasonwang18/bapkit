package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.OnDoubleClickListener;
import com.supcon.mes.mbap.utils.AnimationUtil;

/**
 * Created by wangshizhan on 2017/12/28.
 * Email:wangshizhan@supcon.com
 */
@Deprecated
public class CustomExpandableTextView extends BaseLinearLayout implements View.OnClickListener{

    private TextView customExpandTv;
    private ImageView customExpandIcon;

    private String mText;
    private float mTextSize;
    private int mTextColor;
    private int mTextStyle;
    private int times, maxLines;

    private boolean isExpanded = false;
    private boolean isExpanding = false;
    private boolean expandable = false;

    public CustomExpandableTextView(Context context) {
        super(context);
    }

    public CustomExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customExpandTv.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_expand_textview;
    }

    @Override
    protected void initView() {
        super.initView();
        customExpandTv = findViewById(R.id.customExpandTv);
        customExpandIcon = findViewById(R.id.customExpandIcon);

        if(mTextColor !=0){
            customExpandTv.setTextColor(mTextColor);
        }

        if(mTextSize !=0){
            customExpandTv.setTextSize(mTextSize);
        }


        if(times == maxLines)
        {
            times += 2;
        }
        else if(times < maxLines){
            times = maxLines+2;
        }

        if(mTextStyle != -1){
            setTextStyle(mTextStyle);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        setText(mText);
    }

    public void setText(String text){
        reset();

        if(!TextUtils.isEmpty(text)){
            customExpandTv.setText(text);

            customExpandTv.post(() -> {
                Layout l = customExpandTv.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
//                            LogUtil.d("lines = " + lines);
                    if (lines > 0) {
                        if (l.getEllipsisCount(lines - 1) > 0) {
//                                    LogUtil.d( "Text is ellipsized");
                            /**
                             * 删除了原来的上下扩展功能
                             */
//                            setExpandable(true);
                            setExpandable(false);
                        }
                        else{
                            setExpandable(false);
                        }
                    }
                } else {
                    LogUtil.d( "Layout is null");
                }
            });

        }
    }

    public void reset(){

        if(isExpanded){
            shrink();
        }
        setExpandable(false);

    }


    @Override
    protected void initListener() {
        super.initListener();
        /**
         * 删除了原来的上下扩展功能
         */
        customExpandIcon.setOnClickListener(this);
//        customExpandTv.setOnClickListener(this);

        customExpandTv.setOnLongClickListener(v -> {
            CustomContentTextDialog.showContent(getContext(), customExpandTv.getText().toString());
            return true;
        });
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomExpandableTextView);
            mText = array.getString(R.styleable.CustomExpandableTextView_text);
            mTextSize = array.getInt(R.styleable.CustomExpandableTextView_text_size, 0);
            mTextColor = array.getColor(R.styleable.CustomExpandableTextView_text_color, 0);
            mTextStyle = array.getInt(R.styleable.CustomExpandableTextView_text_style, -1);
            times = array.getInt(R.styleable.CustomExpandableTextView_expand_times, 2);
            maxLines = array.getInt(R.styleable.CustomExpandableTextView_max_lines, 2);
            array.recycle();
        }
    }

    public void setTextStyle(int textStyle){
        customExpandTv.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
        if(expandable){
            customExpandIcon.setVisibility(VISIBLE);
            customExpandTv.setClickable(true);
        }
        else{
            customExpandIcon.setVisibility(GONE);
            /**
             * 删除了原来的上下扩展功能
             */
//            customExpandTv.setClickable(false);
            customExpandTv.setClickable(true);
        }
    }


    @Override
    public void onClick(View v) {
//        LogUtil.d("onclick");
//        if(!isExpanding) {
//            if (isExpanded) {
//                shrink();
//            } else {
//                expand();
//            }
//        }

        onChildViewClick(v, 0);
    }

    private void shrink(){
        isExpanding = true;
        int height = customExpandTv.getHeight();
        AnimationUtil.startAnimation(customExpandTv, height, height/times, ()->{
            customExpandTv.setSingleLine(true);
            customExpandIcon.setImageResource(R.drawable.ic_expand);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) customExpandTv.getLayoutParams();
            lp.rightMargin = DisplayUtil.dip2px(30, getContext());
            customExpandTv.setLayoutParams(lp);
            customExpandIcon.setPadding(
                    DisplayUtil.dip2px(10, getContext()),
                    DisplayUtil.dip2px(5, getContext()),
                    0,
                    0);
            isExpanded = false;
            isExpanding = false;
        });
    }

    private void expand() {
        isExpanding = true;
        int height = customExpandTv.getHeight();

        customExpandTv.setSingleLine(false);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) customExpandTv.getLayoutParams();
        lp.rightMargin = 0;
        customExpandTv.setLayoutParams(lp);
        customExpandIcon.setPadding(
                DisplayUtil.dip2px(10, getContext()),
                DisplayUtil.dip2px(5, getContext()),
                0,
                0);
        if(maxLines!=0){
            customExpandTv.setMaxLines(maxLines);
        }

        AnimationUtil.startAnimation(customExpandTv, height, height * times, ()->{
            customExpandIcon.setImageResource(R.drawable.ic_shrink);
            isExpanded = true;
            isExpanding = false;
        });
    }

}
