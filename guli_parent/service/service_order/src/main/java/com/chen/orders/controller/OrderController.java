package com.chen.orders.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.Result;
import com.chen.orders.entity.Order;
import com.chen.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-04
 */
@RestController
@RequestMapping("/order/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单号
    @PostMapping("createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId, jwtToken);
        return Result.ok().data("orderId", orderNo);
    }

    //根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no", orderId);
        Order order = orderService.getOne(orderQueryWrapper);
        return Result.ok().data("item", order);
    }

    //根据课程id和用户id查询订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
//        return orderService.count(wrapper)>0;
    }
}

