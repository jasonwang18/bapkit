package com.supcon.mes.mbap.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.view.picker.SinglePicker;
import com.supcon.mes.mbap.listener.ICustomView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by wangshizhan on 2018/10/20
 * Email:wangshizhan@supcom.com
 */

public class CustomViewController extends BaseDataController {

    public CustomViewController(Context context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public CustomViewController addEditView(EditText editText, int debounce, OnSuccessListener<String> onSuccessListener){

        RxTextView.textChanges(editText)
                .skipInitialValue()
                .debounce(debounce, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {

                        if(onSuccessListener!=null)
                            onSuccessListener.onSuccess(charSequence.toString());
                    }
                });

        return this;
    }

    @SuppressLint("CheckResult")
    public CustomViewController addEditView(EditText editText, OnSuccessListener<String> onSuccessListener){

        if(editText == null) {
            LogUtil.e("editText == null");
            return this;
        }

        return addEditView(editText, 500, onSuccessListener);
    }

    @SuppressLint("CheckResult")
    public CustomViewController addDateView(ICustomView customView, OnShowListener<Long> onShowListener, OnSuccessListener<String> onSuccessListener){

        customView.setOnChildViewClickListener((childView, action, obj) -> {
            if(action!=-1) {
                PickerHelper.getDatePickController(context)
                        .listener((year, month, day, hour, minute, second) -> {
                            LogUtil.i(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
                            String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
//                            long select = DateUtil.dateFormat(dateStr, "yyyy-MM-dd HH:mm:ss");
                            if(onSuccessListener!=null)
                                onSuccessListener.onSuccess(dateStr);

                        })
                        .show(onShowListener.getContent());
            }
            else{
                if(onSuccessListener!=null)
                    onSuccessListener.onSuccess("");
            }
        });


        return this;
    }

    public CustomViewController addSpinner(ICustomView customView, List<String> list, OnShowListener<String> onShowListener, OnSuccessListener<String> onSuccessListener){

        customView.setOnChildViewClickListener((childView, action, obj) -> {
            if(action!=-1) {
                PickerHelper.getSinglePickController(context)
                        .list(list)
                        .listener((SinglePicker.OnItemPickListener<String>) (index, item) -> {
                            if (onSuccessListener != null)
                                onSuccessListener.onSuccess(item);

                        })
                        .show(onShowListener.getContent());
            }
            else{
                if(onSuccessListener!=null)
                    onSuccessListener.onSuccess("");
            }
        });
        return this;
    }

}
