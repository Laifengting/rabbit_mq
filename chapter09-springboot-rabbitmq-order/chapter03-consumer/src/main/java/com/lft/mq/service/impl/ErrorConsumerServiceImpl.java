package com.lft.mq.service.impl;

import com.lft.mq.service.ErrorConsumerService;
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
@RabbitListener (queues = {"error.direct.queue"})
public class ErrorConsumerServiceImpl implements ErrorConsumerService {
    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("ErrorConsumer 收到了 error 类型的日志信息：" + message);
    }
}
