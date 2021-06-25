package com.lft.mq.service.impl;

import com.lft.mq.service.DevConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Class Name:      SmsConsumerServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code SmsConsumerServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 18:03
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
@RabbitListener (queues = {"dev.topic.queue"})
public class DevConsumerServiceImpl implements DevConsumerService {
    
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("DevConsumer 收到了 Dev 类型的角色信息：" + message);
    }
}
