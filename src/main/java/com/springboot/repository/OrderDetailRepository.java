package com.springboot.repository;

import com.springboot.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by qingfeng
 * 2019/6/24
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    /**
     * 先从master查，拿到订单id，再到订单详情表，通过id来查
     * master一条记录对应detail多条记录，返回一个list
     */
    List<OrderDetail> findByOrderId(String orderId);
}
