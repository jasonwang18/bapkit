package com.supcon.mes.mbap.inter;

import com.supcon.common.com_http.BaseEntity;

/**
 * @Author xushiyun
 * @Create-time 5/23/19
 * @Pageage com.supcon.mes.mbap.inter
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public interface IDataConsumer<T extends BaseEntity> {
  default  void consume(T entity) {
  
  }
}
