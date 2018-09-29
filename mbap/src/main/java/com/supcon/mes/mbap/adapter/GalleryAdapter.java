package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.request.RequestOptions;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.GalleryBean;

import static com.supcon.mes.mbap.view.CustomGalleryView.ACTION_DELETE;
import static com.supcon.mes.mbap.view.CustomGalleryView.ACTION_VIEW;

/**
 * Created by wangshizhan on 2017/8/16.
 * Email:wangshizhan@supcon.com
 */

public class GalleryAdapter extends BaseListDataRecyclerViewAdapter<GalleryBean> {

    private boolean isEditable = false;
    private RequestOptions requestOptions;

    public GalleryAdapter(Context context, boolean editable) {
        super(context);
        isEditable = editable;
        requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_default_pic)
                            .override(DisplayUtil.dip2px(100, context), DisplayUtil.dip2px(100, context))
                            .centerInside();
    }

    public GalleryAdapter(Context context) {
        this(context, false);
    }

    @Override
    protected BaseRecyclerViewHolder<GalleryBean> getViewHolder(int viewType) {
        return new WorkViewHolder(context);
    }



    class WorkViewHolder extends BaseRecyclerViewHolder<GalleryBean> implements View.OnClickListener{

        ImageView itemGalleryIv;

        public WorkViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initView() {
            super.initView();
            itemGalleryIv = itemView.findViewById(R.id.itemGalleryIv);

        }

        @Override
        protected void initListener() {
            super.initListener();

            itemGalleryIv.setOnClickListener(this);
            itemGalleryIv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemChildViewClick(v, ACTION_DELETE, getAdapterPosition());
                    return true;
                }
            });
        }


        @Override
        protected int layoutId() {
            return R.layout.item_gallery;
        }

        @Override
        protected void update(GalleryBean data) {

            if(!TextUtils.isEmpty(data.localPath)){
                Glide.with(context).load(data.localPath).apply(requestOptions).into(itemGalleryIv);
            }
            else if(!TextUtils.isEmpty(data.url)) {
                Glide.with(context).load(data.url).apply(requestOptions).into(itemGalleryIv);
            }


            else if(data.resId != 0){
                Glide.with(context).load(data.resId).apply(requestOptions).into(itemGalleryIv);
            }


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            GalleryBean galleryBean = getItem(position);

//            if(v instanceof ImageButton){
//                onItemChildViewClick(v, ACTION_DELETE, position);
//            }
//            else{

                onItemChildViewClick(v, ACTION_VIEW, position);
//            }
        }
    }
}
