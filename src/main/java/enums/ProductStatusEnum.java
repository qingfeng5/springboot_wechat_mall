package enums;

import lombok.Getter;

/**
 * 枚举方法
 */
@Getter
public enum  ProductStatusEnum implements CodeEnum {

    //0表示上架，1表示下架
    UP(0,"在架"),
    DOWN(1,"下架")
    ;

    //定义一个code,massage
    private Integer code;

    private String massage;

    ProductStatusEnum(Integer code, String massage) {
        this.code = code;
        this.massage = massage;
    }
}
