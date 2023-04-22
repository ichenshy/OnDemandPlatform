package com.chen.orders.service;

import com.chen.orders.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-04
 */
public interface PayLogService extends IService<PayLog> {
    //生成二维码接口

    Map createNative(String orderNo);
    //查询状态
    Map queryPayStatus(String orderNo);
    //添加支付记录,更新订单状态
    void updateOrderState(Map<String,String> map);
}
