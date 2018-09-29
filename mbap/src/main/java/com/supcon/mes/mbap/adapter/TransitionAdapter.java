package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.Transition;

/**
 * Created by wangshizhan on 2017/8/16.
 * Email:wangshizhan@supcon.com
 */

public class TransitionAdapter extends BaseListDataRecyclerViewAdapter<Transition> {

    private int position;

    public TransitionAdapter(Context context) {
        super(context);

    }

    @Override
    protected BaseRecyclerViewHolder<Transition> getViewHolder(int viewType) {
        return new WorkViewHolder(context);
    }


    public void setPosition(int position) {
        this.position = position;
    }



    class WorkViewHolder extends BaseRecyclerViewHolder<Transition> implements View.OnClickListener{

        TextView transitionRouter;

        public WorkViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initView() {
            super.initView();
            transitionRouter = itemView.findViewById(R.id.transitionRouter);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Typeface newFont = MBapApp.fontType();
                transitionRouter.setTypeface(newFont);
            }


        }

        @Override
        protected void initListener() {
            super.initListener();

            transitionRouter.setOnClickListener(this);

        }

        @Override
        protected int layoutId() {
            return R.layout.item_transition_router;
        }

        @Override
        protected void update(Transition data) {

            transitionRouter.setText(data.transitionDesc);
            transitionRouter.setTag(data.outComeType);
            transitionRouter.setOnClickListener(this);
            if(position == getAdapterPosition()){
                transitionRouter.setTextAppearance(context, R.style.OrangeTransitionButton);
                transitionRouter.setBackgroundResource(R.drawable.sh_transition_router_orange);
            }
            else{
                transitionRouter.setTextAppearance(context, R.style.GrayTransitionButton);
                transitionRouter.setBackgroundResource(R.drawable.sh_transition_router_gray);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TransitionAdapter.this.position = position;
            Transition transition = getItem(position);
            onItemChildViewClick(v, 0, transition);
        }
    }
}
