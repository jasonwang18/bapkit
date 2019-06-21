package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.BaseContactEntity;
import com.supcon.mes.mbap.view.CustomContactView;

import java.util.List;

public class CustomContactViewAdapter extends RecyclerView.Adapter {
    private List<BaseContactEntity> list;
    private CustomContactView.ContactViewController contactViewController;
    private OnItemChildViewClickListener onItemChildViewClickListener;


    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    public CustomContactViewAdapter(Context context, List list) {
        super();
        this.list = list;
    }

    public void registerViewController(CustomContactView.ContactViewController contactViewController) {
        this.contactViewController = contactViewController;
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
            View right = contactViewController.view(itemView).rightView();
            if(right!= null)right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemChildViewClickListener != null && getAdapterPosition() >= 0)
                        onItemChildViewClickListener.onItemChildViewClick(v, VH.this.getAdapterPosition(),
                                1, list.get(VH.this.getAdapterPosition()));
                }
            });
            itemView.setOnClickListener(v -> {
                if (onItemChildViewClickListener != null && getAdapterPosition() >= 0)
                    onItemChildViewClickListener.onItemChildViewClick(v, VH.this.getAdapterPosition(),
                            0, list.get(VH.this.getAdapterPosition()));
            });
        }
    }

    public void setList(List list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(contactViewController.layout(), parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        contactViewController.view(holder.itemView).visit(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}