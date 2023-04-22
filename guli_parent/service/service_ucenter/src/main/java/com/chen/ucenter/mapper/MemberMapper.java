package com.chen.ucenter.mapper;

import com.chen.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
public interface MemberMapper extends BaseMapper<Member> {
    //查询某一天的注册人数
    Integer countRegister(String day);
}
