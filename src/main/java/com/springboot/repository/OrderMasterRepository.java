package com.springboot.repository;

import com.springboot.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * dao层订单
 * Created by qingfeng
 * 2019/6/24
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    //某个人的订单查出来，用分页来查
    //按照买家的buyeropenid来查，有分页
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
