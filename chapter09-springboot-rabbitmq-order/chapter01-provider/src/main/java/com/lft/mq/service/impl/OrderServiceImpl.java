package com.lft.mq.service.impl;

import com.lft.mq.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Class Name:      OrderServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code OrderServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 17:29
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 模拟用户下单
     * @param uid       用户id
     * @param productId 商品id
     * @param num       商品数量
     */
    @Override
    public void createOrder(String uid, String productId, Integer num) {
        // 1. 根据商品 id 查询库存是否充足
        
        // 2. 保存订单
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("订单创建成功：" + orderId);
        // 3. 通过 MQ 来完成消息的分发
        // 参数1：交换机名。    参数2：路由key / 队列名    参数3：消息内容
        String exchangeName = "fanout_order_exchange";
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }
}
