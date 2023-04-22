package com.chen.eduservice.controller;


import com.chen.commonutils.Result;
import com.chen.eduservice.service.EduCourseService;
import com.chen.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-21
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    public EduSubjectService eduSubjectService;


    //添加课程
    //获取文件读取
    @PostMapping("/addsubject")
    public Result addsubject(MultipartFile file) {
        eduSubjectService.saveSubjcet(file, eduSubjectService);
        return Result.ok();
    }

    //课程分类的列表功能
    @GetMapping("/getAllsubject")
    public Result getAllsubject() {
        List list = eduSubjectService.getAllsubject();
        return Result.ok().data("list", list);
    }
}

