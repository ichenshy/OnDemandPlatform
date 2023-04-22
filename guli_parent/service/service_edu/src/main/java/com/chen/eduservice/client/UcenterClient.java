package com.chen.eduservice.client;

import com.chen.eduservice.client.Impl.UcenterClientImpl;
import com.chen.ucenter.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/17 15:01
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //根据用户id获取信息
    @PostMapping("/ucenter/member/getInfoUc/{id}")
    Member getInfoUc(@PathVariable("id") String id);
}
