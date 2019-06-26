package com.springboot.Exception;

import enums.ResultEnum;
import lombok.Getter;

/**
 * 异常
 * Created by qingfeng
 * 2019/6/24
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message){
        super(message);
        this.code= code;
    }
}
