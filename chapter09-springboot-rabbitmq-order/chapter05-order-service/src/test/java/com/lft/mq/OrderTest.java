package com.lft.mq;

import com.lft.mq.entity.Order;
import com.lft.mq.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * Class Name:      OrderTest
 * Package Name:    com.lft.mq
 * <p>
 * Function: 		A {@code OrderTest} object With Some FUNCTION.
 * Date:            2021-05-27 14:27
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@SpringBootTest
public class OrderTest {
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void orderCreated() throws Exception {
        // 订单生成
        String orderId = "10000002";
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId("1002");
        order.setOrderContent("鸡爪五份");
        orderService.createOrder(order);
        System.out.println("订单创建成功");
    }
    
}
