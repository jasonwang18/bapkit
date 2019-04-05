package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.Transition;

/**
 * Created by wangshizhan on 2017/8/16.
 * Email:wangshizhan@supcon.com
 */

public class TransitionAdapterOld extends BaseListDataRecyclerViewAdapter<Transition> {

    public TransitionAdapterOld(Context context) {
        super(context);

    }

    @Override
    protected BaseRecyclerViewHolder<Transition> getViewHolder(int viewType) {
        return new WorkViewHolder(context);
    }



    class WorkViewHolder extends BaseRecyclerViewHolder<Transition> implements View.OnClickListener{

        Button tansition;

        public WorkViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initView() {
            super.initView();
            tansition = itemView.findViewById(R.id.transition);
        }

        @Override
        protected void initListener() {
            super.initListener();

            tansition.setOnClickListener(this);

        }

        @Override
        protected int layoutId() {
            return R.layout.item_transition;
        }

        @Override
        protected void update(Transition data) {
            tansition.setText(data.transitionDesc);
//            if("save".equals(data.outComeType)){
//                tansition.setBackgroundResource(R.drawable.sl_blue_btn);
//            }
//            else
            tansition.setTag(data.outComeType);
            tansition.setOnClickListener(this);
            tansition.setTextAppearance(context, R.style.BlueButton);
            tansition.setBackgroundResource(R.drawable.sl_orange_btn);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Transition transition = getItem(position);
            onItemChildViewClick(v, 0, transition);
        }
    }
}
