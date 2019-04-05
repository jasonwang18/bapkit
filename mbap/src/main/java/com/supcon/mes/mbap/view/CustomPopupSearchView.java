package com.supcon.mes.mbap.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.R;

import io.reactivex.functions.Consumer;

public class CustomPopupSearchView extends BaseLinearLayout {
    TextView searchViewPopBtn;
    ImageView searchViewDisplayBtn;
    EditText searchViewTv;
    ImageView searchViewDel;
    CustomPopupWindow customPopupWindow;
    TextView searchBtn;
    OnTitleSelectedListener onTitleSelectedListener;
    OnSearchBtnClickListener onSearchBtnClickListener;

    public CustomPopupSearchView(Context context) {
        this(context, null);
    }

    public CustomPopupSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnTitleSelectedListener(OnTitleSelectedListener onTitleSelectedListener) {
        this.onTitleSelectedListener = onTitleSelectedListener;
    }

    public CustomPopupSearchView onTitleSelectedListener(OnTitleSelectedListener onTitleSelectedListener) {
        this.onTitleSelectedListener = onTitleSelectedListener;
        return this;
    }

    public void setOnSearchBtnClickListener(OnSearchBtnClickListener onSearchBtnClickListener) {
        this.onSearchBtnClickListener = onSearchBtnClickListener;

    }
    public CustomPopupSearchView onSearchBtnClickListener(OnSearchBtnClickListener onSearchBtnClickListener) {
        this.onSearchBtnClickListener = onSearchBtnClickListener;
        return this;
    }

    @Override
    protected void initView() {
        super.initView();
        searchViewPopBtn = findViewById(R.id.searchViewPopBtn);
        searchViewDisplayBtn = findViewById(R.id.searchViewDisplayBtn);
        searchViewTv = findViewById(R.id.searchViewTv);
        searchViewDel = findViewById(R.id.searchViewDel);
        searchBtn = findViewById(R.id.searchBtn);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(searchViewPopBtn).subscribe(o -> showPopupWindow());
        RxView.clicks(searchViewDisplayBtn).subscribe(o -> showPopupWindow());
        RxTextView.textChanges(searchViewTv)
                .skipInitialValue()
                .subscribe(charSequence -> searchViewDel.setVisibility(TextUtils.isEmpty(charSequence) ? INVISIBLE : VISIBLE));
        RxView.clicks(searchViewDel)
                .subscribe(o -> searchViewTv.setText(null));
        RxView.clicks(searchBtn)
                .subscribe(o -> onSearchBtnClickListener.onSearchBtnClick());
    }



    private void showPopupWindow() {
        if (customPopupWindow != null) {
            customPopupWindow.showAsDropDown(searchViewPopBtn);
            return;
        }
        customPopupWindow = new CustomPopupWindow(context).offsetX(0).offsetY(5)
                .bindClickListener("标题1", createListener("标题1"))
                .bindClickListener("标题2", createListener("标题2"))
                .bindClickListener("标题3", createListener("标题3"))
                .bindClickListener("标题4", createListener("标题4"))
                .bindAnchorView(searchViewPopBtn)
                .onToggleListener(() ->
                        searchViewDisplayBtn.post(() ->
                                searchViewDisplayBtn
                                        .setImageResource(customPopupWindow.isShowing() ?
                                                R.drawable.display_up : R.drawable.display_down)))
                .show();
    }

    private OnClickListener createListener(String title) {
        return v -> {
            searchViewPopBtn.setText(title);
            if (onTitleSelectedListener != null) onTitleSelectedListener.OnTitleSelected(title);
            customPopupWindow.dismiss();
        };
    }

    @Override
    protected int layoutId() {
        return R.layout.ly_popup_searchview;
    }

    public interface OnTitleSelectedListener {
        void OnTitleSelected(String title);
    }

    public interface OnSearchBtnClickListener{
        void onSearchBtnClick();
    }
}
