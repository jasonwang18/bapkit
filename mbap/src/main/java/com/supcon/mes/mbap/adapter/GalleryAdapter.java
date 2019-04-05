package com.supcon.mes.mbap.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.GalleryBean;

import static com.supcon.mes.mbap.view.CustomGalleryView.ACTION_DELETE;
import static com.supcon.mes.mbap.view.CustomGalleryView.ACTION_VIDEO_PLAY;
import static com.supcon.mes.mbap.view.CustomGalleryView.ACTION_VIEW;

/**
 * Created by wangshizhan on 2017/8/16.
 * Email:wangshizhan@supcon.com
 */

public class GalleryAdapter extends BaseListDataRecyclerViewAdapter<GalleryBean> {

    private boolean isEditable = false;
    private RequestOptions requestOptions;

    public static final String TYPE_LOCAL = "local";
    public static final String TYPE_NETWORK = "network";

    public static final int FILE_TYPE_PICTURE = 0;
    public static final int FILE_TYPE_VIDEO = FILE_TYPE_PICTURE+1;


    public GalleryAdapter(Context context, boolean editable) {
        super(context);
        isEditable = editable;
        requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_pic_default)
                            .override(DisplayUtil.dip2px(110, context), DisplayUtil.dip2px(110, context))
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
        ImageView itemGalleryPlayIv;

        public WorkViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initView() {
            super.initView();
            itemGalleryIv = itemView.findViewById(R.id.itemGalleryIv);
            itemGalleryPlayIv = itemView.findViewById(R.id.itemGalleryPlayIv);
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

            itemGalleryPlayIv.setOnClickListener(this);
        }


        @Override
        protected int layoutId() {
            return R.layout.item_gallery;
        }

        @Override
        protected void update(GalleryBean data) {

            if(getItemCount() == 1 && !isEditable && data.fileType == FILE_TYPE_PICTURE){

//                if(data.fileType == FILE_TYPE_PICTURE) {
                    requestOptions = requestOptions.override(DisplayUtil.dip2px(300, context), DisplayUtil.dip2px(200, context));
                    ViewGroup.LayoutParams lp = itemGalleryIv.getLayoutParams();
                    lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    itemGalleryIv.setLayoutParams(lp);
//                }
//                else if(data.fileType == FILE_TYPE_VIDEO){
//                    requestOptions = requestOptions.override(DisplayUtil.dip2px(110, context), DisplayUtil.dip2px(110, context));
//                    ViewGroup.LayoutParams lp = itemGalleryIv.getLayoutParams();
//                    lp.width = DisplayUtil.dip2px(110, context);
//                    lp.height = DisplayUtil.dip2px(110, context);
//                    itemGalleryIv.setLayoutParams(lp);
//                }
            }
            else{
                requestOptions = requestOptions.override(DisplayUtil.dip2px(110, context), DisplayUtil.dip2px(110, context));
                ViewGroup.LayoutParams lp = itemGalleryIv.getLayoutParams();
                lp.width = DisplayUtil.dip2px(110, context);
                lp.height = DisplayUtil.dip2px(110, context);
                itemGalleryIv.setLayoutParams(lp);
            }

            if(data.fileType == FILE_TYPE_VIDEO){
                Glide.with(context).load(data.localPath).apply(requestOptions).into(itemGalleryIv);
                itemGalleryPlayIv.setVisibility(View.VISIBLE);
                itemGalleryPlayIv.bringToFront();
            }
            else {
                itemGalleryPlayIv.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(data.localPath)) {
                    Glide.with(context).load(data.localPath).apply(requestOptions).into(itemGalleryIv);
                } else if (!TextUtils.isEmpty(data.url)) {
                    Glide.with(context).load(data.url).apply(requestOptions).into(itemGalleryIv);
                } else if (data.resId != 0) {
                    Glide.with(context).load(data.resId).apply(requestOptions).into(itemGalleryIv);
                }
            }

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int id = v.getId();

            if(id == R.id.itemGalleryPlayIv){
                onItemChildViewClick(v, ACTION_VIDEO_PLAY, position);
            }
            else {
                onItemChildViewClick(v, ACTION_VIEW, position);
            }
        }


        public void setPlayViewCenter(){
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) itemGalleryPlayIv.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            lp.removeRule(RelativeLayout.CENTER_VERTICAL);
            lp.leftMargin = DisplayUtil.dip2px(0, context);
            itemGalleryPlayIv.setLayoutParams(lp);
        }


        public void setPlayViewLeft(){
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) itemGalleryPlayIv.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            lp.removeRule(RelativeLayout.CENTER_IN_PARENT);

            lp.leftMargin = itemGalleryIv.getDrawable().getBounds().width()/2 + 32;
            itemGalleryPlayIv.setLayoutParams(lp);
        }
    }
}
