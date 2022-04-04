package com.springboot.repository;

import com.springboot.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商品类dao
 * Created by 清风
 * 2019/8/2 10:00
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    //通过openid查询它们信息
    SellerInfo findByOpenid(String openid);
}
