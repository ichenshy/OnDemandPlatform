package com.chen.educms.service.impl;

import com.chen.educms.entity.CrmBanner;
import com.chen.educms.mapper.CrmBannerMapper;
import com.chen.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-10
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

//    @Cacheable(key = "'selectIndexList'",value = "banner")
    //查询所有的Banner
    @Override
    public List<CrmBanner> selectGetAllBanner() {
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
