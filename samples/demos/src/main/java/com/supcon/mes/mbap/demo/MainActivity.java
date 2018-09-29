package com.supcon.mes.mbap.demo;

import android.content.Intent;
import android.view.View;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.view.CustomArrowView;

public class MainActivity extends BaseActivity implements View.OnClickListener{

//    private CustomArrowView galleryTest, adTest, expandTest, verticalTest,
//            transitionTest, roundIconTest, filterTest, searchTest, downloadTest, horizontalSearchTest, dialogTest,
//    contentDialog;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

//        galleryTest = findViewById(R.id.galleryTest);
//        adTest = findViewById(R.id.adTest);
//        expandTest = findViewById(R.id.expandTest);
//        verticalTest = findViewById(R.id.verticalTest);
//        transitionTest = findViewById(R.id.transitionTest);
//        roundIconTest = findViewById(R.id.roundIconTest);
//        filterTest = findViewById(R.id.filterTest);
//        searchTest = findViewById(R.id.searchTest);
//        downloadTest = findViewById(R.id.downloadTest);
//        horizontalSearchTest = findViewById(R.id.horizontalSearchTest);
//
//        contentDialog = findViewById(R.id.contentDialog);

    }

    @Override
    protected void onInit() {
        super.onInit();

    }

    @Override
    protected void initListener() {
        super.initListener();

//        galleryTest.setOnClickListener(this);
//        adTest.setOnClickListener(this);
//        expandTest.setOnClickListener(this);
//        verticalTest.setOnClickListener(this);
//        transitionTest.setOnClickListener(this);
//        roundIconTest.setOnClickListener(this);
//        filterTest.setOnClickListener(this);
//        searchTest.setOnClickListener(this);
//        downloadTest.setOnClickListener(this);
//        horizontalSearchTest.setOnClickListener(this);
//        findViewById(R.id.dialogTest).setOnClickListener(this);
//
//        contentDialog.setOnClickListener(this);
//
//
//        findViewById(R.id.refreshTest).setOnClickListener(this);
//
//        findViewById(R.id.kotlinTest).setOnClickListener(this);
//        findViewById(R.id.tabTest).setOnClickListener(this);
//        findViewById(R.id.numViewTest).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.listWidgetTest:
                startActivity(new Intent(this, ListWidgetTestActivity.class));
                break;

            case R.id.recyclerViewTest:

                startActivity(new Intent(this, RecyclerViewTestActivity.class));
                break;
            case R.id.pickerViewTest:

                startActivity(new Intent(this, PickerTestActivity.class));
                break;

            case R.id.numViewTest:

                startActivity(new Intent(this, NumViewTestActivity.class));
                break;
            case R.id.tabTest:

                startActivity(new Intent(this, TabTestActivity.class));
                break;

            case R.id.kotlinTest:

//                startActivity(new Intent(this, KEditTextTest.class));
                break;

            case R.id.refreshTest:

                startActivity(new Intent(this, RefreshTestTestActivity.class));
                break;
            case R.id.galleryTest:

                startActivity(new Intent(this, GalleryTestActivity.class));
                break;

            case R.id.adTest:
                startActivity(new Intent(this, AdTestActivity.class));
                break;

            case R.id.expandTest:
                startActivity(new Intent(this, ExpandTextViewTestActivity.class));
                break;

            case R.id.verticalTest:
                startActivity(new Intent(this, VerticalTestActivity.class));
                break;

            case R.id.transitionTest:
                startActivity(new Intent(this, TransitionTestActivity.class));
                break;

            case R.id.roundIconTest:
                startActivity(new Intent(this, CircleTextImageViewTestActivity.class));
                break;
            case R.id.filterTest:
                startActivity(new Intent(this, FilterTestActivity.class));
                break;
            case R.id.searchTest:
                startActivity(new Intent(this, SearchTestActivity.class));
                break;

            case R.id.downloadTest:
                startActivity(new Intent(this, DownloadTestActivity.class));
                break;

            case R.id.horizontalSearchTest:
                startActivity(new Intent(this, CustomHorizontalSearchActivity.class));
                break;

            case R.id.dialogTest:
                startActivity(new Intent(this, DislogTextActivity.class));
                break;

            case R.id.contentDialog:
                startActivity(new Intent(this, CustomContentDialogActivity.class));
                break;
        }
    }
}
