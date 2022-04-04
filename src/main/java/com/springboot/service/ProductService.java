package com.springboot.service;

import com.springboot.dataobject.ProductInfo;
import com.springboot.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品的service
 */
public interface ProductService {


    ProductInfo findOne(String productId);

    //查询在架的商品列表
    List<ProductInfo> findUpAll();

    //管理端查询所有。管理端进行分页的，不能所有列表都传上来
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

//    上架
    ProductInfo onSale(String productId);

//    下架
    ProductInfo offSale(String productId);
}
