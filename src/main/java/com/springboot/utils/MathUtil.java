package com.springboot.utils;

/**
 * 支付订单价格与商品价格相减的精度
 * Created by 清风
 * 2019/7/16 11:16
 */
public class MathUtil {
    private static final Double MONEY_RANGE = 0.01;

    public static Boolean equals(Double d1,Double d2){
        Double result = Math.abs(d1 - d2);
        if (result < MONEY_RANGE){
            return true;
        }else {
            return false;
        }
    }
}
