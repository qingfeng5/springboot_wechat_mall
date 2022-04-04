package com.springboot.form;

import lombok.Data;

/**
 *
 * Created by 清风
 * 2019-08-1 10:16
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;
}
