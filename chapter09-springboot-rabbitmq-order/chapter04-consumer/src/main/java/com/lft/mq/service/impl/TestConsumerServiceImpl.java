package com.lft.mq.service.impl;

import com.lft.mq.service.TestConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Class Name:      EmailConsumerServiceImpl
 * Package Name:    com.lft.mq.service.impl
 * <p>
 * Function: 		A {@code EmailConsumerServiceImpl} object With Some FUNCTION.
 * Date:            2021-05-26 18:02
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Service
@RabbitListener (queues = {"test.topic.queue"})
public class TestConsumerServiceImpl implements TestConsumerService {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("TestConsumer 收到了 Test 类型的角色信息：" + message);
    }
}
