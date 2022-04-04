package enums;

import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;

/**
 * 支付枚举
 * Created by qingfeng
 * 2019/6/24
 */
@Getter
public enum  PayStatusEnum implements CodeEnum {
    //支付状态
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    //没有支付失败，支付失败就是0
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
