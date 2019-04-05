package com.supcon.mes.mbap.demo;

import android.view.Gravity;
import android.view.View;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.view.CustomDialog;
import com.supcon.mes.mbap.view.CustomExpandableTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2017/12/28.
 * Email:wangshizhan@supcon.com
 */

public class ExpandTextViewTestActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_expand_textview;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        CustomExpandableTextView expandView = findViewById(R.id.expandView);
//        expandView.setText("345");
//        List<String> list = new ArrayList<>();
//        list.add("123");
//        list.add("234");
//        list.add("4364");
//        CustomDialog.instance()
//                .sheetDialog(this, "测试测试测试", Gravity.CENTER)
//                .list(list, new OnItemChildViewClickListener() {
//                    @Override
//                    public void onItemChildViewClick(View childView, int position, int action, Object obj) {
//                        LogUtil.d("position:"+position+"obj:"+obj);
//                    }
//                })
//                .show();
    }
}
