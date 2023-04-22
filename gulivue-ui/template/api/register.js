import request from "../utils/request";

export default {
  //获取验证码
  sendCode(phone) {
    return request({
      url: '/edumsm/msm/send/'+phone,
      method: 'get'
    })
  },
  //注册方法
  register(registerVo) {
    return request({
      url: '/ucenter/member/register',
      method: 'post',
      data: registerVo
    })
  }

}
