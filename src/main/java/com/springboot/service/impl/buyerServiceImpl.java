package com.springboot.service.impl;

import com.springboot.Exception.SellException;
import com.springboot.dto.OrderDTO;
import com.springboot.service.BuyerService;
import com.springboot.service.OrderService;
import enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家openid判断实现
 * Created by 清风
 * 2019/6/27 12:47
 */
@Service
@Slf4j
public class buyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO=checkOrderOwner(openid,orderId);
        //这里给出一个判断，取消订单，取消结果是一个null不行的
        if (orderDTO == null){
            log.error("【取消订单】 查不到订单，orderId={}",orderId);
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }


    //这个方法提出来，给上面两个共用
    private OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO orderDTO=orderService.findOne(orderId);
        //再加一个判断，找到orderdto可能为空
        if (orderDTO == null){
            return null;
        }

        //进行判断，如果不是本人订单，就打印日志
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单的openid不一致，openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

}
