package com.springboot.repository;

import com.springboot.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**订单详情表测试
 * Created by qingfeng
 * 2019/6/24
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("50450418");
        orderDetail.setOrderId("11111114");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductId("11111114");
        orderDetail.setProductName("大闸蟹");
        orderDetail.setProductPrice(new BigDecimal(13.14));
        orderDetail.setProductQuantity(3);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("11111114");
        Assert.assertNotEquals(0, orderDetailList.size());
    }
}