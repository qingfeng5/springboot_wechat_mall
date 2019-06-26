package com.springboot.repository;


import com.springboot.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    //查商品希望查所有类目
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
