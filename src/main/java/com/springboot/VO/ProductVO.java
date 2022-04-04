package com.springboot.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *商品（包含类目）
 */
@Data
public class ProductVO implements Serializable {


    private static final long serialVersionUID = 2974026889738211757L;

    //具体name名字
    //返回前端，加个注解
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    //这个跟Productinfo差不多，但是前端要几个字段就用几个字段，不需要返回内容，
    //出于对内容和隐私安全的考虑
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
