package com.springboot.utils;

import java.util.Random;

/**
 * 生成随机的方法
 * 来自于OrderServiceImpl
 * Created by qingfeng
 * 2019/6/24
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    //多线程也是会重复，加一个字段synchronized
    public static synchronized String genUniqueKey() {
        Random random = new Random();

        //生成6位随机数
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

}
