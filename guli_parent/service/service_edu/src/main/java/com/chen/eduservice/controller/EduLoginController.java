package com.chen.eduservice.controller;

import com.chen.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","admin");
    }
    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
