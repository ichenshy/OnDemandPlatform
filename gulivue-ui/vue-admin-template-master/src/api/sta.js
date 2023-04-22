import request from '@/utils/request'

export default {
  registerCount(day) {
    return request({
      url: '/staservice/sta/registerCount/' + day,
      method: 'post'
    })
  },
  showData(searchObj) {
    return request({
      // url: '/staservice/sta/showData/'+searchObj.type+""+
      url: `/staservice/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'get'
    })
  }
}
