package com.supcon.mes.mbap.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.common.view.util.UrlUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.LinkEntity;
import com.supcon.mes.mbap.beans.WorkFlowEntity;
import com.supcon.mes.mbap.beans.WorkFlowVar;
import com.supcon.mes.mbap.constant.ViewAction;
import com.supcon.mes.mbap.constant.WorkFlowBtn;
import com.supcon.mes.mbap.utils.GsonUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by wangshizhan on 2018/12/5
 * Email:wangshizhan@supcom.com
 */
public class CustomWorkFlowView extends BaseLinearLayout {
    private ImageView commentInputIv, selectPeopleInputIv;
    private CustomEditText commentInput;
    private CustomTextView selectPeopleInput;
    private LinearLayout saveBtn, middleBtn, submitBtn;
    private TextView saveBtnTv, middleBtnTv, submitBtnTv;

    private View selectPeoplePoint;

    private String comment;
    private boolean isCommentable;//可评论的
    private boolean isEnable;

    private List<LinkEntity> mLinkEntities;

    private boolean isOnline;

    private String addUserIds = "";

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
        selectPeopleInputIv = findViewById(R.id.selectPeopleInputIv);
        selectPeopleInput = findViewById(R.id.selectPeopleInput);
        selectPeoplePoint = findViewById(R.id.selectPeoplePoint);

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

        RxView.clicks(selectPeopleInputIv)
                .throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        int visible = selectPeopleInput.getVisibility();
                        if(visible == VISIBLE){
                            selectPeopleInput.setVisibility(GONE);
                        }
                        else {
                            selectPeopleInput.setVisibility(VISIBLE);
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

                        LinkEntity entity = (LinkEntity) middleBtn.getTag();

                        if(linkEntity.selectPeople!=null && ！"0".equals(entity.requiredStaff) && TextUtils.isEmpty(selectPeopleInput.getContent())){//必须选人
                            ToastUtils.show(context, "处理人不能为空");
                            return;
                        }

                        onChildViewClick(middleBtn, WorkFlowBtn.MIDDLE_BTN.value(), createWorkFlowVar(entity));
                    }
                });

        RxView.clicks(submitBtn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LinkEntity entity = (LinkEntity) submitBtn.getTag();

                        if(linkEntity.requiredStaff!=null && ！"0".equals(entity.requiredStaff) && TextUtils.isEmpty(selectPeopleInput.getContent())){//必须选人
                            ToastUtils.show(context, "处理人不能为空");
                            return;
                        }

                        onChildViewClick(submitBtn, WorkFlowBtn.SUBMIT_BTN.value(), createWorkFlowVar(entity));
                    }
                });

        selectPeopleInput.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {


                if(action == ViewAction.CONTENT_CLEAN.value()){
                    addUserIds = "";
                    return;
                }
                CustomWorkFlowView.this.onChildViewClick(selectPeopleInput, action, obj);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void addStaff(String name, long id){
        String content = selectPeopleInput.getContent();

        if(TextUtils.isEmpty(content)){
            content = name;
        }
        else{
            content += ", "+name;
        }

        selectPeopleInput.setContent(content);


        addUserIds += String.valueOf(id)+ "," ;
        LogUtil.d("workflow addUserIds:"+addUserIds);
    }

    public String getComment() {
        return comment;
    }

    public void setLinks(String links){

        if(TextUtils.isEmpty(links)){
            return;
        }

        mLinkEntities = GsonUtil.jsonToList(links, LinkEntity.class);

        if(mLinkEntities!=null) {
            updateView();
            setEnabled(true);
        }
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

        if(isEnable){
            commentInput.setEditable(true);
        }
        saveBtn.setEnabled(isEnable);
        middleBtn.setEnabled(isEnable);
        submitBtn.setEnabled(isEnable);

    }

    private void updateView() {
        boolean selectPeople = false;
        boolean requiredStaff = false;
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
            if(linkEntity.selectPeople!=null &&!"0".equals(linkEntity.selectPeople)){
                selectPeople = true;
            }

            if(linkEntity.requiredStaff!=null && !"0".equals(linkEntity.requiredStaff)){
                requiredStaff = true;
            }
        }

        if(selectPeople){
            ((ViewGroup)selectPeopleInputIv.getParent()).setVisibility(VISIBLE);
        }

        if(requiredStaff){
            selectPeopleInput.setVisibility(VISIBLE);
            selectPeoplePoint.setVisibility(VISIBLE);
        }

        selectPeopleInput.setNecessary(requiredStaff);

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
        LogUtil.d("workflow:"+workFlowVar.toString());

        if(linkEntity.selectPeople!=null && !"0".equals(linkEntity.selectPeople)){
            workFlowEntity.assignUser = "\""+addUserIds+"\"";

//            workFlowVar.idsMap = new HashMap<>();
//            workFlowVar.idsMap.put("assignStaffSelect_"+linkEntity.name+"MultiIDs", addUserIds);
//            workFlowVar.idsMap.put("assignStaffSelect_"+linkEntity.name+"AddIds", "");
//            workFlowVar.idsMap.put("assignStaffSelect_"+linkEntity.name+"DeleteIds", "");
//            workFlowVar.idsMap.put("assignStaffSelect_"+linkEntity.name, "");
//            workFlowVar.idsMap.put("additionalUsersStr", "");
        }

        LogUtil.d("workflow:"+workFlowVar.toString());

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
