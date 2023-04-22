package com.chen.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;
import com.chen.vod.service.VodService;
import com.chen.vod.utils.ConstantVodUtil;
import com.chen.vod.utils.InitObject;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.core.Is;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/817:48
 */
@Service
public class VodServiceImpl implements VodService {
    //阿里云上传代码实现
    @Override
    public String uploadAlyiVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
//            String videold = response.getRequestId();
            String videold = response.getVideoId();
            if (response.isSuccess()) {
                return videold;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //删除视频
    @Override
    public void removeVideo(String id) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtil.ACCESS_KEY_ID,ConstantVodUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (Exception e) {
            throw new ExceptionTest(20001, "视频删除失败");
        }
    }

    //批量删除云端视频
    @Override
    public void removeAlyVideos(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtil.ACCESS_KEY_ID,ConstantVodUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String join = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(join);
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (Exception e) {
            throw new ExceptionTest(20001, "视频删除失败");
        }
    }

}
