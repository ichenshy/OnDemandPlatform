package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoList(String courseId);

    boolean deleteChapterById(String id);
    //2、根据课程id删除章节
    void removeChapterByCourseId(String id);
}
