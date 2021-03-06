package com.supcon.mes.mbap.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.OnTitleSearchExpandListener;
import com.supcon.mes.mbap.utils.AnimationUtil;
import com.supcon.mes.mbap.utils.KeyboardUtil;
import com.supcon.mes.mbap.utils.ViewUtil;



import java.util.List;

/**
 * Environment: hongruijun
 * Created by Xushiyun on 2018/5/7.
 * Desc:横向展开标题栏
 */
public class CustomHorizontalSearchTitleBar extends BaseRelativeLayout {
    /**
     * 标题
     */
    private TextView title;
    /**
     * 搜索栏
     */
    private CustomSearchView searchView;
    /**
     * 取消按钮
     */
    private TextView cancel;
    /**
     * 搜索栏中的编辑框
     */
    private EditText editText;
    /**
     * 标题+返回按钮
     */
    private RelativeLayout rlLeft;
    /**
     * 根布局
     */
    private RelativeLayout rlRootView;
    /**
     * 右侧功能键按钮
     */
    private ImageButton leftBtn;
    /**
     * 功能按钮
     */
    private ImageButton rightBtn;
    /**
     * 是否启用功能按钮
     */
    private boolean rightBtnEnable = false;

    private int rightBtnRes;

    private List<String> contentList;

    private boolean autoSearch;


    public enum ViewType {
        //
        BACK,
        RIGHT_BTN
    }

    private boolean enableMultFuncMode;

    private int left_width = -1;
    private int right_width = -1;

    private final static float ALPHA_ON_SHOW = 1.0f;
    private final static float ALPHA_ON_CANCEL = 1.0f;

    private boolean enableRemainMode = false;

    private boolean isExpand = false;

    private static final int ANIMATION_DURATION = 200;
    private static final int ANIMATION_DURATION_FADE = 40;
    private static final int ORIGIN_PADDING_LEFT_IN_DIP = 10;
    @SuppressWarnings("unused")
    private static final int ORIGIN_PADDING_RIGHT_IN_DIP = 10;
    private static final int END_PADDING_RIGHT_IN_DIP = 50;

    private static final int ORIGIN_PADDING_RIGHT_IN_DIP_ENABLE_MODE = 50;
    private static final int END_PADDING_RIGHT_IN_DIP_ENABLE_MODE = 50;

    private int currentPaddingRightOrigin;
    private int currentPaddingRightEnd;

    private int titleBarBackgroundColor;
    private String titleText;
    private int titleLength;

    private CallBack mCallBack;
    private DisplayCallback mDisplayCallback;

    private CustomPopupWindow customPopupWindow;

    private OnTitleSearchExpandListener mExpandListener;

    private int startColor;

    private int endColor;
    private int popWindowOffX;
    private int popWindowOffy;

    public void enableRemainMode() {
        enableRemainMode = true;
    }

    public void disableRemainMode() {
        enableRemainMode = false;
    }

    public void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public void setDisplayCallBack(DisplayCallback displayCallBack) {
        this.mDisplayCallback = displayCallBack;
    }

    public void setOnExpandListener(OnTitleSearchExpandListener mExpandListener) {
        this.mExpandListener = mExpandListener;
    }

    public CustomHorizontalSearchTitleBar popWindowOffset(int x, int y) {
        popWindowOffX = x;
        popWindowOffy = y;
        return this;
    }

    public CustomHorizontalSearchTitleBar popOffsetX(int x) {
        popWindowOffX = x;
        return this;
    }

    public CustomHorizontalSearchTitleBar popOffsetY(int y) {
        popWindowOffy = y;
        return this;
    }

    public void setAutoSearch(boolean autoSearch) {
        this.autoSearch = autoSearch;
    }

    public CustomHorizontalSearchTitleBar(Context context) {
        this(context, null);
    }

    public CustomHorizontalSearchTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        enableRightBtn();
        searchView.setTypeSearchEnable(false);
    }

    public boolean getStatus() {
        return isExpand;
    }

    public ImageButton leftBtn() {
        return leftBtn;
    }

    public TextView title() {
        return title;
    }

    public void enableRightBtn() {
        this.rightBtnEnable = true;
        onRightBtnChanged();
    }

    public ImageButton rightBtn() {
        return rightBtn;
    }

    private void onRightBtnChanged() {
        currentPaddingRightOrigin = rightBtnEnable ? ORIGIN_PADDING_RIGHT_IN_DIP_ENABLE_MODE : ORIGIN_PADDING_RIGHT_IN_DIP;
        currentPaddingRightEnd = rightBtnEnable ? END_PADDING_RIGHT_IN_DIP_ENABLE_MODE : END_PADDING_RIGHT_IN_DIP;
        ViewUtil.setPaddingRight(searchView, ViewUtil.dpToPx(getContext(), currentPaddingRightOrigin));
        rightBtn.setVisibility(rightBtnEnable ? View.VISIBLE : View.GONE);
    }

    @SuppressWarnings("unused")
    public void disableRightBtn() {
        this.rightBtnEnable = false;
        onRightBtnChanged();
    }

    @SuppressWarnings("unused")
    public CustomSearchView searchView() {
        return searchView;
    }

    @SuppressWarnings("unused")
    public TextView cancel() {
        return cancel;
    }

    public EditText editText() {
        return editText;
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomHorizontalSearchTitleBar);
            titleBarBackgroundColor = array.getResourceId(R.styleable.CustomHorizontalSearchTitleBar_title_background_color, -1);
            titleText = array.getString(R.styleable.CustomHorizontalSearchTitleBar_title_text);
            rightBtnEnable = array.getBoolean(R.styleable.CustomHorizontalSearchTitleBar_title_right_btn_need, false);
            titleLength = array.getDimensionPixelSize(R.styleable.CustomHorizontalSearchTitleBar_title_length, 0);
            startColor = array.getColor(R.styleable.CustomHorizontalSearchTitleBar_start_color, -1);
            endColor = array.getColor(R.styleable.CustomHorizontalSearchTitleBar_end_color, -1);
            rightBtnRes = array.getResourceId(R.styleable.CustomHorizontalSearchTitleBar_right_btn_res, -1);
            enableMultFuncMode = array.getBoolean(R.styleable.CustomHorizontalSearchTitleBar_enable_multi_func, false);
            array.recycle();
        }
    }


    private void bindListener(ViewType viewType, OnClickListener onClickListener) {
        switch (viewType) {
            case BACK:
                leftBtn.setOnClickListener(onClickListener);
                break;
            case RIGHT_BTN:
                rightBtn.setOnClickListener(onClickListener);
                break;
            default:
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
         boolean result = super.dispatchTouchEvent(ev);
         return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float rawX = event.getRawX();
        final float rawY = event.getRawY();
        float viewX = getX();
        float viewY = getY();
        int width = getWidth();
        int height = getHeight();
        if(rawX<viewX||rawX>viewX+width||rawY<viewY||rawY>viewY+height) {
            if(editText.hasFocus())
            editText.clearFocus();
        }
//        return super.onTouchEvent(event);
        return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_horizontal_search_title_bar;
    }

    @Override
    protected void initView() {
        super.initView();
        searchView = findViewById(R.id.customSearchView);//搜索框
        ViewUtil.setBrightness(searchView, ALPHA_ON_CANCEL);//设置搜索框透明度
        cancel = findViewById(R.id.cancelBtn);//取消按钮
        title = findViewById(R.id.titleText);//标题框
        rlRootView = findViewById(R.id.rlRootView);//根布局

        //设置背景颜色
        if (titleBarBackgroundColor != -1) {
            rlRootView.setBackgroundResource(titleBarBackgroundColor);
        }
        //设置渐变背景颜色
        if (startColor != -1 && endColor != -1)
            rlRootView.setBackground(ViewUtil.genGradientDrawable(startColor, endColor));

        //获取搜索框中的编辑框
        editText = searchView.editText();
        //设置编辑模式
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //标题+返回按钮
        rlLeft = findViewById(R.id.rlLeft);
        //返回按钮
        leftBtn = findViewById(R.id.leftBtn);
        //功能按钮
        rightBtn = findViewById(R.id.rightBtn);
        //标题
        title.setText(titleText);
        //设置右侧按钮的可用性
        if (rightBtnEnable) {
            enableRightBtn();
        } else {
            disableRightBtn();
        }
        if (enableMultFuncMode && rightBtnEnable) {
            customPopupWindow = new CustomPopupWindow(context);
            rightBtn.setOnClickListener(v -> customPopupWindow.showAsDropDown(rightBtn, popWindowOffX, popWindowOffy));
            rightBtn.setImageResource(R.drawable.more_fun);
        }
        if (rightBtnRes != -1) {
            rightBtn.setImageResource(rightBtnRes);
        }

        //设置标题宽度
        if (titleLength != 0) {
            ViewGroup.LayoutParams lp = title.getLayoutParams();
            lp.width = titleLength;
            title.setLayoutParams(lp);

            searchView.setPadding(DisplayUtil.dip2px(60, getContext()) + titleLength, 0, 0, 0);
        }
    }

    public void hidePopupWindow() {
        customPopupWindow.dismiss();
    }

    public CustomPopupWindow popupWindow() {
        return customPopupWindow;
    }

    public CustomHorizontalSearchTitleBar bindNewRightBtnFunc(String content, OnClickListener onClickListener) {
        if (enableMultFuncMode && rightBtnEnable) {
            customPopupWindow.bindClickListener(content, onClickListener);
        } else {
            throw new RuntimeException("this method can only be called with MODE MULTI_FUNC");
        }
        return this;
    }

    public void setTitleText(String title) {
        this.title.setText(title);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        super.initListener();
        //点击搜索框时展开输入框
        searchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchView.hasFocus()) editText.requestFocus();
            }
        });
        //点击取消按钮时,取消展开框
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) toggle();
            }
        });
        editText.setOnEditorActionListener((v, actionId, event) ->
        {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!enableRemainMode)
                    toggle();
                else
                    mDisplayCallback.onClickSearchButton();
                return true;
            }
            return false;
        });
    }

    public void setOnTypeSelectListener(CustomSearchView.OnTypeSelectListener onTypeSelectListener) {
        searchView.setOnTypeSelectListener(onTypeSelectListener);
    }

    public void setDefaultType(String defaultType) {
        searchView.setDefaultTypeSelect(defaultType);
    }

    //展开收缩框
    public void toggle() {
        //切换展开状态
        isExpand = !isExpand;
        searchView.setTypeSearchEnable(isExpand);
        if (isExpand) {
            ViewUtil.setBrightness(searchView, ALPHA_ON_SHOW);
            if (mDisplayCallback != null)
                mDisplayCallback.onShow();
            if (mExpandListener != null) {
                mExpandListener.onTitleSearchExpand(true);
            }
            if (left_width == -1)
                left_width = searchView.getPaddingLeft();
            if (right_width == -1)
                right_width = searchView.getPaddingRight();
            searchView.requestFocus();
            KeyboardUtil.showSearchInputMethod(editText);

        } else {
            ViewUtil.setBrightness(searchView, ALPHA_ON_CANCEL);
            if (mDisplayCallback != null)
                mDisplayCallback.onCancel();
            if (mExpandListener != null) {
                mExpandListener.onTitleSearchExpand(false);
            }
            searchView.clearFocus();
            KeyboardUtil.hideSearchInputMethod(editText);
        }
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
        final int ORIGIN_PADDING_LEFT = ViewUtil.dpToPx(getContext(), ORIGIN_PADDING_LEFT_IN_DIP);
        final int END_PADDING_RIGHT = ViewUtil.dpToPx(getContext(), currentPaddingRightEnd);
        valueAnimator.setDuration(ANIMATION_DURATION);
        valueAnimator.addUpdateListener(animation -> {
            final float f = animation.getAnimatedFraction();
            if (isExpand) {
                if (rightBtnEnable) rightBtn.setVisibility(View.GONE);
                ViewUtil.setPaddingLeft(searchView, (int) (left_width - (left_width - ORIGIN_PADDING_LEFT) * f));
                ViewUtil.setPaddingRight(searchView, (int) (right_width + (END_PADDING_RIGHT - right_width) * f));
                if (f == 1) {
                    AnimationUtil.fadeIn(cancel, ANIMATION_DURATION_FADE);
                    searchView.setLightMode();
                    if (mCallBack != null) {
                        mCallBack.afterAnimation(isExpand);
                    }
                    searchView.setSelectTypeTextColorRes(isExpand?R.color.gray:R.color.white);
                }
            } else {
                if (f == 1) {
                    AnimationUtil.fadeOut(cancel, 0, new AnimationUtil.AnimationCallback() {
                        @Override
                        public void onAnimationEnd() {
                            if (rightBtnEnable)
                                AnimationUtil.fadeIn(rightBtn, ANIMATION_DURATION_FADE);
                            searchView.setDarkMode();
                        }

                        @Override
                        public void onAnimationCancel() {

                        }
                    });
                    if (mCallBack != null)
                        mCallBack.afterAnimation(isExpand);
                    searchView.setSelectTypeTextColorRes(isExpand?R.color.gray:R.color.white);
                }
                ViewUtil.setPaddingLeft(searchView, (int) (ORIGIN_PADDING_LEFT + (left_width - ORIGIN_PADDING_LEFT) * f));
                ViewUtil.setPaddingRight(searchView, (int) (END_PADDING_RIGHT - (END_PADDING_RIGHT - right_width) * f));
            }

        });
        final ObjectAnimator leftObjectsAnimator = ObjectAnimator.ofFloat(rlLeft, TRANSLATION_X,
                isExpand ? 0 : -left_width,
                isExpand ? -left_width : 0);
        leftObjectsAnimator.setDuration(ANIMATION_DURATION);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).with(leftObjectsAnimator);
        animatorSet.setDuration(ANIMATION_DURATION);
        //动画前回调
        if (mCallBack != null) {
            mCallBack.beforeAnimation(isExpand);
        }
        animatorSet.start();
        //动画后回调
        if (mCallBack != null) {
            mCallBack.duringAnimation(isExpand);
            mCallBack.afterAnimation(isExpand);
        }
//        searchView.setSelectTypeTextColorRes(isExpand?R.color.black:R.color.white);
    }

    public void setSelectedTypeEnabled(Boolean enabled) {
        searchView.setSelectTypeEnabled(enabled);
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
        searchView.setTypeSelectContent(contentList);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public interface CallBack {
        void beforeAnimation(boolean flag);

        void duringAnimation(boolean flag);

        void afterAnimation(boolean flag);
    }

    public interface DisplayCallback {
        void onShow();

        void onClickSearchButton();

        void onCancel();
    }
}
