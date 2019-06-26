package com.springboot.utils;

import com.springboot.VO.ResultVO;

/**
 * 封装ResultVO方法
 * 表示成功与上架
 * Created by qingfeng
 * 2019/6/24
 */

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO =new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    //有可能成功什么都不传
    public static ResultVO succes(){
        return success(null);
    }

    //失败方法
    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO =new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
