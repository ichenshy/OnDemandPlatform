package com.chen.eduservice.controller;


import com.chen.commonutils.Result;
import com.chen.eduservice.client.VodClient;
import com.chen.eduservice.entity.EduChapter;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.service.EduVideoService;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/eduVideo")
public class EduVideoController {
    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    VodClient vodClient;

    //添加
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        System.out.println("==============>" + eduVideo.getTitle());
        if (eduVideo.getTitle() != null) {
            eduVideoService.save(eduVideo);
            return Result.ok();
        }
        return Result.error().messgae("课时标题不能为空");

    }

    //删除
    @DeleteMapping("{eduId}")
    public Result delVideo(@PathVariable String eduId) {
        EduVideo byId = eduVideoService.getById(eduId);
        String videoSourceId = byId.getVideoSourceId();
        System.out.println("=====云端视频id=====>"+videoSourceId);
        if (!StringUtils.isEmpty(videoSourceId)) {
            Result result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new ExceptionTest(20001,"删除视频失败，段容器....");
            }
        }
        eduVideoService.removeById(eduId);
        return Result.ok();
    }

    //修改、根据id查询值进行数据回显
    //根据id查询小节
    @GetMapping("/getVideoId/{id}")
    public Result getVideoId(@PathVariable String id) {
//        EduChapter eduChapter = eduChapterService.getById(id);
//        return Result.ok().data("eduChapter", eduChapter);
        EduVideo eduVideo = eduVideoService.getById(id);
        return Result.ok().data("eduVideo", eduVideo);
    }

    //修改
    @PostMapping("/upDataVideo")
    public Result upDataVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return Result.ok();
    }

}

