package adapter;

import android.content.Context;
import android.widget.TextView;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.demo.R;

import java.util.List;

import model.TestEntity;

/**
 * Created by wangshizhan on 2018/8/4
 * Email:wangshizhan@supcom.com
 */
public class RecyclerTestAdapter extends BaseListDataRecyclerViewAdapter<TestEntity> {

    public RecyclerTestAdapter(Context context) {
        super(context);
    }

    public RecyclerTestAdapter(Context context, List<TestEntity> list) {
        super(context, list);
    }

    @Override
    protected BaseRecyclerViewHolder<TestEntity> getViewHolder(int viewType) {
        return new TestViewHolder(context);
    }


    class TestViewHolder extends BaseRecyclerViewHolder<TestEntity>{

        private TextView name;

        public TestViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initView() {
            super.initView();
            name = itemView.findViewById(R.id.name);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_recycler_test;
        }

        @Override
        protected void update(TestEntity data) {
            name.setText(data.name);
        }
    }
}
