package com.springboot.service.impl;

import com.springboot.dto.OrderDTO;
import com.springboot.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 清风
 * 2019/8/5 10:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单
     * @throws Exception
     */
    @Test
    public void orderStatus() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1561607748343154220");
        //推送
        pushMessageService.orderStatus(orderDTO);
    }
}