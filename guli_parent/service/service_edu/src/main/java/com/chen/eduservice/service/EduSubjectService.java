package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-21
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubjcet(MultipartFile file,EduSubjectService eduSubjectService);

    List getAllsubject();
}
