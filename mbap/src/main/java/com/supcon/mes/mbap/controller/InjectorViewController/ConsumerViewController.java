package com.supcon.mes.mbap.controller.InjectorViewController;

import android.view.View;

import com.supcon.common.view.base.controller.BaseViewController;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.mes.mbap.beans.BaseMultiStageEntity;
import com.supcon.mes.mbap.inter.IDataConsumer;

/**
 * @Author xushiyun
 * @Create-time 5/23/19
 * @Pageage com.supcon.mes.mbap.controller.InjectorViewController
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public abstract class ConsumerViewController<T extends BaseMultiStageEntity> extends BaseViewController implements IDataConsumer<T> {
    public ConsumerViewController(View rootView) {
        super(rootView);
    }
    
    protected OnItemChildViewClickListener mOnItemChildViewClickListener;
    public int layout() {
        return 0;
    }
    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        mOnItemChildViewClickListener = onItemChildViewClickListener;
    }
    public abstract ConsumerViewController  createNew();
}
