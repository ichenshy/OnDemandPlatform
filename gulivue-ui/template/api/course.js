import request from "../utils/request";

export default {

  //查询课程列表
  getCourseFrontList(page, limit, searchObj) {
    return request({
      url: '/eduservice/course/getCourseFrontList/' + page + "/" + limit,
      method: 'post',
      data: searchObj
    })
  },
  //查询所有分类
  getAllsubject() {
    return request({
      url: '/eduservice/subject/getAllsubject',
      method: 'get'
    })
  },
  //课程详细
  getCourseFrontInfo(id) {
    return request({
      url: '/eduservice/course/getCourseFrontInfo/'+id,
      method: 'get'
    })
  },
}
