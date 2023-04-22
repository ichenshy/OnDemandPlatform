package com.chen.eduservice.mapper;

import com.chen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.frontVo.CourseWebVo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo selectCoursePublishVoById(String id);
    CourseWebVo getBaseCourseInfo(String courseId);
}
