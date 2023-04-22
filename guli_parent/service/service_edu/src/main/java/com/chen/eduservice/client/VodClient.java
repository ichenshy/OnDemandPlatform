package com.chen.eduservice.client;

import com.chen.commonutils.Result;
import com.chen.eduservice.client.Impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/915:32
 */
@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    //删除云端视频
    @DeleteMapping("/eduVod/video/removeAlyVideo/{id}")
    Result removeAlyVideo(@PathVariable("id") String id);

    //批量删除云端视频
    @DeleteMapping("/eduVod/video/delete-batch")
    Result removeAlyVideos(@RequestParam("videoIdList") List<String> videoIdList);

}
