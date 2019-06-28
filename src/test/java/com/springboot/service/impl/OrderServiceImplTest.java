package com.springboot.service.impl;

import com.springboot.dataobject.OrderDetail;
import com.springboot.dto.OrderDTO;
import com.springboot.enums.OrderStatusEnum;
import com.springboot.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * service订单测试
 * Created by qingfeng
 * 2019/6/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//可以用日志打印
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID= "110110";

    private final String ORDER_ID = "1561517534467682345";

    @Test
    public void create() {
        //构成dto对象，买家地址
        OrderDTO orderDTO =new OrderDTO();
        orderDTO.setBuyerName("清风");
        orderDTO.setBuyerAddress("杭州下沙");
        orderDTO.setBuyerPhone("123456789123");
        //很重要的openid，定义到外面
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList =new ArrayList<>();

        OrderDetail o1 =new OrderDetail();
        //这里填数据库已有商品
        o1.setProductId("123456");
        o1.setProductQuantity(1);


        OrderDetail o2 =new OrderDetail();
        o2.setProductId("12345678");
        o2.setProductQuantity(2);

        //list加进去
        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result= orderService.create(orderDTO);
        log.info("[创建订单] result={}",result);
        //最后加测试判断文件
        Assert.assertNotNull(result);
    }

    /**
     * 查询多个订单测试
     */
    @Test
    public void findOne() throws Exception {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}", result);
        Assert.assertEquals(ORDER_ID, result.getOrderId());
    }


    /**
     * 查询订单的列表测试
     */
    @Test
    public void findList() throws Exception {
        PageRequest request =new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage =orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    /**
     *取消订单测试
     */
    @Test
    public void cancel() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        //构造一个对象
        OrderDTO result=orderService.cancel(orderDTO);
        //判断一下取消后的状态相不相等等
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());

    }

    /**
     * 完结订单测试
     */
    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());

    }

    /**
     * 支付订单测试
     */
    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());

    }

    @Test
    public void findList1() {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
//        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有的订单列表", orderDTOPage.getTotalElements() > 0);
    }

}