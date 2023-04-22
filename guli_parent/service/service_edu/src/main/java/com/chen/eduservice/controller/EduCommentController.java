package com.chen.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.Result;
import com.chen.eduservice.client.UcenterClient;
import com.chen.eduservice.entity.EduComment;
import com.chen.eduservice.service.EduCommentService;
import com.chen.ucenter.entity.Member;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-22
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;
    //分页 根据课程id查询评论
    @ApiOperation(value = "分页查询")
    //page当前页
    //limit每页记录数
    @GetMapping("/pageComment/{page}/{limit}")
    public Result pageComment(@PathVariable long page, @PathVariable long limit ,String courseId) {
        //创建对象
        Page<EduComment> commentPage = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        eduCommentService.page(commentPage,wrapper);
        List<EduComment> commentList = commentPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",commentList);
        map.put("current",commentPage.getCurrent());
        map.put("total",commentPage.getTotal());
        map.put("size",commentPage.getSize());
        map.put("pages",commentPage.getPages());
        map.put("hasNext",commentPage.hasNext());
        map.put("hasPrevious",commentPage.hasPrevious());
        return Result.ok().data(map);
    }
    @ApiOperation(value = "添加评论")
    @PostMapping("/addComment")
    public Result addComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        String jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(jwtToken)) {
            return Result.error().code(28004).messgae("请登录");
        }
        eduComment.setMemberId(jwtToken);
//        调用 登录 信息 在另一个模块中
        Member infoUc = ucenterClient.getInfoUc(jwtToken);
        eduComment.setNickname(infoUc.getNickname());
        eduComment.setAvatar(infoUc.getAvatar());
        eduCommentService.save(eduComment);
        return Result.ok();
    }

}

