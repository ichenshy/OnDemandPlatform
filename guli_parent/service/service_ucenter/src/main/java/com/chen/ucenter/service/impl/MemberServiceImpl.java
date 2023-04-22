package com.chen.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.MD5;
import com.chen.servicebase.ExceptionHandler.ExceptionTest;
import com.chen.ucenter.entity.Member;
import com.chen.ucenter.entity.vo.RegisterVo;
import com.chen.ucenter.mapper.MemberMapper;
import com.chen.ucenter.service.MemberService;
import com.chen.ucenter.entity.vo.LoginVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册数据
        String code = registerVo.getCode();// 验证码
        String mobile = registerVo.getMobile();//手机号
        String nickname = registerVo.getNickname();//昵称
        String password = registerVo.getPassword();//密码
        //判空
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new ExceptionTest(20001, "信息不能为空...");
        }
        //验证码判断 取readis的验证码
        String movleCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(movleCode)) {
            throw new ExceptionTest(20001, "验证码不正确...");
        }
        //判断手机号是否重复，表里存在则不添加
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new ExceptionTest(20001, "该手机号已存在...");
        }
        //添加数据库
        Member member = new Member();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setIsDeleted(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

    //查询某一天的注册人数
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }

    //登录login
    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断手机号或密码为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new ExceptionTest(20001, "手机号或密码为空");
        }

        //判断手机号是否正确
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member loginMember = baseMapper.selectOne(wrapper);
        //判断密码是否正确
        if (!MD5.encrypt(password).equals(loginMember.getPassword())) {
            throw new ExceptionTest(20001, "密码不正确");
        }
        //判断查询对象为空
        if (loginMember == null) {
            throw new ExceptionTest(20001, "未找到此用户");
        }
        //判断用户是否被金禁用
        if (loginMember.getIsDisabled()) {
            throw new ExceptionTest(20001, "账号已被禁用,请联系管理员");
        }
        //登录成功
        return JwtUtils.getJwtToken(loginMember.getId(), loginMember.getNickname());
    }
}
