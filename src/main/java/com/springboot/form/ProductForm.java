package com.springboot.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 专门存储表单传递过来的对象
 * 给sellproductControlle中的save方法
 * Created by 清风
 * 2019-07-29 10:11
 */
@Data
public class ProductForm {


    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 类目编号. */
    private Integer categoryType;
}
