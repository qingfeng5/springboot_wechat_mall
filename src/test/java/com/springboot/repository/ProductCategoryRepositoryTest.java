package com.springboot.repository;

import com.springboot.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        //findById(123456).orElse(null);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory = repository.findById(3).orElse(null);
        //这个只是改的类型，要想把数据苦更新时间，在ProductCategory中更新时间注解
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(2,3,4);

        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        //怎么算成功，这个集合的结果大于0算成功
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    //跟在service添加事务不一样，方法里面抛出异常会回滚，之前产生的数据给删除掉
    //测试里面就要完全回滚，这是测试信息就不要显示在数据库中
    @Transactional
    public void updateTest() {
//        ProductCategory productCategory = repository.findOne(4);
//        productCategory.setCategoryName("男生最爱1");
        ProductCategory productCategory = new ProductCategory("女神最爱", 1);
        ProductCategory result = repository.save(productCategory);
        //判断是否成功
//        Assert.assertEquals(productCategory, result);
        Assert.assertNotNull(result);
    }

}