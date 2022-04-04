package com.springboot.dataobject.mapper;

import com.springboot.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * mybatis注解方式，把sql写道到注解里面去
 * 还一种写到xml文件里面去
 * Created by 清风
 * 2019/8/30 10:17
 */
public interface ProductCategoryMapper {

    /**
     * 通过map写入数据库
     * @param map
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) " +
            "values (#{categoryName, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

    /**
     * 通过对象写入数据
     * 用的最多就是对象
     */
    @Insert("insert into product_category(category_name, category_type) " +
            "values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    /**
     * 查询数据 按照categoryType来查
     * @param categoryType
     * @return
     */
    @Select("select * from product_category where category_type = #{categoryType}")
    //select之后要把结果写出来，要获得什么样的结果
    @Results({
            //每一个result其实是一个字段，字段名位column，property，其实就是把字段名映射位这个变量，数据库里的
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    /**
     * 询数据 按照category_name来查
     * @param categoryName
     * @return
     */
    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType")
    })
    List<ProductCategory> findByCategoryName(String categoryName);

    /**
     * 更新 根据一个字段去跟新
     * @param categoryName
     * @param categoryType
     * @return
     */
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    //查多个参数要加一个注解Param
    int updateByCategoryType(@Param("categoryName") String categoryName,
                             @Param("categoryType") Integer categoryType);

    /**
     * 根据一个对象去更新
     * @param productCategory
     * @return
     */
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByObject(ProductCategory productCategory);

    //删除
    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    /**
     * 写一个方法，把sql写道xml中去
     * @param categoryType
     * @return
     */
    ProductCategory selectByCategoryType(Integer categoryType);
}
