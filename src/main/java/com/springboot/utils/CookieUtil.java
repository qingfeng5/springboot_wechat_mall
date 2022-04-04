package com.springboot.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 * Created by 清风
 * 2019/8/3 13:32
 */
public class CookieUtil {

//    设置
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie(name,value);
        //然后设置属性
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        //在整理一下
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param request
     * @param name
     */
    public static Cookie get(HttpServletRequest request,
                           String name){
        //request.getCookies()
        Map<String,Cookie> cookieMap = readCookieMap(request);
        //判断这个map中是否有cookie值
        if (cookieMap.containsKey(name)){
//            如果包含直接获取
            return cookieMap.get(name);
        }else {
            return null;
        }
    }

    /**
     * 讲cookie封装成map
     * @param request
     * @return
     */
    //写一个私有的方法，把原来cookie数组的格式转化为map，方便获取
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request) {
        //外面写一个map
        Map<String, Cookie> cookieMap = new HashMap<>();

        Cookie[] cookies = request.getCookies();
        //先判断是否有值，在遍历
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //cookie的名字和整个cookie
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
