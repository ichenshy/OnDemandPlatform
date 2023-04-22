package com.chen.staservice.controller;


import com.chen.commonutils.Result;
import com.chen.staservice.client.UcenterClient;
import com.chen.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-05
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;
    //统计某一天的注册人数，生成
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day){
        statisticsDailyService.registerCount(day);
        return Result.ok();
    }

    //图表显示 ---- 日期json  数量json
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,
                           @PathVariable String begin,
                           @PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);
        return Result.ok().data(map);
    }

}

