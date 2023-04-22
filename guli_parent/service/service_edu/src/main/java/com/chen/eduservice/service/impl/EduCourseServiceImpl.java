package com.chen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.*;
import com.chen.eduservice.entity.vo.CourseInfovo;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.frontVo.CourseFrontVo;
import com.chen.eduservice.entity.vo.frontVo.CourseWebVo;
import com.chen.eduservice.mapper.EduCourseMapper;
import com.chen.eduservice.service.EduChapterService;
import com.chen.eduservice.service.EduCourseDescriptionService;
import com.chen.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.eduservice.service.EduVideoService;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourse(CourseInfovo courseInfovo) {
        //添加课程的信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfovo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new ExceptionTest(20001, "课程信息添加失败");
        }
        //获取id的值
        String cid = eduCourse.getId();
        //添加课程的简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfovo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //多表查询
    @Override
    public CourseInfovo getCourseInfo(String courseId) {
        //课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfovo courseInfovo = new CourseInfovo();
        BeanUtils.copyProperties(eduCourse, courseInfovo);
        //简介表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfovo.setDescription(courseDescription.getDescription());
        return courseInfovo;
    }

    //修改方法
    @Override
    public void updateCourseInfo(CourseInfovo courseInfovo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfovo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new ExceptionTest(20001, "修改失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfovo.getId());
        eduCourseDescription.setDescription(courseInfovo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(id);
        return coursePublishVo;
    }

    @Override
    public void removeCourse(String id) {
        //1、根据课程id删除小节
        eduVideoService.removeVideoByCourseId(id);
        //2、根据课程id删除章节
        eduChapterService.removeChapterByCourseId(id);
        //3、根据课程id删除描述
        eduCourseDescriptionService.removeById(id);
        //删除封面TODO

        //4、根据课程id删除本身
        int result = baseMapper.deleteById(id);
        if (result == 0) {
            throw new ExceptionTest(20001, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件
        //一级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        //二级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        //销量
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        //创建时间
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        //价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(eduCoursePage, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        List<EduCourse> records = eduCoursePage.getRecords();
        long current = eduCoursePage.getCurrent();
        long size = eduCoursePage.getSize();
        long pages = eduCoursePage.getPages();
        long total = eduCoursePage.getTotal();
        boolean hasNext = eduCoursePage.hasNext(); //下一页
        boolean hasPrevious = eduCoursePage.hasPrevious(); //上一页
        map.put("records", records);
        map.put("current", current);
        map.put("size", size);
        map.put("pages", pages);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
    //查询信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
      return baseMapper.getBaseCourseInfo(courseId);
    }

}
