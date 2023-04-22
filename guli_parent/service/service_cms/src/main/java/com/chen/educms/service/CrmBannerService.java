package com.chen.educms.service;

import com.chen.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-10
 */
public interface CrmBannerService extends IService<CrmBanner> {
    //查询所有的Banner
    List<CrmBanner> selectGetAllBanner();
}
