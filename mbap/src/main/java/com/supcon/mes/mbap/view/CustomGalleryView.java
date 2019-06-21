package com.supcon.mes.mbap.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.common.view.view.custom.ICustomView;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.GalleryAdapter;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.beans.SheetEntity;
import com.supcon.mes.mbap.utils.GridSpaceItemDecoration;
import com.supcon.mes.mbap.utils.NomalSpaceItemDecoration;
import com.supcon.mes.mbap.utils.SheetUtil;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;
import com.supcon.mes.mbap.utils.TextHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomGalleryView extends BaseRelativeLayout implements OnItemChildViewClickListener {

    TextView customGalleryText;
    ImageView customCameraIv/*, customGalleryIv*/;
    RecyclerView customGallery;

    private String mText;
    private int mTextSize;
    private int mTextColor, mBgColor;
    private int columns;
    private int lines;
    private int mCameraIconResId/*, mGalleryIconResId*/;
    private int mTextHeight;
    private boolean isNecessary;
    private boolean isIconVisible;
//    private boolean isGalleryIconVisible;
    private GalleryAdapter mGalleryAdapter;
    private boolean isEditable, isEnable;
    private String imgUrl;
    private boolean mHorizontal;

    public static final int ACTION_VIDEO_PLAY = 9;
    public static final int ACTION_VIEW = 10;
    public static final int ACTION_DELETE = 11;
    public static final int ACTION_TAKE_PICTURE_FROM_CAMERA = 12;
    public static final int ACTION_TAKE_PICTURE_FROM_GALLERY = 13;
    public static final int ACTION_TAKE_VIDEO_FROM_CAMERA = 14;

    public CustomGalleryView(Context context) {
        super(context);
    }

    public CustomGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Typeface newFont = MBapApp.fontType();
//            customGalleryText.setTypeface(newFont);
//        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_gallery;
    }

    @Override
    protected void initView() {
        super.initView();
        customGalleryText = findViewById(R.id.customGalleryText);
        customGallery = findViewById(R.id.customGallery);
        customCameraIv = findViewById(R.id.customCameraIv);
//        customGalleryIv = findViewById(R.id.customGalleryIv);

        if(!TextUtils.isEmpty(mText)){
            customGalleryText.setText(mText);
            customGalleryText.setVisibility(View.VISIBLE);
        }

        if(mTextSize != 0){
            customGalleryText.setTextSize(mTextSize);
        }

        if(mTextColor!= 0){
            customGalleryText.setTextColor(mTextColor);
        }

        setNecessary(isNecessary);

        if(columns == 0){
            columns = 3;//默认一行3个
        }

        if(lines == 0){
            lines = 1;
        }

        if(mBgColor!=0)
            customGallery.setBackgroundColor(mBgColor);

        GridLayoutManager manager = new GridLayoutManager(getContext(), columns);
        if (mHorizontal){
            manager.setOrientation(GridLayoutManager.HORIZONTAL);
        }

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override

            public int getSpanSize(int position) {
                int itemCount = mGalleryAdapter.getItemCount();
                if(position == 0 && itemCount == 1 && !isEditable){

                    return columns;
                }

                return 1;
            }

        });


        customGallery.setLayoutManager(manager);
//        customGallery.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        customGallery.addItemDecoration(new NomalSpaceItemDecoration(DisplayUtil.dip2px(2, getContext())));
        mGalleryAdapter = new GalleryAdapter(getContext(), isEditable);
        customGallery.setAdapter(mGalleryAdapter);


        setIconVisibility(isIconVisible);
//        setGalleryIconVisibility(isGalleryIconVisible);

        if(mCameraIconResId!=-1)
            customCameraIv.setImageResource(mCameraIconResId);

//        if(mGalleryIconResId!=-1)
//            customGalleryIv.setImageResource(mGalleryIconResId);

        if(mTextHeight!=-1){
            setTextHeight(mTextHeight);
        }

        setEditable(isEditable);
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomGalleryView);
            mText= array.getString(R.styleable.CustomGalleryView_text);
            mTextSize =  array.getInt(R.styleable.CustomGalleryView_text_size, 0);
            mTextColor = array.getColor(R.styleable.CustomGalleryView_text_color, 0);
            mBgColor = array.getColor(R.styleable.CustomGalleryView_bg_color, 0);
            isNecessary = array.getBoolean(R.styleable.CustomGalleryView_necessary, false);
            columns = array.getInteger(R.styleable.CustomGalleryView_columns, 3);
            lines = array.getInteger(R.styleable.CustomGalleryView_lines, 1);
            isEditable = array.getBoolean(R.styleable.CustomGalleryView_editable, true);
            isIconVisible = array.getBoolean(R.styleable.CustomGalleryView_icon_visible, false);
//            isGalleryIconVisible = array.getBoolean(R.styleable.CustomGalleryView_gallery_icon_visible, false);
            mCameraIconResId = array.getResourceId(R.styleable.CustomGalleryView_camera_icon_res, -1);
//            mGalleryIconResId = array.getResourceId(R.styleable.CustomGalleryView_gallery_icon_res, -1);
            mTextHeight = array.getDimensionPixelSize(R.styleable.CustomGalleryView_text_height, -1);
            mHorizontal = array.getBoolean(R.styleable.CustomGalleryView_horizontal, false);
            array.recycle();
        }
    }


    @Override
    protected void initListener() {
        super.initListener();

        mGalleryAdapter.setOnItemChildViewClickListener(this);
        customCameraIv.setOnClickListener(view -> {
            /*if(mGalleryAdapter.getItemCount() == 9){
                ToastUtils.show(getContext(), "最多支持9张照片！");
                return;
            }

            onChildViewClick(view, ACTION_TAKE_PICTURE_FROM_CAMERA, -1);*/

            showCustomDialog();

        });

//        customGalleryIv.setOnClickListener(view -> {
//
//            if(mGalleryAdapter.getItemCount() == 9){
//                ToastUtils.show(getContext(), "最多支持9张照片！");
//                return;
//            }
//
//            onChildViewClick(view, ACTION_TAKE_PICTURE_FROM_GALLERY, -1);
//        });

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(!enabled)
            setEditable(false);
        if (enabled) {
            customGalleryText.setAlpha(1);
        } else {
            customGalleryText.setAlpha(0.5f);
        }
    }

    public void setTextHeight(int height){
        ViewGroup.LayoutParams lp =  customGalleryText.getLayoutParams();
        lp.height = height;
        customGalleryText.setLayoutParams(lp);


        ViewGroup.LayoutParams lp2 =  customCameraIv.getLayoutParams();
        lp2.height = height;
        customCameraIv.setLayoutParams(lp2);

//        lp2 =  customGalleryIv.getLayoutParams();
//        lp2.height = height;
//        customGalleryIv.setLayoutParams(lp2);
    }

    public void setText(String text){
        customGalleryText.setText(text);
    }

    public void setTextColor(int color){
        customGalleryText.setTextColor(color);
    }


    public void setTextWidth(int width){
        LayoutParams lp = (LayoutParams) customGalleryText.getLayoutParams();
        lp.width = width;
        customGalleryText.setLayoutParams(lp);

    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public void setNecessary(boolean isNecessary){

//        if(isNecessary){
//            customGalleryText.setTextColor(getResources().getColor(R.color.customRed));
//        }
//        else{
//            customGalleryText.setTextColor(getResources().getColor(MBapConfig.NECESSARY_FALSE_COLOR));
//        }
        TextHelper.setRequired(isNecessary, customGalleryText);
    }

    public void setGalleryBeans(List<GalleryBean> data){
        mGalleryAdapter.clear();
        mGalleryAdapter.setList(data);
        mGalleryAdapter.notifyDataSetChanged();
    }

    public void deletePic(int position){
        mGalleryAdapter.getList().remove(position);
        mGalleryAdapter.notifyDataSetChanged();
    }

    public void clear(){
        mGalleryAdapter.clear();
        mGalleryAdapter.notifyDataSetChanged();
    }

    public void addGalleryBean(GalleryBean bean){
        mGalleryAdapter.addData(bean);
        mGalleryAdapter.notifyItemInserted(mGalleryAdapter.getItemCount());
    }

    public GalleryAdapter getGalleryAdapter() {
        return mGalleryAdapter;
    }

    @Override
    public void onItemChildViewClick(View childView, int position, int action, Object obj) {

        if(action == ACTION_DELETE){
//            mGalleryAdapter.getList().remove(position);
//            mGalleryAdapter.notifyDataSetChanged();
            if(!isEditable){
                return;
            }
        }

        onChildViewClick(childView, action, obj);
    }

    public void setIconVisibility(boolean iconVisibility) {
        isIconVisible = iconVisibility;

        if(isIconVisible){
            customCameraIv.setVisibility(VISIBLE);
        }
        else{
            customCameraIv.setVisibility(GONE);
        }

    }

//    public void setGalleryIconVisibility(boolean iconVisibility) {
//        isGalleryIconVisible = iconVisibility;
//
//        if(isGalleryIconVisible){
//            customGalleryIv.setVisibility(VISIBLE);
//        }
//        else{
//            customGalleryIv.setVisibility(GONE);
//        }
//
//    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    private static final String[] SHEET_ENTITY = {"拍摄照片","本地照片", "拍摄短视频"};

    @SuppressLint("CheckResult")
    private void showCustomDialog() {
        new CustomSheetDialog(context)
                .sheet("请选择获取照片或视频的方式", SheetUtil.getSheetEntities(SHEET_ENTITY))
                .setOnItemChildViewClickListener((childView, position, action, obj) -> {

                    if(!check(position)){
                        return;
                    }

                    if (position == 0) {
                        onChildViewClick(customCameraIv, ACTION_TAKE_PICTURE_FROM_CAMERA, -1);
                    }
                    else if(position == 2){
                        onChildViewClick(customCameraIv, ACTION_TAKE_VIDEO_FROM_CAMERA, -1);
                    }
                    else {
                        onChildViewClick(customCameraIv, ACTION_TAKE_PICTURE_FROM_GALLERY, -1);
                    }
                }).show();
    }

    private boolean check(int position) {

        if(mGalleryAdapter.getItemCount() == 9){
            ToastUtils.show(getContext(), "最多支持9张照片或视频！");
            return false;
        }

        if(checkVideo() && position == 2){
            ToastUtils.show(getContext(), "最多支持录制1次视频！");
            return false;
        }

        return true;
    }

    private boolean checkVideo(){
        if(mGalleryAdapter.getItemCount()!=0)
        for(GalleryBean galleryBean: mGalleryAdapter.getList()){
            if(galleryBean.fileType == GalleryAdapter.FILE_TYPE_VIDEO){
                return true;
            }
        }

        return false;
    }

}
