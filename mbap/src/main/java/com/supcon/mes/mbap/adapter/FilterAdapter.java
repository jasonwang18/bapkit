package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.FilterBean;

/**
 * Created by wangshizhan on 2017/8/16.
 * Email:wangshizhan@supcon.com
 */

public class FilterAdapter<T extends FilterBean> extends BaseListDataRecyclerViewAdapter<T> {

    private int textSize;
    private int textColor;
    private int position;

    public FilterAdapter(Context context) {
        super(context);

    }

    @Override
    protected BaseRecyclerViewHolder<T> getViewHolder(int viewType) {
        return new WorkViewHolder(context);
    }


    public void setTextSize(int textSize){
        this.textSize = textSize;
    }

    public void setTextColor(int textColor){
        this.textColor = textColor;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    class WorkViewHolder extends BaseRecyclerViewHolder<T> implements View.OnClickListener{

        TextView filterName;

        public WorkViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initView() {
            super.initView();
            filterName = itemView.findViewById(R.id.filterName);
            if(textSize!=0){
                filterName.setTextSize(textSize);
            }

            if(textColor!=0){
                filterName.setTextColor(textColor);
            }
        }

        @Override
        protected void initListener() {
            super.initListener();

            filterName.setOnClickListener(this);

        }

        @Override
        protected int layoutId() {
            return R.layout.item_filter;
        }

        @Override
        protected void update(FilterBean data) {

            if(getAdapterPosition() == position){
                filterName.setTextColor(context.getResources().getColor(R.color.filterTextBlue));
                filterName.setBackgroundResource(R.drawable.sh_filter_light_blue);
            }
            else{
                if(textColor!=0){
                    filterName.setTextColor(textColor);
                }
                else{
                    filterName.setTextColor(context.getResources().getColor(R.color.textColorlightblack));
                }
                filterName.setBackgroundResource(R.drawable.sh_filter_gray);
            }
            filterName.setText(data.name);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            FilterAdapter.this.position = position;
            FilterBean filterBean = getItem(position);
            onItemChildViewClick(v, 0, filterBean);

        }
    }
}
