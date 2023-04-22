package com.chen.educms.controller;

import com.chen.commonutils.Result;
import com.chen.educms.entity.CrmBanner;
import com.chen.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端前台控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/educms/FrontBanner")
//@CrossOrigin
public class BannerFrontController {
    @Autowired
    CrmBannerService bannerService;

    //查询所有的Banner
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
       List<CrmBanner> list = bannerService.selectGetAllBanner();
        return Result.ok().data("list",list);
    }

}