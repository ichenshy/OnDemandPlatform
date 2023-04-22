package com.chen.servicebase.ExceptionHandler;

import com.chen.commonutils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().messgae("触发了全局错误");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error1(ArithmeticException e){
        e.printStackTrace();
        return Result.error().messgae("触发了ArithmeticException错误");
    }

    //自定义异常
    @ExceptionHandler(ExceptionTest.class)
    @ResponseBody
    public Result ExceptionTest(ExceptionTest e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).messgae(e.getMsg());
    }

}
