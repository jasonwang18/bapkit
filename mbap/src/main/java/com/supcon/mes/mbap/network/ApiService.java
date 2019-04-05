package com.supcon.mes.mbap.network;

import com.app.annotation.apt.ApiFactory;

/**
 * Created by Administrator on 2016/3/23.
 * 接口参考文件，以下接口必须定义在接口中，复制到工程的接口中
 */
@ApiFactory
public interface ApiService {

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @param map 默认参数
     * @return
    @GET("/cas/mobile/logon")
    Flowable<LoginEntity> login(@Query("username") String username, @Query("password") String password, @QueryMap Map<String, Object> map);

     * 登出
     * @return
    @GET("/cas/mobile/logout")
    Flowable<ResultEntity> logout();


     * 用户心跳接口
     * @return
    @GET("/foundation/refreshSession.action")
    Flowable<HeartBeatEntity> heartbeat();

    */

}
