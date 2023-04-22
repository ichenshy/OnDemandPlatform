package com.chen.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.commonutils.Result;
import com.chen.staservice.client.UcenterClient;
import com.chen.staservice.entity.StatisticsDaily;
import com.chen.staservice.mapper.StatisticsDailyMapper;
import com.chen.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-05
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //统计某一天的注册人数
    @Override
    public void registerCount(String day) {
        //思路二 先删除再添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        Result register = ucenterClient.countRegister(day);
        Integer count = (Integer) register.getData().get("countRegister");
        //加到数据库中----统计表
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(count);//注册人数
        sta.setDateCalculated(day);//统计日期
        //数据模拟---使用随机数 RandomUtils.nextInt(100,500)
        sta.setVideoViewNum(RandomUtils.nextInt(100,500));
        sta.setLoginNum(RandomUtils.nextInt(100,500));
        sta.setCourseNum(RandomUtils.nextInt(100,500));
        baseMapper.insert(sta);
    }
    //图表显示 ---- 日期json  数量json
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StatisticsDaily daily = list.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("numList", numList);
        return map;
    }
}
