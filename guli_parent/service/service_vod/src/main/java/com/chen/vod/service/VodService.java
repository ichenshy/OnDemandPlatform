package com.chen.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/817:48
 */
public interface VodService {
    //阿里云上传代码实现
    String uploadAlyiVideo(MultipartFile file);
    //删除视频
    void removeVideo(String id);
    //批量删除云端视频
    void removeAlyVideos(List<String> videoIdList);

}
