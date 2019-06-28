package com.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

       String url= "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx73694811e68fd5db&secret=b207afb2655d325f27abae0243c84545&code=" +code +"&grant_type=authorization_code";

       //返回json格式，用get请求
        RestTemplate restTemplate =new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);
        log.info("response={}",response);

    }
}
