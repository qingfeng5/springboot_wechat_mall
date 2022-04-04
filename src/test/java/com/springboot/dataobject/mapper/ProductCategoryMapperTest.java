package com.springboot.dataobject.mapper;

import com.springboot.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by 清风
 * 2019/8/30 10:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    //map注入数据测试
    @Test
    public void insertByMap() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","清风最爱");
        map.put("category_type",101);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    //通过对象方式写入数据测试
    @Test
    public void insertByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("师兄最不爱");
        productCategory.setCategoryType(102);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1, result);
    }

    //查询数据，按照CategoryType查询
    @Test
    public void findByCategoryType() {
        ProductCategory result = mapper.findByCategoryType(102);
        Assert.assertNotNull(result);
    }

    //查询数据，按照CategoryName查询
    @Test
    public void findByCategoryName() {
        List<ProductCategory> result = mapper.findByCategoryName("师兄最不爱");
        Assert.assertNotEquals(0, result.size());
    }

    //更新测试 根据一个字段去跟新
    @Test
    public void updateByCategoryType() {
        int result = mapper.updateByCategoryType("师兄最不爱的分类", 102);
        Assert.assertEquals(1, result);
    }

    //根据一个对象去更新
    @Test
    public void updateByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("师兄最不爱");
        productCategory.setCategoryType(102);
        int result = mapper.updateByObject(productCategory);
        Assert.assertEquals(1, result);
    }

    //删除
    @Test
    public void deleteByCategoryType() {
        int result = mapper.deleteByCategoryType(102);
        Assert.assertEquals(1, result);
    }

    @Test
    public void selectByCategoryType() {
        ProductCategory productCategory = mapper.selectByCategoryType(101);
        Assert.assertNotNull(productCategory);
    }

}