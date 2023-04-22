package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.eduservice.entity.EduChapter;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.entity.chapter.ChapterVo;
import com.chen.eduservice.entity.chapter.VideoVo;
import com.chen.eduservice.mapper.EduChapterMapper;
import com.chen.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.eduservice.service.EduVideoService;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoList(String courseId) {
        //查询章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);
        //查询小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> list = eduVideoService.list(videoQueryWrapper);
        //创建一个list集合来存储最终数据
        List<ChapterVo> finAllList = new ArrayList<>();
        //章节封装
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finAllList.add(chapterVo);
            List<VideoVo> videoVoList = new ArrayList<>();
            //小节封装
            for (int m = 0; m < list.size(); m++) {
                EduVideo eduVideo = list.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return finAllList;
    }

    //删除章节
    @Override
    public boolean deleteChapterById(String id) {

        if (eduVideoService.getCountByChapterId(id)) {
            throw new ExceptionTest(20001,"请先删除课程在删除章节");
        }else {
            int result = baseMapper.deleteById(id);
            return result==1;
        }

    }
    //2、根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
