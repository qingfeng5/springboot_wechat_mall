package com.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信
 * Created by 清风
 * 2019/6/27 16:17
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    //根据微信文档，根据code访问，拿下code
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("进入auth方法。。。");
        log.info("code={}",code);
    }
}
