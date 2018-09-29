package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.TransitionAdapter;
import com.supcon.mes.mbap.beans.Transition;
import com.supcon.mes.mbap.utils.NomalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by wangshizhan on 2017/11/24.
 * Email:wangshizhan@supcon.com
 */

public class CustomTransation extends BaseLinearLayout implements View.OnClickListener{

    private Button transitionSaveBtn, transitionRouterBtn, transitionSubmitBtn;
    private TextView transitionStaffChooseTv;
    private ImageView transitionStaffChooseIc;
    private LinearLayout transitionStaffChooseLayout;
    private LinearLayout transitionLayout;
    private RecyclerView contentView;

    TransitionAdapter mTransitionAdapter;
    List<Transition> mTransitions = new ArrayList<>();
    private boolean isOffLineMode = false;

    private int mTextSize;
    private int mPosition;

    private boolean isNavigationBarShow = false;
//    private CustomTimer mTimer;

    public CustomTransation(Context context) {
        super(context);
    }

    public CustomTransation(Context context, AttributeSet attrs) {
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
        return R.layout.v_custom_transition;
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

        transitionLayout = findViewById(R.id.transitionLayout);
        contentView = findViewById(R.id.contentView);

        if(mTextSize !=0){
            transitionSaveBtn.setTextSize(mTextSize);
            transitionRouterBtn.setTextSize(mTextSize);
            transitionSubmitBtn.setTextSize(mTextSize);
            transitionStaffChooseTv.setTextSize(mTextSize);
        }

        if(!showStaffChoose()){
            transitionStaffChooseLayout.setVisibility(GONE);
            transitionSubmitBtn.setEnabled(true);
        }
    }

    private void initRecyclerView() {
        contentView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        contentView.addItemDecoration(new NomalSpaceItemDecoration(DisplayUtil.dip2px(3, getContext())));
        mTransitionAdapter = new TransitionAdapter(getContext());
        mTransitionAdapter.setOnItemChildViewClickListener((childView, position, action, obj) -> {
            mTransitionAdapter.notifyDataSetChanged();
            transitionRouterBtn.setText(mTransitions.get(position).transitionDesc);
            mPosition = position;
            if(!showStaffChoose()){
                transitionStaffChooseLayout.setVisibility(GONE);
                transitionSubmitBtn.setEnabled(true);
                closeTransitionLayout();
            }


            onChildViewClick(childView, action, mTransitionAdapter.getItem(position));

        });

        contentView.setAdapter(mTransitionAdapter);
        mTransitionAdapter.setPosition(mPosition);
        mTransitionAdapter.setList(mTransitions);

    }

    @Override
    protected void initListener() {
        super.initListener();
        transitionRouterBtn.setOnClickListener(v -> {

            if(transitionLayout.getVisibility() == VISIBLE){
                closeAll();
            }
            else{
                transitionLayout.setVisibility(VISIBLE);
                if(!showStaffChoose()){
                    transitionStaffChooseLayout.setVisibility(GONE);
                    transitionSubmitBtn.setEnabled(true);
                }
//                mTimer = CustomTimer.instance()
//                        .main()
//                        .listener(() -> {
//                            transitionLayout.setVisibility(GONE);
//                            mTimer.stop();
//                        })
//                        .start(3, TimeUnit.SECONDS);
            }
            onChildViewClick(v, 0, v.getTag());
        }

        );

        transitionSaveBtn.setOnClickListener(this);
        transitionSubmitBtn.setOnClickListener(v -> {
            closeAll();
            onChildViewClick(v, 0, v.getTag());
        });
        transitionStaffChooseTv.setOnClickListener(this);
        transitionStaffChooseIc.setOnClickListener(this);
    }

    private void closeTransitionLayout(){
        if(transitionLayout.getVisibility() == VISIBLE){
            transitionLayout.setVisibility(GONE);
//            if(mTimer!=null){
//                mTimer.stop();
//            }
        }
    }

    private void closeAll(){
        closeTransitionLayout();

        if(transitionStaffChooseLayout.getVisibility() == VISIBLE){
            transitionStaffChooseLayout.setVisibility(GONE);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        if(mTimer != null){
//            mTimer.stop();
//        }
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
        mTransitions.clear();
        if(!isOffLineMode){
            mTransitions.addAll(transitions);
            initRecyclerView();
        }

        updateView();

    }

    public void setNavigationBarShow(boolean navigationBarShow) {
        isNavigationBarShow = navigationBarShow;
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

                if(!showStaffChoose()){
                    transitionStaffChooseLayout.setVisibility(GONE);
                    transitionSubmitBtn.setEnabled(true);
                    closeTransitionLayout();
                }

                mTransitionAdapter.setPosition(mPosition);
            }, 200);

        }

        transitionSubmitBtn.setTag("submitBtn");

    }

    private boolean showStaffChoose() {

        Transition transition = currentTransition();
        if(transition == null){
            return false;
        }

        if(transition.requiredStaff){
            transitionSubmitBtn.setEnabled(false);
            transitionStaffChooseLayout.setVisibility(VISIBLE);
//            if(mTimer!=null){
//                mTimer.stop();
//            }

            return true;
        }

        return false;
    }


    public Transition currentTransition(){

        if(mPosition == -1){
            return null;
        }

        return mTransitions.get(mPosition);

    }

    @Override
    public void onClick(View v) {
        onChildViewClick(v, 0, v.getTag());
    }


}
