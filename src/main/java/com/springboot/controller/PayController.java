package com.springboot.controller;

import com.lly835.bestpay.model.PayResponse;
import com.springboot.Exception.SellException;
import com.springboot.dto.OrderDTO;

import com.springboot.service.OrderService;
import com.springboot.service.PayService;
import enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 支付sdk
 * Created by 清风
 * 2019/7/1 10:00
 */

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    //传回来两个参数orderId,returnUrl
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                       Map<String, Object>map){

        //1、查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2、发起支付
        //把支付的逻辑写道service中去
        //2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
//        map.put("orderId","1111111");

        //使用模板技术，实现动态参数返回
        return new ModelAndView("pay/create", map);

    }

    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        //notifyData逻辑下载service
        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
