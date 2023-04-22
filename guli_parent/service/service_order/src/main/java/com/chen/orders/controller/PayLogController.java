package com.chen.orders.controller;


import com.chen.commonutils.Result;
import com.chen.orders.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-04
 */
@RestController
@RequestMapping("/order/paylog")
//@CrossOrigin

public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        //生成二维码接口
        Map map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    //查询状态
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        //查询状态
        Map map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return Result.error().messgae("支付失败");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            //添加支付记录,更新订单状态
            payLogService.updateOrderState(map);
            return Result.ok().messgae("支付成功");
        }
        return Result.ok().code(25000).messgae("支付中");
    }

}

