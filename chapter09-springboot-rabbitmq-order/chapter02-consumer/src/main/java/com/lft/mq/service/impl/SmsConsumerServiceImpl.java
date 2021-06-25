package com.lft.mq.service.impl;

import com.lft.mq.service.SmsConsumerService;
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
@RabbitListener (queues = {"sms.fanout.queue"})
public class SmsConsumerServiceImpl implements SmsConsumerService {
    
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("SmsConsumer 收到了订单信息：" + message);
    }
}
