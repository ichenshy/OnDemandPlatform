package com.chen.servicebase.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data//get set方法
@AllArgsConstructor//生成有参构造方法
@NoArgsConstructor//无参构造方法
public class ExceptionTest extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息

}
