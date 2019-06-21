package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supcon.common.com_http.BaseEntity;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xushiyun
 * @Create-time 5/27/19
 * @Pageage com.supcon.mes.mbap.view
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public class CustomInfiniteListView<T extends BaseEntity> extends BaseLinearLayout {
    TextView title;
    ImageView addBtn;
    ImageView refreshBtn;
    LinearLayout contentView;
    private boolean enableAdd;
    private boolean contentViewVisible =true;
    private boolean enableSave;
    private List<T> contentViewList = new ArrayList<>();
    private MyViewController<T> mTMyViewController;
    
    public int getListSize() {
        return contentViewList.size();
    }
    
    public void setTitle(String title) {
        this.title.setText(title);
    }
    
    public void addEntity(T entity) {
        contentViewList.add(entity);
        contentView.addView(createChildView(entity));
    }
    
    public TextView title() {
        return title;
    }
    
    public void remove(int position) {
        if(position>=contentViewList.size()) return;
        if (position<0) return;
        contentViewList.remove(position);
        contentView.removeViewAt(position);
    }
    
    public void removeAll() {
        contentViewList.clear();
        contentView.removeAllViewsInLayout();
    }
    
    public void removeFirst() {
        remove(0);
    }
    
    public void removeLast() {
        int lastIndex = contentViewList.size()-1;
        if(lastIndex <0 )return;
        contentViewList.remove(lastIndex);
        contentView.removeViewAt(lastIndex);
    }
    
    public void setContent(List<T> content) {
        if (contentViewList == null) contentViewList = content;
        contentViewList.addAll(content);
        refresh();
    }
    public void toggleContentVisibility() {
        contentViewVisible = !contentViewVisible;
        contentView.setVisibility(contentViewVisible?VISIBLE:GONE);
    }
    
    @Override
    public void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomInfiniteListView);
            enableAdd = array.getBoolean(R.styleable.CustomInfiniteListView_enable_add, true);
            enableSave    = array.getBoolean(R.styleable.CustomInfiniteListView_enable_save, true);
            array.recycle();
        }
    }
    public List<T> getList() {
        return contentViewList;
    }
    
    private void refresh() {
        contentView.removeAllViewsInLayout();
        for (T entity : contentViewList)
            contentView.addView(createChildView(entity));
    }
    
    private View createChildView(T entity) {
        View childView = LayoutInflater.from(context).inflate(mTMyViewController.layout(), (ViewGroup) null,false);
        childView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTMyViewController.injectData(entity,childView);
        return childView;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
    
    public void setDefaultItemViewController(MyViewController<T> baseViewController) {
        this.mTMyViewController = baseViewController;
    }
    
    public CustomInfiniteListView(Context context) {
        this(context, null);
    }
    
    public CustomInfiniteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected int layoutId() {
        return R.layout.ly_custom_infinite_list_view;
    }
    
    
    @Override
    protected void initView() {
        super.initView();
        title = findViewById(R.id.title);
        addBtn = findViewById(R.id.addBtn);
        refreshBtn = findViewById(R.id.refreshBtn);
        contentView = findViewById(R.id.contentView);
//        addBtn.setVisibility(enableAdd?VISIBLE:GONE);
//        refreshBtn.setVisibility(enableSave?VISIBLE:GONE);
    }
    
    public ImageView addBtn() {
        return addBtn;
    }
    
    public ImageView refreshBtn() {
        return refreshBtn;
    }
    
    
    public static abstract class MyViewController<Data extends BaseEntity>  {
        protected View rootView ;
        public MyViewController(View rootView) {
            this.rootView = rootView;
        }
        
        public MyViewController(Context context) {
            rootView = LayoutInflater.from(context).inflate(layout(),null, false);
        }
        
        public abstract int layout();
        
        public void injectData(Data data) {
        
        }
        
        public void injectData(Data data,View view) {
        
        }
    }
}
