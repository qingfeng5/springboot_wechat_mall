package com.springboot.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 公众号配置
 * Created by 清风
 * 2019/6/28 14:35
 */
@Component
public class WechatMpConfig {

    //微信账号相关的到这里来使用
    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService =new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return  wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        //基于内存的接口
        WxMpInMemoryConfigStorage wxMpConfigStorage =new WxMpInMemoryConfigStorage();
        //方法写到实现类中，实现appid和sercet
        //这里的配置从application.yml读取
        //然后使用WechatAccountConfig的配置调用yml中的wechat
        wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }
}
