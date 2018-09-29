package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.OnContentCheckListener;
import com.supcon.mes.mbap.listener.OnTextListener;
import com.supcon.mes.mbap.utils.TextHelper;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomVerticalEditText extends BaseLinearLayout implements View.OnTouchListener {

    TextView customEditText;
    ImageView customEditDelete;
    EditText customEditInput;

    private String mText;
    private String mHint;
    private String mGravity;
    private OnTextListener mTextListener;

    private int mTextSize, mPadding;
    private int deleteIconResId, maxLength, maxLine;
    private int mTextColor, mHintColor;
    private boolean isNecessary, isEditable, isBold;
    private int mTextHeight, mTextWidth;
    private OnContentCheckListener mOnContentCheckListener;

    public CustomVerticalEditText(Context context) {
        super(context);
    }

    public CustomVerticalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customEditText.setTypeface(newFont);
            customEditInput.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_vertical_edittext;
    }

    @Override
    protected void initView() {
        super.initView();
        customEditText = findViewById(R.id.customEditText);
        customEditDelete = findViewById(R.id.customEditDelete);
        customEditInput = findViewById(R.id.customEditInput);

        if (!TextUtils.isEmpty(mText)) {
            customEditText.setText(mText);
            customEditText.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(mHint)) {
            customEditInput.setHint(mHint);
        }

        if (deleteIconResId != 0) {
            customEditDelete.setImageResource(deleteIconResId);
        }

        if (maxLength != 0) {
            customEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }

        if (mTextSize != 0) {
            customEditInput.setTextSize(mTextSize);
//            customEditText.setTextSize(mTextSize);
        }

        if (maxLine != 0) {
            customEditInput.setMaxLines(maxLine);
        }

        if (mPadding != 0)
            customEditInput.setPadding(mPadding, mPadding, mPadding, mPadding);

        if (!TextUtils.isEmpty(mGravity)) {

            int gravity = Gravity.NO_GRAVITY;

            if (mGravity.contains("center_horizontal")) {
                gravity = Gravity.CENTER_HORIZONTAL;
            } else if (mGravity.contains("center_vertical")) {
                gravity = Gravity.CENTER_VERTICAL;
            } else if (mGravity.contains("center")) {
                gravity = Gravity.CENTER;
            }

            if (mGravity.contains("top")) {
                gravity = gravity | Gravity.TOP;
            }

            if (mGravity.contains("left")) {
                gravity = gravity | Gravity.LEFT;
            }

            if (mGravity.contains("right")) {
                gravity = gravity | Gravity.RIGHT;
            }

            if (mGravity.contains("bottom")) {
                gravity = gravity | Gravity.BOTTOM;
            }

            setInputGravity(gravity);

        }

        if (mPadding != 0) {
            customEditInput.setPadding(mPadding, mPadding, mPadding, mPadding);

        }

        if (mTextColor != 0)
            customEditText.setTextColor(mTextColor);

        if (isNecessary)
            setNecessary(isNecessary);
        if (mTextHeight != -1) {
            setTextHeight(mTextHeight);
        }

        if (mTextWidth != -1) {
            setTextWidth(mTextWidth);
        }

        if (mHintColor != 0) {
            setHintColor(mHintColor);
        }

        setEditable(isEditable);

        if (isBold)
            setContentTextStyle(Typeface.BOLD);
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomVerticalEditText);
            mText = array.getString(R.styleable.CustomVerticalEditText_text);
            mHint = array.getString(R.styleable.CustomVerticalEditText_edit_hint);
            mGravity = array.getString(R.styleable.CustomVerticalEditText_gravity);
            mTextSize = array.getInt(R.styleable.CustomVerticalEditText_text_size, 0);
            deleteIconResId = array.getResourceId(R.styleable.CustomEditText_edit_delete, R.drawable.ic_delete);
            maxLength = array.getDimensionPixelSize(R.styleable.CustomEditText_edit_maxLength, 0);
            maxLine = array.getInt(R.styleable.CustomEditText_edit_maxLine, 0);
            mPadding = array.getDimensionPixelSize(R.styleable.CustomVerticalEditText_padding, 0);
            mTextColor = array.getColor(R.styleable.CustomVerticalEditText_text_color, 0);
            isNecessary = array.getBoolean(R.styleable.CustomVerticalEditText_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomVerticalEditText_editable, true);
            mTextHeight = array.getDimensionPixelSize(R.styleable.CustomVerticalEditText_text_height, -1);
            mTextWidth = array.getDimensionPixelSize(R.styleable.CustomVerticalEditText_text_width, -1);
            mHintColor = array.getColor(R.styleable.CustomVerticalEditText_edit_hint_color, 0);
            isBold = array.getBoolean(R.styleable.CustomVerticalEditText_bold, false);
            array.recycle();
        }
    }

    public EditText editText() {
        return customEditInput;
    }

    public TextView textView() {
        return customEditText;
    }

    public void setNecessary(boolean isNecessary) {

//        if(isNecessary){
//            customEditText.setTextColor(getResources().getColor(R.color.customRed));
//        }
//        else {
//            customEditText.setTextColor(getResources().getColor(MBapConfig.NECESSARY_FALSE_COLOR));
//        }
        TextHelper.setRequired(isNecessary, customEditText);
    }

    public void setTextStyle(int textStyle) {
        customEditText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle) {
        customEditInput.setTypeface(Typeface.defaultFromStyle(textStyle));
    }


    public void setTextWidth(int width) {
        ViewGroup.LayoutParams lp = customEditText.getLayoutParams();
        lp.width = width;
        customEditText.setLayoutParams(lp);

    }

    public void setTextHeight(int height) {
        ViewGroup.LayoutParams lp = customEditText.getLayoutParams();
        lp.height = height;
        customEditText.setLayoutParams(lp);


        ViewGroup.LayoutParams lp2 = customEditDelete.getLayoutParams();
        lp2.height = height;
        customEditDelete.setLayoutParams(lp2);
    }

    public void setInputTextSize(int textSize) {
        customEditInput.setTextSize(textSize);
    }

    @Override
    protected void initListener() {
        super.initListener();

        customEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && isEditable) {
                    customEditDelete.setVisibility(View.VISIBLE);
                } else {
                    customEditDelete.setVisibility(View.INVISIBLE);
                }

                if (mTextListener != null) {
                    mTextListener.onText(s.toString());
                }
            }
        });

        customEditDelete.setOnClickListener(v -> customEditInput.getText().clear());

        customEditInput.setOnTouchListener(this);

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.customEditInput && canVerticalScroll(customEditInput))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(enabled);
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
        customEditInput.setEnabled(editable);

        if (editable) {
            customEditInput.setFocusable(true);
        } else {
            customEditInput.setFocusable(false);
            customEditInput.setPadding(0, 0, DisplayUtil.dip2px(8, getContext()), 0);
        }
    }

    //新加不可编辑方法
    public void setCompile(boolean editable) {
        isEditable = editable;
        customEditInput.setEnabled(editable);

        if (!editable) {
            customEditInput.setPadding(0, 0, DisplayUtil.dip2px(8, getContext()), 0);
        }
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            customEditText.setVisibility(VISIBLE);
            customEditText.setText(text);
        }
    }

    public String getText() {
        return customEditText.getText().toString();
    }

    public void setInput(String input) {
        customEditInput.setText(input);
        if (!TextUtils.isEmpty(input))
            customEditInput.setSelection(input.length());
    }

    public void setHint(String hint) {
        customEditInput.setHint(hint);
    }

    public String getInput() {
        return customEditInput.getText().toString();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        customEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setHintColor(int color) {
        customEditInput.setHintTextColor(color);
    }

    public void setInputGravity(int gravity) {
        customEditInput.setGravity(gravity);
    }

    public void setInputType(int type) {
        customEditInput.setInputType(type);
    }


    public void setTextListener(OnTextListener textListener) {
        mTextListener = textListener;
    }


    public void setImeOptions(int imeOptions) {
        customEditInput.setImeOptions(imeOptions);
    }

}
