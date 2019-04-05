package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.view.loader.CircularLoaderView;
import com.supcon.mes.mbap.R;


/**
 * Created by wangshizhan on 2018/4/18.
 * Email:wangshizhan@supcon.com
 */

public class CustomDownloadView extends BaseRelativeLayout implements View.OnClickListener{

    RelativeLayout downloadLayout;
    TextView downloadName;
    TextView downloadInfo;
    TextView downloadDate;
    CustomCheckBox downloadChk;
    CircularLoaderView downloadLoader;

    private String mText;
    private int mLoaderHeight;
    private int mTextSize, mInfoTextSize, mTextColor, mInfoSuccessColor, mInfoFailedColor;

    private CustomCheckBox.OnCheckedListener mOnCheckedListener;
    private boolean isChecked = false;

    public CustomDownloadView(Context context) {
        super(context);
    }

    public CustomDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_download;
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomDownloadView);
            mText = array.getString(R.styleable.CustomDownloadView_text);
            mTextSize = array.getInt(R.styleable.CustomDownloadView_text_size, 0);
            mTextColor = array.getColor(R.styleable.CustomDownloadView_text_color, 0);

            mInfoTextSize = array.getInt(R.styleable.CustomDownloadView_info_text_size, 0);
            mInfoSuccessColor = array.getColor(R.styleable.CustomDownloadView_info_success_color, 0);
            mInfoFailedColor = array.getColor(R.styleable.CustomDownloadView_info_failed_color, 0);
            mLoaderHeight = array.getDimensionPixelSize(R.styleable.CustomDownloadView_loader_height, 0);

            array.recycle();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        downloadName = findViewById(R.id.downloadName);
        downloadInfo = findViewById(R.id.downloadInfo);
        downloadDate = findViewById(R.id.downloadDate);
        downloadChk = findViewById(R.id.downloadChk);
        downloadLoader = findViewById(R.id.downloadLoader);
        downloadLayout = findViewById(R.id.downloadLayout);

        downloadLoader.setDoneColor(ContextCompat.getColor(getContext(), R.color.customBlue4));

        if(mLoaderHeight == 0){
            mLoaderHeight = DisplayUtil.dip2px(30, getContext());
        }
        downloadLoader.setInitialHeight(mLoaderHeight);
//        downloadLoader.setSpinningBarColor(ContextCompat.getColor(getContext(), R.color.customOrange2));


        if(!TextUtils.isEmpty(mText)){
            setDownloadName(mText);
        }

        if(mTextSize!=0){
            setDownloadTextSize(mTextSize);
        }

        if(mInfoTextSize!=0){
            setInfoTextSize(mInfoTextSize);
        }

        if(mTextColor!=0){
            setDownloadTextColor(mTextColor);
        }

        if(mInfoSuccessColor == 0){
            mInfoSuccessColor = getResources().getColor(R.color.customBlue4);
        }

        if(mInfoFailedColor == 0){
            mInfoFailedColor = getResources().getColor(R.color.customRed);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();

        downloadChk.setOnCheckedListener(isChecked -> {
            CustomDownloadView.this.isChecked = isChecked;
            if(mOnCheckedListener!=null){
                mOnCheckedListener.onChecked(isChecked);
            }
        });

        downloadLayout.setOnClickListener(this);
//        downloadName.setOnClickListener(this);
//        downloadInfo.setOnClickListener(this);
//        downloadDate.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void setChecked(boolean isChecked){
        if(downloadChk!=null){
            downloadChk.setChecked(isChecked);
        }
        this.isChecked = isChecked;
    }

    public void setLoadStatus(boolean isFinished){

        setChecked(!isFinished);

        if(mOnCheckedListener!=null){
            mOnCheckedListener.onChecked(!isFinished);
        }

    }

    public void startLoader(){

//        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
        downloadChk.setVisibility(GONE);
        downloadLoader.setVisibility(VISIBLE);
        downloadLoader.revertAnimation();
        downloadLoader.startAnimation();

    }

    public void stopLoader(boolean isSuccess){
//        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//            return;
//        }
        if(isSuccess) {
            downloadLoader.doneLoadingAnimation(
                    mInfoSuccessColor,
                    BitmapFactory.decodeResource(getResources(), R.drawable.ic_download_success));
        }
        else{
            downloadLoader.doneLoadingAnimation(
                    mInfoFailedColor,
                    BitmapFactory.decodeResource(getResources(), R.drawable.ic_download_failed));
        }
//        downloadLoader.stopAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                downloadLoader.setVisibility(GONE);
                downloadChk.setVisibility(VISIBLE);
            }
        }, 1000);


//        downloadLoader.revertAnimation();
//        downloadLoader.setSpinningBarColor(Color.MAGENTA);
    }

    public void setOnCheckedListener(CustomCheckBox.OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }

    public void setDownloadName(String name) {
        downloadName.setText(name);
    }

    public void setDownloadTextSize(int textSize) {
        mTextSize = textSize;
        downloadName.setTextSize(textSize);
    }

    public void setInfoTextSize(int textSize){
        mInfoTextSize = textSize;
        downloadInfo.setTextSize(textSize);
    }

    public void setDownloadTextColor(int textColor) {
        mTextColor = textColor;
        downloadName.setTextColor(textColor);
    }

    public void setInfoSuccessColor(int infoSuccessColor){
        mInfoSuccessColor = infoSuccessColor;
    }

    public void setInfoFailedColor(int infoFailedColor){
        mInfoFailedColor = infoFailedColor;
    }

    public void setDownloadInfo(String info) {
        downloadInfo.setVisibility(VISIBLE);
        downloadInfo.setText(info);
        downloadInfo.setTextColor(getResources().getColor(R.color.textColorlightblack));
    }

    public void setDownloadInfo(String info, boolean isSuccess) {
        downloadInfo.setVisibility(VISIBLE);
        downloadInfo.setText(info);

        if(isSuccess){
            downloadInfo.setTextColor(mInfoSuccessColor);
        }
        else{
            downloadInfo.setTextColor(mInfoFailedColor);
        }
    }

    public void setDownloadDate(String date) {
        downloadDate.setText(date);
    }

    public void setLoaderHeight(int loaderHeight) {
        mLoaderHeight = loaderHeight;
        downloadLoader.setInitialHeight(mLoaderHeight);
    }

    @Override
    public void onClick(View v) {
        boolean check = !isChecked;
        setChecked(check);
        if(mOnCheckedListener!=null){
            mOnCheckedListener.onChecked(check);
        }
    }
}
