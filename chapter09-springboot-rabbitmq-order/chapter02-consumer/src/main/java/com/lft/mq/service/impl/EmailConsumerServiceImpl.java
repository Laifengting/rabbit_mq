package com.lft.mq.service.impl;

import com.lft.mq.service.EmailConsumerService;
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
@RabbitListener (queues = {"email.fanout.queue"})
public class EmailConsumerServiceImpl implements EmailConsumerService {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("EmailConsumer 收到了订单信息：" + message);
    }
}
