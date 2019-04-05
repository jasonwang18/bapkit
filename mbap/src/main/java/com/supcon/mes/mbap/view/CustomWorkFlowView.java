package com.supcon.mes.mbap.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapConstant;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.LinkEntity;
import com.supcon.mes.mbap.beans.WorkFlowEntity;
import com.supcon.mes.mbap.beans.WorkFlowVar;
import com.supcon.mes.mbap.constant.WorkFlowBtn;
import com.supcon.mes.mbap.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by wangshizhan on 2018/12/5
 * Email:wangshizhan@supcom.com
 */
public class CustomWorkFlowView extends BaseLinearLayout {
    private ImageView commentInputIv;
    private CustomEditText commentInput;
    private LinearLayout saveBtn, middleBtn, submitBtn;
    private TextView saveBtnTv, middleBtnTv, submitBtnTv;
    private String comment;
    private boolean isCommentable;//可评论的
    private boolean isEnable;

    private List<LinkEntity> mLinkEntities;

    private boolean isOnline;

    public CustomWorkFlowView(Context context) {
        super(context);
    }

    public CustomWorkFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_workflow;
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomWorkFlowView);
            comment = array.getString(R.styleable.CustomWorkFlowView_comment);
            isCommentable = array.getBoolean(R.styleable.CustomWorkFlowView_commentable, true);
            isEnable = array.getBoolean(R.styleable.CustomWorkFlowView_enable, false);
            isOnline = array.getBoolean(R.styleable.CustomWorkFlowView_online, true);
            array.recycle();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        commentInput = findViewById(R.id.commentInput);
        saveBtn = findViewById(R.id.saveBtn);
        middleBtn = findViewById(R.id.middleBtn);
        submitBtn = findViewById(R.id.submitBtn);
        commentInputIv = findViewById(R.id.commentInputIv);


        saveBtnTv = findViewById(R.id.saveBtnTv);
        middleBtnTv = findViewById(R.id.middleBtnTv);
        submitBtnTv = findViewById(R.id.submitBtnTv);

        if(!TextUtils.isEmpty(comment)){
            commentInput.setVisibility(VISIBLE);
            commentInput.setText(comment);
        }
        setCommentable(isCommentable);
        setEnabled(isEnable);
        setOnline(isOnline);


    }


    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        super.initListener();

        RxTextView.textChanges(commentInput.editText())
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        comment = charSequence.toString();
                    }
                });

        RxView.clicks(commentInputIv)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        int visible = commentInput.getVisibility();
                        if(visible == VISIBLE){
                            commentInput.setVisibility(GONE);
                        }
                        else {
                            commentInput.setVisibility(VISIBLE);
                        }
                    }
                });

        RxView.clicks(saveBtn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        if(!isOnline){
                            onChildViewClick(saveBtn, WorkFlowBtn.SAVE_LOCAL_BTN.value(), createWorkFlowVar((LinkEntity) saveBtn.getTag()));
                        }
                        else {
                            onChildViewClick(saveBtn, WorkFlowBtn.SAVE_BTN.value(), createWorkFlowVar((LinkEntity) saveBtn.getTag()));
                        }
                    }
                });


        RxView.clicks(middleBtn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        onChildViewClick(middleBtn, WorkFlowBtn.MIDDLE_BTN.value(), createWorkFlowVar((LinkEntity) middleBtn.getTag()));
                    }
                });

        RxView.clicks(submitBtn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        onChildViewClick(submitBtn, WorkFlowBtn.SUBMIT_BTN.value(), createWorkFlowVar((LinkEntity) submitBtn.getTag()));
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public String getComment() {
        return comment;
    }

    public void setLinks(String links){

        if(TextUtils.isEmpty(links)){
            return;
        }

        mLinkEntities = GsonUtil.jsonToList(links, LinkEntity.class);

        if(mLinkEntities!=null)
            updateView();
    }

    public void setCommentable(boolean isCommentable){
        this.isCommentable = isCommentable;
        if(isCommentable){
            commentInputIv.setVisibility(VISIBLE);
        }
        else{
            commentInputIv.setVisibility(GONE);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.isEnable = enabled;
        commentInput.setEnabled(isEnable);
        saveBtn.setEnabled(isEnable);
        middleBtn.setEnabled(isEnable);
        submitBtn.setEnabled(isEnable);

    }

    private void updateView() {

        for(int i = 0; i< mLinkEntities.size();  i++){

            LinkEntity linkEntity = mLinkEntities.get(i);
            if(i == 0){
                submitBtnTv.setText(linkEntity.description);
                submitBtn.setTag(linkEntity);
                submitBtn.setVisibility(VISIBLE);
                submitBtn.setEnabled(true);
                LinkEntity saveLinkEntity = new LinkEntity();
                saveLinkEntity.name = linkEntity.name;
                saveLinkEntity.description = "保存";
                saveBtn.setTag(saveLinkEntity);
                saveBtn.setEnabled(true);
            }
            else if(i == 1){
                middleBtnTv.setText(linkEntity.description);
                middleBtn.setTag(linkEntity);
                middleBtn.setVisibility(VISIBLE);
                middleBtn.setEnabled(true);
            }

        }

        commentInput.setEnabled(true);

    }

    private WorkFlowVar createWorkFlowVar(LinkEntity linkEntity){
        WorkFlowVar workFlowVar = new WorkFlowVar();

        workFlowVar.outCome = linkEntity.name;
        workFlowVar.comment = comment;

        if(linkEntity.description.equals("保存")){
            workFlowVar.operateType = "save";
            workFlowVar.dec = linkEntity.description;
            return workFlowVar;
        }


        List<WorkFlowEntity> workFlowEntities = new ArrayList<>();
        WorkFlowEntity workFlowEntity = new WorkFlowEntity();
        workFlowEntity.dec = linkEntity.description;
        workFlowEntity.outcome = linkEntity.name;
        workFlowEntity.type = "normal";

        workFlowEntities.add(workFlowEntity);

        workFlowVar.operateType = "submit";
        workFlowVar.dec = linkEntity.description;
        workFlowVar.outCome = linkEntity.name;
        workFlowVar.outcomeMapJson = workFlowEntities;

        return workFlowVar;
    }

    public void setOnline(boolean online) {
        isOnline = online;
        if(!isOnline){
            middleBtn.setVisibility(GONE);
            submitBtn.setVisibility(GONE);
            LinkEntity saveLinkEntity = new LinkEntity();
            saveLinkEntity.description = "保存本地";
            saveBtn.setTag(saveLinkEntity);
            saveBtnTv.setText("保存本地");
            saveBtn.setEnabled(true);
        }
    }
}
