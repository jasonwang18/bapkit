package com.supcon.mes.mbap.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.view.MyPopupWindow;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.TransitionAdapter;
import com.supcon.mes.mbap.beans.Transition;
import com.supcon.mes.mbap.utils.NomalSpaceItemDecoration;
import com.supcon.mes.mbap.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by wangshizhan on 2017/11/24.
 * Email:wangshizhan@supcon.com
 */

public class CustomPopTransation extends BaseLinearLayout implements View.OnClickListener{

    private Button transitionSaveBtn, transitionRouterBtn, transitionSubmitBtn;
    private TextView transitionStaffChooseTv;
    private ImageView transitionStaffChooseIc;
    private LinearLayout transitionStaffChooseLayout;

    TransitionAdapter mTransitionAdapter;
    List<Transition> mTransitions = new ArrayList<>();
    private boolean isOffLineMode = false;
    MyPopupWindow mPopupWindow;

    private int mTextSize;
    private int mPosition;

    private boolean isDismissOutside = false;


    public CustomPopTransation(Context context) {
        super(context);
    }

    public CustomPopTransation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            transitionSaveBtn.setTypeface(newFont);
            transitionRouterBtn.setTypeface(newFont);
            transitionSubmitBtn.setTypeface(newFont);
            transitionStaffChooseTv.setTypeface(newFont);
        }


    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_pop_transition;
    }


    @Override
    protected void initView() {
        super.initView();
        transitionSaveBtn = findViewById(R.id.transitionSaveBtn);
        transitionRouterBtn = findViewById(R.id.transitionRouterBtn);
        transitionSubmitBtn = findViewById(R.id.transitionSubmitBtn);

        transitionStaffChooseTv = findViewById(R.id.transitionStaffChooseTv);
        transitionStaffChooseIc = findViewById(R.id.transitionStaffChooseIc);
        transitionStaffChooseLayout = findViewById(R.id.transitionStaffChooseLayout);


        if(mTextSize !=0){
            transitionSaveBtn.setTextSize(mTextSize);
            transitionRouterBtn.setTextSize(mTextSize);
            transitionSubmitBtn.setTextSize(mTextSize);
            transitionStaffChooseTv.setTextSize(mTextSize);
        }

    }


    @Override
    protected void initListener() {
        super.initListener();
        transitionRouterBtn.setOnClickListener(v -> {
            if(mPopupWindow == null || isDismissOutside){
                return;
            }

            boolean isNavigationBarShow = SystemUtil.isNavigationBarShow((Activity) getContext());

            if(mPopupWindow.isShowing()){
                    mPopupWindow.dismiss(true);

            }
            else if(currentTransition().requiredStaff){
                if(transitionStaffChooseLayout.getVisibility() == View.GONE){
                    transitionStaffChooseLayout.setVisibility(VISIBLE);
                }
                mPopupWindow.showAtLocation(transitionStaffChooseLayout, Gravity.BOTTOM ,
                        0,
                        DisplayUtil.dip2px(isNavigationBarShow?138:91, getContext()));
            }
            else{

                mPopupWindow.showAtLocation(transitionRouterBtn, Gravity.BOTTOM ,
                        0,
                        DisplayUtil.dip2px(isNavigationBarShow?99:51, getContext()));

            }
            onChildViewClick(v, 0, v.getTag());
        });

        transitionSaveBtn.setOnClickListener(this);
        transitionSubmitBtn.setOnClickListener(v -> {
//            if(mPopupWindow.isShowing()){
//                mPopupWindow.dismiss(true);
//            }
            onChildViewClick(v, 0, v.getTag());
        });
        transitionStaffChooseTv.setOnClickListener(this);
        transitionStaffChooseIc.setOnClickListener(this);


    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomPopTransation);
            mTextSize = array.getInt(R.styleable.CustomPopTransation_text_size, 0);
            mPosition = array.getInt(R.styleable.CustomPopTransation_position, -1);
            array.recycle();
        }
    }

    /**
     * 添加跳转列表
     * @param transitions
     */
    public void setTransitions(@NonNull List<Transition> transitions){

        isOffLineMode = transitions.size() ==0;
//        mTransitionAdapter.clear();
        mTransitions.clear();
        if(!isOffLineMode){
            mTransitions.addAll(transitions);
            initPop();
        }

        updateView();

    }

    private void updateView() {

        if(isOffLineMode){
            transitionSaveBtn.setText("保存本地");
            transitionSaveBtn.setTag("saveLocal");
            transitionSubmitBtn.setVisibility(GONE);
            ((ViewGroup)transitionRouterBtn.getParent()).setVisibility(GONE);
            return;
        }
        else{
            transitionSaveBtn.setText("保存");
            transitionSaveBtn.setTag("save");
        }

        if(mTransitions.size()==1){
            Transition transition = mTransitions.get(0);
            transitionRouterBtn.setText(transition.transitionDesc);
            mPosition = 0;

            new Handler().postDelayed(() -> {

                if(transition.requiredStaff){
                    transitionSubmitBtn.setEnabled(false);
                    transitionStaffChooseLayout.setVisibility(VISIBLE);

                }
                else{
                    transitionStaffChooseLayout.setVisibility(GONE);
                    transitionSubmitBtn.setEnabled(true);
                }

                mTransitionAdapter.setPosition(mPosition);
            }, 200);

        }

        transitionSubmitBtn.setTag("submitBtn");

    }

    private void initPop() {
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.ly_transition_recyclerview, null);
        RecyclerView contentView = popupView.findViewById(R.id.contentView);
        contentView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        contentView.addItemDecoration(new NomalSpaceItemDecoration(DisplayUtil.dip2px(5, getContext())));
        mTransitionAdapter = new TransitionAdapter(getContext());
        mTransitionAdapter.setOnItemChildViewClickListener((childView, position, action, obj) -> {
            mTransitionAdapter.notifyDataSetChanged();
            transitionRouterBtn.setText(mTransitions.get(position).transitionDesc);
            mPosition = position;
            if(mTransitions.get(position).requiredStaff){
                transitionSubmitBtn.setEnabled(false);
                transitionStaffChooseLayout.setVisibility(VISIBLE);
                boolean isNavigationBarShow = SystemUtil.isNavigationBarShow((Activity) getContext());
                mPopupWindow.update(
                        0,
                        DisplayUtil.dip2px(isNavigationBarShow?138:91, getContext()),
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            else{
                transitionStaffChooseLayout.setVisibility(GONE);
                transitionSubmitBtn.setEnabled(true);
                mPopupWindow.dismiss(true);
            }

//            onChildViewClick(childView, action, mTransitionAdapter.getItem(position));

        });

        LinearLayout.LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.width = DisplayUtil.getScreenWidth(getContext())- 2*DisplayUtil.dip2px(5, getContext());
        contentView.setLayoutParams(lp);

        contentView.setAdapter(mTransitionAdapter);
        mTransitionAdapter.setPosition(mPosition);
        mTransitionAdapter.setList(mTransitions);
        mPopupWindow = new MyPopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopupWindow.setAnimationStyle(R.style.fadeStyle);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.sh_white_stroke));
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setCancelOutSide(true);
        mPopupWindow.update();

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isDismissOutside = true;

                Flowable.timer(200, TimeUnit.MILLISECONDS)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                isDismissOutside = false;
                            }
                        });
            }
        });

    }

    public Transition currentTransition(){

        if(mPosition == -1){
            return new Transition();
        }

        return mTransitions.get(mPosition);

    }

    @Override
    public void onClick(View v) {
        onChildViewClick(v, 0, v.getTag());
    }


}
