import request from "../utils/request";
export default {
  frontIndex(){
    return request({
      url: '/eduservice/index/frontIndex',
      method: 'get'
    })
  }
}
