package com.supcon.mes.mbap.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;

import java.util.List;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomScanView extends BaseRelativeLayout {

    TextView customScanText;

    ImageView customScanDelete;

    AutoCompleteTextView customScanInput;


    public CustomScanView(Context context) {
        super(context);
    }

    public CustomScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customScanText.setTypeface(newFont);
            customScanInput.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_scan;
    }

    @Override
    protected void initView() {
        super.initView();
        customScanText = findViewById(R.id.customScanText);
        customScanDelete = findViewById(R.id.customScanDelete);
        customScanInput = findViewById(R.id.customScanInput);

    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

//        if(attrs!=null) {
//            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
//            mText = array.getString(R.styleable.CustomEditText_edit_text);
//            mHint = array.getString(R.styleable.CustomEditText_edit_hint);
//            deleteIconResId = array.getResourceId(R.styleable.CustomEditText_edit_delete, R.drawable.btn_delete);
//            array.recycle();
//        }
    }

    public EditText editText() {
        return customScanInput;
    }

    @Override
    protected void initListener() {
        super.initListener();
        customScanDelete.setOnClickListener(v -> customScanInput.getText().clear());

        customScanInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    customScanDelete.setVisibility(View.VISIBLE);
                }
                else{
                    customScanDelete.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void setText(String text){
        customScanText.setText(text);
    }

    public void setInput(String input){
        customScanInput.setText(input);
    }

    public void setHint(String hint){
        customScanInput.setHint(hint);
    }

    public String getInput(){
        return customScanInput.getText().toString();
    }


    @Override
    protected void initData() {
        super.initData();
    }

    public void initWorks(List<String> words){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, words);
        customScanInput.setAdapter(adapter);
    }
}
