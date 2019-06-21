package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.annotation.BindByTag;
import com.supcon.common.view.base.adapter.BaseRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.mes.mbap.beans.BaseMultiStageEntity;
import com.supcon.mes.mbap.controller.InjectorViewController.ConsumerViewController;
import com.supcon.mes.mbap.controller.InjectorViewController.InjectorViewController;
import com.supcon.mes.mbap.controller.InjectorViewController.MultiStageInjectorViewController;
import com.supcon.mes.mbap.controller.InjectorViewController.MutiStageConsumerViewController_1;
import com.supcon.mes.mbap.controller.InjectorViewController.MutiStageConsumerViewController_2;
import com.supcon.mes.mbap.inter.IDataConsumer;
import com.supcon.mes.mbap.inter.IDataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xushiyun
 * @Create-time 5/23/19
 * @Pageage com.supcon.mes.mbap.adapter
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public class MultiStageAdapter<T extends BaseMultiStageEntity> extends BaseRecyclerViewAdapter<T> {
    private List<T> contentList = new ArrayList<>();
    private Map<Integer, ConsumerViewController> mIntegerConsumerViewControllerMap = new HashMap<>();
    
    public MultiStageAdapter(Context context) {
        super(context);
    }
    
    public List<T> getContentList() {
        return contentList;
    }
    
    public void setContentList(List<T> contentList) {
        this.contentList = contentList;
        mIntegerConsumerViewControllerMap.put(0, new MutiStageConsumerViewController_1(new View(context)));
        mIntegerConsumerViewControllerMap.put(1, new MutiStageConsumerViewController_2(new View(context)));
    }
    
    public void changStatus(int pos) {
        for (int i = pos + 1; i < contentList.size(); i++) {
            if (contentList.get(i).getType() == 0) {
                notifyDataSetChanged();
                return;
            }
            contentList.get(i).setVisible(!contentList.get(i).getVisible());
        }
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemViewType(int position) {
        return contentList.get(position).getType();
    }
    
    @Override
    protected BaseRecyclerViewHolder getViewHolder(int viewType) {
        return new ViewHolder(context) {
            @Override
            protected int layoutId() {
                return mIntegerConsumerViewControllerMap.get(viewType).layout();
            }
        };
    }
    
    @Override
    public T getItem(int position) {
        return contentList.get(position);
    }
    
    
    @Override
    public int getItemCount() {
        return contentList.size();
    }
    
    public abstract class ViewHolder extends BaseRecyclerViewHolder<T> implements IDataProvider<T>, IDataConsumer<T> {
        private InjectorViewController mTInjectorViewController = new MultiStageInjectorViewController<>();
        private ConsumerViewController mTConsumerViewController;
        
        
        public ViewHolder(Context context) {
            super(context, parent);
        }
        
        @Override
        public T provide() {
            return getItem(getAdapterPosition());
        }
        
        @Override
        public void consume(T entity) {
            mTConsumerViewController = mIntegerConsumerViewControllerMap.get(getItemViewType()).createNew();
            mTConsumerViewController.attachView(itemView);
            if (getItemViewType() == 0) {
                mTConsumerViewController
                        .setOnItemChildViewClickListener(
                                new OnItemChildViewClickListener() {
                                    @Override
                                    public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                                        changStatus(ViewHolder.this.getAdapterPosition());
                                    }
                                });
            } else {
                mTConsumerViewController.setOnItemChildViewClickListener(null);
            }
            mTConsumerViewController.consume(entity);
        }
        
        @Override
        protected void update(T data) {
//            if (data.getVisible() != null && data.getVisible() == false) {
//                itemView.setVisibility(View.GONE);
//                return;
//            }
//            itemView.setVisibility(View.VISIBLE);
//            if (getItemViewType()==0)
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    changStatus(getAdapterPosition());
//                }
//            });
            
            mTInjectorViewController.inject(this, this);
        }
    }
    
}
