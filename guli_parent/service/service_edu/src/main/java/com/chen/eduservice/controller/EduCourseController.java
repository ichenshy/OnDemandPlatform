package com.chen.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.Result;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.vo.CourseInfovo;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.CourseQuery;
import com.chen.eduservice.service.EduCourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
@RestController
@RequestMapping("/eduservice/edu-course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //获取信息list
    @GetMapping
    public Result allList() {
        List<EduCourse> list = eduCourseService.list(null);
        return Result.ok().data("list", list);
    }

    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody CourseInfovo courseInfovo) {
        String id = eduCourseService.saveCourse(courseInfovo);
        return Result.ok().data("courseId", id);
    }

    //根据课程id查看课程的基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseInfovo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfovo courseInfovo) {
        eduCourseService.updateCourseInfo(courseInfovo);
        return Result.ok();
    }

    //发布信息课程
    @GetMapping("/CoursePublish/{id}")
    public Result CoursePublish(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishById(id);
        return Result.ok().data("coursePublishVo", coursePublishVo);
    }

    //最终发布
    @PostMapping("/pushlish/{id}")
    public Result pushlish(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

    //    4、条件查询带分页
    @PostMapping("/pagePushlish/{current}/{limit}")
    public Result pagePushlish(@PathVariable long current,
                               @PathVariable long limit,
                               @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //多条件组合查询、动态sql、获取值
        String name = courseQuery.getTitle();
        BigDecimal level = courseQuery.getPrice();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        //判断条件值是否为空，如果不为空接拼条件
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("title", name);
        }
        if (level != null) {
            wrapper.eq("price", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法
        eduCourseService.page(pageCourse, wrapper);
        long total = pageCourse.getTotal();//记录数
        List<EduCourse> recode = pageCourse.getRecords();//数据list集合
        return Result.ok().data("total", total).data("rows", recode);
    }

    //删除课程
    @DeleteMapping("{id}")
    public Result deleteCourse(@PathVariable String id) {
        eduCourseService.removeCourse(id);
        return Result.ok();
    }
}