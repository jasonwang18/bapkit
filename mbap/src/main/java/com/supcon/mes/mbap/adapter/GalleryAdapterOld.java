package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.GalleryBean;

import static com.supcon.mes.mbap.view.CustomGalleryView.ACTION_DELETE;
import static com.supcon.mes.mbap.view.CustomGalleryView.ACTION_VIEW;

/**
 * Created by wangshizhan on 2017/8/16.
 * Email:wangshizhan@supcon.com
 */

public class GalleryAdapterOld extends BaseListDataRecyclerViewAdapter<GalleryBean> {

    private boolean isEditable = false;

    public GalleryAdapterOld(Context context, boolean editable) {
        super(context);
        isEditable = editable;
    }

    public GalleryAdapterOld(Context context) {
        this(context, false);

    }

    @Override
    protected BaseRecyclerViewHolder<GalleryBean> getViewHolder(int viewType) {
        return new WorkViewHolder(context);
    }



    class WorkViewHolder extends BaseRecyclerViewHolder<GalleryBean> implements View.OnClickListener{

        ImageView itemGalleryIv;
        ImageButton itemGalleryDelete;

        public WorkViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initView() {
            super.initView();
            itemGalleryIv = itemView.findViewById(R.id.itemGalleryIv);
            itemGalleryDelete = itemView.findViewById(R.id.itemGalleryDelete);
            if(isEditable){
                itemGalleryDelete.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void initListener() {
            super.initListener();

            itemGalleryIv.setOnClickListener(this);
            itemGalleryDelete.setOnClickListener(this);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_gallery;
        }

        @Override
        protected void update(GalleryBean data) {

            if(!TextUtils.isEmpty(data.localPath)){
                Glide.with(context).load(data.localPath).into(itemGalleryIv);
            }
            else if(!TextUtils.isEmpty(data.url)) {
                Glide.with(context).load(data.url).into(itemGalleryIv);
            }


            else if(data.resId != 0){
                Glide.with(context).load(data.resId).into(itemGalleryIv);
            }


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            GalleryBean galleryBean = getItem(position);

            if(v instanceof ImageButton){
                onItemChildViewClick(v, ACTION_DELETE, position);
            }
            else{

                onItemChildViewClick(v, ACTION_VIEW, position);
            }
        }
    }
}
