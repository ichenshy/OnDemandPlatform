package com.chen.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.Result;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.entity.vo.TeacherQuery;
import com.chen.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/eduservice")
@Api(tags = "讲师管理")
//@CrossOrigin
public class EduTeacherController {
    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //查询讲师所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public Result findAll() {
//        1.调用service方法查询
        List<EduTeacher> list = teacherService.list(null);
        return Result.ok().data("item", list);
    }

    //2.逻辑删除讲师的方法
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    @ApiParam(name = "id", value = "讲师ID", required = true)
    public Result removeteach(@PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //3、分页功能
    @ApiOperation(value = "分页查询")
    //current当前页
    //limit每页记录数
    @GetMapping("/pageTeach/{current}/{limit}")
    public Result pageTeach(@PathVariable long current, @PathVariable long limit) {
        //创建对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用方法 wrapper：条件
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();//记录数
        List<EduTeacher> recode = pageTeacher.getRecords();//数据list集合
        //第一种方法
//        Map map = new HashMap();
//        map.put("total", total);
//        map.put("rows", recode);
//        return Result.ok().data(map);
        //第二种方法
        return Result.ok().data("total", total).data("rows", recode);
    }

    //    4、条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("/pageTeachCondtion/{current}/{limit}")
    public Result pageTeachCondtion(@PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询、动态sql、获取值
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空接拼条件
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (null != level) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法
        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();//记录数
        List<EduTeacher> recode = pageTeacher.getRecords();//数据list集合
        return Result.ok().data("total", total).data("rows", recode);
    }

    //4、分页讲师列表（为解决方法）
//    @ApiOperation(value = "分页讲师列表")
//    @GetMapping("{page}/{limit}")
//    public Result pageQuery(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
//                            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
//                            @ApiParam(name = "teacherQuery", value = "查询对象", required = false) TeacherQuery teacherQuery) {
//        Page<EduTeacher> pageParm = new Page<>(page, limit);
//        teacherService.pageQuery(pageParm, teacherQuery);
//        List<EduTeacher> records = pageParm.getRecords();
//        long total = pageParm.getTotal();
//        return Result.ok().data("total", total).data("rows", records);
//    }

    //5、添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public Result addTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                             @RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //6、按照id查询
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping({"/getid/{id}"})
    public Result getByid(@ApiParam(name = "id", value = "讲师ID", required = true)
                          @PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return Result.ok().data("item", teacher);
    }

    //7、根据id修改
    @ApiOperation(value = "根据id修改讲师")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

