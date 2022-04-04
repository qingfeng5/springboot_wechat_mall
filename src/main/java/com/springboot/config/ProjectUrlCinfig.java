package com.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * url前缀
 * Created by 清风
 * 2019/8/2 10:49
 */
@Data
@ConfigurationProperties(prefix = "project-url")
@Component
public class ProjectUrlCinfig {

    /**
     * 微信公众平台授权url
     */
    public String wechatMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    public String wechatOpenAuthorize;

    /**
     * 点餐系统
     */
    public String sell;
}
