package com.springboot.service.impl;

import com.springboot.dataobject.ProductCategory;
import com.springboot.repository.ProductCategoryRepository;
import com.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目-商品
 * CategoryService接口实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;



    @Override
    public ProductCategory find0ne(Integer categoryId) {
        /**
         * 查不到返回null
         * .get 抛异常
         */
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
