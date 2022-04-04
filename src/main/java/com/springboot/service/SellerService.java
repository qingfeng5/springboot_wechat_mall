package com.springboot.service;

import com.springboot.dataobject.SellerInfo;

/**
 * 卖家端
 * Created by 清风
 * 2019/8/2 10:06
 */
public interface SellerService {

    //通过openid查询卖家信息
    SellerInfo findSellerInfoByOpenid(String openid);
}
