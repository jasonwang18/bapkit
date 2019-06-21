package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.widget.CompoundButton;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.RadioBean;
import com.supcon.mes.mbap.view.MyRadioButton;

/**
 * Created by wangshizhan on 2019/6/20
 * Email:wangshizhan@supcom.com
 */
public class CustomRadioButtonAdapter extends BaseListDataRecyclerViewAdapter<RadioBean> {

    private int mTextColor, mTextSize;
    private int mCheckedPosition = 0;

    public CustomRadioButtonAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseRecyclerViewHolder<RadioBean> getViewHolder(int viewType) {
        return new ViewHolder(context);
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    class ViewHolder extends BaseRecyclerViewHolder<RadioBean>{

        private MyRadioButton checkBox;

        public ViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_radio_button;
        }

        @Override
        protected void initView() {
            super.initView();
            checkBox = itemView.findViewById(R.id.checkBox);
            if(mTextColor!=0){
                checkBox.setTextColor(mTextColor);
            }

            if(mTextSize!=0){
                checkBox.setTextSize(mTextSize);
            }
        }

        @Override
        protected void initListener() {
            super.initListener();
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    LogUtil.d("position:"+getAdapterPosition()+" isChecked:"+isChecked);
                    int currentPosition = getAdapterPosition();

                    if(currentPosition == mCheckedPosition || !isChecked){
                        return;
                    }
                    RadioBean radioBean = getItem(currentPosition);
                    radioBean.isChecked = true;
                    onItemChildViewClick(checkBox, 0, radioBean);
                    getItem(mCheckedPosition).isChecked = false;
                    notifyItemChanged(mCheckedPosition);
                    mCheckedPosition = currentPosition;
                }
            });

        }

        @Override
        protected void update(RadioBean data) {
            checkBox.setText(data.name);
            checkBox.setChecked(data.isChecked);

        }
    }
}
