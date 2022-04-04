package com.springboot.controller;

import com.springboot.VO.ProductInfoVO;
import com.springboot.VO.ProductVO;
import com.springboot.VO.ResultVO;
import com.springboot.dataobject.ProductCategory;
import com.springboot.dataobject.ProductInfo;
import com.springboot.service.CategoryService;
import com.springboot.service.ProductService;
import com.springboot.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Calendar.DATE;

/**
 * 买家商品
 */

//返回json格式
@RestController
//返回url格式
@RequestMapping("/buyer/product")
public class BuyerProductController {

    //查商品
    @Autowired
    private ProductService productService;

    //查类目
    @Autowired
    private CategoryService categoryService;

    //get请求
    @GetMapping("/list")
    //使用VO中ResultVO对象
//    @Cacheable(cacheNames = "product",key = "123")
    @CacheEvict(cacheNames = "product",key = "123")
    public ResultVO list(){
        //1、查询所有的上架商品
        List<ProductInfo> productInfoList =productService.findUpAll();

        //2、查询类目（一次性查询）
//        List<Integer> categoryTypeList =new ArrayList<>();
        //categoryTypeList从productInfoList这里面取
        //传统方法，for循环
//        for (ProductInfo productInfo : productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法，用的是（java8,lambda)
        List<Integer> categoryTypeList= productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList) ;

        //3、数据拼装
        //(4)最外面一层是ProductVO是一个list，在外面定义下
        List<ProductVO> productVOList =new ArrayList<>();
        //(1)首先遍历类目
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO= new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            //(3)拷贝之后add一个list中去，在外面定义下
            List<ProductInfoVO> productInfoVOList =new ArrayList<>();
            //(2)下面在遍历商品详情
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO =new ProductInfoVO();
                    //要写5个值，要写五遍，spring提供BeanUtils
                    //可以把productInfo拷贝到productInfoVO中去
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                    //(3)拷贝之后add一个list中去，在外面定义下
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
            //(4)最外面一层是ProductVO是一个list，在外面定义下
        }


        //封装在ResultVOUtil中减少麻烦
//        ResultVO resultVO =new ResultVO();
//        resultVO.setData(productVOList);
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        return resultVO;

        return ResultVOUtil.success(productVOList);

    }
}
