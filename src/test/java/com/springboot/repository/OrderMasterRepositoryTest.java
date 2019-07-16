package com.springboot.repository;

import com.springboot.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by qingfeng
 * 2019/6/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "oTgZpwS8xX35iLZuuRXa62gRyW9s";

    /**
     * 测试保存的方法
     */
    @Test
    public void saveTest(){
        OrderMaster orderMaster =new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("清风");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("浙江杭州");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(13.14));

        OrderMaster result= repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    /**
     * 查分页参数
     */
    @Test
    public void findByBuyerOpenid() throws Exception{

        //PageRequest实现了以Pageable接口，用都用PageRequest
        PageRequest request = PageRequest.of(1, 3);

        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, request);

        //只要条数不等于0就行
        Assert.assertNotEquals(0, result.getTotalElements());
    }
}