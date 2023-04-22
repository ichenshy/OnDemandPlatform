package com.chen.ucenter.service;

import com.chen.ucenter.entity.Member;
import com.chen.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.ucenter.entity.vo.LoginVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
public interface MemberService extends IService<Member> {
    //登录login
    String login(LoginVo loginVo);

    //注册
    void register(RegisterVo registerVo);

    Member getByOpenid(String openid);
    //查询某一天的注册人数
    Integer countRegister(String day);
}
