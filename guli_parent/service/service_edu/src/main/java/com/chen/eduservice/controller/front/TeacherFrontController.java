package com.chen.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.Result;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.service.EduCourseService;
import com.chen.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1511:53
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService eduCourseService;

    //分页查询
    @GetMapping("/getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String,Object>map = teacherService.getTeacherFrontList(teacherPage);
        //返回分页所有数据
        return Result.ok().data(map);
    }
    //查询讲师的详细信息
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        //查询信息
        EduTeacher teacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //查询课程
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return Result.ok().data("courseList",courseList).data("teacher",teacher);
    }
}
