package com.chen.orders.service;

import com.chen.orders.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-04
 */
public interface OrderService extends IService<Order> {
    //生成订单号
    String createOrder(String courseId, String jwtToken);
}
