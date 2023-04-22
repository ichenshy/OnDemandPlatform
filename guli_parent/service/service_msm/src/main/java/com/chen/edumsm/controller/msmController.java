package com.chen.edumsm.controller;

import com.chen.commonutils.Result;
import com.chen.edumsm.service.MsmService;
import com.chen.edumsm.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1218:28
 */
@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class msmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //短信发送
    @GetMapping("send/{phone}")
    public Result senMsm(@PathVariable String phone) {
        String s = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(s)) {
            return Result.ok();
        }
        String code = RandomUtil.getFourBitRandom();
//        Map<String, Object> param = new HashMap<>();
//        param.put("code", code);
//        System.out.println(code);
        boolean isSend = msmService.send(code, phone);
        if (isSend) {
            redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().messgae("短信发送失败");
        }
    }
}
