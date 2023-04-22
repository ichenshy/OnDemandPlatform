package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.eduservice.client.VodClient;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.mapper.EduVideoMapper;
import com.chen.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(eduVideoQueryWrapper);
        if (count > 0) {
            throw new ExceptionTest(20001, "请先删除课时在删除章节");
        } else {
            return count == 1;
        }
    }

    //1、根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String course_id) {
        //1、根据课程id查询所有视频
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", course_id);
        //只查询video_source_id字段
        videoWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(videoWrapper);
        List<String> voidId = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            if (!(eduVideo == null)) {
                String videoSourceId = eduVideo.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)) {
                    voidId.add(videoSourceId);
                }
            }
        }
        if (voidId.size() > 0) {
            vodClient.removeAlyVideos(voidId);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", course_id);
        baseMapper.delete(wrapper);
    }
}
