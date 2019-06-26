package com.springboot.repository;

import com.springboot.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * dao
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    //查询上架商品
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
