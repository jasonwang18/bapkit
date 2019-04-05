package com.supcon.mes.mbap.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.OnTextListener;

import java.util.List;

/**
 * Created by wangshizhan on 2017/8/29.
 * Email:wangshizhan@supcon.com
 */

public class CustomDialog {

    public CustomDialog(Context context){
        this(context, 0);
    }


    public CustomDialog(Context context, int themeResId) {
        this.mContext = context;
        if(themeResId == 0)
            mDialog = new Dialog(context, R.style.CustomDialog);
        else
            mDialog = new Dialog(context, themeResId);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
    }

    private Dialog mDialog;
    private OnHideListener mFinishListener;
    private Context mContext;

/*    public CustomDialog bottomInDialog(Context context, int layoutId){

        mDialog = new Dialog(context, R.style.bottomSheet);

        View root = LayoutInflater.from(context).inflate(layoutId, null);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(root);
        mDialog.setCanceledOnTouchOutside(false);

        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setBackgroundDrawableResource(R.drawable.sh_white);
        dialogWindow.setWindowAnimations(R.style.dialogStyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标

        lp.y = -20; // 新位置Y坐标
        lp.width = DisplayUtil.getScreenWidth(context); // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);

        return this;

    }*/



    public Dialog getDialog() {
        return mDialog;
    }


    public CustomDialog grayBtn(View.OnClickListener listener, boolean dismissDialog){

        return bindClickListener(R.id.grayBtn, listener, dismissDialog);
    }

    public CustomDialog redBtn(View.OnClickListener listener, boolean dismissDialog){

        return bindClickListener(R.id.redBtn, listener, dismissDialog);
    }

    public CustomDialog blueBtn(View.OnClickListener listener, boolean dismissDialog){

        return bindClickListener(R.id.blueBtn, listener, dismissDialog);
    }

    public CustomDialog bindClickListener(int id, View.OnClickListener listener, boolean dismissDialog) throws NullPointerException{

        View view = mDialog.findViewById(id);

        if(view == null){
            throw new NullPointerException("CustomDialog do not contain id:"+id);
        }

        mDialog.findViewById(id).setOnClickListener(v -> {
            if(dismissDialog){
                mDialog.dismiss();
                if(mFinishListener != null){
                    mFinishListener.onAfterHide();
                }
            }

            if(listener!=null)
                listener.onClick(v);

        });
        return this;
    }

    public CustomDialog bindCustomEditText(int id, String value) throws NullPointerException{

        if(mDialog.findViewById(id) == null){
            throw new NullPointerException("CustomDialog do not contain id:"+id);
        }

        if(TextUtils.isEmpty(value)){
            return this;
        }

        ((CustomEditText)mDialog.findViewById(id)).setInput(value);
        return this;
    }

    public CustomDialog visible(int id){
        if(id  == 0){
            throw new IllegalArgumentException("The id cannot be 0 !");
        }

        View view = mDialog.findViewById(id);

        if(view.getVisibility() == View.GONE){
            view.setVisibility(View.VISIBLE);
        }

        return this;
    }

    public CustomDialog gone(int id){

        if(id  == 0){
            throw new IllegalArgumentException("The id cannot be 0 !");
        }

        View view = mDialog.findViewById(id);

        if(view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.GONE);
        }

        return this;
    }

    public CustomDialog bindView(int id, String value) throws NullPointerException{

        if(mDialog.findViewById(id) == null){
            throw new NullPointerException("CustomDialog do not contain id:"+id);
        }

        if(TextUtils.isEmpty(value)){
            return this;
        }

        ((TextView)mDialog.findViewById(id)).setText(value);
        return this;
    }

    public CustomDialog bindEditView(int id, String value) throws NullPointerException{

        if(mDialog.findViewById(id) == null){
            throw new NullPointerException("CustomDialog do not contain id:"+id);
        }

        if(TextUtils.isEmpty(value)){
            return this;
        }

        if(mDialog.findViewById(id) instanceof  CustomVerticalEditText)
            ((CustomVerticalEditText)mDialog.findViewById(id)).setInput(value);
        else if(mDialog.findViewById(id) instanceof  CustomEditText){
            ((CustomEditText)mDialog.findViewById(id)).setInput(value);
        }

        return this;
    }

    public CustomDialog bindTextChangeListener(int id, OnTextListener listener) throws NullPointerException{

        if(mDialog.findViewById(id) instanceof  CustomVerticalEditText)
            ((CustomVerticalEditText)mDialog.findViewById(id)).setTextListener(listener);
        else if(mDialog.findViewById(id) instanceof  CustomEditText){
            ((CustomEditText)mDialog.findViewById(id)).setTextListener(listener);
        }
        return this;
    }

    public CustomDialog bindFinishListener(OnHideListener listener) throws NullPointerException{

        this.mFinishListener = listener;

        return this;
    }

    /**
     * 为自定义view设置监听器,一般自定义view为BaseLinearLayout 或 BaseRelativeLayout两种类型
     * @param id
     * @param listener
     * @return
     * @throws NullPointerException
     */
    public CustomDialog bindChildListener(int id, OnChildViewClickListener listener) throws NullPointerException{

        if(mDialog.findViewById(id) == null){
            throw new NullPointerException("CustomDialog do not contain id:"+id);
        }

        try {
            ((BaseLinearLayout)mDialog.findViewById(id)).setOnChildViewClickListener(listener);
        }
        catch (ClassCastException e){

            ((BaseRelativeLayout)mDialog.findViewById(id)).setOnChildViewClickListener(listener);
        }
        return this;
    }


    /**
     * 为自定义dialog添加列表视图
     * @param items      视图显示的名字
     * @param listener   每个视图点击的监听
     * @return
     * @throws NullPointerException
     */
    public CustomDialog list(List<String> items, OnItemChildViewClickListener listener) throws NullPointerException{

        if(mDialog.findViewById(R.id.sheetContainer) == null){
            throw new NullPointerException("CustomDialog do not contain R.id.sheetContainer!");
        }

        LinearLayout layout = mDialog.findViewById(R.id.sheetContainer);

        Context  context = mContext;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(40, context));
        lp.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(1, context));
        for(String item:items){
            TextView textView = new TextView(context);
            textView.setTextColor(context.getResources().getColor(R.color.hintColor));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setText(item);
            textView.setGravity(Gravity.CENTER);
//            layout.addView(LayoutInflater.from(context).inflate(R.layout.ly_line_light, null), lp3);
            layout.addView(textView, lp);
            textView.setOnClickListener(v -> {
                for(int i = 0; i < layout.getChildCount(); i++){
                    View view = layout.getChildAt(i);
                    if(view instanceof TextView){
                        ((TextView)view).setTextColor(context.getResources().getColor(R.color.hintColor));
//                        ((TextView)view).setCompoundDrawables(null, null, null, null);
                        ((TextView)view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    }
                }
                textView.setTextColor(context.getResources().getColor(R.color.customBlue4));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//                Drawable image = context.getResources().getDrawable(R.drawable.ic_choose_yes);
//                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
//                textView.setCompoundDrawables(image, null, null, null);
//                textView.setCompoundDrawablePadding(0);
                listener.onItemChildViewClick(textView, layout.indexOfChild(textView), 0, item);

            });
        }
        View root  = mDialog.getWindow().getDecorView();
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp2 = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        root.measure(0, 0);
        lp2.height +=  DisplayUtil.dip2px(40*items.size(), context);
        dialogWindow.setAttributes(lp2);

        return this;
    }



    public  CustomDialog threeButtonAlertDialog(String msg){

        View root = LayoutInflater.from(mContext).inflate(R.layout.ly_custom_three_btn_dialog, null);
        if(!TextUtils.isEmpty(msg)) {
            TextView textView = root.findViewById(R.id.msgText);
            textView.setText(msg);
        }
        else{
            root.findViewById(R.id.msgText).setVisibility(View.GONE);
        }

        mDialog.setContentView(root);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width = DisplayUtil.getScreenWidth(mContext) - 2* DisplayUtil.dip2px(40, mContext); // 宽度
        lp.alpha = 9f; // 透明度
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public CustomDialog twoButtonAlertDialog(String msg){

        View root = LayoutInflater.from(mContext).inflate(R.layout.ly_custom_two_btn_dialog, null);

        if(!TextUtils.isEmpty(msg)) {
            TextView textView = root.findViewById(R.id.msgText);
            textView.setText(msg);
        }
        else{
            root.findViewById(R.id.msgText).setVisibility(View.GONE);
        }

        mDialog.setContentView(root);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width = DisplayUtil.getScreenWidth(mContext) - 2* DisplayUtil.dip2px(40, mContext); // 宽度
        lp.alpha = 9f; // 透明度
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public CustomDialog imageViewActivityAlertDialog(String msg){

        View root = LayoutInflater.from(mContext).inflate(R.layout.ly_custom_image_activity_dialog, null);

        if(!TextUtils.isEmpty(msg)) {
            TextView textView = root.findViewById(R.id.msgText);
            textView.setText(msg);
        }
        else{
            root.findViewById(R.id.msgText).setVisibility(View.GONE);
        }

        mDialog.setContentView(root);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width = DisplayUtil.getScreenWidth(mContext) - 2* DisplayUtil.dip2px(40, mContext); // 宽度
        dialogWindow.setAttributes(lp);
        return this;
    }




    public CustomDialog title(String title){


        if(!TextUtils.isEmpty(title)) {
            TextView textView = mDialog.findViewById(R.id.titleText);
            textView.setText(title);
        }
        else{
            mDialog.findViewById(R.id.titleText).setVisibility(View.GONE);
        }

        return this;
    }


    public CustomDialog gravity(int gravity){

        if(mDialog == null){
            throw new NullPointerException("CustomDialog not created, you must call creat first!!");
        }
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.gravity = gravity;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public CustomDialog animation(int animation){

        if(mDialog == null){
            throw new NullPointerException("CustomDialog not created, you must call creat first!!");
        }
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setWindowAnimations(animation); // 添加动画

        return this;
    }

    public CustomDialog layout(int resId){

        View root = LayoutInflater.from(mContext).inflate(resId, null);
        mDialog.setContentView(root);

        return this;
    }

    public CustomDialog layout( View view){

        mDialog.setContentView(view);

        return this;
    }

    public CustomDialog layout(int resId, int width, int height){

        layout(resId);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width  = width; // 宽度
        lp.height = height; // 宽度
        dialogWindow.setAttributes(lp);

        return this;
    }

    public CustomDialog layout(View layout, int width, int height){

        layout(layout);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width  = width; // 宽度
        lp.height = height; // 宽度
        dialogWindow.setAttributes(lp);

        return this;
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

    public interface OnHideListener{


        void onAfterHide();
    }
}
