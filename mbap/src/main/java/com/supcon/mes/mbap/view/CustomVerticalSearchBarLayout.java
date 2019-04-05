package com.supcon.mes.mbap.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.AnimationUtil;
import com.supcon.mes.mbap.utils.KeyboardUtil;
import com.supcon.mes.mbap.utils.ViewUtil;

import io.reactivex.functions.Consumer;

public class CustomVerticalSearchBarLayout extends BaseRelativeLayout {

    private View searchTitle;
    private TextView searchCancel;
    private CustomSearchView searchView;
    private EditText searchEditText;
    private RelativeLayout rlSearch;
    private ImageButton leftBtn;
    private TextView titleText;
    private View searchContainerView;

    private final static int ORIGIN_SEARCH_PADDING_RIGHT_IN_DIP = 20;
    private final static int ORIGIN_SEARCH_PADDING_LEFT_IN_DIP = 20;

    private static final int STATUS_BAR_HEIGHT_IN_DP = 0;

    private float scroll_search = 100.0f;
    private float scroll_title = 0.0f;

    private static final int ANIMATION_DURATION = 200;
    private static final int ANIMATION_DURATION_CANCEL = 20;

    @Override
    protected void initData() {
        super.initData();
    }

    private boolean isExpand = false;

    public CustomVerticalSearchBarLayout(Context context) {
        this(context, null);
    }

    public CustomVerticalSearchBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_vertical_searchbar_layout;
    }

    private void toggle() {
        isExpand = !isExpand;
        if (isExpand) {
            searchEditText.requestFocus();
            KeyboardUtil.showSearchInputMethod(searchEditText);
        } else {
            searchEditText.clearFocus();
            KeyboardUtil.hideSearchInputMethod(searchEditText);
        }
        if (scroll_search == 0) {
            scroll_search = DisplayUtil.dip2px(40, getContext());
        }
        final ObjectAnimator translation_search_y = ObjectAnimator.ofFloat(CustomVerticalSearchBarLayout.this, TRANSLATION_Y,
                isExpand ? 0 : -scroll_search,
                isExpand ? -scroll_search : 0);

        translation_search_y.setDuration(ANIMATION_DURATION);
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
        valueAnimator.setDuration(ANIMATION_DURATION);
        ObjectAnimator translation_title_y = null;
        if (searchTitle != null) {
            if (scroll_title == 0) {
                float title_y = searchTitle.getY();
                float title_h = searchTitle.getHeight();
                scroll_title = title_y + title_h;
            }

            translation_title_y = ObjectAnimator.ofFloat(searchTitle, TRANSLATION_Y,
                    isExpand ? 0 : -scroll_title,
                    isExpand ? -scroll_title : 0);
            translation_title_y.setDuration(ANIMATION_DURATION);
        }
        final int padding_cancel = ViewUtil.dpToPx(getContext(), 80);
        final int padding_origin = searchView.getPaddingRight();
        final int origin_search_padding_right = ViewUtil.dpToPx(getContext(), ORIGIN_SEARCH_PADDING_RIGHT_IN_DIP);
        final int end_search_padding_right = ViewUtil.dpToPx(getContext(), 80);
        valueAnimator.addUpdateListener(animation -> {
            final int padding_expand = (int) (origin_search_padding_right + (end_search_padding_right - origin_search_padding_right) * animation.getAnimatedFraction());
            final int padding_collapse = (int) (end_search_padding_right-(end_search_padding_right - origin_search_padding_right) * animation.getAnimatedFraction());
            ViewUtil.setPaddingRight(searchView, isExpand ? padding_expand : padding_collapse);
        });
        translation_search_y.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!isExpand) {
                    searchCancel.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isExpand) AnimationUtil.fadeIn(searchCancel, ANIMATION_DURATION_CANCEL);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        final AnimatorSet animationSet = new AnimatorSet();
            animationSet.play(translation_search_y).with(valueAnimator).with(translation_title_y);
        animationSet.start();
    }

    @Override
    protected void initView() {
        super.initView();
        searchCancel = findViewById(R.id.searchCancel);
        searchTitle = findViewById(R.id.searchTitle);
        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.editText();
        rlSearch = findViewById(R.id.rlSearch);
        searchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        leftBtn = findViewById(R.id.leftBtn);
        titleText = findViewById(R.id.titleText);

    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(searchView).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
//                ToastUtils.show(getContext(), "点击了搜索视图");
                if (searchView.hasFocus())
                {
                    if(!searchEditText.hasFocus()) {
                        searchEditText.requestFocus();
                        KeyboardUtil.showSearchInputMethod(searchEditText);
                    }
                }
            }
        });
        RxView.clicks(searchCancel).subscribe(o -> toggle());
        RxView.focusChanges(editText()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
//                    ToastUtils.show(getContext(), "焦点改变1");
                    CustomVerticalSearchBarLayout.this.toggle();
                }else  {
//                    ToastUtils.show(getContext(), "焦点改变2");
                    KeyboardUtil.hideKeyboard(getContext(), editText());
                }
            }
        });
        searchEditText.setOnEditorActionListener((v, actionId, event) ->
        {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toggle();
                return true;
            }
            return false;
        });
    }

    public EditText editText(){
        return searchEditText;
    }
    public ImageView leftBtn() {
        return leftBtn;
    }
    public TextView titleText() {
        return titleText;
    }
}
