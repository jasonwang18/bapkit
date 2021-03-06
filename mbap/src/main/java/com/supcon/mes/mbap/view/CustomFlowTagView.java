package com.supcon.mes.mbap.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 徐时运
 * @E-mail ciruy.victory@gmail.com
 * @date 2018/11/2118:46
 */
public class CustomFlowTagView extends ViewGroup {

    private ListAdapter mAdapter;
    private AdapterDataSetObserver mDataSetObserver;
    @SuppressLint("UseSparseArrays")
    private SparseArray mCheckTagArray = new SparseArray();

    public static final int FLOW_TAG_CHECKED_NONE = 0;
    public static final int FLOW_TAG_CHECKED_SINGLE = 1;
    public static final int FLOW_TAG_CHECKED_MULTI = 2;

    private int mTagCheckMode;

    private OnTagClickListener mOnTagClickListener;
    private OnTagSelectListener mOnTagSelectListener;

    public void setTagCheckedMode(int checkedMode) {
        this.mTagCheckMode = checkedMode;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public void setOnTagSelectListener(OnTagSelectListener onTagSelectListener) {
        mOnTagSelectListener = onTagSelectListener;
    }

    public CustomFlowTagView(Context context) {
        super(context);
    }

    public CustomFlowTagView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomFlowTagView(Context context, AttributeSet attributeSet, int theme) {
        super(context, attributeSet, theme);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int flowWidth = getWidth();

        int childLeft = 0;
        int childTop = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();

            if (childLeft + mlp.leftMargin + childWidth + mlp.rightMargin > flowWidth) {
                childTop += (mlp.topMargin + childHeight + mlp.bottomMargin);
                childLeft = 0;
            }

            int left = childLeft + mlp.leftMargin;
            int top = childTop + mlp.topMargin;
            int right = childLeft + mlp.leftMargin + childWidth;
            int bottom = childTop + mlp.topMargin + childHeight;
            childView.layout(left, top, right, bottom);

            childLeft += (mlp.leftMargin + childWidth + mlp.rightMargin);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取padding
        //获取它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //Flowlayout最终的宽度和高度值
        int resultWidth = 0;
        int resultHeight = 0;

        //测量每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        //遍历每一个子元素
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            //测量每一个子view的宽度和高度
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子view可能设置margin，添加margin的距离
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + mlp.leftMargin + mlp.rightMargin;
            int realChildHeight = childHeight + mlp.topMargin + mlp.bottomMargin;

            //如果当前一行宽度加上要加入的子view的宽度大于父容器给的宽度，就换行
            if ((lineWidth + realChildWidth) > sizeWidth) {
                //换行
                resultWidth = Math.max(lineWidth, realChildWidth);
                resultHeight += realChildHeight;

                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {
                //不换行，直接相加
                lineWidth += realChildWidth;
                //每一行高度取二者最大值
                lineHeight = Math.max(lineHeight, realChildHeight);
            }

            if (i == childCount - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
            }

            setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                    modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
        }
    }

    public void setAdapter(android.widget.ListAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        removeAllViews();
        mAdapter = adapter;

        if (mAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    private void reloadData() {
        removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int j = i;
            mCheckTagArray.put(i, false);
            final View childView = mAdapter.getView(i, null, this);
            addView(childView, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            final int finalI = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTagCheckMode == FLOW_TAG_CHECKED_NONE) {
                        if (mOnTagClickListener != null) {
                            mOnTagClickListener.onItemClick(CustomFlowTagView.this, childView, j);
                        }
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                        if ((Boolean) mCheckTagArray.get(j)) {
                            mCheckTagArray.put(j, false);
                            childView.setSelected(false);
                            if (mOnTagSelectListener != null) {
                                mOnTagSelectListener.onItemSelect(CustomFlowTagView.this, new ArrayList<Integer>());
                            }
                            return;
                        }

                        for (int k = 0; k < mAdapter.getCount(); k++) {
                            mCheckTagArray.put(k, false);
                            getChildAt(k).setSelected(false);
                        }
                        mCheckTagArray.put(j, true);
                        childView.setSelected(true);

                        if (mOnTagSelectListener != null) {
                            mOnTagSelectListener.onItemSelect(CustomFlowTagView.this, Arrays.asList(j));
                        }
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                        if ((Boolean) mCheckTagArray.get(j)) {
                            mCheckTagArray.put(j, false);
                            childView.setSelected(false);
                        } else {
                            mCheckTagArray.put(j, true);
                            childView.setSelected(true);
                        }

                        if (mOnTagSelectListener != null) {
                            List<Integer> list = new ArrayList<>();
                            for (int k = 0; k < mAdapter.getCount(); k++) {
                                if ((Boolean) mCheckTagArray.get(k)) {
                                    list.add(k);
                                }
                            }
                            mOnTagSelectListener.onItemSelect(CustomFlowTagView.this, list);
                        }
                    }

                }
            });
        }
    }

    public interface OnTagClickListener {
        void onItemClick(CustomFlowTagView parent, View view, int position);
    }

    public interface OnTagSelectListener {
        void onItemSelect(CustomFlowTagView parent, List<Integer> integerList);
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }
    }

}
