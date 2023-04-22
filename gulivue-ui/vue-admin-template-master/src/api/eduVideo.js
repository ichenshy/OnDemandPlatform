import request from '@/utils/request'

export default {
  // 添加
  addVideo(eduVideo) {
    return request({
      url: '/eduservice/eduVideo/addVideo/',
      method: 'post',
      data: eduVideo
    })
  },

  // 删除
  deleteChapterVideoId(chapterId) {
    return request({
      url: '/eduservice/eduVideo/' + chapterId,
      method: 'delete'
    })
  },
  // 根据id获取
  getVideoId(id) {
    return request({
      url: '/eduservice/eduVideo/getVideoId/' + id,
      method: 'get'
    })
  },
  // 修改
  upDataVideo(eduVideo) {
    return request({
      url: '/eduservice/eduVideo/upDataVideo/',
      method: 'post',
      data: eduVideo
    })
  },
  // 删除
  removeAlyVideo(id) {
    return request({
      url: '/eduVod/video/removeAlyVideo/' + id,
      method: 'delete'
    })
  }
}
