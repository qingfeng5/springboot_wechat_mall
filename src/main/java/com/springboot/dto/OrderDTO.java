package com.springboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.dataobject.OrderDetail;
import com.springboot.utils.EnumUtil;
import com.springboot.utils.serializer.Date2LongSerializer;



import enums.OrderStatusEnum;
import enums.PayStatusEnum;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    /**买家端页面显示订单状态，o和1等的枚举来说明状态，不要用if语句来表达**/

    /**在这个枚举中写一个遍历的方法，通过code的值返回这个枚举，这样把所有的遍历一遍
     * 这个造成每一个枚举种豆要写这样的代价，这就是不合理之处
     * 别人优秀的地方在于，省去不合理地方，不能以为复制粘贴就是好的
     * 这样我们可以写一个公用的方法**/

    /**OrderStatusEnum和PayStatusEnum都能实现一个接口
     * 通过枚举工具实现**/
    //JsonIgnore转换成json格式时就会忽略这个方法
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

}
