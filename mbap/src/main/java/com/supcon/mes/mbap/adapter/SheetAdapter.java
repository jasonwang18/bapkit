package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.annotation.BindByTag;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.SheetEntity;

import java.util.List;

/**
 * Created by wangshizhan on 2018/5/16.
 * Email:wangshizhan@supcon.com
 */

public class SheetAdapter extends BaseListDataRecyclerViewAdapter<SheetEntity> {

    public SheetAdapter(Context context) {
        super(context);
    }

    public SheetAdapter(Context context, List<SheetEntity> list) {
        super(context, list);
    }


    @Override
    protected BaseRecyclerViewHolder<SheetEntity> getViewHolder(int viewType) {
        return new SheetViewHolder(context);
    }

    class SheetViewHolder extends BaseRecyclerViewHolder<SheetEntity>{

        @BindByTag("sheetName")
        TextView sheetName;

        public SheetViewHolder(Context context) {
            super(context);
        }

        public SheetViewHolder(Context context, ViewGroup parent) {
            super(context, parent);
        }

        public SheetViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            super.initView();
        }

        @Override
        protected int layoutId() {
            return R.layout.item_sheet;
        }

        @Override
        protected void update(SheetEntity data) {
            sheetName.setText(data.name);
        }
    }
}
