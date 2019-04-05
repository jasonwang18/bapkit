package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomPotraitView extends BaseLinearLayout {

    ImageView potraitIcon;
    TextView potraitUserName;
    TextView potraitText;
    private String username, mText;
    private int iconId;

    public CustomPotraitView(Context context) {
        super(context);
    }

    public CustomPotraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_potrait;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            potraitUserName.setTypeface(newFont);
            potraitText.setTypeface(newFont);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        potraitIcon = findViewById(R.id.potraitIcon);
        potraitUserName = findViewById(R.id.potraitUserName);
        potraitText = findViewById(R.id.potraitText);

        if(!TextUtils.isEmpty(mText)){
            potraitText.setText(mText);
        }

        if(!TextUtils.isEmpty(username)){
            potraitUserName.setText(username);
        }

        if(iconId != 0){
            potraitIcon.setImageResource(iconId);
        }

    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomPotraitView);
            iconId = array.getResourceId(R.styleable.CustomPotraitView_potrait_icon, 0);
            mText = array.getString(R.styleable.CustomPotraitView_potrait_text);
            username= array.getString(R.styleable.CustomPotraitView_potrait_username);
            array.recycle();
        }
    }




    public void setText(String text){
        potraitText.setText(text);
    }

    public void setText(int resId){
        potraitText.setText(resId);
    }

    public void setTextColor(int color){

        potraitText.setTextColor(color);

    }

    public void setUsernameColor(int color){
        potraitUserName.setTextColor(color);
    }

    public void setIcon(int resId){
        potraitIcon.setImageResource(resId);
    }

    public String getUsername() {
        return potraitUserName.getText().toString();

    }

    public void setUsername(String username){

       potraitUserName.setText(username);
    }

    public ImageView imageView(){

        return potraitIcon;
    }

}
