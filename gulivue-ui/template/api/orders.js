import request from "../utils/request";
export default {
  //生成订单接口
  createOrder(courseId){
    return request({
      url: '/order/order/createOrder/'+courseId,
      method: 'post'
    })
  },
  //根据id查询订单接口
  getOrderInfo(orderId){
    return request({
      url: '/order/order/getOrderInfo/'+orderId,
      method: 'get'
    })
  },
  //生成二维码
  createNative(orderNo){
    return request({
      url: '/order/paylog/createNative/'+orderNo,
      method: 'get'
    })
  },
  //查询订单状态
  queryPayStatus(orderNo){
    return request({
      url: '/order/paylog/queryPayStatus/'+orderNo,
      method: 'get'
    })
  },
}
