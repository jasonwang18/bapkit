package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.GalleryAdapter;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.utils.GridSpaceItemDecoration;
import com.supcon.mes.mbap.utils.NomalSpaceItemDecoration;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;
import com.supcon.mes.mbap.utils.TextHelper;

import java.util.List;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomGalleryView extends BaseRelativeLayout implements OnItemChildViewClickListener {

    TextView customGalleryText;
    ImageView customGalleryIv;
    RecyclerView customGallery;

    private String mText;
    private int mTextSize;
    private int mTextColor, mBgColor;
    private int columns;
    private int lines;
    private int mIconResId;
    private int mTextHeight;
    private boolean isNecessary;
    private boolean isIconVisible;
    private GalleryAdapter mGalleryAdapter;
    private boolean isEditable, isEnable;
    private String imgUrl;

    public static final int ACTION_VIEW = 10;
    public static final int ACTION_DELETE = 11;
    public static final int ACTION_TAKE_PICTURE_FROM_CAMERA = 12;
    public static final int ACTION_TAKE_PICTURE_FROM_GALLERY = 13;

    public CustomGalleryView(Context context) {
        super(context);
    }

    public CustomGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customGalleryText.setTypeface(newFont);
        }
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
        customGalleryIv = findViewById(R.id.customGalleryIv);

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
            columns = 4;//默认一行4个
        }

        if(lines == 0){
            lines = 1;
        }

        if(mBgColor!=0)
            customGallery.setBackgroundColor(mBgColor);
        customGallery.setLayoutManager(new GridLayoutManager(getContext(), columns));
        customGallery.addItemDecoration(new NomalSpaceItemDecoration(DisplayUtil.dip2px(2, getContext())));
        mGalleryAdapter = new GalleryAdapter(getContext(), isEditable);
        customGallery.setAdapter(mGalleryAdapter);

        setIconVisibility(isIconVisible);

        if(mIconResId!=-1)
            customGalleryIv.setImageResource(mIconResId);

        if(mTextHeight!=-1){
            setTextHeight(mTextHeight);
        }

        setEnabled(isEnable);
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
            isEditable = array.getBoolean(R.styleable.CustomGalleryView_editable, false);
            isIconVisible = array.getBoolean(R.styleable.CustomGalleryView_icon_visible, false);
            mIconResId = array.getResourceId(R.styleable.CustomGalleryView_icon_res, -1);
            mTextHeight = array.getDimensionPixelSize(R.styleable.CustomGalleryView_text_height, -1);
            isEnable =  array.getBoolean(R.styleable.CustomGalleryView_enable, true);
            array.recycle();
        }
    }


    @Override
    protected void initListener() {
        super.initListener();

        mGalleryAdapter.setOnItemChildViewClickListener(this);
        customGalleryIv.setOnClickListener(view -> {

            if(mGalleryAdapter.getItemCount() == 9){
                ToastUtils.show(getContext(), "最多支持9张照片！");
                return;
            }

            onChildViewClick(view, ACTION_TAKE_PICTURE_FROM_CAMERA, -1);
        });
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


        ViewGroup.LayoutParams lp2 =  customGalleryIv.getLayoutParams();
        lp2.height = height;
        customGalleryIv.setLayoutParams(lp2);
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
            customGalleryIv.setVisibility(VISIBLE);
        }
        else{
            customGalleryIv.setVisibility(GONE);
        }

    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
