package com.supcon.mes.mbap.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.AdPagerAdapter;
import com.supcon.mes.mbap.adapter.GalleryAdapter;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.utils.GridSpaceItemDecoration;
import com.supcon.mes.mbap.utils.ImageCarouseHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomAdView extends BaseRelativeLayout implements OnItemChildViewClickListener {

    ViewPager customAdVP;
    LinearLayout customAdPointLayout;
    List<ImageView> mImageViews = new ArrayList<>();
    List<ImageView> pointimgs = new ArrayList<>();

    private AdPagerAdapter mAdPagerAdapter;

    public Handler handler;

    public CustomAdView(Context context) {
        super(context);
    }

    public CustomAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_ad;
    }

    @Override
    protected void initData() {
        super.initData();
        handler = new ImageCarouseHandler(new WeakReference<>(this));
    }

    @Override
    protected void initView() {
        super.initView();
        customAdVP = findViewById(R.id.customAdVP);
        customAdPointLayout = findViewById(R.id.customAdPointLayout);


    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

    }



    @Override
    protected void initListener() {
        super.initListener();
        customAdVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                showpoint(position);
                handler.sendMessage(Message.obtain(handler, ImageCarouseHandler.MSG_PAGE_CHANGED, position, 0));

            }

            //覆写该方法实现轮播效果
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ImageCarouseHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ImageCarouseHandler.MSG_UPDATE_IMAGE, ImageCarouseHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });


    }


    private void showpoint(int position) {
        //dian0-白色 dian1-灰色
        int length = pointimgs.size();
        for (int i = 0; i < length; i++) {
            pointimgs.get(i).setImageResource(R.drawable.sh_ad_point);
        }
        if(length != 0){
            pointimgs.get(position % length).setImageResource(R.drawable.sh_ad_point_s);
        }
    }

    @SuppressLint("CheckResult")
    public void setGalleryBeans(List<GalleryBean> data){

        Flowable.fromIterable(data)
                .compose(RxSchedulers.io_main())
                .subscribe(galleryBean -> {
                    ImageView imageView = new ImageView(getContext());
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(lp);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(!TextUtils.isEmpty(galleryBean.localPath)){
                        Glide.with(getContext()).load(galleryBean.localPath).into(imageView);
                    }
                    else if(galleryBean.resId != 0){
                        Glide.with(getContext()).load(galleryBean.resId).into(imageView);
                    }
                    else if(!TextUtils.isEmpty(galleryBean.url)){
                        Glide.with(getContext()).load(galleryBean.url).into(imageView);
                    }
                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onChildViewClick(v,0,galleryBean);
                        }
                    });

//                        imageView.setImageResource(galleryBean.resId);
                    mImageViews.add(imageView);

                    ImageView point = new ImageView(getContext());
                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                            DisplayUtil.dip2px(7, getContext()),
                            DisplayUtil.dip2px(7, getContext()));
                    lp2.leftMargin = 3;
                    point.setLayoutParams(lp2);
                    point.setImageResource(R.drawable.sh_ad_point);
                    pointimgs.add(point);
                    customAdPointLayout.addView(point);
                }, throwable -> LogUtil.i(""+throwable.getMessage()), new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mAdPagerAdapter == null){
                            mAdPagerAdapter = new AdPagerAdapter(getContext(), mImageViews);
                            customAdVP.setAdapter(mAdPagerAdapter);
                        }
                        else{
                            mAdPagerAdapter.notifyDataSetChanged();
                        }

                        customAdVP.setCurrentItem(0);
                        //开始轮播效果
                        handler.sendEmptyMessageDelayed(ImageCarouseHandler.MSG_UPDATE_IMAGE, ImageCarouseHandler.MSG_DELAY);
                        //注意，设置Page 即缓存页面的个数，数过小时会出现fragment重复加载的问题
                    }
                });

/*        Flowable.fromIterable(data)
                .compose(RxSchedulers.io_main())
                .map(new Function<GalleryBean, ImageView>() {
                    @Override
                    public ImageView apply(GalleryBean galleryBean) throws Exception {
                        ImageView imageView = new ImageView(getContext());
                        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        imageView.setLayoutParams(lp);
                        Glide.with(getContext()).load(galleryBean.localPath).into(imageView);
//                        imageView.setImageResource(galleryBean.resId);
                        return imageView;
                    }
                })
                .subscribe(new Consumer<ImageView>() {
                    @Override
                    public void accept(ImageView imageView) throws Exception {
                        mImageViews.add(imageView);

                        ImageView point = new ImageView(getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                DisplayUtil.dip2px(7,getContext()),
                                DisplayUtil.dip2px(7,getContext()));
                        lp.leftMargin = 3;
                        point.setLayoutParams(lp);
                        point.setImageResource(R.drawable.sh_ad_point);
                        pointimgs.add(point);
                        customAdPointLayout.addView(point);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.i(""+throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mAdPagerAdapter == null){
                            mAdPagerAdapter = new AdPagerAdapter(getContext(), mImageViews);
                            customAdVP.setAdapter(mAdPagerAdapter);
                        }
                        else{
                            mAdPagerAdapter.notifyDataSetChanged();
                        }

                        customAdVP.setCurrentItem(0);
                        showpoint(0);
                        handler.sendMessage(Message.obtain(handler, ImageCarouseHandler.MSG_UPDATE_IMAGE, 0, 0));
                    }
                });*/



    }

    public void ChanggeViewPagerCurrentItem(int currentItem){
        customAdVP.setCurrentItem(currentItem);
        showpoint(currentItem);
    }


    public int getItemCount(){
        return mImageViews.size();
    }

    public void clear(){
        mImageViews.clear();
        pointimgs.clear();
        customAdPointLayout.removeAllViews();
        mAdPagerAdapter.clear();
        mAdPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemChildViewClick(View childView, int position, int action, Object obj) {
        onChildViewClick(childView, action, obj);
    }
}
