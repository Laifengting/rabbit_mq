package com.lft.mq.controller;

import com.lft.mq.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class Name:      DispatcherController
 * Package Name:    com.lft.mq.controller
 * <p>
 * Function: 		A {@code DispatcherController} object With Some FUNCTION.
 * Date:            2021-05-27 13:49
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@RestController
@RequestMapping ("dispatcher")
public class DispatcherController {
    
    @Autowired
    private DispatcherService dispatcherService;
    
    @GetMapping ("order")
    public String lock(String orderId) throws Exception {
        // 模拟业务耗时，接口调用者会认为超时
        if (orderId.equals("10000002")) {
            Thread.sleep(3000L);
        }
        // 将外卖订单分配给小哥
        dispatcherService.dispatch(orderId);
        return "SUCCESS";
    }
}
