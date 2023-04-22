import request from '@/utils/request'

export default {
  // 课程信息添加
  addCourse(courseInfo) {
    return request({
      url: `/eduservice/edu-course/addCourse`,
      method: 'post',
      data: courseInfo
    })
  },
  findAll() {
    return request({
      url: `/eduservice/findAll`,
      method: 'get'
    })
  },
  getCourseInfo(courseId) {
    return request({
      url: '/eduservice/edu-course/getCourseInfo/' + courseId,
      method: 'get'
    })
  },
  updateCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/edu-course/updateCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  pushlish(id) {
    return request({
      url: '/eduservice/edu-course/pushlish/' + id,
      method: 'post'
    })
  },
  CoursePublish(id) {
    return request({
      url: '/eduservice/edu-course/CoursePublish/' + id,
      method: 'get'
    })
  },
  allList() {
    return request({
      url: '/eduservice/edu-course',
      method: 'get'
    })
  },
  // 获取分页列表
  pagePushlish(current, limit, courseQuery) {
    return request({
      url: `/eduservice/edu-course/pagePushlish/${current}/${limit}`,
      method: 'post',
      data: courseQuery
    })
  },
  // 删除课程
  deleCourse(id) {
    return request({
      url: '/eduservice/edu-course/' + id,
      method: 'delete'
    })
  }

}
