package com.supcon.mes.mbap.controller;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.view.CustomContactView;

public class MyContactTitleController extends CustomContactView.ContactViewController<CustomContactView.DemoEntity> {
    TextView name;
    ImageView right;

    public MyContactTitleController(View rootView) {
        super(rootView);
        this.rootView = rootView;
        findViewById(rootView);
    }

    @Override
    protected void findViewById(View rootView) {
        name = rootView.findViewById(R.id.name);
        right = rootView.findViewById(R.id.right);
    }

    @Override
    public int layout() {
        return R.layout.ly_my_contact_title;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void visit(CustomContactView.DemoEntity obj) {
        name.setText(obj.name);
        right.setVisibility(isLast == null||!isLast ? View.VISIBLE : View.GONE);
        if (obj.isSelected != null && obj.isSelected) {
            name.setTextColor(Color.BLUE);
        } else {
            name.setTextColor(Color.BLACK);
        }
    }
}
