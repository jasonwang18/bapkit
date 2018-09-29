package com.supcon.mes.mbap.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.SheetEntity;
import com.supcon.mes.mbap.utils.GsonUtil;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;
import com.supcon.mes.mbap.utils.StateListHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushiyun on 2018/5/16.
 * Email:ciruy.victory@gmail.com
 */

public class CustomTableDialog {

    private BottomSheetDialog mDialog;
    private CustomDialog.OnHideListener mFinishListener;
    private Context mContext;
    private OnItemChildViewClickListener mOnItemChildViewClickListener;
    private View rootView;

    public CustomTableDialog(Context context){
        this(context, 0);
    }


    private CustomTableDialog(Context context, int themeResId) {
        mContext = context;
        if(themeResId == 0)
            mDialog = new BottomSheetDialog(context);
        else
            mDialog = new BottomSheetDialog(context, themeResId);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
    }

    public CustomTableDialog setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        mOnItemChildViewClickListener = onItemChildViewClickListener;
        return this;
    }

    public CustomTableDialog onItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        mOnItemChildViewClickListener = onItemChildViewClickListener;
        return this;
    }

    public CustomTableDialog table( View root) {
        this.rootView = root;
        mDialog.setContentView(root);
        return this;
    }

    public CustomTableDialog table(@LayoutRes int res) {
        this.rootView = LayoutInflater.from(mContext).inflate(res, null);
        mDialog.setContentView(rootView);
        return this;
    }

    public View rootView() {
        return rootView;
    }

    public void hide(){

        if(mDialog!=null){
            mDialog.hide();
        }
    }

    public void dismiss(){

        if(mDialog!=null){
            mDialog.dismiss();
        }

    }

    public void show(){

        if(mDialog!= null && !mDialog.isShowing()){
            mDialog.show();
        }
    }

    public Dialog dialog(){

        return mDialog;

    }

}
