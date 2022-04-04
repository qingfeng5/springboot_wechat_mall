package enums;

import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;

/**
 * 订单service枚举方法
 * Created by qingfeng
 * 2019/6/24
 */
@Getter
public enum  OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;


    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**在这个枚举中写一个遍历的方法，通过code的值返回这个枚举，这样把所有的遍历一遍
     * 这个造成每一个枚举种豆要写这样的代价，这就是不合理之处
     * 别人优秀的地方在于，省去不合理地方，不能以为复制粘贴就是好的
     * 这样我们可以写一个公用的方法**/
//    public static OrderStatusEnum getOrderStatusEnum(Integer code){
//        for (OrderStatusEnum orderStatusEnum:OrderStatusEnum.values()){
//            if (orderStatusEnum.getCode().equals(code)){
//                return orderStatusEnum;
//            }
//        }
//        return null;
//    }
}
