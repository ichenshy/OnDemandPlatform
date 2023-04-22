import request from '@/utils/request'

export default {
  // 获取分页列表
  pageTeachCondtion(current, limit, teacherQuery) {
    return request({
      url: `/eduservice/pageTeachCondtion/${current}/${limit}`,
      method: 'post',
      data: teacherQuery
    })
  },
  // 删除方法
  removeteach(id) {
    return request({
      url: `/eduservice/${id}`,
      method: 'delete'
    })
  },
  // 添加方法
  addTeacher(eduTeacher) {
    return request({
      url: `/eduservice/addTeacher/`,
      method: 'post',
      data: eduTeacher
    })
  },
  // 根据id获取
  getByid(id) {
    return request({
      url: `/eduservice/getid/${id}`,
      method: 'get'
    })
  },
  // 修改方法
  updateTeacher(eduTeacher) {
    return request({
      url: `/eduservice/updateTeacher`,
      method: 'post',
      data: eduTeacher
    })
  }
}
