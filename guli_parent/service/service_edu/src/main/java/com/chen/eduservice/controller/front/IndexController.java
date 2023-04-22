package com.chen.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.commonutils.Result;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.service.EduCourseService;
import com.chen.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.List;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1214:07
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/index")
public class IndexController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService eduCourseService;
    @GetMapping("/frontIndex")
//        @Cacheable(key = "'frontIndex'",value = "frontIndex")
    public Result frontIndex(){
        // 查询最新前4条讲师数据
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);
        // 查询最新前8条课程数据
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(courseWrapper);
        return Result.ok().data("teacherList",teacherList).data("courseList",courseList);
    }
}
