package com.chen.eduservice.client.Impl;

import com.chen.commonutils.Result;
import com.chen.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/920:07
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result removeAlyVideo(String id) {
        return Result.error().messgae("删除视频失败...");
    }

    @Override
    public Result removeAlyVideos(List<String> videoIdList) {
        return Result.error().messgae("删除多个视频失败...");
    }
}
