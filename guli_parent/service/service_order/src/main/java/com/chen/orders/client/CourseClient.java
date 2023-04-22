package com.chen.orders.client;

import com.chen.commonutils.orderUtils.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author CSY
 * @version v1.0
 * @date 2022/2/4 15:17
 */
@Component
@FeignClient("service-edu")
public interface CourseClient {
    //根据课程id查询课程信息
    @PostMapping("/eduservice/course/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
