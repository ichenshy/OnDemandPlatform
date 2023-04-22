package com.chen.staservice.client;

import com.chen.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author CSY
 * @version v1.0
 * @date 2022/2/5 13:32
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //查询某一天的注册人数
    @GetMapping("/ucenter/member/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day);
}
