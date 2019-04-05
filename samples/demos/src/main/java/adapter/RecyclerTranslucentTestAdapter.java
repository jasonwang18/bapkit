package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.constant.ListType;
import com.supcon.mes.mbap.demo.R;

import java.util.List;

import model.TestEntity;

/**
 * Created by wangshizhan on 2018/8/4
 * Email:wangshizhan@supcom.com
 */
public class RecyclerTranslucentTestAdapter extends BaseListDataRecyclerViewAdapter<TestEntity> {

    public RecyclerTranslucentTestAdapter(Context context) {
        super(context);
    }

    public RecyclerTranslucentTestAdapter(Context context, List<TestEntity> list) {
        super(context, list);
    }

    @Override
    protected BaseRecyclerViewHolder<TestEntity> getViewHolder(int viewType) {

        if(viewType == ListType.HEADER.value()){
            return new PicViewHolder(context);
        }
        else if(viewType == ListType.TITLE.value()){
            return new TitleViewHolder(context);
        }

        return new TestViewHolder(context);
    }


    @Override
    public int getItemViewType(int position, TestEntity testEntity) {

        return testEntity.viewType;
    }

    class PicViewHolder extends BaseRecyclerViewHolder<TestEntity>{
        private ImageView pic;

        public PicViewHolder(Context context) {
            super(context, parent);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_recycler_pic;
        }

        @Override
        protected void initView() {
            super.initView();
            pic = itemView.findViewById(R.id.itemRecyclerPic);
        }

        @Override
        protected void update(TestEntity data) {
            pic.setImageResource(data.imageResoureId);
        }
    }

    class TitleViewHolder extends BaseRecyclerViewHolder<TestEntity>{
        private TextView title;

        public TitleViewHolder(Context context) {
            super(context, parent);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_recycler_title;
        }

        @Override
        protected void initView() {
            super.initView();
            title = itemView.findViewById(R.id.itemRecyclerTitle);

        }

        @Override
        protected void update(TestEntity data) {
            title.setText(data.title);
        }
    }


    class TestViewHolder extends BaseRecyclerViewHolder<TestEntity>{

        private TextView name;

        public TestViewHolder(Context context) {
            super(context, parent);
        }

        @Override
        protected void initView() {
            super.initView();
            name = itemView.findViewById(R.id.itemRecyclerContent);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_recycler_content;
        }

        @Override
        protected void update(TestEntity data) {
            name.setText(data.name);
        }
    }
}
