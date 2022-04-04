package com.springboot.aspect;

import com.springboot.Exception.SellerAuthorizeException;
import com.springboot.constant.CookieConstant;
import com.springboot.constant.RedisConstant;
import com.springboot.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 切入点
 * Aop身份验证
 * Created by 清风
 * 2019/8/4 14:33
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //有主要成分，和不需要成分
    //拦截就是商品的订单的这些操作
    //sell开头中有用户的登录登出，就没有必要过滤它
    @Pointcut("execution(public * com.springboot.controller.Seller*.*(..))"+
    "&& !execution(public * com.springboot.controller.SellerUserController.*(..))")
    public void verify(){

    }

    //在切入点之前获取这个操作
    @Before("verify()")
    public void doVerify(){
        //在方法里面获取http的request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【登录检验】Cookie中查不打token");
            throw new SellerAuthorizeException();
        }

        //去redis里查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        //加入是空的
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】Redis中查不打token");
            throw new SellerAuthorizeException();
        }


    }

}
