package com.supcon.mes.mbap.controller.InjectorViewController;

import android.view.View;
import android.widget.TextView;

import com.app.annotation.BindByTag;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.beans.ExampleMultistageEntity;

/**
 * @Author xushiyun
 * @Create-time 5/23/19
 * @Pageage com.supcon.mes.mbap.controller.InjectorViewController
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public class MutiStageConsumerViewController_1 extends ConsumerViewController< ExampleMultistageEntity> {
    @BindByTag("content")
    TextView content;
    
    
    
    
    public MutiStageConsumerViewController_1(View rootView) {
        super(rootView);
    }
    
    @Override
    public int layout() {
        return R.layout.item_multistage_list_1;
    }
    
    @Override
    public void initListener() {
        super.initListener();
    }
    
    @Override
    public ConsumerViewController createNew() {
        return new MutiStageConsumerViewController_1(new View(context));
    }
    
    @Override
    public void consume(ExampleMultistageEntity entity) {
        content.setText(entity.content);
    
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(context,"点击了第一级图标");
//                content.setVisibility(View.GONE);
                if(mOnItemChildViewClickListener!= null)
                mOnItemChildViewClickListener.onItemChildViewClick(null,0,0,0);
            }
        });
    }
    
}
