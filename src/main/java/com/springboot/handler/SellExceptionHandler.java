package com.springboot.handler;

import com.springboot.Exception.ResponseBankException;
import com.springboot.Exception.SellException;
import com.springboot.Exception.SellerAuthorizeException;
import com.springboot.VO.ResultVO;
import com.springboot.config.ProjectUrlCinfig;
import com.springboot.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 对异常的捕获
 * Created by 清风
 * 2019/8/4 15:06
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlCinfig projectUrlCinfig;

    //拦截登录异常
    //拦截异常的名字写到注解里面去
    //http://qingfeng.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://qingfeng.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handerAuthorizeException(){
        //拦截之后让他跳转一个界面去
        return new ModelAndView("redirect:"
        //直接跳转到登录界面去
                //这个是授权的地址
                .concat(projectUrlCinfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlCinfig.getSell())
                .concat("/sell/seller/login"));
    }

    /**
     * 商品不存在时候的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = SellException.class)
    //上面的不是resopnsecontroller，这里需要加个注解ResponseBody
    @ResponseBody
    public ResultVO handlerSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * 专门定义一个特定的异常，专门返回404，402等，不要返回200
     * 然后抛出的异常捕获它
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException() {
    }
}
