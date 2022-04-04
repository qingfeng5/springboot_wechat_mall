package com.springboot.service.impl;

import com.springboot.Exception.SellException;
import com.springboot.dataobject.ProductInfo;
import com.springboot.dto.CartDTO;
import com.springboot.repository.ProductInfoRepository;
import com.springboot.service.ProductService;
import enums.ProductStatusEnum;
import enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;

//    @Autowired
//    private UpYunConfig upYunConfig;

    @Override
//    @Cacheable(key = "123")
    @Cacheable(cacheNames = "product",key = "123")
    public ProductInfo findOne(String productId) {
//        Optional<ProductInfo> productInfoOptional = repository.findById(productId);
//        if (productInfoOptional.isPresent()) {
//            return productInfoOptional.get().addImageHost(upYunConfig.getImageHost());
//        }
//        return null;

//        productInfoOptional.ifPresent(e -> e.addImageHost(upYunConfig.getImageHost()));
        return repository.findById(productId).orElse(null);

    }

    /**
     * 添加枚举ProductStatusEnum，不用添加0和1，之间通过单词一次来查
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
//                .stream()
//                .map(e -> e.addImageHost(upYunConfig.getImageHost()))
//                .collect(Collectors.toList());
    }

    /**
     * 查询所有
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
//        Page<ProductInfo> productInfoPage = repository.findAll(pageable);
//        productInfoPage.getContent().stream()
//                .forEach(e -> e.addImageHost(upYunConfig.getImageHost()));
//        return productInfoPage;
        return repository.findAll(pageable);
    }

    @Override
//    @CachePut(key = "123")
    //为什么要加key，因为你保存后，在每一个key的情况下，就是ProductInfo，二刷新后，key是ProductId,不同的，传不进来
    //不能起到刷新缓存，依然存在的
    @CachePut(cacheNames = "product",key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
                //.addImageHost(upYunConfig.getImageHost());
    }

    //加库存
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            //先查询
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
           // 先判断
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //增加库存
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            //加库存完不需要判断，直接set进去
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    //减库存
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        //遍历购物车
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            //没有这个商品抛异常
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //然后减库存
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            //判断是不是小于0
            if (result < 0) {
                //小于0就是库存不足
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }


            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        //查询一个商品的信息
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //如果商品存在要判断上架状态
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
