package com.springboot.service;

import com.springboot.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目-商品
 */
public interface CategoryService {
    //查询记录提供
    ProductCategory find0ne(Integer categoryId);

    //查询所有
    List<ProductCategory> findAll();

    //买家端用这个，通过tpye来查
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    //新增更新
    ProductCategory save(ProductCategory productCategory);
}
