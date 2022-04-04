package com.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis分布式加锁解锁
 * Created by 清风
 * 2019/8/31 10:59
 */
@Component
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        //如果setIfAbsent就是被锁定了
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //currentValue=A   这两个线程的value都是B  只会其中一个线程拿到锁
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        //多个线程进来的时候，只会一个线程拿到锁
        //就是存储进去的时间小于当前时间
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            //使用getAndSet方法，此时value值是B
            //第一个线程拿到oldvalue值就是A,当前的currentValue也是A，如果相等就返回ture
//            第二个线程拿到oldvalue值是B,但是currentValue一直是A，不相等，就没有拿到这把锁
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            //如果这个值不为空，并且和当前的value值相等的话
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                //解锁就是删掉key
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }
    }

}
