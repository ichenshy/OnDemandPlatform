import request from "../utils/request";

export default {
  getPageList(page, limit, courseId) {
    return request({
      url: '/eduservice/comment/pageComment/' + page + "/" + limit,
      method: 'get',
      params: {courseId}
    })
  },

  addComment(eduComment) {
    return request({
      url: '/eduservice/comment/addComment',
      method: 'post',
      data: eduComment
    })
  },

}
