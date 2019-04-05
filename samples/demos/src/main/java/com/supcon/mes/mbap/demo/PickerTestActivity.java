package com.supcon.mes.mbap.demo;

import android.view.View;
import android.widget.Button;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.view.picker.SinglePicker;
import com.supcon.mes.mbap.listener.OnContentCheckListener;
import com.supcon.mes.mbap.utils.controllers.SinglePickController;
import com.supcon.mes.mbap.view.CustomSpinner;
import com.supcon.mes.mbap.view.CustomVerticalEditText;
import com.supcon.mes.mbap.view.CustomVerticalSpinner;
import com.supcon.mes.mbap.view.CustomVerticalTextView;
import com.supcon.mes.mbap.view.XEditText;

import java.util.ArrayList;
import java.util.List;

public class PickerTestActivity extends BaseActivity {

    List<String> spinnerStrs = new ArrayList<>();

    private SinglePickController<String> mPickController;
    CustomSpinner spinner;
    CustomVerticalSpinner verticalSpinner;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_picker;
    }

    @Override
    protected void onInit() {
        super.onInit();

        for(int i =0 ; i< 5; i++){
            spinnerStrs.add(String.valueOf(i));
        }

    }

    @Override
    protected void initView() {
        super.initView();

        mPickController = new SinglePickController<String>(this).list(spinnerStrs).listener(new SinglePicker.OnItemPickListener() {
            @Override
            public void onItemPicked(int index, Object item) {
                LogUtil.d("item:"+item);
                verticalSpinner.setSpinner(String.valueOf(item));
            }
        });

        mPickController.textSize(18);
        mPickController.setCycleDisable(false);
        mPickController.setDividerVisible(false);


        spinner = findViewById(R.id.spinner);
        spinner.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                mPickController.show(spinner.getSpinnerValue());
            }
        });


        verticalSpinner = findViewById(R.id.verticalSpinner);
        verticalSpinner.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                mPickController.show(verticalSpinner.getSpinnerValue());
            }
        });
    }


}
