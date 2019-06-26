package com.springboot.dataobject;

import enums.OrderStatusEnum;
import enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * service订单数据库
 * Created by qingfeng
 * 2019/6/24
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id. */
    //id是这个这个OrderMaster表的主键，需要加个注解
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    /**
     * 用于detail和master，共order_id
     * 但是这样就会很乱，我们重新新建一个对象dto
     */
    //在数据对应的时候把错误忽略掉
//    @Transient
//    private List<OrderDetail> orderDetailList;
}
