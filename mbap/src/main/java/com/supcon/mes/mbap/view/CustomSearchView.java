package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.ScreenUtil;
import com.supcon.mes.mbap.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomSearchView extends BaseRelativeLayout {
    public enum MODE {
        //搜索模式
        COMMON,
        SELECT_TYPE,
    }

    ImageView customSearchIcon, customSearchScan;

    ImageView customSearchDelete;

    AutoCompleteTextView customSearchInput;

    private String mHint;
    private int mTextSize;
    private int mBgColor, mTextColor;
    private Drawable mInputBgColor;
    private OnItemSelectedListener mOnItemSelectedListener;
    private boolean isLightMode = false;
    boolean editable = true;

    private LinearLayout selectType;
    private TextView tvSelectType;
    private ImageView ivSelectType;

    private List<String> selectedTypes = new ArrayList<>();

    private CustomPopupWindow customPopupWindow;

    private Boolean selectTypeEnabled;

    private OnTypeSelectListener onTypeSelectListener;

    public void setOnTypeSelectListener(OnTypeSelectListener onTypeSelectListener) {
        this.onTypeSelectListener = onTypeSelectListener;
    }

    public Boolean getSelectTypeEnabled() {
        return selectTypeEnabled;
    }

    public void setSelectTypeTextColorRes(@ColorRes int colorRes) {
        tvSelectType.setTextColor(getResources().getColor(colorRes));
    }

    public void setSelectTypeEnabled(Boolean selectTypeEnabled) {
        this.selectTypeEnabled = selectTypeEnabled;
        selectType.setVisibility(selectTypeEnabled?VISIBLE:GONE);
    }

    public CustomSearchView(Context context) {
        super(context);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customSearchInput.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_search;
    }

    @Override
    protected void initView() {
        super.initView();
        customSearchIcon = findViewById(R.id.customSearchIcon);
        customSearchDelete = findViewById(R.id.customSearchDelete);
        customSearchInput = findViewById(R.id.customSearchInput);
        customSearchScan = findViewById(R.id.customSearchScan);

        selectType = findViewById(R.id.selectType);
        tvSelectType = findViewById(R.id.tvSelectType);
        ivSelectType = findViewById(R.id.ivSelectType);

        selectType.requestDisallowInterceptTouchEvent(true);
        if (!TextUtils.isEmpty(mHint)) {
            customSearchInput.setHint(mHint);
        }

        if (mTextSize != 0) {
            customSearchInput.setTextSize(mTextSize);
        }

        if (mBgColor != 0) {
            rootView.setBackgroundColor(mBgColor);
        }

        if (mTextColor != 0) {
            customSearchInput.setTextColor(mTextColor);
        }

        if (mInputBgColor != null) {
            ((ViewGroup) customSearchInput.getParent()).setBackground(mInputBgColor);
        }

    }


    public void initWords(List<String> words) {

        if (words != null && words.size() != 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_search, words);
            customSearchInput.setAdapter(adapter);
        }

    }


    public ImageView searchView() {

        return customSearchIcon;

    }

    public void setBgColor(int bgColor) {
        this.mBgColor = bgColor;
        rootView.setBackgroundColor(bgColor);
    }

    public void setInputBgColor(Drawable bg) {
        this.mInputBgColor = bg;
        ((ViewGroup) customSearchInput.getParent()).setBackground(mInputBgColor);
    }

    public void setLightMode() {
        customSearchIcon.setImageResource(R.drawable.ic_search_99);
//        customSearchScan.setImageResource(R.drawable.ic_scanning_99);
        customSearchScan.setImageResource(R.drawable.ic_search_99);
        customSearchDelete.setImageResource(R.drawable.ic_close_99);
        ((ViewGroup) customSearchInput.getParent()).setBackground(getResources().getDrawable(R.drawable.sh_search));
        isLightMode = true;
    }

    public void setDarkMode() {
        customSearchIcon.setImageResource(R.drawable.ic_search_cc);
//        customSearchScan.setImageResource(R.drawable.ic_scanning_cc);
        customSearchScan.setImageResource(R.drawable.ic_search_cc);
        customSearchDelete.setImageResource(R.drawable.ic_close_cc);
        ((ViewGroup) customSearchInput.getParent()).setBackground(getResources().getDrawable(R.drawable.sh_search_black));
        isLightMode = false;
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSearchView);
            mHint = array.getString(R.styleable.CustomSearchView_search_hint);
            mTextSize = array.getInt(R.styleable.CustomSearchView_search_text_size, 0);
            mBgColor = array.getColor(R.styleable.CustomSearchView_search_bg_color, 0);
            mInputBgColor = array.getDrawable(R.styleable.CustomSearchView_search_text_bg_color);
            mTextColor = array.getColor(R.styleable.CustomSearchView_search_text_color, 0);
            array.recycle();
        }
    }
    public void setDefaultTypeSelect(String defaultContent) {
        this.tvSelectType.setText(defaultContent);
    }

    public void setTypeSelectContent(List<String> contentList){
        this.selectedTypes = contentList;
    }

    public EditText editText() {
        return customSearchInput;
    }
    public void setTypeSearchEnable(boolean enable) {
        selectType.setClickable(enable);
    }
    @Override
    protected void initListener() {
        super.initListener();
        customSearchDelete.setOnClickListener(v -> customSearchInput.getText().clear());

        selectType.setOnClickListener(v -> {
            if (selectedTypes == null || selectedTypes.size() <= 0) return;
            customPopupWindow = new CustomPopupWindow(context);
            customPopupWindow.setOnToggleListener(() -> {
//                if(customPopupWindow.isShowing()) {
//                    ivSelectType.setImageResource(R.drawable.display_up);
//                }else {
//                    ivSelectType.setImageResource(R.drawable.display_down);
//                }
            });
            for (String content : selectedTypes) {
                customPopupWindow.bindAnchorView(tvSelectType)
                        .offsetX(ViewUtil.dpToPx(context,-2))
                        .offsetY(ViewUtil.dpToPx(context,10))
                        .bindClickListener(content, v1 -> {
                            if(onTypeSelectListener!= null)
                            onTypeSelectListener.onTypeSelect(content);
                            tvSelectType.setText(content);
                            customPopupWindow.dismiss();
                        });
            }
            customPopupWindow.show();
        });

        customSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (editable) {
                        customSearchDelete.setVisibility(View.VISIBLE);
                    }
                } else {
                    customSearchDelete.setVisibility(View.INVISIBLE);
                }
            }
        });

        customSearchInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) customSearchInput.getAdapter().getItem(position);

                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onItemSelect(s);
                }
            }
        });

        customSearchInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!isLightMode) {
                        setLightMode();
                    }
                } else {
                    if (isLightMode) {
                        setDarkMode();
                    }
                }
            }
        });

    }


    public void setInput(String input) {
        customSearchInput.setText(input);
    }

    public void setInputTextColor(int color) {
        customSearchInput.setTextColor(color);
    }

    public void setHint(String hint) {
        customSearchInput.setHint(hint);
    }

    public String getInput() {
        return customSearchInput.getText().toString();
    }

    public void setEditable(boolean edit) {
        editable = edit;
        customSearchInput.setEnabled(edit);
    }

    @Override
    protected void initData() {
        super.initData();

    }


    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {

        customSearchInput.setAdapter(adapter);

    }

    public void setOnListItemSelectedListener(OnItemSelectedListener listener) {

        mOnItemSelectedListener = listener;

    }

    public interface OnItemSelectedListener {


        void onItemSelect(String s);

    }

    public interface OnTypeSelectListener{
        void onTypeSelect(String type);
    }
}
