package com.supcon.mes.mbap.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.app.annotation.BindByTag;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.MultiStageAdapter;
import com.supcon.mes.mbap.beans.BaseMultiStageEntity;
import com.supcon.mes.mbap.beans.ExampleMultistageEntity;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;

import java.util.List;

/**
 * @Author xushiyun
 * @Create-time 5/22/19
 * @Pageage com.supcon.mes.mbap.view
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc 多层级自定义列表
 */
public class CustomMultiStageRecyclerView extends BaseLinearLayout {
    RecyclerView contentView;
    
    private MultiStageAdapter mMultiStageAdapter;
    
    public CustomMultiStageRecyclerView(Context context) {
        this(context,null);
    }
    
    public CustomMultiStageRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setContentList(List<ExampleMultistageEntity> list) {
        mMultiStageAdapter.setContentList(list);
        mMultiStageAdapter.notifyDataSetChanged();
    }
    
    
    @Override
    protected void initListener() {
        super.initListener();
    }
    
    @Override
    protected void initView() {
        super.initView();
        contentView = findViewById(R.id.contentView);
        contentView.setLayoutManager(new LinearLayoutManager(context,VERTICAL,false));
//        contentView.setItemAnimator();
        contentView.addItemDecoration(new SpaceItemDecoration(1));
        mMultiStageAdapter = new MultiStageAdapter(context);
        contentView.setAdapter(mMultiStageAdapter);
    }
    
    @Override
    protected void initData() {
        super.initData();
    }
    
    @Override
    protected int layoutId() {
        return R.layout.ly_custom_multistage_recyclerview;
    }
}
