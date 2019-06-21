package com.supcon.mes.mbap.controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.view.CustomContactView;

public class MyContactViewController extends CustomContactView.ContactViewController<CustomContactView.DemoEntity> {
    View rootView;
    ImageView icon;
    TextView name;
    TextView info;
    ImageView right;
    public MyContactViewController(View rootView) {
        super(rootView);
        this.rootView = rootView;
        findViewById(rootView);
    }

    @Override
    public View rightView() {
        return right;
    }

    @Override
    protected void findViewById(View rootView) {
        icon = rootView.findViewById(R.id.icon);
        name = rootView.findViewById(R.id.name);
        info = rootView.findViewById(R.id.info);
        right = rootView.findViewById(R.id.right);
    }

    @Override
    public int layout() {
        return R.layout.ly_my_contact_item;
    }

    @Override
    public void visit(CustomContactView.DemoEntity obj) {
        right.setVisibility(obj.subDepartment.size()>0?View.VISIBLE:View.INVISIBLE);
        name.setText(obj.name);
        info.setText(obj.info);
    }
}
