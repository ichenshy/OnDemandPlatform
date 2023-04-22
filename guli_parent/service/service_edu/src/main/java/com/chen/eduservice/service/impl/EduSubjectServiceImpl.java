package com.chen.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.eduservice.Listrener.subjectExcelListrener;
import com.chen.eduservice.entity.EduSubject;
import com.chen.eduservice.entity.excel.SubjectExel;
import com.chen.eduservice.entity.subject.OneSubject;
import com.chen.eduservice.entity.subject.TwoSubject;
import com.chen.eduservice.mapper.EduSubjectMapper;
import com.chen.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubjcet(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectExel.class,new subjectExcelListrener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getAllsubject() {
        //查询一级分类 parent_id=0
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> onewrapper = baseMapper.selectList(oneWrapper);

        //查询二级分类parent_id!=0
        QueryWrapper<EduSubject> twoWrtapper = new QueryWrapper<>();
        twoWrtapper.ne("parent_id","0");
        List<EduSubject> twowrapper = baseMapper.selectList(twoWrtapper);

        List<OneSubject> finalSubjects = new ArrayList<>();
        //封装一级分类
        for (int i = 0; i < onewrapper.size(); i++) {
            EduSubject eduSubject = onewrapper.get(i);
            OneSubject oneSubject = new OneSubject();

            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjects.add(oneSubject);
            //封装二级分类
            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();
            for (int j = 0; j < twowrapper.size(); j++) {
                EduSubject Tsubjcet = twowrapper.get(j);
                if(Tsubjcet.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(Tsubjcet,twoSubject);
                    twoSubjects.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjects);
        }
        return finalSubjects;
    }
}
