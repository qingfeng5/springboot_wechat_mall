package com.springboot.service.impl;

import com.springboot.dataobject.ProductInfo;
import enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 查询商品测试
 * ProductServiceImplTest测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    /**
     * 查询一个商品
     */
    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    /**
     * 查询一个在架的商品
     */
    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }


    @Test
    public void findAll() throws Exception {
        //查询第几页，页面有几条内容
        PageRequest request = PageRequest.of(0, 2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
//        System.out.println(productInfoPage.getTotalElements());
        //只要查到总数不等于0就通过了
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("烤全羊");
        productInfo.setProductPrice(new BigDecimal(5.2));
        productInfo.setProductStock(200);
        productInfo.setProductDescription("很好吃的养");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    /**上架*/
    @Test
    public void onSale() {
        ProductInfo result = productService.onSale("123457");
        Assert.assertEquals(ProductStatusEnum.UP, result.getProductStatusEnum());
    }

    /**下架商品*/
    @Test
    public void offSale() {
        ProductInfo result = productService.offSale("123457");
        Assert.assertEquals(ProductStatusEnum.DOWN, result.getProductStatusEnum());
    }
}