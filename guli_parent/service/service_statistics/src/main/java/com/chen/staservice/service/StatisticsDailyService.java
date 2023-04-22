package com.chen.staservice.service;

import com.chen.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-05
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    //统计某一天的注册人数
    void registerCount(String day);
    //图表显示 ---- 日期json  数量json
    Map<String, Object> getShowData(String type, String begin, String end);
}
