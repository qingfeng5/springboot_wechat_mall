package com.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 日志
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoggerTest.class)

@Slf4j
//@data
public class LoggerTest {
    //省去注解，减少麻烦
    //private  final Logger logger= LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
//        logger.debug("debug...");
//        logger.info("info...");
//        logger.error("error...");

        /**
         * 日志中输出变量
         */
        String name ="qingfeng";
        String password ="123456";


        /**
         * 简化日志编写格式
         */
        log.debug("debug...");
        log.info("info...");

        //这个相对比较复杂
        log.info("name" + name + " password" + password);
        //这种就是吧name放到第一个大括号里面
        log.info("name:{},password: {}" ,name,password);

        log.error("error...");
    }

}
