package com.springboot.service;

import com.springboot.dto.OrderDTO;

/**
 * 微信端消息推送
 * Created by 清风
 * 2019/8/4 15:30
 */
public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
