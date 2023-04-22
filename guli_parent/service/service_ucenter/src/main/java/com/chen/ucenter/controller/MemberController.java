package com.chen.ucenter.controller;


import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.Result;
import com.chen.commonutils.orderUtils.MemberOrder;
import com.chen.ucenter.entity.Member;
import com.chen.ucenter.entity.vo.LoginVo;
import com.chen.ucenter.entity.vo.RegisterVo;
import com.chen.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
@RestController
@RequestMapping("/ucenter/member")
//@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    //登录login
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return Result.ok().data("token", token);
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        String token = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(token);
        System.out.println(member);
        return Result.ok().data("member", member);
    }
    //根据用户id获取信息
    @PostMapping("/getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable String id){
        Member member = memberService.getById(id);
        MemberOrder memberOrder = new MemberOrder();
        BeanUtils.copyProperties(member,memberOrder);
        return memberOrder;
    }
    //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable String day){
        Integer count = memberService.countRegister(day);
        return Result.ok().data("countRegister",count);
    }
}

