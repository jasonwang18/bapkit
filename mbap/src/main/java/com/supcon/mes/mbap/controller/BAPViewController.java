package com.supcon.mes.mbap.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.view.base.controller.BaseController;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.view.custom.ICustomView;
import com.supcon.common.view.view.custom.OnContentCallback;
import com.supcon.common.view.view.custom.OnResultListener;
import com.supcon.common.view.view.picker.SinglePicker;
import com.supcon.mes.mbap.utils.PickerHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by wangshizhan on 2018/10/20
 * Email:wangshizhan@supcom.com
 */

public class BAPViewController extends BaseController {

    private Context context;

    public BAPViewController(Context context) {
        this.context = context;
    }

    @SuppressLint("CheckResult")
    public BAPViewController addEditView(EditText editText, int debounce, OnResultListener<String> onResultListener){

        RxTextView.textChanges(editText)
                .skipInitialValue()
                .debounce(debounce, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    if(onResultListener!=null)
                        onResultListener.onResult(charSequence.toString());
                });

        return this;
    }

    @SuppressLint("CheckResult")
    public BAPViewController addEditView(EditText editText, OnResultListener<String> onSuccessListener){

        if(editText == null) {
            LogUtil.e("editText == null");
            return this;
        }

        return addEditView(editText, 500, onSuccessListener);
    }

    @SuppressLint("CheckResult")
    public BAPViewController addDateView(ICustomView customView, OnContentCallback<Long> onContentCallback, OnResultListener<String> onResultListener){

        customView.setOnChildViewClickListener((childView, action, obj) -> {
            if(action!=-1) {
                PickerHelper.getDatePickController(context)
                        .listener((year, month, day, hour, minute, second) -> {
                            LogUtil.i(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
                            String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
//                            long select = DateUtil.dateFormat(dateStr, "yyyy-MM-dd HH:mm:ss");
                            if(onResultListener!=null)
                                onResultListener.onResult(dateStr);

                        })
                        .show(onContentCallback.getContent());
            }
            else{
                if(onResultListener!=null)
                    onResultListener.onResult("");
            }
        });


        return this;
    }

    public BAPViewController addSpinner(ICustomView customView, List<String> list, OnContentCallback<String> onContentCallback,
                                        OnResultListener<String> onResultListener){

        customView.setOnChildViewClickListener((childView, action, obj) -> {
            if(action!=-1) {
                PickerHelper.getSinglePickController(context)
                        .list(list)
                        .listener((SinglePicker.OnItemPickListener<String>) (index, item) -> {
                            if (onResultListener != null)
                                onResultListener.onResult(item);

                        })
                        .show(onContentCallback.getContent());
            }
            else{
                if(onResultListener!=null)
                    onResultListener.onResult("");
            }
        });
        return this;
    }

}
