package com.springboot.service;

import com.lly835.bestpay.model.PayResponse;

import com.lly835.bestpay.model.RefundResponse;
import com.springboot.dto.OrderDTO;

/**
 *支付逻辑
 * Created by 清风
 * 2019/7/1 10:07
 */

public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    //支付异常
    PayResponse  notify(String notifyData);

    //退款
    RefundResponse refund(OrderDTO orderDTO);
}
