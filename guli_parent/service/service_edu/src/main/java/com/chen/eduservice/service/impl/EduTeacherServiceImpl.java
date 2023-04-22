package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.mapper.EduTeacherMapper;
import com.chen.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage, null);
        HashMap<String, Object> map = new HashMap<>();
        List<EduTeacher> records = teacherPage.getRecords();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        long total = teacherPage.getTotal();
        boolean hasNext = teacherPage.hasNext(); //下一页
        boolean hasPrevious = teacherPage.hasPrevious(); //上一页
        map.put("records", records);
        map.put("current", current);
        map.put("size", size);
        map.put("pages", pages);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
