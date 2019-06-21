package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.CustomRadioButtonAdapter;
import com.supcon.mes.mbap.beans.RadioBean;
import com.supcon.mes.mbap.utils.TextHelper;

import java.util.List;

/**
 * Created by wangshizhan on 2019/6/20
 * Email:wangshizhan@supcom.com
 */
public class CustomRadioButton extends BaseLinearLayout {

    TextView customRadioButtonText;
    RecyclerView contentView;
    CustomRadioButtonAdapter customRadioButtonAdapter;
    private boolean isVertical = false;
    private List<RadioBean> checkDatas;
    private static final int DEFAULT_SPAN_NUM = 2;
    private int spanCount ;

    private boolean isNecessary;
    private int mKeyColor, mKeyTextSize, mContentTextColor, mContentTextSize;
    private String mText;

    public CustomRadioButton(Context context) {
        super(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomRadioButton);
            mText= array.getString(R.styleable.CustomRadioButton_key);
            mKeyTextSize =  array.getInt(R.styleable.CustomRadioButton_key_size, 0);
            mKeyColor = array.getColor(R.styleable.CustomRadioButton_key_color, 0);
            mContentTextSize =  array.getInt(R.styleable.CustomRadioButton_content_size, 0);
            mContentTextColor = array.getColor(R.styleable.CustomRadioButton_content_color, 0);
            spanCount = array.getInt(R.styleable.CustomRadioButton_span_num, DEFAULT_SPAN_NUM);
            isNecessary = array.getBoolean(R.styleable.CustomRadioButton_necessary, false);
            isVertical = array.getBoolean(R.styleable.CustomRadioButton_vertical, false);
            array.recycle();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_radio_button;
    }

    @Override
    protected void initView() {
        super.initView();
        customRadioButtonText = findViewById(R.id.customRadioButtonText);
        contentView = findViewById(R.id.contentView);

        if(!TextUtils.isEmpty(mText)){
            customRadioButtonText.setVisibility(VISIBLE);
            customRadioButtonText.setText(mText);
        }

        if(mKeyColor!=0){
            customRadioButtonText.setTextColor(mKeyColor);
        }

        if(mKeyTextSize != 0){
            customRadioButtonText.setTextSize(mKeyTextSize);
        }


//        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
//        contentView.setLayoutManager(layoutManager);
        setVertical(isVertical);
        customRadioButtonAdapter = new CustomRadioButtonAdapter(context);
        contentView.setAdapter(customRadioButtonAdapter);


        if(mContentTextColor!=0){
            customRadioButtonAdapter.setTextColor(mContentTextColor);
        }

        if(mContentTextSize!=0){
            customRadioButtonAdapter.setTextSize(mContentTextSize);
        }

        setNecessary(isNecessary);
    }

    @Override
    protected void initListener() {
        super.initListener();
        customRadioButtonAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                LogUtil.d("position:"+position+" isChecked:"+action);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void setNecessary(boolean isNecessary){

        TextHelper.setRequired(isNecessary, customRadioButtonText);
    }

    public void setCheckDatas(List<RadioBean> checkDatas) {
        this.checkDatas = checkDatas;
        customRadioButtonAdapter.setList(checkDatas);
        customRadioButtonAdapter.notifyDataSetChanged();
    }

    public List<RadioBean> getCheckDatas() {
        return checkDatas;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
        if(isVertical) {
            updateLayout(1);
        }
        else{
            updateLayout(spanCount);
        }

    }

    private void updateLayout(int spanCount) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        contentView.setLayoutManager(layoutManager);
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        updateLayout(spanCount);
    }
}
