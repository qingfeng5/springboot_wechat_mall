package com.springboot.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.dataobject.OrderDetail;
import com.springboot.utils.serializer.Date2LongSerializer;
import com.springboot.enums.OrderStatusEnum;
import com.springboot.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据传输对象，专门在各个层数据传输用的
 * Created by qingfeng
 * 2019/6/24
 */
@Data
//orderDetailList返回又null，希望不返回资格字段
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    //JsonSerialize使用date转化为long型
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    //orderDetailList返回又null，希望不返回资格字段
    //如果orderDetailList有初始值，这样定义
//    List<OrderDetail> orderDetailList =new ArrayList<>();

    //没有初始值，null
    List<OrderDetail> orderDetailList;

//    @JsonIgnore
//    public OrderStatusEnum getOrderStatusEnum() {
//        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
//    }
//
//    @JsonIgnore
//    public PayStatusEnum getPayStatusEnum() {
//        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
//    }

}
