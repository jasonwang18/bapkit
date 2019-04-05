package com.supcon.mes.mbap.demo;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.beans.SheetEntity;
import com.supcon.mes.mbap.view.CustomDialog;
import com.supcon.mes.mbap.view.CustomSheetDialog;
import com.supcon.mes.mbap.view.CustomTableDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2018/5/16.
 * Email:wangshizhan@supcon.com
 */

public class DislogTextActivity extends BaseActivity {


    @Override
    protected int getLayoutID() {
        return R.layout.activity_dlalog_test;
    }


    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.iaBtnDialog).setOnClickListener(v -> showIADialog());
        findViewById(R.id.twoBtnDialog).setOnClickListener(v -> showTwoBtnDialog());
        findViewById(R.id.threeBtnDialog).setOnClickListener(v -> showThreeBtnDialog());
        findViewById(R.id.sheetDialog).setOnClickListener(v -> showSheetListDialog());
        findViewById(R.id.multiSheetDialog).setOnClickListener(v -> showMultiSheetListDialog());
    }


    public void showBottomInDialog(View view) {
        new CustomDialog(this)
                .layout(R.layout.ly_custom_two_btn_dialog, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .gravity(Gravity.BOTTOM)
                .animation(R.style.bottomInStyle)
                .show();
    }


    private void showIADialog() {
        new CustomDialog(this)
                .imageViewActivityAlertDialog("两个按钮的对话框")
                .show();
    }

    private void showTwoBtnDialog() {
        new CustomDialog(this)
                .twoButtonAlertDialog("两个按钮的对话框")
                .bindView(R.id.grayBtn, "灰色按钮")
                .bindView(R.id.redBtn, "红色按钮")
                .show();
    }

    private void showThreeBtnDialog() {
        new CustomDialog(this)
                .threeButtonAlertDialog("三个按钮的对话框")
                .bindView(R.id.grayBtn, "灰色按钮")
                .bindView(R.id.redBtn, "红色按钮")
                .gone(R.id.redBtn)
                .visible(R.id.blueBtn)
                .bindView(R.id.blueBtn, "蓝色按钮")
                .show();
    }

    private void showSheetListDialog() {

        List<SheetEntity> sheetEntities = new ArrayList<>();
        SheetEntity sheetEntity = new SheetEntity();
        sheetEntity.name = "1";
        for(int i = 0; i < 20; i++)
        {
            sheetEntities.add(sheetEntity);
        }

        new CustomSheetDialog(this)
                .sheet("底部sheet", sheetEntities)
                .show();
//        new CustomTableDialog(this).table(LayoutInflater.from(context).inflate(R.layout.ly_titlebar, null)).show();
    }

    private void showMultiSheetListDialog() {
        List<SheetEntity> sheetEntities = new ArrayList<>();
        SheetEntity sheetEntity = new SheetEntity();
        sheetEntity.name = "154654";
        sheetEntities.add(sheetEntity);
        sheetEntity.name = "多选条件1";
        sheetEntities.add(sheetEntity);
        sheetEntity.name = "多选条件12";
        sheetEntities.add(sheetEntity);
        sheetEntity.name = "多选条件13";
        sheetEntities.add(sheetEntity);


        new CustomSheetDialog(this)
                .multiSheet("多选对话框", sheetEntities)
                .setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
                    @Override
                    public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                        LogUtil.d("obj:"+obj.toString());
                    }
                })
                .show();
    }

}
