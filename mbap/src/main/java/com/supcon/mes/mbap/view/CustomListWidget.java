package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.com_http.BaseEntity;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;

import java.util.List;

/**
 * Created by wangshizhan on 2018/1/17.
 * Email:wangshizhan@supcon.com
 */

public class CustomListWidget<T extends BaseEntity> extends BaseRelativeLayout {

    ImageView customListWidgetIc, customListWidgetAdd, customListWidgetEdit;
    TextView customListWidgetName;
    TextView customListWidgetViewAll;
    RecyclerView contentView;
    RelativeLayout customListWidgetTitle;

    int mTextSize, iconRes, mNum, mTotal, mTitleBgColor;
    String mText;
    BaseListDataRecyclerViewAdapter<T> mBaseListDataRecyclerViewAdapter;
    private List<T> mData;
    private boolean isAddable;
    private boolean isEditable;

    public static final int ACTION_VIEW_ALL = 1;
    public static final int ACTION_EDIT = 2;
    public static final int ACTION_ADD = 3;
    public static final int ACTION_ITEM_DELETE = 4;

    public CustomListWidget(Context context) {
        super(context);
    }

    public CustomListWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_list_widget;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customListWidgetName.setTypeface(newFont);
            customListWidgetViewAll.setTypeface(newFont);
        }
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomListWidget);
            mTextSize   = array.getInt(R.styleable.CustomListWidget_text_size, 0);
            iconRes    = array.getResourceId(R.styleable.CustomListWidget_icon_res, 0);
            mNum      = array.getInt(R.styleable.CustomListWidget_widget_num, 0);
            mTotal      = array.getInt(R.styleable.CustomListWidget_total, 0);
            mText       = array.getString(R.styleable.CustomListWidget_widget_name);
            mTitleBgColor = array.getColor(R.styleable.CustomListWidget_title_bg_color, 0);
            isAddable = array.getBoolean(R.styleable.CustomListWidget_is_addable, false);
            isEditable = array.getBoolean(R.styleable.CustomListWidget_is_editable, false);
            array.recycle();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customListWidgetIc = findViewById(R.id.customListWidgetIc);
        customListWidgetAdd = findViewById(R.id.customListWidgetAdd);
        customListWidgetEdit = findViewById(R.id.customListWidgetEdit);
        customListWidgetName = findViewById(R.id.customListWidgetName);
        customListWidgetViewAll = findViewById(R.id.customListWidgetViewAll);
        customListWidgetTitle = findViewById(R.id.customListWidgetTitle);
        contentView = findViewById(R.id.contentView);
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(1, getContext())));

//        if(!TextUtils.isEmpty(mText)){
//            if(mTotal!=0){
//                customListWidgetName.setText(mText+"("+mTotal+")");
//            }
//            else {
//                customListWidgetName.setText(mText);
//            }
//        }
//
//        if(mTextSize!=0){
//            customListWidgetName.setTextSize(mTextSize);
//            customListWidgetViewAll.setTextSize(mTextSize);
//        }
//
//        if(iconRes!=0){
//            customListWidgetIc.setVisibility(VISIBLE);
//            customListWidgetIc.setImageResource(iconRes);
//        }

        setIconRes(iconRes);
        setTextSize(mTextSize);
        setName(mText);
        setTotal(mTotal);
        setShowNum(mNum);
        setTitleBgColor(mTitleBgColor);
        setEditable(isEditable);
        setAddable(isAddable);
    }

    @Override
    protected void initListener() {
        super.initListener();
        rootView.setOnClickListener(v -> onChildViewClick(v, 0,  null));
        customListWidgetViewAll.setOnClickListener(v -> onChildViewClick(v, ACTION_VIEW_ALL,  null));
        customListWidgetAdd.setOnClickListener(v -> onChildViewClick(v, ACTION_ADD, null));
        customListWidgetEdit.setOnClickListener(v -> onChildViewClick(v, ACTION_EDIT, null));


    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void setShowNum(int num){
        this.mNum = num;
    }

    public void setTitleBgColor(int color){
        this.mTitleBgColor = color;
        customListWidgetTitle.setBackgroundColor(color);
    }

    public void setEditable(boolean isEditable){
        this.isEditable = isEditable;
        customListWidgetEdit.setVisibility(isEditable?VISIBLE:GONE);
    }

    public void setAddable(boolean isAddable){
        this.isAddable = isAddable;
        customListWidgetAdd.setVisibility(isAddable?VISIBLE:GONE);
    }

    public void setTotal(int total){
        this.mTotal = total;
        if(mTotal!=0){
            customListWidgetViewAll.setText("查看全部 ("+mTotal+")");
        }
        else {
            customListWidgetViewAll.setText("查看全部");
        }
    }

    public void setName(String name){
        mText = name;
        if(!TextUtils.isEmpty(mText)){
            customListWidgetName.setText(mText);
        }
    }

    public void setTextSize(int textSize){
        this.mTextSize = textSize;
        if(mTextSize!=0){
            customListWidgetName.setTextSize(mTextSize);
        }
    }

    public void setIconRes(int iconRes){
        this.iconRes  = iconRes;
        if(iconRes!=0){
            customListWidgetIc.setVisibility(VISIBLE);
            customListWidgetIc.setImageResource(iconRes);
        }
        else{
            customListWidgetIc.setVisibility(GONE);
        }
    }

    public  void setAdapter(BaseListDataRecyclerViewAdapter<T> adapter){
        mBaseListDataRecyclerViewAdapter = adapter;
        contentView.setAdapter(adapter);
    }

    public void setData(List<T> data){
        if(data==null || data.size() == 0){
            return;
        }

        if(mBaseListDataRecyclerViewAdapter == null){
            throw new IllegalArgumentException("You should init adapter first!");
        }
        this.mData = data;
        List<T> showData;
        if(mNum!=0 && data.size() >= mNum)
            showData = data.subList(0, mNum);
        else
            showData = data;

        mBaseListDataRecyclerViewAdapter.setList(showData);
        mBaseListDataRecyclerViewAdapter.notifyDataSetChanged();
    }


    public BaseListDataRecyclerViewAdapter getBaseListDataRecyclerViewAdapter() {
        return mBaseListDataRecyclerViewAdapter;
    }

    public List<T> getData() {
        return mData;
    }

    public void clear(){
        if(mBaseListDataRecyclerViewAdapter != null){
            mBaseListDataRecyclerViewAdapter.getList().clear();
            mBaseListDataRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void setShowText(String showText){
        customListWidgetViewAll.setText(showText);
    }

}
