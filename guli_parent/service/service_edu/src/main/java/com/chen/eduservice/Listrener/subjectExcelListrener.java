package com.chen.eduservice.Listrener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.eduservice.entity.EduSubject;
import com.chen.eduservice.entity.excel.SubjectExel;
import com.chen.eduservice.service.EduSubjectService;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;

public class subjectExcelListrener extends AnalysisEventListener<SubjectExel> {

    public EduSubjectService eduSubjectService;

    public subjectExcelListrener() {}


    public subjectExcelListrener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;

    }


    @Override
    public void invoke(SubjectExel subjectExel, AnalysisContext analysisContext) {
        //添加一级分类
        if (subjectExel == null) {
            throw new ExceptionTest(20001, "文件数据为空");
        }
        EduSubject oneSubject = this.OneSubject(subjectExel.getOneSubjectName(), eduSubjectService);
        if (oneSubject == null) {
            //没有相同，进行添加
            oneSubject  = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectExel.getOneSubjectName());//一级分类名称
            eduSubjectService.save(oneSubject);
        }
        //添加二级分类
        String pid = oneSubject.getId();
        EduSubject twoSubject = this.twoSubject(subjectExel.getTwoSubjectName(), eduSubjectService, pid);
        if (twoSubject == null) {
            twoSubject  = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectExel.getTwoSubjectName());//一级分类名称
            eduSubjectService.save(twoSubject);
        }
    }

    //判断一级分类
    private EduSubject OneSubject(String name, EduSubjectService eduSubjectService) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }

    //判断二级分类
    private EduSubject twoSubject(String name, EduSubjectService eduSubjectService, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject two = eduSubjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
