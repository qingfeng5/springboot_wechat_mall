package com.springboot.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.springboot.Exception.SellException;
import com.springboot.dto.OrderDTO;
import com.springboot.service.OrderService;
import com.springboot.service.PayService;
import com.springboot.utils.JsonUtil;
import com.springboot.utils.MathUtil;
import enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 支付实现接口
 * Created by 清风
 * 2019/7/1 10:13
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;


    @Override
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest payRequest =new PayRequest();

        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付,request={}", JsonUtil.toJson(payRequest));

        //发起支付
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付,response={}",JsonUtil.toJson(payResponse));

        return payResponse;
    }

    /**
     * 处理异步通知的方法名
     * @param notifyData
     */
    @Override
    public PayResponse notify(String notifyData) {
        //1、异步通知你需要先验证签名
        //2、支付状态，发起支付通知，但是不一定是百分之百消费成功
        //  bestpayservice sdk已经做了一二两点
        //3、支付金额，就是支付1分钱，其实给多， 但是订单状态还在继续往下更新，这是危险操作
        //4、支付人（下单人 == 支付人）

       PayResponse payResponse = bestPayService.asyncNotify(notifyData);

       log.info("【微信支付】异步通知,payResponse={}",JsonUtil.toJson(payResponse));


        //拿到orderid先查询订单状态
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if (orderDTO == null){
            log.error("【微信支付】异步通知,订单不存在,orderId={}",payResponse.getOrderId());
            //同时抛出异常
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一置，如果不一致才使用这个方法（0.10  0.1）
        //所有可以判断一个她们之间相减，只要小于一个精度，就对的，写MathUtil中去
        //不要使用equals，这是java初学者常犯的要注意
        //if (!orderDTO.getOrderAmount().equals(payResponse.getOrderAmount())){
       // if (orderDTO.getOrderAmount().compareTo(new BigDecimal(payResponse.getOrderAmount())) != 0){
        if (!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】异步通知，订单金额不一致,orderId={},微信通知金额={}，系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单支付状态
        orderService.paid(orderDTO);



       return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 request={}",JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
