package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String id);
    //1、根据课程id删除小节
    void removeVideoByCourseId(String id);

}
