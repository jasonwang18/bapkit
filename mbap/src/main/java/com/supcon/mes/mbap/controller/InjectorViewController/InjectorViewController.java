package com.supcon.mes.mbap.controller.InjectorViewController;

import android.view.View;

import com.supcon.common.com_http.BaseEntity;
import com.supcon.common.view.base.controller.BaseViewController;
import com.supcon.mes.mbap.inter.IDataInjector;

/**
 * @Author xushiyun
 * @Create-time 5/23/19
 * @Pageage com.supcon.mes.mbap.controller
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public abstract class InjectorViewController<T extends BaseEntity> extends BaseViewController implements IDataInjector<T>{
    public static View view;
    private InjectorViewController(View rootView) {
        super(rootView);
    }
    
    protected InjectorViewController() {
        this(view);
    }
    
//    private static final class InjectorViewControllerHolder {
//        private static final InjectorViewController SINGLETON = new InjectorViewController();
//    }
    
//    public static InjectorViewController singleton() {
//        return InjectorViewControllerHolder.SINGLETON;
//    }

}
