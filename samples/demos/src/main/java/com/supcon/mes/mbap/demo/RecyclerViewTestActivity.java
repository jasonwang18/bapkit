package com.supcon.mes.mbap.demo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.annotation.BindByTag;
import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.base.activity.BaseRefreshRecyclerActivity;
import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.base.controller.RefreshRecyclerController;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.view.CustomSwipeLayout;
import com.supcon.mes.mbap.adapter.RecyclerEmptyAdapter;
import com.supcon.mes.mbap.beans.EmptyViewEntity;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;
import com.supcon.mes.mbap.view.CustomAdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import adapter.RecyclerTestAdapter;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import model.TestEntity;

public class RecyclerViewTestActivity extends BaseRefreshRecyclerActivity<TestEntity> {

    @BindByTag("contentView")
    RecyclerView contentView;

    RecyclerEmptyAdapter mRecyclerEmptyAdapter;

    @Override
    protected void onInit() {
        super.onInit();
        refreshListController.setPullDownRefreshEnabled(false);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        super.initView();
        contentView.setLayoutManager(new LinearLayoutManager(context));  //线性布局
        contentView.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(8, context)));  //列表项之间间隔
        contentView.addOnItemTouchListener(new CustomSwipeLayout.OnSwipeItemTouchListener(this));
        initEmptyView();
    }

    @Override
    protected IListAdapter<TestEntity> createAdapter() {
        return new RecyclerTestAdapter(context);
    }

    private void initEmptyView(){
        mRecyclerEmptyAdapter = new RecyclerEmptyAdapter(context);

        EmptyViewEntity emptyViewEntity = new EmptyViewEntity();
        emptyViewEntity.icon = R.drawable.ic_nodata_notext;
        emptyViewEntity.contentText = "暂无数据";

        mRecyclerEmptyAdapter.addData(emptyViewEntity);
        ((RefreshRecyclerController<TestEntity>)refreshListController).setEmpterAdapter(mRecyclerEmptyAdapter);
    }



    @Override
    protected void initData() {
        super.initData();

        List<TestEntity> list = new ArrayList<>();
        TestEntity testEntity = new TestEntity();
        testEntity.name = String.valueOf(new Random(5));
        list.add(testEntity);
        list.add(testEntity);
        list.add(testEntity);
        list.add(testEntity);
        list.add(testEntity);
        list.add(testEntity);


        Flowable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        refreshListController.refreshComplete(null);
                    }
                });

    }
}
