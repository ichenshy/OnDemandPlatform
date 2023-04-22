package com.chen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.vo.CourseInfovo;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.frontVo.CourseFrontVo;
import com.chen.eduservice.entity.vo.frontVo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseInfovo courseInfovo);

    CourseInfovo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfovo courseInfovo);

    CoursePublishVo getCoursePublishById(String id);

    void removeCourse(String id);
///条件 分页查询
    Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo);

    //查询信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
