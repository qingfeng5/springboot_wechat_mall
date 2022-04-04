package com.springboot.controller;

import com.springboot.config.ProjectUrlCinfig;
import com.springboot.constant.CookieConstant;
import com.springboot.constant.RedisConstant;
import com.springboot.dataobject.SellerInfo;
import com.springboot.service.SellerService;
import com.springboot.utils.CookieUtil;
import enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户登录
 * Created by 清风
 * 2019/8/2 10:57
 */
//页面跳转用,RestController不适合，用下面的Controller
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlCinfig projectUrlCinfig;

    /**
     * 登录
     */
    @GetMapping("login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){

        //1、登录的操作，可以获取openid去和数据库的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        //2、设置token至redis
        //生成uuid作为token
        String token = UUID.randomUUID().toString();
        //再给他一个过期时间，都配到常量包constant中RedisConstant
        Integer expire = RedisConstant.EXPIRE;

        //redis写入数据
        //set第一个元素是格式化了一下啊，希望存储的是token_的一个key
        //第二元素，value值直接把openid的内容存储进去
        //第三个元素，过期时间，第四个是，时间格式（秒）
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //3、设置token至cookie
        //如何往response写cookie
        //先new一个cookie
//        Cookie cookie = new Cookie("token",token);
//        //然后设置属性
//        cookie.setPath("/");
//        cookie.setMaxAge(7200);
//        //在整理一下
//        response.addCookie(cookie);

        //但每次都写上面的有点累，写一个工具类cookieutil
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);

        ///只做跳转
        return new ModelAndView("redirect:"+ projectUrlCinfig.getSell() +"/sell/seller/order/list");
        //redirect:/sell/seller/order/list这个地址跳转回出现两个sell/sell/seller。。
        //所有地址跳转时候，用相对地址，不要用绝对地址

    }

    /**
     * 退出
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie，就是把过期时间设置为0就行
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        //清除完跳转到成功界面
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
