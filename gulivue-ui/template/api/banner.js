import request from "../utils/request";
export default {
  getAllBanner() {
    return request({
      url: '/educms/FrontBanner/getAllBanner',
      method: 'get'
    })
  }
}
