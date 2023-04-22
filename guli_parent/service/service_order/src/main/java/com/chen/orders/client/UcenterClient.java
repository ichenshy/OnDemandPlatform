package com.chen.orders.client;

import com.chen.commonutils.orderUtils.MemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author CSY
 * @version v1.0
 * @date 2022/2/4 15:17
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据用户id获取信息
    @PostMapping("/ucenter/member/getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable("id") String id);

}
