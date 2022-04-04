package com.springboot.dataobject.dao;

import com.springboot.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by 清风
 * 2019/8/30 10:31
 */
public class ProductCategoryDao {
    @Autowired
    ProductCategoryMapper mapper;

    public int insertByMap(Map<String, Object> map) {
        return mapper.insertByMap(map);
    }


}
