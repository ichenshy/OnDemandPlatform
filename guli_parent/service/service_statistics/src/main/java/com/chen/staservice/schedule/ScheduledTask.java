package com.chen.staservice.schedule;

import com.chen.staservice.service.StatisticsDailyService;
import com.chen.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author CSY
 * @version v1.0
 * @date 2022/2/5 15:12
 */
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //每天凌晨一点---添加前一天的数据    表达式spring boot只支持6位数  7位报错 理论是可以的
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.registerCount(day);
    }
}
