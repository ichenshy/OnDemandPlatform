package com.chen.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.Result;
import com.chen.educms.entity.CrmBanner;
import com.chen.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 首页banner表 前端后台控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/educms/adminBanner")
//@CrossOrigin
public class BannerAdminController {
    @Autowired
    CrmBannerService bannerService;

    //banner后台分页查询、添加、修改、删除接口
    @GetMapping("pageBanner/{current}/{limit}")
    public Result pageBanner(@PathVariable long current, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(current, limit);
        bannerService.page(pageBanner, null);
        long total = pageBanner.getRecords().size();
        List<CrmBanner> recode = pageBanner.getRecords();//数据list集合
        return Result.ok().data("rows", recode).data("total", total);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner) {
        String uuid = UUID.randomUUID().toString();
        String id = uuid.replace("-", "");
        crmBanner.setId(id);
        bannerService.save(crmBanner);
        return Result.ok();
    }
    @CacheEvict(value = "banner", allEntries=true)
    @DeleteMapping({"remove/{id}"})
    public Result deleteBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return Result.ok();
    }

    @GetMapping("getBannerById/{id}")
    public Result getBannerById(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("item", banner);
    }
    @CacheEvict(value = "banner", allEntries=true)
    @PutMapping("updateBanner")
    public Result updateBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return Result.ok();
    }


}

