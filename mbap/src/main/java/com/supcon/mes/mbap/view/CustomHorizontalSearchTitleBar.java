package com.supcon.mes.mbap.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.OnTitleSearchExpandListener;
import com.supcon.mes.mbap.utils.AnimationUtil;
import com.supcon.mes.mbap.utils.KeyboardUtil;
import com.supcon.mes.mbap.utils.StatusBarUtils;
import com.supcon.mes.mbap.utils.ViewUtil;

/**
 * Environment: hongruijun
 * Created by Xushiyun on 2018/5/7.
 */

public class CustomHorizontalSearchTitleBar extends BaseRelativeLayout {
    private TextView title;
    private CustomSearchView searchView;
    //取消按钮
    private TextView cancel;
    private EditText editText;
    private RelativeLayout rlLeft;
    private RelativeLayout rlRootView;
    //右侧功能键按钮
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private boolean rightBtnEnable = false;

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

    private OnTitleSearchExpandListener mExpandListener;

    private int startColor;

    private int endColor;

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

    public CustomHorizontalSearchTitleBar(Context context) {
        this(context, null);
    }

    public CustomHorizontalSearchTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
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
            titleLength  = array.getDimensionPixelSize(R.styleable.CustomHorizontalSearchTitleBar_title_length, 0);
            startColor = array.getColor(R.styleable.CustomHorizontalSearchTitleBar_start_color, -1);
            endColor = array.getColor(R.styleable.CustomHorizontalSearchTitleBar_end_color, -1);
            array.recycle();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_horizontal_search_title_bar;
    }

    @Override
    protected void initView() {
        super.initView();
        searchView = findViewById(R.id.customSearchView);
        ViewUtil.setBrightness(searchView, ALPHA_ON_CANCEL);
        cancel = findViewById(R.id.cancelBtn);
        title = findViewById(R.id.titleText);
        rlRootView = findViewById(R.id.rlRootView);

        if (titleBarBackgroundColor != -1) {
            rlRootView.setBackgroundResource(titleBarBackgroundColor);
        }
        if(startColor!=-1 && endColor!=-1)
        rlRootView.setBackground(ViewUtil.genGradientDrawable(startColor, endColor));

        editText = searchView.editText();
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        rlLeft = findViewById(R.id.rlLeft);
        leftBtn = findViewById(R.id.leftBtn);
        rightBtn = findViewById(R.id.rightBtn);
        title.setText(titleText);

        if (rightBtnEnable) {
            enableRightBtn();
        } else {
            disableRightBtn();
        }

        if(titleLength!=0){
            ViewGroup.LayoutParams lp = title.getLayoutParams();
            lp.width = titleLength;
            title.setLayoutParams(lp);

            searchView.setPadding(DisplayUtil.dip2px(60, getContext())+titleLength, 0, 0, 0);
        }
    }

    public void setTitleText(String title) {
        this.title.setText(title);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(searchView).subscribe(o -> {
            if (!searchView.hasFocus()) editText.requestFocus();
        });
        RxView.clicks(cancel).subscribe(o -> toggle());
        RxView.focusChanges(editText).subscribe(aBoolean -> {
            if (aBoolean) toggle();
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


    public void toggle() {
        isExpand = !isExpand;
        if (isExpand) {
            ViewUtil.setBrightness(searchView, ALPHA_ON_SHOW);
            if (mDisplayCallback != null)
                mDisplayCallback.onShow();
            if(mExpandListener!=null){
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
            if(mExpandListener!=null){
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
                    if (mCallBack != null)
                        mCallBack.afterAnimation(isExpand);
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
        if (mCallBack != null)
            mCallBack.beforeAnimation(isExpand);
        animatorSet.start();
        if (mCallBack != null)
            mCallBack.duringAnimation(isExpand);
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
