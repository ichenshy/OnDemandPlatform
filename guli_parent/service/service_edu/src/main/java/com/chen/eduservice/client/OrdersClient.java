package com.chen.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author CSY
 * @version v1.0
 * @date 2022/2/4 18:42
 */
@Component
@FeignClient("service-order")
public interface OrdersClient {

    //根据课程id和用户id查询订单状态
    @GetMapping("/order/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}
