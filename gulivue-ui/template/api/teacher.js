import request from "../utils/request";

export default {
  //登录
  getTeacherFrontList(page,limit) {
    return request({
      url: '/eduservice/teacher/getTeacherFrontList/'+page+"/"+limit,
      method: 'get'
    })
  },
  //查询讲师的详细信息以及课程
  getTeacherFrontInfo(id) {
    return request({
      url: '/eduservice/teacher/getTeacherFrontInfo/'+id,
      method: 'get'
    })
  },

}
