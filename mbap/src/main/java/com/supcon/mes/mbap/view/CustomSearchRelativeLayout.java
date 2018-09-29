package com.supcon.mes.mbap.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.AnimationUtil;
import com.supcon.mes.mbap.utils.KeyboardUtil;
import com.supcon.mes.mbap.utils.ViewUtil;

import io.reactivex.functions.Consumer;

public class CustomSearchRelativeLayout extends BaseRelativeLayout {

    private View search_relative_title;
    private TextView search_relative_cancel;
    private CustomSearchView search_relative_searchView;
    private EditText search_relative_editText;
    private RelativeLayout rl_search_relative;
    private ImageButton leftBtn;
    private TextView titleText;
    private View search_container_view;

    private final static int ORIGIN_SEARCH_PADDING_RIGHT_IN_DIP = 20;
    private final static int ORIGIN_SEARCH_PADDING_LEFT_IN_DIP = 20;

    private static final int STATUS_BAR_HEIGHT_IN_DP = 0;

    private float scroll_search = 0.0f;
    private float scroll_title = 0.0f;

    private static final int ANIMATION_DURATION = 200;
    private static final int ANIMATION_DURATION_CANCEL = 20;

    @Override
    protected void initData() {
        super.initData();
    }

    private boolean isExpand = false;

    public CustomSearchRelativeLayout(Context context) {
        this(context, null);
    }

    public CustomSearchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_scrollable_search_bar;
    }

    private void toggle() {
        isExpand = !isExpand;
        if (isExpand) {
            search_relative_editText.requestFocus();
            KeyboardUtil.showSearchInputMethod(search_relative_editText);
        } else {
            search_relative_editText.clearFocus();
            KeyboardUtil.hideSearchInputMethod(search_relative_editText);
        }
        if (scroll_search == 0) {
            final int status_bar_height = ViewUtil.dpToPx(getContext(), STATUS_BAR_HEIGHT_IN_DP);
            float searchView_y = search_relative_searchView.getY();
            scroll_search = searchView_y - status_bar_height;
        }
        final ObjectAnimator translation_search_y = ObjectAnimator.ofFloat(CustomSearchRelativeLayout.this, TRANSLATION_Y,
                isExpand ? 0 : -scroll_search,
                isExpand ? -scroll_search : 0);

        translation_search_y.setDuration(ANIMATION_DURATION);
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
        valueAnimator.setDuration(ANIMATION_DURATION);
        ObjectAnimator translation_title_y = null;
        if (search_relative_title != null) {
            if (scroll_title == 0) {
                float title_y = search_relative_title.getY();
                float title_h = search_relative_title.getHeight();
                scroll_title = title_y + title_h;
            }

            translation_title_y = ObjectAnimator.ofFloat(search_relative_title, TRANSLATION_Y,
                    isExpand ? 0 : -scroll_title,
                    isExpand ? -scroll_title : 0);
            translation_title_y.setDuration(ANIMATION_DURATION);
        }
        final int padding_cancel = ViewUtil.dpToPx(getContext(), 80);
        final int padding_origin = search_relative_searchView.getPaddingRight();
        final int origin_search_padding_right = ViewUtil.dpToPx(getContext(), ORIGIN_SEARCH_PADDING_RIGHT_IN_DIP);
        final int end_search_padding_right = ViewUtil.dpToPx(getContext(), 80);
        valueAnimator.addUpdateListener(animation -> {
            final int padding_expand = (int) (origin_search_padding_right + (end_search_padding_right - origin_search_padding_right) * animation.getAnimatedFraction());
            final int padding_collapse = (int) (end_search_padding_right-(end_search_padding_right - origin_search_padding_right) * animation.getAnimatedFraction());
            ViewUtil.setPaddingRight(search_relative_searchView, isExpand ? padding_expand : padding_collapse);
        });
        translation_search_y.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!isExpand) {
                    search_relative_cancel.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isExpand) AnimationUtil.fadeIn(search_relative_cancel, ANIMATION_DURATION_CANCEL);
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
        search_relative_cancel = findViewById(R.id.search_relative_cancel);
        search_relative_title = findViewById(R.id.search_relative_title);
        search_relative_searchView = findViewById(R.id.search_relative_searchView);
        search_relative_editText = search_relative_searchView.editText();
        rl_search_relative = findViewById(R.id.rl_search_relative);
        search_relative_editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        rl_search_relative = findViewById(R.id.rl_search_relative);
        leftBtn = findViewById(R.id.leftBtn);
        titleText = findViewById(R.id.titleText);

    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(search_relative_searchView).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
//                ToastUtils.show(getContext(), "点击了搜索视图");
                if (search_relative_searchView.hasFocus())
                {
                    if(!search_relative_editText.hasFocus()) {
                        search_relative_editText.requestFocus();
                        KeyboardUtil.showSearchInputMethod(search_relative_editText);
                    }
                }
            }
        });
        RxView.clicks(search_relative_cancel).subscribe(o -> toggle());
        RxView.focusChanges(editText()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
//                    ToastUtils.show(getContext(), "焦点改变1");
                    CustomSearchRelativeLayout.this.toggle();
                }else  {
//                    ToastUtils.show(getContext(), "焦点改变2");
                    KeyboardUtil.hideKeyboard(getContext(), editText());
                }
            }
        });
        search_relative_editText.setOnEditorActionListener((v, actionId, event) ->
        {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toggle();
                return true;
            }
            return false;
        });
    }

    public EditText editText(){
        return search_relative_editText;
    }
    public ImageView leftBtn() {
        return leftBtn;
    }
    private TextView titleText() {
        return titleText;
    }
}
