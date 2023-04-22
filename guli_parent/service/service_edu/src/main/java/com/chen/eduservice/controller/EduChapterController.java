package com.chen.eduservice.controller;


import com.chen.commonutils.Result;
import com.chen.eduservice.entity.EduChapter;
import com.chen.eduservice.entity.chapter.ChapterVo;
import com.chen.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//@CrossOrigin
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    //根据id查询课程大纲列表
    @GetMapping("/getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoList(courseId);
        return Result.ok().data("allChapterVideo", list);
    }

    //章节添加
    @PostMapping("/addChapterVideo")
    public Result addChapterVideo(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return Result.ok();
    }

    //根据id查询章节
    @GetMapping("/getChapterVideoId/{id}")
    public Result getChapterVideoId(@PathVariable String id) {
        EduChapter eduChapter = eduChapterService.getById(id);
        return Result.ok().data("eduChapter", eduChapter);
    }

    //修改
    @PostMapping("/upDataChapterVideo")
    public Result upDataChapterVideo(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return Result.ok();
    }

    //章节删除
    @DeleteMapping("{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId) {
        boolean result = eduChapterService.deleteChapterById(chapterId);
        if (result) {
            return Result.ok().messgae("删除成功");
        } else {
            return Result.error().messgae("请先删除课时在删除章节");
        }
    }
}

