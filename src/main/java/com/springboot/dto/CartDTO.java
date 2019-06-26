package com.springboot.dto;

import lombok.Data;

/**
 * 购物车
 * 只传回两个，id和数量
 * Created by qingfeng
 * 2019/6/25
 */
@Data
public class CartDTO {

    /** 商品Id. */
    private String productId;

    /** 数量. */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
