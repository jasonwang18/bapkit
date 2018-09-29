package com.supcon.mes.mbap.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
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

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.TransitionAdapterOld;
import com.supcon.mes.mbap.beans.Transition;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by wangshizhan on 2017/11/24.
 * Email:wangshizhan@supcon.com
 */

public class CustomTransationOld extends BaseLinearLayout implements View.OnClickListener{

    LinearLayout transitionLayout;
    TransitionAdapterOld mTransitionAdapterOld;
    List<Transition> mTransitions = new ArrayList<>();
    private int btnNum = 3;
    private boolean isOffLineMode = false;
    ImageView transitionMore;
    PopupWindow mPopupWindow;

    public CustomTransationOld(Context context) {
        super(context);
    }

    public CustomTransationOld(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected int layoutId() {
        return R.layout.v_custom_transition;
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
    }

    @Override
    protected void initView() {
        super.initView();
        transitionLayout = findViewById(R.id.transitionLayout);
        transitionMore = findViewById(R.id.transitionMore);
        transitionMore.setTag("transitionMore");


//        transitionLayout.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        transitionLayout.addItemDecoration(new GridSpaceItemDecoration(5, 2));
//        mTransitionAdapterOld = new TransitionAdapterOld(getContext());
//        transitionLayout.setAdapter(mTransitionAdapterOld);
    }

    @Override
    protected void initListener() {
        super.initListener();
        transitionMore.setOnClickListener(v -> {
            if(mPopupWindow == null){
                return;
            }
            if(mPopupWindow.isShowing()){
                mPopupWindow.dismiss();
            }
            else{
//                mPopupWindow.showAtLocation(transitionLayout, Gravity.BOTTOM ,
//                        DisplayUtil.getScreenWidth(getContext())/2-DisplayUtil.dip2px(50, getContext()),
//                        DisplayUtil.dip2px(100, getContext()));

                mPopupWindow.showAsDropDown(transitionLayout);
            }

        });
    }

    private void addSave() {

        Transition save = new Transition();
        save.transitionDesc = "保存";
        save.outComeType = "save";
        mTransitions.add(save);
    }

    private void addSaveLocal() {

        Transition save = new Transition();
        save.transitionDesc = "保存本地";
        save.outComeType = "saveLocal";
        mTransitions.add(save);
    }


    /**
     * 设置一行几个button
     * @param num
     */
    public void setBtnNum(int num){
        this.btnNum = num;
    }

    /**
     * 添加跳转列表
     * @param transitions
     */
    public void setTransitions(@NonNull List<Transition> transitions){

        isOffLineMode = transitions.size() ==0;
        transitionLayout.removeAllViews();
        mTransitions.clear();
        if(isOffLineMode){
            addSaveLocal();
        }
        else {
            addSave();
            mTransitions.addAll(transitions);
//            addSaveLocal();
        }

        updateView();

    }

    private void updateView() {

        int size = mTransitions.size();
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;

        if( size > btnNum){
            transitionMore.setVisibility(VISIBLE);
            initPop();
        }

        if(btnNum > size){
            btnNum = size;
        }

        for(int i = 0; i < btnNum; i++){

            if(i == 0){
                lp.leftMargin = 0;
            }
            else{
                lp.leftMargin = 1;
            }
            addTransition(mTransitions.get(i), lp);
        }



    }

    private void initPop() {
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.ly_custom_recyclerview, null);
        RecyclerView contentView = popupView.findViewById(R.id.contentView);
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.addItemDecoration(new SpaceItemDecoration(2));
        mTransitionAdapterOld = new TransitionAdapterOld(getContext());
        mTransitionAdapterOld.setOnItemChildViewClickListener((childView, position, action, obj) -> {
            onChildViewClick(childView, action, childView.getTag());
            mPopupWindow.dismiss();
        });
        contentView.setAdapter(mTransitionAdapterOld);
        mTransitionAdapterOld.setList(mTransitions.subList(btnNum, mTransitions.size()));
        mPopupWindow = new PopupWindow(popupView,
                DisplayUtil.dip2px(94, getContext()), DisplayUtil.dip2px(60*(mTransitions.size()-btnNum), getContext()));

        mPopupWindow.setAnimationStyle(R.style.fadeStyle);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_btnmore));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();

    }

    private void addTransition(Transition transition, LayoutParams lp) {

        Button button = new Button(getContext());
        button.setText(transition.transitionDesc);
        button.setTag(transition.outComeType);
        button.setOnClickListener(this);
        button.setTextColor(Color.WHITE);
        button.setTextSize(14);
        button.setTextAppearance(getContext(), R.style.BlueButton);
        if("save".equals(transition.outComeType)){
            button.setBackgroundResource(R.drawable.sl_blue_btn);
        }
        else
            button.setBackgroundResource(R.drawable.sl_red_btn);
        transitionLayout.addView(button, lp);

    }

    @Override
    public void onClick(View v) {
        onChildViewClick(v, 0, v.getTag());
    }


}
