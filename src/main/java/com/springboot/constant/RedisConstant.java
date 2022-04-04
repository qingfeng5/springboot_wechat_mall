package com.springboot.constant;

/**
 * redis常量
 * 过期时间来自sellerusercontroller
 * Created by 清风
 * 2019/8/3 13:20
 */
public interface RedisConstant {

    //设置前缀，希望token存储是以token_开头的
    String TOKEN_PREFIX = "token_%s";

    //过期时间
    Integer EXPIRE = 7200; //2小时
}
