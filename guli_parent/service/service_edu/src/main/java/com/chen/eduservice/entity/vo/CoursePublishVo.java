package com.chen.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/621:32
 */
@Data
public class CoursePublishVo{
    private String title;
    private String cover;
    private Integer lessonNum;
    private String oneSubject;
    private String twoSubject;
    private String name;
    private String price;//只用于显示
}
