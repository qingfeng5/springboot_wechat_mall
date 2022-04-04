package com.springboot.utils;

import enums.CodeEnum;

/**
 * 枚举工具类
 * Created by 清风
 * 2019/7/16 15:19
 */
public class EnumUtil {
    //返回的结果就是一个枚举
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        //下面循环，如果code值相等，返回这个枚举，否则
        for (T each: enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return  each;
            }
        }
        return null;
    }
}
