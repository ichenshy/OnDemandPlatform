package com.chen.orders.service.impl;

import com.chen.commonutils.orderUtils.CourseWebVoOrder;
import com.chen.commonutils.orderUtils.MemberOrder;
import com.chen.orders.client.CourseClient;
import com.chen.orders.client.UcenterClient;
import com.chen.orders.entity.Order;
import com.chen.orders.mapper.OrderMapper;
import com.chen.orders.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.orders.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UcenterClient ucenterClient;

    //生成订单号
    @Override
    public String createOrder(String courseId, String jwtToken) {
        //课程信息 ---远程调用
        CourseWebVoOrder courseInfoOrder = courseClient.getCourseInfoOrder(courseId);
        //用户信息 ---远程调用
        MemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(jwtToken);
        //创建Order对象 ,set相应的数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);//课程id
        order.setCourseTitle(courseInfoOrder.getTitle());//课程标题
        order.setCourseCover(courseInfoOrder.getCover());//课程封面
        order.setTeacherName(courseInfoOrder.getTeacherName());//讲师姓名
        order.setTotalFee(courseInfoOrder.getPrice());//课时数
        order.setMemberId(jwtToken);//用户id
        order.setMobile(userInfoOrder.getMobile());//用户手机
        order.setNickname(userInfoOrder.getNickname());//用户姓名
        order.setStatus(0);//订单状态（0：未支付 1：已支付）
        order.setPayType(1);//支付类型（1：微信 2：支付宝）
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
