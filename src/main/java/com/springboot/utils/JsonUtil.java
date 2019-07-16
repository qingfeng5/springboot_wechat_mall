package com.springboot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json格式话工具
 * Created by 清风
 * 2019/7/2 16:20
 */
public class JsonUtil {
    public static String toJson(Object object){
        //将对象格式化位gson格式
        GsonBuilder gsonBuilder =new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson =gsonBuilder.create();
        return gson.toJson(object);
    }
}
