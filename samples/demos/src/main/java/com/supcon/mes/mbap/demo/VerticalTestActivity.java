package com.supcon.mes.mbap.demo;

import android.view.View;
import android.widget.Button;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.listener.OnContentCheckListener;
import com.supcon.mes.mbap.view.CustomVerticalEditText;
import com.supcon.mes.mbap.view.CustomVerticalSpinner;
import com.supcon.mes.mbap.view.CustomVerticalTextView;
import com.supcon.mes.mbap.view.XEditText;

import java.util.ArrayList;
import java.util.List;

public class VerticalTestActivity extends BaseActivity {

    List<String> spinnerStrs = new ArrayList<>();

    XEditText xEditText, xEditText2;
    Button submitBtn;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_vertical;
    }

    @Override
    protected void onInit() {
        super.onInit();

        spinnerStrs.add("1");
        spinnerStrs.add("2");


    }

    @Override
    protected void initView() {
        super.initView();

        xEditText = findViewById(R.id.xEditText);
        xEditText2 = findViewById(R.id.xEditText2);
        submitBtn = findViewById(R.id.submitBtn);
        CustomVerticalTextView verticalView = findViewById(R.id.verticalView);
        verticalView.setKeyHeight(50);
        verticalView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                LogUtil.i("verticalView click!");
            }
        });

        CustomVerticalEditText verticalEdit = findViewById(R.id.verticalEdit);
        verticalEdit.setInput("水电费公司的反馈上岛咖啡水电费广东省分公什么什么什么模式买什么什么什么什么模式买什么什么什么模式没事没事司律地方规定发给对方更多师代理费是的风格的风格的发个梵蒂冈的手动方式独领风骚的浪费乐山大佛历史地理酸辣粉乐山大佛律师代理费收到了");

    }

    @Override
    protected void initListener() {
        super.initListener();

        CustomVerticalSpinner  verticalSpinner = findViewById(R.id.verticalSpinner);
        verticalSpinner.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                LogUtil.i("verticalSpinner click!");
            }
        });


        xEditText.setOnContentCheckListener(new OnContentCheckListener() {
            @Override
            public boolean isCheckPass(String content) {
                return "王世展".equals(content);
            }

            @Override
            public String createCheckInfo() {
                return "内容不合理";
            }
        });


        submitBtn.setOnClickListener(v->xEditText.check());
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
