import request from '@/utils/request'

export default {
  // 获取章节与小节方法
  getChapterVideo(courseId) {
    return request({
      url: '/eduservice/chapter/getChapterVideo/' + courseId,
      method: 'get'
    })
  },

  // 添加
  addChapterVideo(eduChapter) {
    return request({
      url: '/eduservice/chapter/addChapterVideo/',
      method: 'post',
      data: eduChapter
    })
  },

  // 删除

  deleteChapterVideoId(chapterId) {
    return request({
      url: '/eduservice/chapter/' + chapterId,
      method: 'delete'
    })
  },
  // 根据id获取
  getChapterVideoId(id) {
    return request({
      url: '/eduservice/chapter/getChapterVideoId/' + id,
      method: 'get'
    })
  },
  // 修改
  upDataChapterVideo(eduChapter) {
    return request({
      url: '/eduservice/chapter/upDataChapterVideo/',
      method: 'post',
      data: eduChapter
    })
  }
}
