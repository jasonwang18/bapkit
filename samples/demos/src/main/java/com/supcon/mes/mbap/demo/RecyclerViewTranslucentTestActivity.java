package com.supcon.mes.mbap.demo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.annotation.BindByTag;
import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.base.activity.BaseRefreshRecyclerActivity;
import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.view.CustomSwipeLayout;
import com.supcon.mes.mbap.adapter.RecyclerEmptyAdapter;
import com.supcon.mes.mbap.beans.EmptyViewEntity;
import com.supcon.mes.mbap.constant.ListType;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import adapter.RecyclerTranslucentTestAdapter;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import model.TestEntity;

public class RecyclerViewTranslucentTestActivity extends BaseRefreshRecyclerActivity<TestEntity> {

    @BindByTag("contentView")
    RecyclerView contentView;

    RecyclerEmptyAdapter mRecyclerEmptyAdapter;

    ImageView mPicView;

    @BindByTag("titleBarLayout")
    RelativeLayout titleBarLayout;

    @Override
    protected void onInit() {
        super.onInit();
        refreshListController.setPullDownRefreshEnabled(false);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_recyclerview_translucent;
    }

    @Override
    protected void initView() {
        super.initView();
        contentView.setLayoutManager(new LinearLayoutManager(context));  //线性布局
        contentView.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(8, context)));  //列表项之间间隔
        contentView.addOnItemTouchListener(new CustomSwipeLayout.OnSwipeItemTouchListener(this));
        initEmptyView();

        titleBarLayout.getBackground().setAlpha(0);
    }

    @Override
    protected IListAdapter<TestEntity> createAdapter() {
        return new RecyclerTranslucentTestAdapter(context);
    }

    private void initEmptyView(){
        mRecyclerEmptyAdapter = new RecyclerEmptyAdapter(context);

        EmptyViewEntity emptyViewEntity = new EmptyViewEntity();
        emptyViewEntity.icon = R.drawable.ic_nodata_notext;
        emptyViewEntity.contentText = "暂无数据";

        mRecyclerEmptyAdapter.addData(emptyViewEntity);
        refreshListController.setEmpterAdapter(mRecyclerEmptyAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        contentView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mPicView == null){
                    mPicView = contentView.getChildAt(0).findViewById(R.id.itemRecyclerPic);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mPicView == null){
                    mPicView = contentView.getChildAt(0).findViewById(R.id.itemRecyclerPic);
                }
                changeTitle(getScroll());
            }
        });
    }

    /**
     * 获取滚动的高度
     * @return
     */
    private int getScroll(){
        View v = contentView.getChildAt(0);//获取listVIew的第一个子View
        if(v instanceof ImageView){//如果是继承自ImageView
            if(v != null){
                int top = v.getTop();//获取图片顶部的坐标
                return -top;//这里作为我们滚动的值返回
            }else{
                return 0;
            }
        }else{//如果不是继承自ImageView，说明已经把headerView完全滚出了屏幕,这里减去了标题栏的高度
            return mPicView.getHeight() - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
        }
    }

    /**
     * 根据滚动的高度，改变标题栏透明度
     * @param scroll
     */
    private void changeTitle(int scroll){
        int height = mPicView.getHeight() - (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
        float alpha = 1f/height;
        float a = alpha*scroll;//这里范围从0~1，0完全透明，1不透明

        //滚动变化限制，超过这个值，滚动不做改变
        if(a >= 1){//不透明
            titleBarLayout.getBackground().setAlpha((int)255);
        }else{
            a = a * 255;//这里转换成背景透明度的值0~255
            titleBarLayout.getBackground().setAlpha((int)a);
        }

    }

    @Override
    protected void initData() {
        super.initData();

        List<TestEntity> list = new ArrayList<>();
        TestEntity testEntity = new TestEntity();
        testEntity.viewType = ListType.HEADER.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        testEntity.imageResoureId = R.drawable.pic_eam;
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);


        testEntity = new TestEntity();
        testEntity.viewType = ListType.TITLE.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);

        testEntity = new TestEntity();
        testEntity.viewType = ListType.CONTENT.value();
        testEntity.name = String.valueOf(new Random().nextInt(3));
        list.add(testEntity);



        Flowable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        refreshListController.refreshComplete(list);
                    }
                });

    }
}
