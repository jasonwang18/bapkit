package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.adapter.FilterAdapter;
import com.supcon.mes.mbap.beans.FilterBean;
import com.supcon.mes.mbap.utils.GridSpaceItemDecoration;
import com.supcon.mes.mbap.utils.NomalSpaceItemDecoration;
import com.supcon.mes.mbap.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by wangshizhan on 2017/12/22.
 * Email:wangshizhan@supcon.com
 */

public class CustomFilterView<T extends FilterBean> extends BaseLinearLayout implements View.OnClickListener{

    public static final int VIEW_TYPE_ALL = -1;

    private TextView customFilterTv;
    private ImageView customFilterIcon;
    private LinearLayout customFilterLayout;

    private String mText;
    private int mTextSize, mItemTextSize;
    private int mTextColor, mItemBgColor, mItemTextColor;
    private int mPosition;
    private int mIconRes;
    private boolean isExpanded = false;

    private FilterAdapter<T> mFilterAdapter;

    private List<T> datas = new ArrayList<>();
//    private List<String> names = new ArrayList<>();

    private PopupWindow mPopupWindow;

    private FilterSelectChangedListener mFilterSelectChangedListener;

    public CustomFilterView(Context context) {
        super(context);
    }

    public CustomFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customFilterTv.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_filter;
    }

    @Override
    protected void initView() {
        super.initView();
        customFilterTv = findViewById(R.id.customFilterTv);
        customFilterIcon = findViewById(R.id.customFilterIcon);

        customFilterLayout = findViewById(R.id.customFilterLayout);

        if(mTextColor !=0){
            customFilterTv.setTextColor(mTextColor);
        }

        if(mTextSize !=0){
            customFilterTv.setTextSize(mTextSize);
        }

        if(!TextUtils.isEmpty(mText)){
            customFilterTv.setText(mText);
        }


        if(mIconRes!=0){
            setIcon(mIconRes);
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        customFilterTv.setOnClickListener(this);
        customFilterIcon.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFilterView);
            mText = array.getString(R.styleable.CustomFilterView_text);
            mTextSize = array.getInt(R.styleable.CustomFilterView_text_size, 0);
            mTextColor = array.getColor(R.styleable.CustomFilterView_text_color, 0);
            mItemTextColor = array.getColor(R.styleable.CustomFilterView_item_text_color, 0);
            mItemTextSize = array.getInt(R.styleable.CustomFilterView_item_text_size, 0);
            mItemBgColor = array.getColor(R.styleable.CustomFilterView_item_bg_color, 0);
            mPosition = array.getInt(R.styleable.CustomFilterView_position, -1);
            mIconRes  = array.getResourceId(R.styleable.CustomFilterView_icon_res, 0);
            array.recycle();
        }
    }

    public void setIcon(int resId){
        mIconRes = resId;
        customFilterIcon.setImageResource(mIconRes);
    }

    public void setData(List<T> data){

        if(datas.size() != 0){
            datas.clear();
        }

//        if(names.size()!=0){
//            names.clear();
//        }

        datas.addAll(data);
//        addNames(data);

        initPop();
    }

    private void addNames(List<T> data) {
//
//        Flowable.fromIterable(data)
//                .compose(RxSchedulers.io_main())
//                .subscribe(t -> names.add(t.name));

    }

    public void addData(T data){
        datas.add(data);
//        names.add(data.name);

//        mFilterAdapter.addData(data);
        mFilterAdapter.notifyItemInserted(datas.size()-1);
    }

    public void setCurrentPosition(int position){
        this.mPosition = position;
        mFilterAdapter.setPosition(position);
        mFilterAdapter.notifyDataSetChanged();
    }
    public Integer getCurrentPosition() {
        return mPosition;
    }
    public String getCurrentItem() {
        return datas.get(mPosition).name;
    }
    int position;
    public void setCurrentItem(FilterBean filterBean){
        position =0;
        Flowable.fromIterable(datas)
                .compose(RxSchedulers.io_main())
                .filter(t -> {
                    if(t.name.trim().equals(filterBean.name.trim())){
                        return true;
                    }
                    position++;
                    return false;
                })
                .map(t -> position)
                .subscribe(integer -> {
                    CustomFilterView.this.mPosition = integer.intValue();
                    mFilterAdapter.setPosition(mPosition);
                    mFilterAdapter.notifyDataSetChanged();
                    customFilterTv.setText(filterBean.name);
                });
    }

    public void setCurrentItem(String filterName){
        position =0;
        Flowable.fromIterable(datas)
                .compose(RxSchedulers.io_main())
                .filter(t -> {
                    if(t.name.trim().equals(filterName.trim())){
                        return true;
                    }
                    position++;
                    return false;
                })
                .map(t -> position)
                .subscribe(integer -> {
                    CustomFilterView.this.mPosition = integer.intValue();
                    mFilterAdapter.setPosition(mPosition);
                    mFilterAdapter.notifyDataSetChanged();
                    customFilterTv.setText(filterName);
                });
    }

    public <T extends FilterBean> void setFilterSelectChangedListener(FilterSelectChangedListener<T> filterSelectChangedListener) {
        mFilterSelectChangedListener = filterSelectChangedListener;
    }

    @Override
    public void onClick(View v) {
        if(isExpanded){
            shrink();
        }
        else{
            expanded();
        }
    }

    private void expanded() {
        if(mPopupWindow == null){
            return;
        }
        if(mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
        else{
//            mFilterAdapter.setList(datas);
            mPopupWindow.showAsDropDown(customFilterIcon);
        }
        isExpanded = true;
    }


    private void shrink() {

        if(mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
       isExpanded = false;
    }


    private void initPop() {
        View popupView = LayoutInflater.from(SystemUtil.getActivityFromView(this)).inflate(R.layout.ly_filter_recyclerview, null);
        RecyclerView contentView = popupView.findViewById(R.id.contentView);
        contentView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        contentView.addItemDecoration(new NomalSpaceItemDecoration(DisplayUtil.dip2px(3, getContext())));
        mFilterAdapter = new FilterAdapter<>(SystemUtil.getActivityFromView(this));
        mFilterAdapter.setOnItemChildViewClickListener((childView, position, action, obj) -> {
            onChildViewClick(childView, action, childView.getTag());
            mFilterAdapter.notifyDataSetChanged();
            mPopupWindow.dismiss();
            isExpanded = false;
            FilterBean filterBean = (FilterBean) obj;
//            customFilterTv.setText(filterBean.name);
            setCurrentItem(filterBean);
            if(mFilterSelectChangedListener!=null){
                mFilterSelectChangedListener.onFilterSelected(filterBean);
            }

            if(mTextColor == 0) {
                if (filterBean.type == VIEW_TYPE_ALL) {
                    customFilterTv.setTextColor(getContext().getResources().getColor(R.color.textColorlightblack));
//                    customFilterTv.setBackgroundResource(R.color.transparent);
//                    customFilterTv.setText(mText);
                } else {
                    customFilterTv.setTextColor(getContext().getResources().getColor(R.color.filterTextBlue));
//                    customFilterTv.setBackgroundResource(R.drawable.sh_filter_gray);
                }
            }

        });
        contentView.setAdapter(mFilterAdapter);
        mFilterAdapter.setPosition(mPosition);

        if(mItemTextColor!=0)
            mFilterAdapter.setTextColor(mItemTextColor);

        if(mItemTextSize!=0)
            mFilterAdapter.setTextSize(mItemTextSize);

        mFilterAdapter.setList(datas);

//        int size = datas.size();
//        int lines = size%4==0?size/4:size/4+1;

        LinearLayout.LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.width = DisplayUtil.getScreenWidth(getContext())- 2*DisplayUtil.dip2px(5, getContext());
//        lp.height = DisplayUtil.dip2px(48*lines, getContext());
        contentView.setLayoutParams(lp);


        mPopupWindow = new PopupWindow(popupView,
//                DisplayUtil.dip2px(360, getContext())
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
                /*DisplayUtil.dip2px(50*lines, getContext())*/);

        mPopupWindow.setAnimationStyle(R.style.fadeStyle);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.sh_white_stroke));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();

        mPopupWindow.setOnDismissListener(() -> isExpanded = false);

    }


    public interface FilterSelectChangedListener<T extends FilterBean>{

        void onFilterSelected(T t);

    }
}
