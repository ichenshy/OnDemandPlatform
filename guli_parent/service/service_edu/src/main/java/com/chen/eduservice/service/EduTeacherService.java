package com.chen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //分页查询
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
