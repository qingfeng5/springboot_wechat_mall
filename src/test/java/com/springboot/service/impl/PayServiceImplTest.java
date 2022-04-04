package com.springboot.service.impl;

import com.springboot.dto.OrderDTO;
import com.springboot.service.OrderService;
import com.springboot.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 清风
 * 2019/7/2 15:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//可以用日志打印
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    /**支付创建订单**/
    @Test
    public void create() throws Exception{
        OrderDTO orderDTO =orderService.findOne("1563069508583141846");
        payService.create(orderDTO);
    }

    /**退款订单**/
    @Test
    public void refund(){
        OrderDTO orderDTO =orderService.findOne("1563253330258142320");
        payService.refund(orderDTO);
    }

}