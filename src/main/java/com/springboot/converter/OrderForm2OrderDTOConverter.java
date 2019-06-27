package com.springboot.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springboot.Exception.SellException;
import com.springboot.dataobject.OrderDetail;
import com.springboot.dto.OrderDTO;
import com.springboot.form.OrderForm;
import enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * orderForm换成OrderDTO转换方法
 * Created by 清风
 * 2019/6/26 13:49
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO converter(OrderForm orderForm){
        Gson gson =new Gson();

        OrderDTO orderDTO= new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //以前都用BeanUtils方法来转,但是这里不行
        //因为orderDTO定义的是buyer_name,而orderForm中定义的是nam e,名字不一样不能转
        //BeanUtils.copyProperties();

        List<OrderDetail> orderDetailList =new ArrayList<>();
        //这里需要gson格式
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        }catch (Exception e){
            log.error("【对象转换】 错误,string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
