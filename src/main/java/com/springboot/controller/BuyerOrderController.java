package com.springboot.controller;

import com.springboot.Exception.SellException;
import com.springboot.VO.ResultVO;
import com.springboot.converter.OrderForm2OrderDTOConverter;
import com.springboot.dto.OrderDTO;
import com.springboot.form.OrderForm;
import com.springboot.service.OrderService;
import com.springboot.utils.ResultVOUtil;
import enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 买家订单 api 控制层
 * Created by 清风
 * 2019/6/26 13:30
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    //创建需要service，引入
    @Autowired
    private OrderService orderService;

    //创建订单
    //加上url的路径
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                              BindingResult bindingResult) {
        //首先检验表单有没有错误
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确,orderForm={}",orderForm);
            //抛出来异常，抛出就是参数不正确
            //具体是哪个参数不正确，比如姓名每填，像把姓名这个返回回去
            //这里message传回去
            //这里抛红，是由于没有这样构造方法，再SellException创建下
           // log.error("【对象转换】 错误,string={}",orderForm.getItems());

            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //create方法需要orderdto对象，这里设计对象的转换
        //需要把orderForm换成OrderDTO，创建一个转化方法
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);

        //转化之后，没有错误即可下单，？
        //还要做一个判断，转了之后购物车还是空的
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw  new SellException(ResultEnum.CART_EMPTY);
        }
        //然后就可以创建订单
        OrderDTO createResult = orderService.create(orderDTO);

        //运行id
        Map<String,String> map =new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtil.success(map);

    }


    //订单列表

    //订单详情

    //取消订单


}
