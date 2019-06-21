package com.supcon.mes.mbap.inter;

import android.view.View;

import com.supcon.common.com_http.BaseEntity;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;

/**
 * @Author xushiyun
 * @Create-time 5/22/19
 * @Pageage com.supcon.mes.mbap.inter
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public interface IDataInjector<T extends BaseEntity> extends IDataProvider<T>, IDataConsumer<T> {
    default void inject() {
        inject(this, this);
    }
    
    default void inject(IDataProvider<T> iDataProvider, IDataConsumer<T> iDataConsumer) {
        iDataConsumer.consume(iDataProvider.provide());
    }
    
    default void inject(IDataProvider<T> iDataProvider) {
        inject(iDataProvider, this);
    }
    
    default void inject(IDataConsumer<T> iDataConsumer) {
        inject(this, iDataConsumer);
    }
    
    default void inject(IDataInjector<T> injector) {
        inject(injector,injector);
    }
}
