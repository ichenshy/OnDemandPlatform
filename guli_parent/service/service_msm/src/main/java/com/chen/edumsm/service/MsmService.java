package com.chen.edumsm.service;

import java.util.Map;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1218:29
 */
public interface MsmService {
    //短信发送
//    boolean send(Map<String, Object> param, String phone);
    boolean send(String code, String phone);
}
