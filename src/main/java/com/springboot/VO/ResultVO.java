package com.springboot.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 * GSON请求的信息
 *
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//Serializable添加缓存，
public class ResultVO<T> implements Serializable {

    //序列号，可以保证id唯一
    private static final long serialVersionUID = 3992098372355274267L;
    /**错误码*/
    private Integer code;

    /**提示信息
     */
    private String msg;

    /**返回具体内容
     */
    private T data;

}
