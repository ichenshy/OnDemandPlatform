package com.chen.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.chen.commonutils.Result;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;
import com.chen.vod.service.VodService;
import com.chen.vod.utils.ConstantVodUtil;
import com.chen.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/817:47
 */
@RestController
@RequestMapping("/eduVod/video")
//@CrossOrigin
public class VodController {
    @Autowired
    VodService vodService;

    //阿里云上传代码实现
    @PostMapping("/uploadAlyiVideo")
    public Result uploadAlyiVideo(MultipartFile file) {

        String vodId = vodService.uploadAlyiVideo(file);
        System.out.println("==========>" + vodId);
        return Result.ok().data("vodId", vodId);
    }

    //删除云端视频
    @DeleteMapping("removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable String id) {
        vodService.removeVideo(id);
        return Result.ok().messgae("删除视频成功！");
    }
    //批量删除云端视频
    @DeleteMapping("delete-batch")
    public Result removeAlyVideos(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeAlyVideos(videoIdList);
        return Result.ok();
    }
    //获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtil.ACCESS_KEY_ID,ConstantVodUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new ExceptionTest(20001,"获取凭证失败");
        }
    }

}
