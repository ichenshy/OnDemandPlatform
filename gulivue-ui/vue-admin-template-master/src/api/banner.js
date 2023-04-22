import request from '@/utils/request'

export default {
  // 获取分页列表
  pageBanner(current, limit) {
    return request({
      // url: `/educms/adminBanner/pageBanner/${current}/${limit}`,
      url: `/educms/adminBanner/pageBanner/${current}/${limit}`,
      method: 'get'
    })
  },

  // 增加banner
  addBanner(crmBanner) {
    return request({
      url: '/educms/adminBanner/addBanner',
      method: 'post',
      data: crmBanner
    })
  },
  // 删除banner
  deleteBanner(id) {
    return request({
      url: '/educms/adminBanner/remove/' + id,
      method: 'delete'
    })
  },
  // 根据id查询banner
  getBannerById(id) {
    return request({
      url: '/educms/adminBanner/getBannerById/' + id,
      method: 'get'
    })
  },
  // 修改banner
  updateBanner(crmBanner) {
    return request({
      url: '/educms/adminBanner/updateBanner/',
      method: 'put',
      data: crmBanner
    })
  }
}
