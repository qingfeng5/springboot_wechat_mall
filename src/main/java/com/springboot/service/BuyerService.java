package com.springboot.service;

import com.springboot.dto.OrderDTO;

/**
 * 买家openid判断接口
 * Created by 清风
 * 2019/6/27 12:51
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
