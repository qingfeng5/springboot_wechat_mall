package com.springboot.converter;

import com.springboot.dataobject.OrderMaster;
import com.springboot.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * service转换器
 * Created by 清风
 * 2019/6/26 11:06
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO =new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    //再写一个list
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
