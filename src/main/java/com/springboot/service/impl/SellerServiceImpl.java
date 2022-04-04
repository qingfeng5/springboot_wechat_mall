package com.springboot.service.impl;

import com.springboot.dataobject.SellerInfo;
import com.springboot.repository.SellerInfoRepository;
import com.springboot.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 卖家段接口实现
 * Created by 清风
 * 2019/8/2 10:09
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;


    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
