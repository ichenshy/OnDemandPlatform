package com.chen.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.Result;
import com.chen.commonutils.orderUtils.CourseWebVoOrder;
import com.chen.eduservice.client.OrdersClient;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.chapter.ChapterVo;
import com.chen.eduservice.entity.vo.frontVo.CourseFrontVo;
import com.chen.eduservice.entity.vo.frontVo.CourseWebVo;
import com.chen.eduservice.service.EduChapterService;
import com.chen.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1511:53
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrdersClient ordersClient;
    //条件 分页查询
    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String,Object>map = eduCourseService.getCourseFrontList(eduCoursePage,courseFrontVo);
        //返回分页所有数据
        return Result.ok().data(map);
    }

    //查询课程的详细信息
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public Result getCourseFrontInfo(@PathVariable String courseId , HttpServletRequest  request){
        //查询信息
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        //查询章节与小节
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoList(courseId);
        //根据课程id和用户id查询订单状态
        String jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if(jwtToken.equals("")){
            return Result.error().code(28004).messgae("请先登录");
        }
        boolean buyCourse = ordersClient.isBuyCourse(courseId,jwtToken);
        return Result.ok().data("courseWebVo",courseWebVo)
                            .data("chapterVideoList",chapterVideoList)
                .data("isbuyCourse",buyCourse);
    }
    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo baseCourseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
