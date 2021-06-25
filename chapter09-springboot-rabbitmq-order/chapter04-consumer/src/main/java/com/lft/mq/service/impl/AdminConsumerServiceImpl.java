package com.lft.mq.service.impl;

import com.lft.mq.service.AdminConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Class Name:      WeixinConsumerServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code WeixinConsumerServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 18:03
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
@RabbitListener (queues = {"admin.topic.queue"})
public class AdminConsumerServiceImpl implements AdminConsumerService {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("AdminConsumer 收到了 Admin 类型的角色信息：" + message);
    }
}
