package com.springboot.service.impl;

import com.springboot.Exception.ResponseBankException;
import com.springboot.Exception.SellException;
import com.springboot.converter.OrderMaster2OrderDTOConverter;
import com.springboot.dataobject.OrderDetail;
import com.springboot.dataobject.OrderMaster;
import com.springboot.dataobject.ProductInfo;
import com.springboot.dto.CartDTO;
import com.springboot.dto.OrderDTO;
import com.springboot.repository.OrderDetailRepository;
import com.springboot.repository.OrderMasterRepository;
import com.springboot.service.OrderService;
import com.springboot.service.PayService;
import com.springboot.service.ProductService;
import com.springboot.utils.KeyUtil;
import enums.OrderStatusEnum;
import enums.PayStatusEnum;
import enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单service实现类
 * Created by qingfeng
 * 2019/6/24
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;



    @Override
    //一旦事务回滚就会发生异常就会失败
    //加个事务，
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        //2.1先定义一个总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList = new ArrayList<>();

        //1. 查询商品（数量, 价格）
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            //如果数据库里面没有这个信息，有问题
            if (productInfo == null) {
                //抛出异常，商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                //这里抛异常，来自己出现200变成403，演示一下
//                throw new ResponseBankException();
            }

            //2. 计算订单总价
            //2.2、单价乘以总价在加上orderAmount
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            //生成随机数
            orderDetail.setDetailId(KeyUtil.genUniqueKey());

            //OrderId整个id创建的时候积极生成了
            orderDetail.setOrderId(orderId);

            //只穿了商品的id和数量，其他都没有传，这是有问题的
            //商品的图片要传，在productinfo里面,考过了
            //Spring 提供的方法，进行对象的属性拷贝，把productInfo拷贝到orderDetail里面
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //3、写入订单详情入库（orderMatser和orderDetail）
            OrderMaster orderMaster =new OrderMaster();
            orderDTO.setOrderId(orderId);
             BeanUtils.copyProperties(orderDTO, orderMaster);
            //orderMaster.setOrderId(orderId);
            orderMaster.setOrderAmount(orderAmount);

            //这里就先拷贝，在设置，放上面取
            //BeanUtils.copyProperties(orderDTO, orderMaster);

            //用的值有被覆盖，重新写回去
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

            orderMasterRepository.save(orderMaster);


        //4、扣库存
        List<CartDTO> cartDTOList =orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        return  orderDTO;


    }

    /**
     * 查询多个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        //判断一下是否存在
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //接着查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        //判断是否为空，
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        //再就是返回
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        //设置订单详情的列表
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询订单的列表
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        //这里不需要查详情，前端列表显示，不显示具体的详情的
        //查询列表，查到就是有东西哦，否则就是无，这里不需要像上面来判断
        //创建一个返回的参数
        //这里写一个转换器OrderMaster转化成OrderDTOConverter
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

    }

    /**
     * 取消订单
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        /**
         * 写程序是就把程序列清楚，在写的时候逻辑不至于漏掉
         */

        OrderMaster orderMaster =new OrderMaster();
        //BeanUtils.copyProperties(orderDTO,orderMaster);

        /**
         *   判断订单状态
         *   更新订单记录需要判断这条记录的状态
         *
         */
        //只有指定的订单才能被取消，完结状态就不能被取消
        //拿到的订单状态不等于新下单的状态，逻辑不能写反
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){


            //使用日志,打印错误日志
            //可以把orderId和订单状态记录下路
            log.error("【取消订单】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            //然后直接抛出一个异常
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        /**
         *  接着可以修改订单状态，修改为取消
         */

        //2、接着修改订单状态，修改成已取消
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());

        //修改完了之后在进行拷贝
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //1、通过调用orderMasterRepository中的save方法进行修改
        //直接写orderDTO是不对，的需要把ordermaster转化成orderDto,写在上面

        //3、判断下结果，是否成功
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        //如果返回是空的话，就是失败
        if (updateResult == null){
            log.error("【取消订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        /**
         * 接着就要返还库存
         */
        //如果没有商品就不要返回，先判断下
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //调用加库存的方法
        List<CartDTO> cartDTOList =orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        //increaseStock写入这个方法，放上面
        productService.increaseStock(cartDTOList);


        /**
         * 用户下单可能支付，那么就要退款
         */
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //todo好处再于可以立马让你查到你写代码的地方，小技巧
            //TODO
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    /**
     * 订单完结
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        //只有新下单的时候完结
        //如果订单的状态不等于新下单就会报错
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        //先把orderDTO状态改了
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模版消息
        //pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    /**
     * 支付订单
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        //如果订单状态不等于待支付，就要报错
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

}
