package com.supcon.mes.mbap.adapter;

import android.content.Context;

import com.supcon.common.view.base.adapter.BaseListViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseViewHolder;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.EmptyViewEntity;
import com.supcon.mes.mbap.view.CustomEmptyView;

import java.util.List;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */
public class ListEmptyAdapter extends BaseListViewAdapter<EmptyViewEntity> {
    public ListEmptyAdapter(Context context) {
        super(context);
    }

    public ListEmptyAdapter(Context context, List<EmptyViewEntity> list) {
        super(context, list);
    }

    @Override
    protected BaseViewHolder<EmptyViewEntity> getViewHolder(int position) {
        return new EmptyViewHolder(context);
    }

    private class EmptyViewHolder extends BaseViewHolder<EmptyViewEntity> {

        protected CustomEmptyView emptyView;

        public EmptyViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_empty;
        }

        @Override
        protected void initView() {
            super.initView();
            emptyView = rootView.findViewById(R.id.customEmptyView);
        }

        @Override
        protected void initListener() {
            super.initListener();
            emptyView.setOnChildViewClickListener((childView, action, obj) -> onItemChildViewClick(childView,action,obj));
        }

        @Override
        protected void update(EmptyViewEntity data) {
            emptyView.update(data);
        }
    }
}
