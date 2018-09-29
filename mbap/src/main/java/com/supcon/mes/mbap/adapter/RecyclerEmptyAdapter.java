package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.view.View;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.EmptyViewEntity;
import com.supcon.mes.mbap.view.CustomEmptyView;

import java.util.List;


/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */
public class RecyclerEmptyAdapter extends BaseListDataRecyclerViewAdapter<EmptyViewEntity> {
    public RecyclerEmptyAdapter(Context context) {
        super(context);
    }

    public RecyclerEmptyAdapter(Context context, List<EmptyViewEntity> list) {
        super(context, list);
    }

    @Override
    protected BaseRecyclerViewHolder<EmptyViewEntity> getViewHolder(int viewType) {
        return new EmptyViewHolder(context);
    }

    private class EmptyViewHolder extends BaseRecyclerViewHolder<EmptyViewEntity> {

        protected CustomEmptyView customEmptyView;

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
            customEmptyView = itemView.findViewById(R.id.customEmptyView);
        }

        @Override
        protected void initListener() {
            super.initListener();
            customEmptyView.setOnChildViewClickListener(new OnChildViewClickListener() {
                @Override
                public void onChildViewClick(View childView, int action, Object obj) {
                    onItemChildViewClick(childView,action,obj);
                }
            });
        }

        @Override
        protected void update(EmptyViewEntity data) {
            customEmptyView.update(data);
        }
    }
}
